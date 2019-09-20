package vip.dcpay.validate.sdk.bean.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vip.dcpay.validate.sdk.bean.CheckParam;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: liq
 * @Date: 2019/6/19 10:33
 * @Description:
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckRecord implements Serializable {

    private ValidateItemTypeEnum validateItemType;

    private CheckParam param;

    @Builder.Default
    private Date createTime = new Date();

    @Builder.Default
    private Boolean result = true;

    private String remark;

}
