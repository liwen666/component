package com.temp.common.common.node_parse.data;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务链的指向和节点
 *
 * @author: looyii
 * @Date: 2018/8/29 15:28
 * @Description:
 */
public class TaskLinkNodeDTO {

    private List<TaskLinkData> linkDatas;

    private List<TaskNodeData> nodeDatas;

    public List<TaskLinkData> getLinkDatas() {
        return linkDatas;
    }

    public void setLinkDatas(List<TaskLinkData> linkDatas) {
        this.linkDatas = linkDatas;
    }

    public List<TaskNodeData> getNodeDatas() {
        return nodeDatas;
    }

    public void setNodeDatas(List<TaskNodeData> nodeDatas) {
        this.nodeDatas = nodeDatas;
    }

    public List<String> getTaskKeys(){

        if(CollectionUtils.isEmpty(linkDatas)){
            return null;
        }

        List<String> allKeys = new ArrayList<>();
        List<String> startKeys = new ArrayList<>();
        for(TaskLinkData taskLinkData : linkDatas){
            if(TaskNodeConstants.START_NODE.equals(taskLinkData.getFrom())){
                startKeys.add(taskLinkData.getTo());
                allKeys.add(taskLinkData.getTo());
            }
        }

        this.eachKeys(startKeys,allKeys);

        return allKeys;
    }


    private void eachKeys(List<String> taskKeys,List<String> allKeys){

        if(CollectionUtils.isEmpty(taskKeys)){
            return;
        }

        List<String> toKeys = new ArrayList<>();
        for(String key : taskKeys){
            for (TaskLinkData taskLinkData : linkDatas){
                if(key.equals(taskLinkData.getFrom()) && !TaskNodeConstants.END_NODE.equals(taskLinkData.getTo())){
                  if (!toKeys.contains(taskLinkData.getTo())) {
                    toKeys.add(taskLinkData.getTo());
                  }
                  if (!allKeys.contains(taskLinkData.getTo())) {
                    allKeys.add(taskLinkData.getTo());
                  }
                }
            }
        }

        this.eachKeys(toKeys,allKeys);
    }


}
