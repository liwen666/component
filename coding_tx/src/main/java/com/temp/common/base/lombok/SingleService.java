package com.temp.common.base.lombok;

import lombok.Getter;
import lombok.Singular;

public class SingleService {
    @Getter    String name="www";


    public static void main(String[] args) {
        System.out.println(new SingleService().getName());
    }
}
