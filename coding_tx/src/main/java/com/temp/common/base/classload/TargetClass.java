package com.temp.common.base.classload;

public class TargetClass {
    private static MyClassLoad myClassLoad;

    static void hello(){
        myClassLoad.say();
    }
}
