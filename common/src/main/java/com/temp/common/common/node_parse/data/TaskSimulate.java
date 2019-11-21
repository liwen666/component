package com.temp.common.common.node_parse.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
@Slf4j
@Data
public class TaskSimulate {

    private String chainNode;

    public TaskSimulate(String chainNode) {
        this.chainNode = chainNode;
    }

    public Map<Integer,List<String>> taskChain(String data) {
        Map<Integer,List<String>> integerListMap = null;
        try {
            TaskChain taskChain = new TaskChain(data);
            List<String> history = new ArrayList<>();
            List<String> error = new ArrayList<>();
            List<String> running = new ArrayList<>();
            history.add("1");
            List<List<String>> taskLinks = new ArrayList<>();
            runNext(history, error, running, taskChain, taskLinks);
            integerListMap = new HashMap<>();
            for(int i=0;i<taskLinks.size();i++){
                integerListMap.put(i,taskLinks.get(i));
            }
        } catch (Exception e) {
            throw new PlanExecutionException("任务链节点解析错误 nodeData:"+data);
        }
        return integerListMap;
    }

    private void runNext(List<String> history, List<String> error, List<String> running, TaskChain taskChain, List<List<String>> taskLinks) {
        Set<TaskNode> nextNodesAll = new HashSet<>();
        for (String key : history) {
            TaskNode taskNode = taskChain.getAllTaskNode().get(key);
            List<TaskNode> nextNodes1 = taskNode.getNextNodes();
            if (!CollectionUtils.isEmpty(error)) {
                nextNodesAll.addAll(nextNodes1);
            } else {
                nextNodesAll.addAll(nextNodes1);
            }
        }
        Iterator<String> iterator = running.iterator();
        while (iterator.hasNext()) {
            List<String> runkeyParent = taskChain.getParentNode(iterator.next());
            if (history.containsAll(runkeyParent) && !(runkeyParent.size() == 1 && runkeyParent.get(0).equals("1"))) {
                iterator.remove();
            }
        }
        Set<TaskNode> exeCollect = nextNodesAll.stream().filter(e -> !running.contains(e.getTaskKey()) && !history.contains(e.getTaskKey()) && !error.contains(e.getTaskKey())).collect(Collectors.toSet());
        List<String> execKeys = new ArrayList<>();
        for (TaskNode taskNode1 : exeCollect) {
            if (!running.contains(taskNode1.getTaskKey())) {
                execNode(history, error, running, taskNode1, taskChain, execKeys);
            }

        }
        //检查task状态
        if (CollectionUtils.isEmpty(exeCollect)) {
         log.info("===============任务排序结束=============");
            return;
        }
        taskLinks.add(execKeys);
        runNext(history, error, running, taskChain, taskLinks);
    }


    private void execNode(List<String> history, List<String> error, List<String> running, TaskNode taskNode, TaskChain taskChain, List<String> execKeys) {
        boolean exec = true;
        //            判断节点父节点是否完成
        List<String> parentNode = taskChain.getParentNode(taskNode.getTaskKey());
        if (!history.containsAll(parentNode)) {
            running.add(taskNode.getTaskKey());
            exec = false;
        }
        if (exec) {
            try {
                execKeys.add(taskNode.getTaskKey());
                history.add(taskNode.getTaskKey());
            } catch (Exception e) {
                e.printStackTrace();
                error.add(taskNode.getTaskKey());
            }
        }
    }

}
