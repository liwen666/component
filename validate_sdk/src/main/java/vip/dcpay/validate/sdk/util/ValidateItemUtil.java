package vip.dcpay.validate.sdk.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.validate.sdk.enums.ReturnCodeEnum;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidateItemUtil {

    //左补齐 左补0,左高右低
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);//左补0,高位补齐
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }


    public static Result<String> getValidateItems(String baseItems, String selfItem) {

        StringBuilder okResultItems = new StringBuilder();
        StringBuilder errorResultItems = new StringBuilder();

        //baseItems 与selfItems 做与交集，获取最终需要验证的项
        Integer nSelfItem = Integer.valueOf(selfItem, 2);
        String[] baseItemArray = baseItems.split(",");
        for (String baseItem : baseItemArray) {
            Integer nBaseItem = Integer.valueOf(baseItem, 2);

            Integer tempItem = nSelfItem & nBaseItem; //获取并集

            //并集与基础配置相同则表明满足条件
            if (tempItem.compareTo(nBaseItem) == 0) {
                if (okResultItems.length() > 0) {
                    okResultItems.append(",");
                }
                okResultItems.append(baseItem);

            } else {//不满足条件需要知道是哪些不满足

                Integer tempSelfItem = ~nSelfItem;//取反
                Integer nNeedBindItem = nBaseItem & tempSelfItem;//获取并集，未不满足的项

                //左补齐，高位补0
                String needBindItem = ValidateItemUtil.addZeroForNum(Integer.toString(nNeedBindItem, 2), baseItem.length());//10进制转换为2进制。

                if (errorResultItems.length() > 0) {
                    errorResultItems.append(",");
                }
                errorResultItems.append(needBindItem);
            }
        }
        //如果为0 表明没有满足的，需要给提示
        if (okResultItems.length() > 0) {
            return Result.success(ReturnCodeEnum.SUCCEED.code(), ReturnCodeEnum.SUCCEED.desc(), ValidateItemUtil.addZeroForNum(okResultItems.toString(), ValidateItemTypeEnum.values().length));

        } else {
            return Result.error(ReturnCodeEnum.VALIDATE_ITEM_NOT_FIT.code(), ReturnCodeEnum.VALIDATE_ITEM_NOT_FIT.desc() + "。" + errorResultItems.toString());
        }
    }


    //index的位置从右向左开始算
    public static boolean checkValidateItems(String baseItems, Map<Integer, Integer> validateItemFlags) {

        String[] baseItemArray = baseItems.split(",");
        for (String baseItem : baseItemArray) {
            boolean bRet = true;

            for (int i = 0; i < baseItem.length(); i++) {

                int index = baseItem.length() - 1 - i;
                if (baseItem.charAt(index) == '1') {

                    if (!validateItemFlags.containsKey(i) || validateItemFlags.get(i).equals(0)) {
                        bRet = false;
                        break;
                    }
                }
            }
            if (bRet) {
                return bRet;
            }
        }

        return false;
    }

    public static String builderValidateItem(Map<Integer, String> itemMap) {

        int size = ValidateItemTypeEnum.values().length;
        String selfItem = addZeroForNum("", size);
        StringBuffer buffer = new StringBuffer(selfItem);

        for (Map.Entry<Integer, String> entity : itemMap.entrySet()) {
            int index = size - 1 - entity.getKey();
            buffer.replace(index, index + 1, entity.getValue());
        }

        return buffer.toString();
    }

    public static boolean checkBaseValidateItems(String baseValidateItems) {

        String[] baseValidateList = baseValidateItems.split(",");

        for (String baseValidate : baseValidateList) {
            if (baseValidate.length() != ValidateItemTypeEnum.values().length) {
                return false;
            }
        }

        return true;
    }

    public static JSONObject convertItem(String validateItem) {
        if (StringUtils.isBlank(validateItem)) {
            return null;
        }

        JSONObject obj = new JSONObject();
        for (int i = 0; i < validateItem.length(); i++) {

            int index = validateItem.length() - 1 - i;

            ValidateItemTypeEnum validateType = ValidateItemTypeEnum.getEnum(i);
            if (null == validateType) {
                MyLogManager.error("validateItem 位数不正确。validateItem：" + validateItem);
                return null;
            }

            obj.put(validateType.name(), validateItem.charAt(index));
        }

        return obj;
    }

    public static List<JSONObject> convertItems(String validateItems) {
        if (StringUtils.isBlank(validateItems)) {
            return null;
        }

        String[] itemArr = validateItems.split(",");
        List<JSONObject> list = new ArrayList<>();

        for (String item : itemArr) {
            JSONObject obj = convertItem(item);
            if (null != obj) {
                list.add(obj);
            }
        }

        return list;
    }

}
