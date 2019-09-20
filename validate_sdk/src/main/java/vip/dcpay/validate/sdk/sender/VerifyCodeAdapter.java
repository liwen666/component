package vip.dcpay.validate.sdk.sender;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import vip.dcpay.notify.sdk.MyNotifyManager;
import vip.dcpay.notify.sdk.enums.NotifyTypeEnum;
import vip.dcpay.validate.sdk.bean.record.SendRecord;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/19 14:24
 * @Description:
 */
@Component
public class VerifyCodeAdapter {

    private AdapterSendRecordCollection recordCollection = new AdapterSendRecordCollection();
    private String[] area = new String[]{"+86"};

    /**
     * 发送验证码
     *
     * @param validateType
     * @param receiver
     * @param verifyCode
     * @return
     */
    public Result send(ValidateItemTypeEnum validateType, String receiver, String templateCode, String verifyCode) {

        // 发送频率校验
        if (recordCollection.checkSendFrequency(receiver)) {
            return Result.error("发送太频繁。validateItemType：" + validateType + "|receiver:" + receiver);
        }

        // 每日最大发送条数校验
        if (recordCollection.checkOnedayMaxNum(receiver)) {
            return Result.error("超出当日最大允许发送条数。validateItemType：" + validateType + "|receiver:" + receiver);
        }

        Result sendResult = sendVerifyCode(validateType, receiver, templateCode, verifyCode);
        if (null == sendResult || !sendResult.getSuccess()) {
            return sendResult;
        }

        // 缓存发送记录
        recordCollection.add(SendRecord.builder()
                .receiver(receiver)
                .verifyCode(verifyCode)
                .validateItemType(validateType)
                .build());

        return Result.success();
    }

    /**
     * 发送验证码
     *
     * @param validateType
     * @param receiver
     * @param verifyCode
     * @return
     */
    private Result sendVerifyCode(ValidateItemTypeEnum validateType, String receiver, String templateCode, String verifyCode) {
        Map<String, String> notifyArgs = new HashMap<>();
        notifyArgs.put("code", verifyCode);

        String template = "sms_take_phone";

        NotifyTypeEnum notifyType;
        String target;
        String targetEx = null;

        if (ValidateItemTypeEnum.SMS.equals(validateType)) {
            notifyType = NotifyTypeEnum.SMS;
            template = "sms_take_phone";

            String[] arr = receiver.split(" ");
            target = arr[1];
            targetEx = arr[0];


        } else {
            notifyType = NotifyTypeEnum.MAIL;
            template = "mail_verify_code";

            target = receiver;
        }

        if (StringUtils.isNoneBlank(templateCode)) {
            template = templateCode;
        }

        if (StringUtils.isNoneBlank(targetEx)
                && !Arrays.asList(area).contains(targetEx)
                && !StringUtils.endsWith(template, "_en")) {
            template = template + "_en";
        }

        Result result = MyNotifyManager.sendMsg(notifyType, target, targetEx, template, notifyArgs);
        return JSON.parseObject(JSON.toJSONString(result), Result.class);
    }

}
