package com.temp.common.complier.tools;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/12  14:38
 */
public enum ElementEnum {
    PACKAGE("package "),
    IMPORT("import "),
    CLASS(" class "),
    ENUM(" enum "),
    INTERFACE(" interface "),
    SIGN("sign"),
    METHOD("method"),
    PARAM("param");
    private String type;

    public String getType() {
        return type;
    }

    ElementEnum(String type) {
        this.type = type;
    }}
