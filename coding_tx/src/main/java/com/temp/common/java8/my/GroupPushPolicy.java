package com.temp.common.java8.my;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("group_push_policy")
public class GroupPushPolicy {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("order_group_id")
    private Long orderGroupId;

    @TableField("merchant_group_id")
    private Long merchantGroupId;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer  sort;

    @TableField("push_number")
    private Integer  pushNumber;

    @TableField("delay")
    private Long  delay; //延时执行

    @TableField("create_Time")
    private  Date createTime;

    @TableField("modify_Time")
    private Date modifyTime;
}
