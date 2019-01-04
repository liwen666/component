package com.temp.common.base.classload.high;

import lombok.Data;

@Data
public class TargetClass {
    private String name="wss";

    public String getName() {
        return name;
    }
}
