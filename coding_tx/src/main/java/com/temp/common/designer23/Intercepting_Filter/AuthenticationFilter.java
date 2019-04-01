package com.temp.common.designer23.Intercepting_Filter;

public class AuthenticationFilter implements Filter {
   public void execute(String request){
      System.out.println("Authenticating request: " + request);
   }
}