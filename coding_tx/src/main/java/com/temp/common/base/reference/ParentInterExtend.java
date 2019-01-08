package com.temp.common.base.reference;

import com.temp.common.base.parent_children.ParentInterface;
import com.temp.common.base.parent_children.TargetContext;

public class ParentInterExtend implements ParentInterface {
    private TargetContext context;
    @Override
    public void setApplicationContext(TargetContext context) {
        System.out.println("执行扩展类的方法。。。。。");
        this.context=context;
    }
}
