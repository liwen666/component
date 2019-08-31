package com.temp.common.common.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 收付款信息表
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentDto implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 账户ID，根据accountType映射到不同表
     */
    private Long accountId;
    /**
     * 账户类型  1-平台 2-商家
     */
    private Integer accountType;
    /**
     * 所有者姓名。非实名信息
     */
    private String ownerName;
    /**
     * 用途 1-抢单收款 2-商户充提
     */
    private Integer purpose;
    /**
     * 方式。支付方式code
     */
    private String payWay;
    /**
     * 收付账号
     */
    private String payAccount;
    /**
     * 二维码图片
     */
    private String qrImg;
    /**
     * 二维码内含链接
     */
    private String qrLink;
    /**
     * 二维码签名数据
     */
    private String qrSign;
    /**
     * 所属机构
     */
    private String belongTo;
    /**
     * 所属子机构
     */
    private String subBelongTo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 状态  1-开启 2-关闭 3-删除
     */
    private Integer status;

}
