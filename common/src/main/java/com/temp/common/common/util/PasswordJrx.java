package com.temp.common.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class PasswordJrx {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String lw123456 = encoder.encode("Lw123456");
        String lw123456 = encoder.encode("123456");

        System.out.println(lw123456);
    }
}
