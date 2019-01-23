package com.temp.common.base.io.xml;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 流程模版定义实体。
 * 
 * @author Administrator
 *
 */
public class BpmnTemplateDef implements Serializable {
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getDeploymentBusiId() {
		return deploymentBusiId;
	}

	public void setDeploymentBusiId(String deploymentBusiId) {
		this.deploymentBusiId = deploymentBusiId;
	}


	/** 已部署 */
	public static final Integer DEPLOY = 0;
	/** 未部署 */
	public static final Integer UN_DEPLOY = 1;
	/** 停用 */
	public static final Integer DISABLE = 0;
	/** 启用 */
	public static final Integer ENABLE = 1;
	/** 禁用 */ 
	public static final Integer FORBIDDEN = 2;
	/** 模版Id */
	private String id;
	/** 模版名称 */
	private String name;
	/** 模版分类 */
	private String category;
	/** 模版版本 */
	private Integer version;
	/** 模版创建人 */
	private String createBy;
	/** 模版修改人 */
	private String modifyBy;
	/** 模版创建时间 */
	private Date createTime;
	/** 创建时间字符串 */
	private String createTimeStr;
	/** 模版修改时间 */
	private Date modifyTime;
	/** 修改时间字符串 */
	private String modifyTimeStr;
	/** 模版发布状态 */
	private Integer deployState;
	/** 模版发部署态str */
	private String deployStateStr;
	/** 版本状态：启用，停用，禁用 */
	private Integer versionState;
	/** 版本发布状态str */
	private String versionStateStr;
	/** 模版部署Id */
	private String deploymentId;
	/** 详情 */
	private byte[] contentBytes;
	/** 字符串的详情 */
	private String contentBytesStr;
	/** 节点数字 */
	private Integer initNum;
	/** 模板画布宽度 */
	private String canvasWidth;
	/** 模板画布高度 */
	private String canvasHeight;
	/** 表单绑定流程的tableIds */
	private String tableIds;
	/** 表单绑定流程的tableNames */
	private String tableNames;
	/**系统标识*/
	private String appId;
	/**业务部署Id*/
	private String deploymentBusiId;
	/**业务部署状态*/
	private Integer deploymentState;
	

	/**
	 * 表单绑定流程的tableIds
	 */
	public String getTableIds() {
		return tableIds;
	}

	public void setTableIds(String tableIds) {
		this.tableIds = tableIds;
	}

	/**
	 * 表单绑定流程的tableNames
	 */
	public String getTableNames() {
		return tableNames;
	}

	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}

	/**
	 * 模板画布宽度 
	 */
	public String getCanvasWidth() {
		return canvasWidth;
	}

	public void setCanvasWidth(String canvasWidth) {
		this.canvasWidth = canvasWidth;
	}

	/**
	 * 模板画布高度
	 */
	public String getCanvasHeight() {
		return canvasHeight;
	}

	public void setCanvasHeight(String canvasHeight) {
		this.canvasHeight = canvasHeight;
	}

	/**
	 * 模板Id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 模板分类
	 */
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 模板名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 模板版本
	 */
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 模板创建人
	 */
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 模板创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 模板修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 模板发布状态
	 */
	public Integer getDeployState() {
		return deployState;
	}

	public void setDeployState(Integer deployState) {
		this.deployState = deployState;
	}

	/**
	 * 模板版本状态
	 */
	public Integer getVersionState() {
		return versionState;
	}

	public void setVersionState(Integer versionState) {
		this.versionState = versionState;
	}

	/**
	 * 模板修改人
	 */
	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	/**
	 * 模板发布状态str
	 */
	public String getDeployStateStr() {
		return deployStateStr;
	}

	public void setDeployStateStr(String deployStateStr) {
		this.deployStateStr = deployStateStr;
	}

	/**
	 * 模板版本状态str
	 */
	public String getVersionStateStr() {
		return versionStateStr;
	}

	public void setVersionStateStr(String versionStateStr) {
		this.versionStateStr = versionStateStr;
	}

	/**
	 * 模板创建时间字符串
	 */
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	/**
	 * 模板修改时间字符串
	 */
	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}

	/**
	 * 节点数字
	 */
	public Integer getInitNum() {
		return initNum;
	}

	public void setInitNum(Integer initNum) {
		this.initNum = initNum;
	}

	/**
	 * 详情
	 */
	public byte[] getContentBytes() {
		return contentBytes;
	}

	public void setContentBytes(byte[] contentBytes) {
		this.contentBytes = contentBytes;
	}

	/**
	 * 字符串的详情
	 */
	public String getContentBytesStr() {
		return contentBytesStr;
	}

	public void setContentBytesStr(String contentBytesStr) {
		this.contentBytesStr = contentBytesStr;
	}

	/**
	 * 模板创部署Id
	 */
	public String getDeploymentId() {
		return deploymentId;
	}

	public Integer getDeploymentState() {
		return deploymentState;
	}

	public void setDeploymentState(Integer deploymentState) {
		this.deploymentState = deploymentState;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	@Override
	public String toString() {
		return "BpmnTemplateDef{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", category='" + category + '\'' +
				", version=" + version +
				", createBy='" + createBy + '\'' +
				", modifyBy='" + modifyBy + '\'' +
				", createTime=" + createTime +
				", createTimeStr='" + createTimeStr + '\'' +
				", modifyTime=" + modifyTime +
				", modifyTimeStr='" + modifyTimeStr + '\'' +
				", deployState=" + deployState +
				", deployStateStr='" + deployStateStr + '\'' +
				", versionState=" + versionState +
				", versionStateStr='" + versionStateStr + '\'' +
				", deploymentId='" + deploymentId + '\'' +
				", contentBytes=" + Arrays.toString(contentBytes) +
				", contentBytesStr='" + contentBytesStr + '\'' +
				", initNum=" + initNum +
				", canvasWidth='" + canvasWidth + '\'' +
				", canvasHeight='" + canvasHeight + '\'' +
				", tableIds='" + tableIds + '\'' +
				", tableNames='" + tableNames + '\'' +
				", appId='" + appId + '\'' +
				", deploymentBusiId='" + deploymentBusiId + '\'' +
				", deploymentState=" + deploymentState +
				'}';
	}
}
