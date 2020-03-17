package com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Random;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Accessors(chain = true)
public class ModelTest {

    private String area_code;
    private String address;
    private Date create_time;
    private String province;
    private String city;
    private String user_name;
    private String id_card;
    private String tel_phone;
    private String parent_area_code;


    public static void main(String[] args) {
        int max = 100, min = 4;
        int ran2 = (int) (Math.random() * (max - min) + min);
        System.out.println(ran2);
    }


//    public static void main(String[] args) {
//        Random r = new Random(1);
//        for (int i = 0; i < 20; i++) {
//            int ran1 = r.nextInt(3)+1;
//            System.out.println(ran1);
//        }
//    }


}
