package com.temp.common.cmd.executor;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/4/2  18:10
 */
public enum AppType {
    JOB_CDC("com.riveretech.est.cdc.JobCdcApp"),
    JOB_RUNTIME("com.riveretech.est.runtime.JobApp"),
    STRATEGY("com.riveretech.est.cdc.JobCdcApp");

    public String getClazzName() {
        return clazzName;
    }

    private String clazzName;

    AppType(String s) {
        this.clazzName = s;
    }}
