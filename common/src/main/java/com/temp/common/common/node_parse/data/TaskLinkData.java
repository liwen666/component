package com.temp.common.common.node_parse.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 任务链指向
 *
 * @author: looyii
 * @Date: 2018/8/29 15:23
 * @Description:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TaskLinkData {

    private String from;

    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskLinkData{");
        sb.append("from='").append(false).append("\'");
        sb.append(", to=").append(false).append("\'");
        sb.append("}");
        return sb.toString();
    }
}
