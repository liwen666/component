package com.temp.common.base.thread.multiplethread.cas;

import sun.misc.Unsafe;

/**
 * 特殊处理  使用unsafe
 * 
* <p>describe</p> 
* <p>UnsafeTest.java</p>
* <p></p>
* @author lw
* @date 2017年1月10日
* @version 1.0
* @link
*/
public class UnsafeTest implements java.io.Serializable{
	private static final long serialVersionUID = 6214790243416807050L;

    // setup to use Unsafe.compareAndSwapInt for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (UnsafeTest.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;

	public static long getValueoffset() {
		return valueOffset;
	}
	public static void main(String[] args) {
		UnsafeTest ut = new UnsafeTest();
		
			System.out.println(ut.value);
		
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static Unsafe getUnsafe() {
		return unsafe;
	}
	
}
