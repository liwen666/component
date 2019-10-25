package com.temp.common.java8.stream.map;


import lombok.Data;

import java.util.List;

/**
 * 对应资源目录的权限，不是实体类
 */
@Data
public class AuthScope {

    private Long resourceId;
    private List<String> operates;

    private List<Long> viewContents;

    private List<Long> viewScope;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public List<String> getOperates() {
        return operates;
    }

    public void setOperates(List<String> operates) {
        this.operates = operates;
    }

    public List<Long> getViewContents() {
        return viewContents;
    }

    public void setViewContents(List<Long> viewContents) {
        this.viewContents = viewContents;
    }

    public List<Long> getViewScope() {
        return viewScope;
    }

    public void setViewScope(List<Long> viewScope) {
        this.viewScope = viewScope;
    }

    public AuthScope() {
    }

    public AuthScope(Long resourceId, List<String> operates, List<Long> viewContents, List<Long> viewScope) {
        this.resourceId = resourceId;
        this.operates = operates;
        this.viewContents = viewContents;
        this.viewScope = viewScope;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthScope{");
        sb.append("resourceId=").append(resourceId);
        sb.append(", operates=").append(operates);
        sb.append(", viewContents=").append(viewContents);
        sb.append(", viewScope=").append(viewScope);
        sb.append('}');
        return sb.toString();
    }
}