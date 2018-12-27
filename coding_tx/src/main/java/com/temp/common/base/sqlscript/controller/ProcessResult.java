package com.temp.common.base.sqlscript.controller;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 流程获取结果信息
 * 
 * @author Administrator
 * 
 */
public class ProcessResult<K> {

	/**
	 * 结果信息编码值
	 */
	private String code;
	/**
	 * 结果信息说明
	 */
	private String message;
	/**
	 * 返回结果信息是否成功
	 */
	private boolean success;
	/**
	 * 具体的结果信息数据
	 */
	private K result;
	/**
	 * 扩展数据
	 * @return
	 */
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public K getResult() {
		return result;
	}

	public void setResult(K result) {
		this.result = result;
	}
	  @Override
	    public String toString() {
	        try {
	            return BeanUtils.describe(this).toString();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        return "";
	    }

}