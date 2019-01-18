package com.temp.common.base.thread.test;

public class Target extends Thread {
	int num;

	public Target(int num) {
		super();
		this.num = num;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			num++;
			System.out.println(currentThread().getName()+num+"-------");
			//		super.run();
		}
	}
	

}
