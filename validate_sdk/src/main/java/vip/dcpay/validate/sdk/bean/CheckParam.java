package vip.dcpay.validate.sdk.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @Auther: liq
 * @Date: 2019/6/18 20:52
 * @Description:
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckParam implements Serializable {

    private String verifyCode;

    /**
     * 业务校验传递参数
     */
    Map<String, String> param;

}
