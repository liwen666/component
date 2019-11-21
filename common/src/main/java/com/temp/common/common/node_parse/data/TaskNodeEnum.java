package com.temp.common.common.node_parse.data;

/**
 * 任务链节点
 *
 * @author: looyii
 * @Date: 2018/8/29 15:24
 * @Description:
 */
public enum TaskNodeEnum {

    START("开始"),
    END("结束"),
    FOCAL("汇聚点");

    private String desc;

    private TaskNodeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
