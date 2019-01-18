package com.temp.common.base.thread.test1;

public class TargerExtendTread extends Thread {
	private int num;

	
	public TargerExtendTread(int num) {
		super();
		this.num = num;
	}


	public TargerExtendTread(Runnable target, String name) {
	
		super(target, name);
		System.out.println("调用target构造");
		// TODO Auto-generated constructor stub
	}


	@Override
	public void run() {
		
		for (int i = 0; i < 100; i++) {
			System.out.println(num+"+++++++++++++++");
			num++;
			super.run(); // 此处调用的是父类的run方法   如果构造此线程又Target则调用此方法直接触发  目标run执行
		}
	}
	

}
