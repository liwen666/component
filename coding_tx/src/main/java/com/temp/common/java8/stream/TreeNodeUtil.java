package com.temp.common.java8.stream;

import java.util.List;

/**
 * author lw
 * date 2019/8/9  15:27
 * discribe 构造树
 */
public class TreeNodeUtil {

//    private List<TreeNode> createTree(Integer pid, Map<Integer, List<OrderRecord>> map){
//        return Optional.ofNullable(map.get(pid)).orElseGet(()->new ArrayList<OrderRecord>()).stream().filter(x->x.getParentId() == pid).sorted((x, y)->{return x.getSortNo().compareTo(y.getSortNo());}).map(x->{
//            return new TreeNode(x.getRecId(), x.getParentId(), x.getPermissionName(), x.getIcon(), createTree(x.getRecId(), map));
//        }).collect(Collectors.toList());
//    }
//    private List<TreeNode> getTree(boolean onlyModule){
//        EntityWrapper<OrderRecord> entityWrapper = new EntityWrapper<>();
//        entityWrapper.where("data_status>0");
//        if (onlyModule){
//            entityWrapper.eq("permission_type", PermissionType.MODULE.getValue());
//        }
//        entityWrapper.orderBy("sort_no");
//        List<SysPermission> list = selectList(entityWrapper);
//        return createTree(0, list.stream().collect(groupingBy(SysPermission::getParentId)));
//    }

    public static void main(String[] args) {
//        createTree()
    }

    public static List<TreeNode> createTree(List data, List<TreeRelation> relations) {

        return null;
    }

}
