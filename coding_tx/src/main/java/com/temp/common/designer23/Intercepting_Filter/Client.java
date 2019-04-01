package com.temp.common.designer23.Intercepting_Filter;

public class Client {
   FilterManager filterManager;
 
   public void setFilterManager(FilterManager filterManager){
      this.filterManager = filterManager;
   }
 
   public void sendRequest(String request){
      filterManager.filterRequest(request);
   }
}