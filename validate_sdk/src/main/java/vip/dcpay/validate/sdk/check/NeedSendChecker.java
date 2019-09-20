package vip.dcpay.validate.sdk.check;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import vip.dcpay.util.frame.spring.SpringContextUtil;
import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.bean.record.CheckRecord;
import vip.dcpay.validate.sdk.bean.record.SendRecord;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;
import vip.dcpay.validate.sdk.sender.VerifyCodeAdapter;
import vip.dcpay.vo.basic.Result;

import java.util.Date;

/**
 * @Auther: liq
 * @Date: 2019/6/18 20:47
 * @Description:
 */
public abstract class NeedSendChecker extends BaseChecker {

    /**
     * 验证码长度
     */
    protected int CODE_LEN = 6;

    /**
     * 验证码发送最小间隔(秒)
     */
    protected int INTERVAL = 60;//60 * 2;

    /**
     * 验证码失效时间
     */
    protected int EXPIRE = 60 * 2;

    private VerifyCodeAdapter adapter;

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    public NeedSendChecker(ValidateItemTypeEnum validateItemType, SystemConfig systemConfig, UserConfig userConfig) {
        super(validateItemType, systemConfig, userConfig);

        adapter = SpringContextUtil.getBean(VerifyCodeAdapter.class);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    @Override
    public Result sendVerifyCode(String receiver, String templateCode) {

        // 校验发送人格式
        if (!checkReceiver(receiver)) {
            return Result.error("发送人格式不正确。receiver:" + receiver);
        }

        // 是否满足发送间隔（按token统计）
        if (checkSendFrequency()) {
            return Result.error("发送太频繁。validateItemType：" + validateItemType + "|receiver:" + receiver);
        }

        // 生成验证码
        String verifyCode = RandomStringUtils.random(CODE_LEN, false, true);

        // 发送验证码
        Result sendResult = adapter.send(validateItemType, receiver, templateCode, verifyCode);
        if (null == sendResult || !sendResult.getSuccess()) {
            return sendResult;
        }

        // 保存发送记录
        sendRecordCollection.add(SendRecord.builder()
                .validateItemType(validateItemType)
                .receiver(receiver)
                .verifyCode(verifyCode)
                .createTime(new Date())
                .build());

        return Result.success();
    }

    @Override
    public Result checkVerifyCode(CheckParam param) {
        CheckRecord checkRecord = CheckRecord.builder()
                .validateItemType(validateItemType)
                .param(param)
                .createTime(new Date())
                .result(true)
                .build();

        String errorMsg;

        // 获取最后一次发送记录
        SendRecord record = sendRecordCollection.last();
        if (null == record) {
            errorMsg = "请重新获取验证码";

            // 保存校验记录
            checkRecord.setResult(false);
            checkRecord.setRemark(errorMsg);
            checkRecordCollection.add(checkRecord);

            return Result.error(errorMsg);
        }

        // 验证码是否失效
        if (checkCodeExpire(record)) {
            errorMsg = "验证码失效";

            // 保存校验记录
            checkRecord.setResult(false);
            checkRecord.setRemark(errorMsg);
            checkRecordCollection.add(checkRecord);

            return Result.error(errorMsg);
        }

        // 验证码超限
        if (record.getErrorNum() >= CHECK_ERROR_NUM) {
            errorMsg = "验证码错误次数超限";

            // 保存校验记录
            checkRecord.setResult(false);
            checkRecord.setRemark(errorMsg);
            checkRecordCollection.add(checkRecord);

            return Result.error(errorMsg);
        }

        // 验证码错误
        if (!checkVeryCode(record, param)) {
            errorMsg = "验证码错误";

            // 保存校验记录
            checkRecord.setResult(false);
            checkRecord.setRemark(errorMsg);
            checkRecordCollection.add(checkRecord);

            return Result.error(errorMsg);
        }

        // 保存校验记录
        checkRecordCollection.add(checkRecord);

        return Result.success();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    /**
     * 校验发送人格式
     *
     * @param receiver
     * @return
     */
    protected abstract boolean checkReceiver(String receiver);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    /**
     * 是否发送太频繁
     *
     * @return
     */
    private boolean checkSendFrequency() {
        // 获取最后一条发送记录
        SendRecord record = sendRecordCollection.last();
        if (null == record) {
            return false;
        }

        Date curDate = new Date();
        long intervalTime = curDate.getTime() - record.getCreateTime().getTime() - INTERVAL * 1000;

        return intervalTime < 0;
    }

    /**
     * 验证码是否失效
     *
     * @return
     */
    private boolean checkCodeExpire(SendRecord record) {
        if (!record.getIsValid()) {
            return true;
        }

        Date curDate = new Date();
        long expireTime = curDate.getTime() - record.getCreateTime().getTime() - EXPIRE * 1000;

        return expireTime > 0;
    }

    /**
     * 比对验证码
     *
     * @param record
     * @param param
     * @return
     */
    private boolean checkVeryCode(SendRecord record, CheckParam param) {
        if (null == param || StringUtils.isBlank(param.getVerifyCode())) {
            record.setErrorNum(record.getErrorNum() + 1);
            return false;
        }

        if (!record.getVerifyCode().equals(param.getVerifyCode())) {
            record.setErrorNum(record.getErrorNum() + 1);
            return false;
        }

        return true;
    }

}
