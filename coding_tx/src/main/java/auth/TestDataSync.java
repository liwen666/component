package auth;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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
public class TestDataSync implements Serializable {


private String name;

private LocalDateTime timeStampTest;

private LocalDateTime time2;

private int key_pry;



}
