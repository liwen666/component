package vip.dcpay.validate.sdk.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/17 19:16
 * @Description:
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateInfo implements Serializable {

    private String token;

    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType;

    /**
     * 校验项
     */
    private String validateItems;

    /**
     * 校验结果
     */
    @Builder.Default
    private Map<Integer, Integer> validateItemCheckedFlags = new HashMap<>();

    @Builder.Default
    private Date createTime = new Date();

    private Date deadline;

    private UserConfig userConfig;

}
