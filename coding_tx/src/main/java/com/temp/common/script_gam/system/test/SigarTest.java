package com.temp.common.script_gam.system.test;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
public class SigarTest {

	/**
	 * main(这里用一句话描述这个方法的作用)
	 * @Title: main
	 * @Description: TODO
	 * @param @param args    设定文件
	 * @return void    返回类型
	 * @throws
	 */

	public static void main(String[] args) {
		Sigar sigar = new Sigar();
		try{
		  Mem mem = sigar.getMem();
		  CpuPerc cpuCerc = sigar.getCpuPerc();
		  System.out.println("当前CPU使用情况 ：");
		  System.out.println("#总使用率: " + cpuCerc.getCombined() * 100 + "%");
		  System.out.println("#用户使用率(user): " + cpuCerc.getUser() * 100 + "%");
		  System.out.println("#系统使用率(sys): " + cpuCerc.getSys() * 100 + "%");
		  System.out.println("#当前空闲率(idel): " + cpuCerc.getIdle() * 100 + "%");
		  System.out.println("当前内存使用情况 ：");
		  System.out.println("#内存总量：" + mem.getTotal() / 1024 / 1024 + "M");
		  System.out.println("#已使用内存：" + mem.getUsed() / 1024 / 1024 + "M");
		  System.out.println("#剩余内存：" + mem.getFree() / 1024 / 1024 + "M");
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}