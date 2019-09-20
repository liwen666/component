package com.temp.common.message;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginedUser implements Serializable {

    private Long id;

    private String ips;

    private String uid;

    private Long platformId;

    private String account;

    private String bindIp;
}
