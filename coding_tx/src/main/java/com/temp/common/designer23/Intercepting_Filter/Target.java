package com.temp.common.designer23.Intercepting_Filter;

public class Target {
   public void execute(String request){
      System.out.println("Executing request: " + request);
   }
}