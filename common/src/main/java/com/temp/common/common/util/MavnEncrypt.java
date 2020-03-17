package com.temp.common.common.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class MavnEncrypt
{
    public static void main(String[] args) {
        String hashpw = BCrypt.hashpw("123456789", BCrypt.gensalt());
        System.out.println(hashpw);
        boolean checkpw = BCrypt.checkpw("123456789", hashpw);
        System.out.println(checkpw);
    }
}
