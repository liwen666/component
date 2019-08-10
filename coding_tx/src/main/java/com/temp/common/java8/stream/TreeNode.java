package com.temp.common.java8.stream;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private static final long serialVersionUID = 1L;

    private Integer recId;
    private Integer pid;
    private String nodeName;
    private Object data;
    private String icon;
    private List<TreeNode> children;

    public TreeNode(Integer recId, Integer pid, String nodeName, String icon) {
        this.recId = recId;
        this.pid = pid;
        this.nodeName = nodeName;
        this.icon = icon;
        this.children = new ArrayList<TreeNode>();
    }

    public TreeNode(Integer recId, Integer pid, String nodeName, String icon, List<TreeNode> children) {
        this.recId = recId;
        this.pid = pid;
        this.nodeName = nodeName;
        this.icon = icon;
        this.children = children;
    }

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}