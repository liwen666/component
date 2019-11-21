package com.temp.common.common.node_parse.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务链数据库结构
 * a->b
 * b-c
 * c->d
 * d->e
 * e->f
 * e->g
 * e->h
 * f->i
 * g->i
 * h->i
 *
 * @author yxy
 * @date 2018/7/29
 */
public class TaskChain {

    private static final Logger logger = LoggerFactory.getLogger(TaskChain.class);

    private String taskChainInfo;
    /**
     * key为taskKey，value为节点的父节点的taskKey集合
     **/
    private Map<String,List<String>> parents = new HashMap<>();
    private TaskNode taskNode = new TaskNode();

    private Map<String,TaskNode> nodeMap = new HashMap<>();


    public TaskChain(String taskChainInfo) {
        this.taskChainInfo = taskChainInfo;
        build();
    }

    private void build() {
        TaskLinkNodeDTO taskLinkNode = JsonUtils.fromJson(taskChainInfo, TaskLinkNodeDTO.class);
        if (taskLinkNode == null) {
            return;
        }

        for (TaskNodeData nodeData : taskLinkNode.getNodeDatas()) {
            TaskNode node = new TaskNode(nodeData.getKey());
            if(!nodeMap.containsKey(node.getTaskKey())){
                nodeMap.put(node.getTaskKey(),node);
            }else{
                logger.error("调用链中的任务有重复定义：{}",taskLinkNode.getNodeDatas());
                throw new TaskNodeException("调用链中的任务有重复定义,taskKey: "+node.getTaskKey());
            }
            if(TaskNodeEnum.START.name().contentEquals(nodeData.getType().toUpperCase())){
                taskNode = node;
                taskNode.setPriority(99999);
            }
        }//end for

        for (TaskLinkData linkData : taskLinkNode.getLinkDatas()) {
            if (!nodeMap.containsKey(linkData.getFrom()) || !nodeMap.containsKey(linkData.getTo())) {
                logger.error("调用关联的taskkey没有在节点中定义,{}",linkData);
                throw new TaskNodeException("调用关联的taskkey没有在节点中定义");
            }

            if(parents.containsKey(linkData.getTo())){
                parents.get(linkData.getTo()).add(linkData.getFrom());
            }else{
                List<String> ll = new ArrayList<>();
                ll.add(linkData.getFrom());
                parents.put(linkData.getTo(),ll);
            }
            nodeMap.get(linkData.getFrom()).addChild(nodeMap.get(linkData.getTo()));

        }

    }

    public Set<String> getTaskKeys(){
        Set<String> keys = new HashSet<>();
        if (nodeMap.keySet().size() > 0) {
            for (String key : nodeMap.keySet()) {
                if (TaskNodeConstants.START_NODE.equals(key) || TaskNodeConstants.END_NODE.equals(key)) {
                    continue;
                }
                keys.add(key);
            }
        }
        return keys;
    }

    public TaskNode getNode(String taskKey){
        return nodeMap.get(taskKey);
    }

    public Map<String,TaskNode> getAllTaskNode(){
        return nodeMap;
    }

    public TaskNode getTaskNode(){
        return taskNode;
    }

    public List<String> getParentNode(String taskKey){
        return parents.get(taskKey);
    }

    public int getTaskSize(){
        return nodeMap.size();
    }

    public String prettyPrint(){
        StringBuilder info = new StringBuilder();
        AtomicInteger i = new AtomicInteger();
        List<TaskNode> ll = new ArrayList<>();
        ll.addAll(nodeMap.values());
        Collections.sort(ll);
        ll.forEach(node->{
            info.append(i.incrementAndGet());
            info.append(": ");
            info.append(node.outputPretty(2));
            info.append("\n");
        });

        return info.toString();
    }

    /**
     * 查找参数集合中优先级最高的任务
     *
     * @param originSet
     * @return
     */
    public Set<String> findHighestPriorityTaskNode(Set<String> originSet){
        TreeMap<Double,Set<String>> cache = new TreeMap<>();
        for(String key:originSet){
            TaskNode node = this.getNode(key);
            if(cache.containsKey(node.getPriority())){
                cache.get(node.getPriority()).add(key);
            }else{
                Set<String> set = new HashSet<>();
                set.add(key);
                cache.put(node.getPriority(),set);
            }
        }

        return cache.lastEntry() == null ? new HashSet<>() : cache.lastEntry().getValue();
    }

    public TaskChain() {
    }
}
