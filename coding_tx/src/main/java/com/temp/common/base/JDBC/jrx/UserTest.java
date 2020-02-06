package com.temp.common.base.JDBC.jrx;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
@Data
@Getter
@Setter
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserTest {
    private String idx_key;
    private String uuid;
    private String user_name;
    private String cj_wd2;
    private String cj_wd1;
    private Date createTime;
}
