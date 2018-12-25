package com.temp.component.redis.bean;

import java.util.Date;

/**
 * @author Administrator
 * @date 2018/12/13 10:30
 */
public class ProcessInstanceCache {

    private String ticketId;

    private String bpmnType;

    private String piId;

    private String pdId;

    private String deplymentId;

    private String startUserId;

    private Date startTime;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getBpmnType() {
        return bpmnType;
    }

    public void setBpmnType(String bpmnType) {
        this.bpmnType = bpmnType;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getDeplymentId() {
        return deplymentId;
    }

    public void setDeplymentId(String deplymentId) {
        this.deplymentId = deplymentId;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "ProcessInstanceCache{" +
                "ticketId='" + ticketId + '\'' +
                ", bpmnType='" + bpmnType + '\'' +
                ", piId='" + piId + '\'' +
                ", pdId='" + pdId + '\'' +
                ", deplymentId='" + deplymentId + '\'' +
                ", startUserId='" + startUserId + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
