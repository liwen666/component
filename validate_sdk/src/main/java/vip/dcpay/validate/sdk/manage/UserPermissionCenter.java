package vip.dcpay.validate.sdk.manage;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vip.dcpay.dto.account.AccountInfo;
import vip.dcpay.validate.sdk.bean.VerifyTypeResponse;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.demand.BaseDemand;
import vip.dcpay.validate.sdk.demand.DemandCollection;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;
import vip.dcpay.validate.sdk.enums.ReturnCodeEnum;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.vo.basic.Result;

import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/15 17:27
 * @Description:
 */
@Configuration
@EnableScheduling
@Service
public class UserPermissionCenter {

    private DemandCollection demandCollection = DemandCollection.getInstance();

    /**
     * 获取校验内容
     *
     * @param businessType
     * @param systemConfig
     * @param userConfig
     * @return
     */
    public Result<VerifyTypeResponse> getValidateItems(BusinessTypeEnum businessType, SystemConfig systemConfig, UserConfig userConfig) {

        BaseDemand demand = demandCollection.createDemand(businessType, systemConfig, userConfig);

        Result<VerifyTypeResponse> result = demand.getValidateItems();
        if (result.getSuccess()) {
            // 获取成功，保存demand
            demandCollection.saveDemand(result.getData().getValidateToken(), demand);
        } else if (result.getCode() == ReturnCodeEnum.TIMEOUT.code()) {
            // 超时清理
            demandCollection.clean(result.getData().getValidateToken());
        }

        return result;
    }

    /**
     * 发送验证码
     *
     * @param validateToken
     * @param validateItemType
     * @param receiver
     * @return
     */
    public Result sendVerifyCode(String validateToken, ValidateItemTypeEnum validateItemType, String receiver, String templateCode) {

        BaseDemand demand = demandCollection.getDemand(validateToken);
        if (null == demand) {
            return Result.error("请先获取校验内容");
        }

        return demand.sendVerifyCode(validateItemType, receiver, templateCode);
    }

    /**
     * 设置单项或多项校验结果
     *
     * @param validateToken
     * @param verifyCodes
     * @return
     */
    public Result setValidateItems(String validateToken, Map<String, String> verifyCodes) {

        BaseDemand demand = demandCollection.getDemand(validateToken);
        if (null == demand) {
            return Result.error("请先获取校验内容");
        }

        return demand.setValidateItems(verifyCodes);
    }

    /**
     * 提交校验结果
     *
     * @param validateToken
     * @return
     */
    public Result submitValidate(String validateToken) {

        BaseDemand demand = demandCollection.getDemand(validateToken);
        if (null == demand) {
            return Result.error("请先获取校验内容");
        }

        return demand.submitValidate();
    }

    /**
     * 清理失效token
     *
     * @return
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void cleanAbandonToken() {
        demandCollection.cleanAbandonToken();
    }

    /**
     * 获取token关联账号
     *
     * @param token
     * @return
     */
    public Result<AccountInfo> getAccountInfo(String token) {

        BaseDemand demand = demandCollection.getDemand(token);
        if (null == demand) {
            return Result.error("请先获取校验内容");
        }

        return Result.success(demand.getAccountInfo());
    }

}
