package vip.dcpay.validate.sdk.bean.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vip.dcpay.validate.sdk.enums.ValidateItemTypeEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

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
public class SendRecord implements Serializable {

    @Builder.Default
    private String uid = UUID.randomUUID().toString().replaceAll("-", "");

    private ValidateItemTypeEnum validateItemType;

    private String receiver;

    private String verifyCode;

    @Builder.Default
    private Date createTime = new Date();

    @Builder.Default
    private Integer errorNum = 0;

    @Builder.Default
    private Boolean isValid = true;

}
