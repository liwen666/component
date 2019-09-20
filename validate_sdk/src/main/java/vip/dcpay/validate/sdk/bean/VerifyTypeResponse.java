package vip.dcpay.validate.sdk.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 校验类型结果
 *
 * @Auther: liq
 * @Date: 2019/6/16 22:41
 * @Description:
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTypeResponse implements Serializable {

    private String validateToken;

    private List<JSONObject> validateItems;

    private Date createTime;

    private Date deadline;

}
