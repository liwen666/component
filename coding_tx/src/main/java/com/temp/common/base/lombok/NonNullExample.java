package com.temp.common.base.lombok;

import lombok.Getter;


import lombok.NonNull;

@Getter
public class NonNullExample {
    private String name;

    public NonNullExample(@NonNull Person person) {
        this.name = person.getName();
    }


    public static void main(String[] args) {
        NonNullExample n = new NonNullExample(new Person("lll"));
        System.out.println(n.getName());
    }
}