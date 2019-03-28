package com.temp.common.designer23.Null;

public class NullCustomer extends AbstractCustomer {
 
   @Override
   public String getName() {
      return "Not Available in Customer Database";
   }
 
   @Override
   public boolean isNil() {
      return true;
   }
}