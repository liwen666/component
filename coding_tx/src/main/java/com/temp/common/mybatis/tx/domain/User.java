package com.temp.common.mybatis.tx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 
    * </p>
*
* @author tx
* @since 2019-02-01
*/
@Data
@Slf4j
 @EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

private static final long serialVersionUID = 1L;

private String id;
 private int uid;

private String avatar;

private String name;

private String sign;

private String mobile;

private String email;

private String password;

private String loginName;

private String defaultGroupId;

private LocalDateTime createDate;

private String createBy;

private LocalDateTime updateDate;

private String updateBy;

private String remarks;

private String delFlag;


}
