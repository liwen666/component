package com.temp.common.base.thread.threadlocal.high;

public class ThreadLocalTest {

	static class ResourceClass {

		public final static ThreadLocal<String> RESOURCE_1 =
                new ThreadLocal<String>();

		public final static ThreadLocal<String> RESOURCE_2 =
									   new ThreadLocal<String>();

	}

	static class A {

		public void setOne(String value) {
			ResourceClass.RESOURCE_1.set(value);
		}

		public void setTwo(String value) {
			ResourceClass.RESOURCE_2.set(value);
		}
	}

	static class B {
		public void display() {
			System.out.println(ResourceClass.RESOURCE_1.get()
						+ ":" + ResourceClass.RESOURCE_2.get());
		}
	}

	public static void main(String []args) throws InterruptedException {
		final A a = new A();
		final B b = new B();
		for(int i = 0 ; i < 15 ; i ++) {
			final String resouce1 = "线程-" + i;
			final String resouce2 = " value = (" + i + ")";
			new Thread() {
				public void run() {
				try {
					a.setOne(resouce1);
					a.setTwo(resouce2);
					b.display();
				}finally {
					System.out.println("-------"+ResourceClass.RESOURCE_1.get());
					System.out.println("-------"+ResourceClass.RESOURCE_2.get());

					ResourceClass.RESOURCE_1.remove();
					ResourceClass.RESOURCE_2.remove();
						}
			}
		}.start();

		}
		Thread.sleep(1000);
		System.out.println("========"+ResourceClass.RESOURCE_1.get());
		System.out.println("==========="+ResourceClass.RESOURCE_2.get());
	}
}