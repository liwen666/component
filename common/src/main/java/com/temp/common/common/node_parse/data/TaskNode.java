package com.temp.common.common.node_parse.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yxy
 * @date 2018/7/29
 */
@Data
@Slf4j
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Accessors(chain = true)
public class TaskNode implements Comparable<TaskNode> {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskNode.class);

    private String taskKey;
    private List<TaskNode> nextNodes = new ArrayList<>();

    public static final String END_NODE = "END";

    //优先级，值越高越优先执行
    private double priority = 0D;

    public TaskNode(){
    }

    public TaskNode(String taskKey, double priority) {
        this.taskKey = taskKey;
        this.priority = priority;
    }

    public TaskNode(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<TaskNode> getNextNodes() {
        return nextNodes;
    }

    public void addChild(TaskNode taskNode){
        if(!this.nextNodes.contains(taskNode)){
            double p = priority / 1.1;
            if (taskNode.getPriority() > 0) {
                if (taskNode.getPriority() > p) {
                    taskNode.setPriority(p);
                }
            } else {
                taskNode.setPriority(p);
            }
            this.nextNodes.add(taskNode);
        }else{
            logger.warn("节点已经包含在子节点当中，不能再重复添加, 父节点：{} ,被添加 node：{}",taskKey,taskNode);
        }

    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public String outputPretty(int intent){
        StringBuilder info = new StringBuilder();
        String strPadding = StringUtils.leftPad(" ",intent);
        info.append(strPadding);
        info.append("taskKey: ");
        info.append(taskKey);
        info.append(",priority:");
        info.append(priority);
        if(!CollectionUtils.isEmpty(nextNodes)){
            info.append(", children:[ ");
            info.append(String.join(",",nextNodes.stream().map(TaskNode::getTaskKey).collect(Collectors.toList())));
            info.append(" ]");
        }

        return info.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskNode taskNode = (TaskNode) o;

        return taskKey != null ? taskKey.equals(taskNode.taskKey) : taskNode.taskKey == null;
    }

    @Override
    public int hashCode() {
        return taskKey != null ? taskKey.hashCode() : 0;
    }

    @Override
    public String toString() {
        return outputPretty(1);
    }

    @Override
    public int compareTo(TaskNode o) {
        if (o != null && Double.valueOf(o.priority).compareTo(Double.valueOf(priority)) == 0) {
            return Double.valueOf(o.priority).compareTo(Double.valueOf(priority));
        }else{
            if (o == null) {
                return -1;
            }
            return o.taskKey.compareTo(taskKey);
        }
    }


}
