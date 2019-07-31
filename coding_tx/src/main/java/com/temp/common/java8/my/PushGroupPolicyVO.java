package com.temp.common.java8.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhugx
 * @version V1.0
 * @note
 * @date 2019/7/3 12:10
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PushGroupPolicyVO implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    private Long orderGroupId;

    private Long merchantGroupId;

    /**
     * 排序
     */
    private Integer  sort;

    private Integer  pushNumber;

    private Long  delay; //延时执行

    private  Date createTime;

    private Date modifyTime;
}
