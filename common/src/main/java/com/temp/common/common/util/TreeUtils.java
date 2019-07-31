package com.temp.common.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ***************************************************************************
 *模块名 :
 *创建时间 : 2017年9月7日
 *实现功能 :树形结构处理工具类
 *作者 : 揣怀梦想的猪
 *版本 : v0.0.1
-----------------------------------------------------------------------------
 *修改记录:
 *日 期 版本 修改人 修改内容
 *2017年9月7日 v0.0.1 揣怀梦想的猪 创建
 ****************************************************************************
 */
public class TreeUtils {
    private static final String COLUMN_ID_LOWCASE = "categoryCode";    //编号字段
    private static final String COLUMN_CHILDREN = "merchantId";
//    private static final String COLUMN_CHILDREN = "children";
    private static final String PARENT_FIELD_NAME="parentId";
//    private static final String PARENT_FIELD_NAME="parentCode";
    private static Logger logger = LoggerFactory.getLogger(TreeUtils.class);
    
    /**
     * ====================================================================
     *函 数 名： 
     *@param parent
     *@param treeDataList
     *@return
     *功 能： 通过父节点获取子节点生成树形结构数据
    ----------------------------------------------------------------------
     *修改记录 ：
     *日 期  版本 修改人 修改内容
     *2017年8月31日 v0.0.1 揣怀梦想的猪 创建
    ====================================================================
     */
    public static <T extends Object> List<T> treeNode(String parent, List<T> treeDataList) {
        List<T> trees = null;
        try {
            if (trees == null) {
                trees = new ArrayList<T>();
            }

            if (treeDataList == null) {
                return trees;
            }

            for (T t : treeDataList) {
                if (getFiledVal(t,PARENT_FIELD_NAME).equals(parent)) {
                    setFiledVal(t,COLUMN_CHILDREN,treeNode(getFiledVal(t,COLUMN_ID_LOWCASE), treeDataList));
                    trees.add(t);
                }
            }
        } catch (Exception e) {
            logger.error("递归生成树节点错误: " + e);
        }

        return trees;
    }
    
    
    public static <T extends Object> List<T> treeNodeList(String parent, List<T> treeDataList){
        return treeNodeList(parent, treeDataList,null);
    }
    
    
    /**
     * ====================================================================
     *函 数 名： 
     *@param parent 传入当前节点名称
     *@param treeDataList
     * @return 
     *@return
     *功 能： 通过父节点获取子节点返回列表结构数据
    ----------------------------------------------------------------------
     *修改记录 ：
     *日 期  版本 修改人 修改内容
     *2017年8月31日 v0.0.1 揣怀梦想的猪 创建
    ====================================================================
     */
    private  static <T extends Object> List<T> treeNodeList(String parent, List<T> treeDataList,List<T> trees) {
        try {

            if (treeDataList == null) {
                return trees;
            }

            for (T t : treeDataList) {
                if (getFiledVal(t,PARENT_FIELD_NAME).equals(parent)) {
                    trees.add(t);
                    treeNodeList(getFiledVal(t,COLUMN_ID_LOWCASE), treeDataList,trees);
                }
            }
        } catch (Exception e) {
            logger.error("递归生成树节点错误: " + e);
        }

        return trees;
    }
    
    
    public static <T extends Object> Map<String, T> childToParentNode(String parent, List<T> treeDataList){
        return childToParentNode(parent,treeDataList,null);
    }
    
    /**
     * ====================================================================
     *函 数 名： 
     *@param parent 传入子节点对应的上级节点
     *@param treeDataList 数据源
     *@param trees 获取到的父节点数据
     *@return
     *功 能：通过子节点获取到对应父节点数据
    ----------------------------------------------------------------------
     *修改记录 ：
     *日 期  版本 修改人 修改内容
     *2017年8月31日 v0.0.1 揣怀梦想的猪 创建
    ====================================================================
     */
    public static <T extends Object> Map<String, T> childToParentNode(String parent, List<T> treeDataList,
            Map<String, T> trees) {
        try {
            if (trees == null) {
                trees = new HashMap<String, T>();
            }

            if (treeDataList == null) {
                return trees;
            }

            for (T t : treeDataList) {
                if (getFiledVal(t, COLUMN_ID_LOWCASE).equals(parent)) {
                    trees.put(getFiledVal(t, COLUMN_ID_LOWCASE), t);
                    childToParentNode(getFiledVal(t, PARENT_FIELD_NAME), treeDataList, trees);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("执行childToParentNode时发生错误: " + e);
        }

        return trees;
    }
    
    /**
     * ====================================================================
     *函 数 名： 
     *@param t
     *@param name
     *@return
     *功 能： 获取字段值
    ----------------------------------------------------------------------
     *修改记录 ：
     *日 期  版本 修改人 修改内容
     *2017年9月6日 v0.0.1 揣怀梦想的猪 创建
    ====================================================================
     */
    private static <T extends Object> String getFiledVal(T t, String name) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (int i = 0,j=fields.length; i <j ; i++) {
                String fieldName = fields[i].getName();
                if (name.equals(fieldName)) {
                    fields[i].setAccessible(true);
                    if (fields[i].getType().getName().equals(String.class.getName())) {
                        // String type
                        Object obj= fields[i].get(t);
                        if(obj==null){
                            return "";
                        }else{
                            return obj.toString();
                        }
                    } else if (fields[i].getType().getName().equals(Integer.class.getName())
                            || fields[i].getType().getName().equals("int")) {
                        // Integer type
                        Object obj=fields[i].get(t);
                        if(obj==null){
                            return "";
                        }else{
                            return String.valueOf(obj);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }
    
    /**
     * ====================================================================
     *函 数 名： 
     *@param t
     *@param name
     *功 能：
    ----------------------------------------------------------------------
     *修改记录 ：
     *日 期  版本 修改人 修改内容
     *2017年9月6日 v0.0.1 揣怀梦想的猪 创建
    ====================================================================
     */
    public static <T extends Object> void setFiledVal(T t, String name, List<T> list) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (name.equals(fields[i].getName())) {
                    fields[i].set(t, list);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
//        ScmMaterialCategoryVo vo=new ScmMaterialCategoryVo();
//        List<ScmMaterialCategoryVo> list=new ArrayList<>();
//        ScmMaterialCategoryVo temp=new ScmMaterialCategoryVo();
//        temp.setCategoryCode("测试赛");
//        list.add(temp);
//        setFiledVal(vo, "children", list);
//        System.out.println(vo);
    }
}