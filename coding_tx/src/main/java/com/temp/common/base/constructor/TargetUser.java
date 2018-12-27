package com.temp.common.base.constructor;

public class TargetUser {
    private  String  name= "a";
    private  Integer age = 11;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public TargetUser (String extendTools){
        PersionService.getPersionService().setName(extendTools);
    }
}
