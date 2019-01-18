package com.temp.common.base.thread.test;
/**
 * 测试线程的run方法的执行  以及  run方法内部调用父类的方法
* <p>describe</p> 
* <p>TestThread.java</p>
* <p></p>
* @author lw
* @date 2017年1月4日
* @version 1.0
* @link
 */
public class TestThread {
	public static void main(String[] args) {
//		TargerExtendTread tet = new TargerExtendTread(10);
//		tet.start();
		testSuper();
	}
  private static void testSuper(){
	  Target t = new Target(10);//构造器中的目标线程，需要调用时调用
	  TargerExtendTread tet = new TargerExtendTread(t,"aaa");//构造目标线程使用为目标线程命名的方式
	  tet.start();
	  
  }
}
