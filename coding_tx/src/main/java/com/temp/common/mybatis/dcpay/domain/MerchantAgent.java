package com.temp.common.mybatis.dcpay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    * 商家层级结构表
    * </p>
*
* @author tx
* @since 2019-08-10
*/
@Data
@Slf4j
 @EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MerchantAgent implements Serializable {

private static final long serialVersionUID = 1L;

  /**
  * 主键ID
  */
  @TableId(value = "id", type = IdType.AUTO)
private Long id;

  /**
  * 商家ID
  */
private Long merchantId;

  /**
  * 商户上级ID
  */
private Long parentId;

  /**
  * 状态 0-无效 1-有效
  */
private Integer status;

  /**
  * 版本号,乐观锁使用
  */
private Long version;

  /**
  * 创建时间
  */
private LocalDateTime createTime;

  /**
  * 修改时间
  */
private LocalDateTime modifyTime;


  public MerchantAgent selectMy() {
    return null;
  }
}
