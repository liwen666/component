package vip.dcpay.validate.sdk.bean.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: liq
 * @Date: 2019/6/19 21:21
 * @Description:
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviterInfo implements Serializable {

    private String invitationCode;

    private Long userId;

    private Integer userType;

}
