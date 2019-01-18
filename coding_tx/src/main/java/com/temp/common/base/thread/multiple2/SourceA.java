package com.temp.common.base.thread.multiple2;

import java.util.ArrayList;
import java.util.List;

public class SourceA {
    private List<String> list = new ArrayList<String>();
    public synchronized void getSource(){
    	System.out.println("source");
        for(int i=0;i<list.size();i++){
            System.out.println("------"+list.get(i));
        }
    }
    public synchronized void setSource(String id){
        list.add(id);
    }
}