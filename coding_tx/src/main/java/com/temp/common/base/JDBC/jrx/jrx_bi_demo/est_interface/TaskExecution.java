package com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
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
* @since 2020-03-06
*/
@Data
@Slf4j
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
 @EqualsAndHashCode(callSuper = false)

@Accessors(chain = true)
public class TaskExecution implements Serializable {

private static final long serialVersionUID = 1L;

  @TableId("TASK_EXECUTION_ID")
private Long taskExecutionId;

 @TableField("START_TIME")
private LocalDateTime startTime;

 @TableField("END_TIME")
private LocalDateTime endTime;

 @TableField("TASK_NAME")
private String taskName;

 @TableField("EXIT_CODE")
private Integer exitCode;

 @TableField("EXIT_MESSAGE")
private String exitMessage;

 @TableField("ERROR_MESSAGE")
private String errorMessage;

 @TableField("LAST_UPDATED")
private LocalDateTime lastUpdated;

 @TableField("EXTERNAL_EXECUTION_ID")
private String externalExecutionId;

 @TableField("PARENT_EXECUTION_ID")
private Long parentExecutionId;


}
