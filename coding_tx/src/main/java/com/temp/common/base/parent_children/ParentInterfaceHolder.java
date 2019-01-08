package com.temp.common.base.parent_children;

import javafx.application.Application;
import org.springframework.context.ApplicationContext;

public class ParentInterfaceHolder implements ParentInterface {
    private TargetContext context;
    @Override
    public void setApplicationContext(TargetContext context) {
    this.context=context;
    }

    public TargetContext getContext() {
        return context;
    }
}
