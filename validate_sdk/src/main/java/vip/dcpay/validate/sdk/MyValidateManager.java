package vip.dcpay.validate.sdk;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import vip.dcpay.dto.account.AccountInfo;
import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.util.frame.spring.AnnotationBeanNameGeneratorRewrite;
import vip.dcpay.validate.sdk.bean.VerifyTypeResponse;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.validate.sdk.manage.UserPermissionCenter;
import vip.dcpay.vo.basic.Result;

import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/21 17:27
 * @Description:
 */
@ComponentScan(nameGenerator = AnnotationBeanNameGeneratorRewrite.class, basePackages = {"vip.dcpay.validate.sdk", "vip.dcpay.notify.sdk"})
@Service
public class MyValidateManager {

    private volatile boolean isInit = false;

    @Autowired
    private UserPermissionCenter permissionCenter;

    private SystemConfig systemConfig;

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    public boolean isInit() {
        return isInit;
    }

    /**
     * 初始化
     *
     * @param systemConfig
     */
    public Result init(SystemConfig systemConfig) {
        MyLogManager.develop("》》》》》执行 Validate 初始化systemConfig：" + JSON.toJSONString(systemConfig));
        this.systemConfig = systemConfig;

        isInit = true;
        return Result.success();
    }

    /**
     * 获取校验项
     *
     * @param businessType
     * @param userConfig
     * @return
     */
    public Result<VerifyTypeResponse> getValidateItems(BusinessTypeEnum businessType, UserConfig userConfig) {
        String randomStr = RandomStringUtils.random(4, false, true);
        MyLogManager.info("【" + randomStr + "】获取 Validate校验项 入参：businessType -> " + businessType + "|userConfig -> " + JSON.toJSONString(userConfig));

        Result<VerifyTypeResponse> result;
        if (null == systemConfig) {
            result = Result.error("请先初始化，设置systemConfig。");
            MyLogManager.error("【" + randomStr + "】获取 Validate校验项 结果：" + JSON.toJSONString(result));
            return result;
        }

        if (null == businessType) {
            result = Result.error("businessType 不能为空");
            MyLogManager.error("【" + randomStr + "】获取 Validate校验项 结果：" + JSON.toJSONString(result));
            return result;
        }

        result = permissionCenter.getValidateItems(businessType, systemConfig, userConfig);
        MyLogManager.info("【" + randomStr + "】获取 Validate校验项 结果：" + JSON.toJSONString(result));
        return result;
    }

    /**
     * 发送验证码
     *
     * @param validateToken
     * @param validateType
     * @param receiver
     * @return
     */
    public Result sendVerifyCode(String validateToken, ValidateItemTypeEnum validateType, String receiver) {
        String templateCode = null;
        switch (validateType) {
            case SMS:
                templateCode = systemConfig.getSmsTemplateCode();
                break;
            case MAIL:
                templateCode = systemConfig.getMailTemplateCode();
                break;
        }
        return sendVerifyCode(validateToken, validateType, receiver, templateCode);
    }

    /**
     * 发送验证码
     *
     * @param validateToken
     * @param validateType
     * @param receiver
     * @param templateCode
     * @return
     */
    public Result sendVerifyCode(String validateToken, ValidateItemTypeEnum validateType, String receiver, String templateCode) {
        String randomStr = getRandomString();
        MyLogManager.info("【" + randomStr + "】Validate发送验证码 入参：validateToken -> " + validateToken + "|validateType -> " + validateType + "|receiver -> " + receiver);

        Result result;
        if (null == systemConfig) {
            result = Result.error("请先初始化，设置systemConfig。");
            MyLogManager.error("【" + randomStr + "】Validate发送验证码 结果：" + JSON.toJSONString(result));
            return result;
        }

        if (null == validateType) {
            result = Result.error("不支持的校验类型。validateType：" + validateType);
            MyLogManager.error("【" + randomStr + "】Validate发送验证码 结果：" + JSON.toJSONString(result));
            return result;
        }

        if (!ValidateItemTypeEnum.SMS.equals(validateType) && !ValidateItemTypeEnum.MAIL.equals(validateType)) {
            result = Result.error("不支持的校验类型。validateType：" + validateType);
            MyLogManager.error("【" + randomStr + "】Validate发送验证码 结果：" + JSON.toJSONString(result));
            return result;
        }

        result = permissionCenter.sendVerifyCode(validateToken, validateType, receiver, templateCode);
        MyLogManager.info("【" + randomStr + "】Validate发送验证码 结果：" + JSON.toJSONString(result));
        return result;
    }

    /**
     * 设置校验结果
     *
     * @param validateToken
     * @param verifyCodes
     * @return
     */
    public Result setValidateItems(String validateToken, Map<String, String> verifyCodes) {
        String randomStr = getRandomString();
        MyLogManager.info("【" + randomStr + "】validate设置校验结果 入参：validateToken -> " + validateToken + "|verifyCodes -> " + JSON.toJSONString(verifyCodes));

        Result result;
        if (null == systemConfig) {
            result = Result.error("请先初始化，设置systemConfig。");
            MyLogManager.error("【" + randomStr + "】validate设置校验结果 结果：" + JSON.toJSONString(result));
            return result;
        }

        result = permissionCenter.setValidateItems(validateToken, verifyCodes);
        MyLogManager.info("【" + randomStr + "】validate设置校验结果 结果：" + JSON.toJSONString(result));
        return result;
    }

    /**
     * 提交校验结果
     *
     * @param validateToken
     * @return
     */
    public Result submitValidate(String validateToken) {
        String randomStr = getRandomString();
        MyLogManager.info("【" + randomStr + "】validate提交校验结果 入参：validateToken -> " + validateToken);

        Result result;
        if (null == systemConfig) {
            result = Result.error("请先初始化，设置systemConfig。");
            MyLogManager.error("【" + randomStr + "】validate提交校验结果 结果：" + JSON.toJSONString(result));
            return result;
        }

        result = permissionCenter.submitValidate(validateToken);
        MyLogManager.info("【" + randomStr + "】validate提交校验结果 结果：" + JSON.toJSONString(result));
        return result;
    }

    /**
     * 获取token关联账户
     *
     * @param token
     * @return
     */
    public Result<AccountInfo> getAccountInfo(String token) {
        String randomStr = getRandomString();
        MyLogManager.info("【" + randomStr + "】获取token关联账户 入参：validateToken -> " + token);

        Result<AccountInfo> result = permissionCenter.getAccountInfo(token);
        MyLogManager.info("【" + randomStr + "】获取token关联账户 结果：" + JSON.toJSONString(result));
        return result;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    // 获取随机数
    private String getRandomString() {
        return RandomStringUtils.random(4, false, true);
    }

}
