package auth;

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
* @since 2020-03-15
*/
@Data
@Slf4j
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
 @EqualsAndHashCode(callSuper = false)

@Accessors(chain = true)
public class User implements Serializable {


private Long id;

private String email;

private String username;

private String password;

private Boolean admin;

private Boolean active;

private String name;

private String description;

private String department;

private String avatar;

private LocalDateTime createTime;

private Long createBy;

private LocalDateTime updateTime;

private Long updateBy;


}
