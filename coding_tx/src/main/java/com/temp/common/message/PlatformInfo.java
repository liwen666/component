package com.temp.common.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zys
 * @since 2019-05-30
 */
@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("platform_info")
public class PlatformInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String website;
    @TableField("contact_phone")
    private String contactPhone;
    @TableField("contact_name")
    private String contactName;
    private Integer status;
    @TableField("parent_id")
    private Long parentId;
    @TableField("proxy_id")
    private Long proxyId;
    @TableField("proxy_rate")
    private BigDecimal proxyRate;
    @TableField("recharge_switch")
    private Integer rechargeSwitch;
    @TableField("issue_switch")
    private Integer issueSwitch;
    @TableField("regist_type")
    private Integer registType;
    @TableField("regist_ip")
    private String registIP;
    @TableField("approval_id")
    private Long approvalId;
    @TableField("approval_status")
    private Integer approvalStatus;
    @TableField("approval_reason")
    private String approvalReason;
    @TableField("approval_time")
    private Date approvalTime;
    @TableField("remark")
    private String remark;
    @TableField("create_time")
    private Date createTime;
    @TableField("modify_time")
    private Date modifyTime;
    @TableField("operator")
    private String operator;
    @TableField("bind_ip")
    private String bindIp;
    @TableField("uid")
    private String uid;

}