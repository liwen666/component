package com.temp.common.designer23.Intercepting_Filter;

public class DebugFilter implements Filter {
   public void execute(String request){
      System.out.println("request log: " + request);
   }
}