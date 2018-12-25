package com.temp.common.common.bean;

public class BpmnProcessConstant {
	
	
	/*------------------------流程选项类型------------------------*/
	/**
	 * 创建新的流程实例
	 */
	public static final int START_PROCESS_INSTANCE = 1;
	/**
	 * 流程流转
	 */
	public static final int TURN_TRANSITION = 2;
	/**
	 * 创建子任务
	 */
	public static final int START_SUBTASK = 3;
	/**
	 * 流程子流程
	 */
	public static final int TURN_SUB_PROCESSINSTANCE = 4;
	/**
	 * 自定义按钮
	 */
	public static final int CUSTOM_BUTTON = 5;
	/**
	 * 开始事件节点的自定义按钮
	 */
	public static final int START_EVENT_CUSTOM_BUTTON = 51;
	/**
	 * 接收
	 */
	public static final int TO_BE_RECEIVED = 6;
	/**
	 * 退回
	 */
	public static final int TO_BE_RETURNED = 7;
	/**
	 * 撤销
	 */
	public static final int TO_REVOKED = 8;
	/**
	 * 会签
	 */
	public static final int COUNTERSIGN = 9;
	/**
	 * 删除
	 */
	public static final int TO_BE_DELETE = 10;
	/**
	 * 补正
	 */
	public static final int TO_BE_SUPPLEMENT = 11;
	/**
	 * 自由流转
	 */
	public static final int TO_BE_FREEDOM_NODE = 12;
	
	
	/*------------------------任务节点扩展属性------------------------*/
	/**
	 * 接收关键字
	 */
	public static final String PROCESS_TO_BE_RECEIVED = "bpmnToBeReceived";
	/**
	 * 退回关键字
	 */
	public static final String PROCESS_TO_BE_RETURNED = "bpmnToBeReturned";
	/**
	 * 自由节点退回关键字
	 */
	public static final String PROCESS_FREEDOM_NODE_TO_BE_RETURNED = "bpmnFreedomNodeToBeReturned";
	/**
	 * 撤销关键字
	 */
	public static final String PROCESS_TO_REVOKED = "bpmnToRevoked";
	/**
	 * 自由节点撤销关键字
	 */
	public static final String PROCESS_FREEDOM_NODE_TO_REVOKED = "bpmnFreedomNodeToRevoked";
	/**
	 * 删除关键字
	 */
	public static final String PROCESS_TO_DELETE = "bpmnToDelete";
	/**
	 * 自由节点关键字
	 */
	public static final String PROCESS_TO_BE_FREEDOM_NODE = "bpmnToBeFreedomNode";
	
	
	/*------------------------流程内置变量信息------------------------*/
	/**
	 * 业务系统自定义实现分支条件变量
	 */
	public static final String PROCESS_BUSINESS_CUSTOM_CONDITION_VAR = "businessCustomCondition";
	/**
	 * 流程变量字符串类型
	 */
	public static final String BPMN_VARIABLE_STRING = "string";
	/**
	 * 候选人名称
	 */
	public static final String PROCESS_CANDIDATE_USER_NAME = "bpmnCandidateUsers";
	/**
	 * 候选组名称
	 */
	public static final String PROCESS_CANDIDATE_GROUP_NAME = "bpmnCandidateGroups";
	/**
	 * 候选机构名称
	 */
	public static final String PROCESS_CANDIDATE_ORG_NAME = "bpmnCandidateOrgs";
	/**
	 * 审批意见变量名
	 */
	public static final String PROCESS_COMMENT_NAME = "bpmnComment";
	/**
	 * 流程选项变量名（当前元素）
	 */
	public static final String PROCESS_CHOOSE_TRANSITION_NAME = "bpmnTransitionId";
	/**
	 * 子流程 
	 */
	public static final String PROCESS_SUBPROCESS_INPUT_DATA = "subProcessList";
	/**
	 * 会签流程变量 
	 */
	public static final String PROCESS_COUNTERSIGN_INPUT_DATA = "countersignList";
	/**
	 * 自由流人员关系类型Key
	 */
	public static final String PROCESS_FREEPROCESS_PERSONNELALLOCATION_TYPEKEY = "freeProcessPersonnelAllocationTypeKey";
	/**
	 * 子流程业务ID
	 */
	public static final String PROCESS_SUB_TICKET_ID = "bpmnSubTicketId";
	/**
	 * 流程跳转跳转任务原因
	 */
	public static final String PROCESS_JUMP_TASK_REASON = "processJumpTaskReason";
	/**
	 * 参数标记当前是否为撤回的参数名
	 */
	public static final String PROCESS_TO_REVOKED_VARIABLE_NAME = "bpmnToRevokedVariable";
	/**
	 * 参数标记当前是否为退回的参数名
	 */
	public static final String PROCESS_TO_BE_RETURNED_VARIABLE_NAME = "bpmnToBeReturnVariable";
	/**
	 * 参数标记当前是否为自由跳转的参数名
	 */
	public static final String PROCESS_FREE_CHANGE_VARIABLE_NAME = "bpmnFreeChangeVariable";
	/**
	 * 参数标记当前是否为终止流程的参数名
	 */
	public static final String PROCESS_STOP_VARIABLE_NAME = "bpmnStopVariable";
	/**
	 * 参数标记当前是否为伪办结流程
	 */
	public static final String PROCESS_PRETEND_FINISH_VARIABLE_NAME = "bpmnPretendFinishProcessDescription";
	/**
	 * 待办任务类型变量名
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_VARIABLE= "task_type_001";

	/**
	 * 虚拟节点Id
	 */
	public static final String PROCESS_DEF_STARTEVENT_USERTASK_NAME = "startevent_userTask";
	/**
	 * 虚拟开始出线Id
	 */
	public static final String PROCESS_DEF_STARTEVENT_FLOW_ID = "startevent_flow";
	/**
	 * 超级管理员userId
	 */
	public static final String PROCESS_DEAL_ADMIN_MAN = "hqWorkFlowAdmin01";
	/**
	 * 线上条件节点处理者部门变量名
	 */
	public static final String PROCESS_DEA_PREV_TRANSACTOR_DEPT = "prevTransactorDept";
	/**
	 * 线上条件节点处理者角色变量名
	 */
	public static final String PROCESS_DEA_PREV_TRANSACTOR_ROLE = "prevTransactorRole";
	/**
	 * 流程标题变量名
	 */
	public static final String PROCESS_DEAL_BPMN_TITLE = "hqWorkFlowTitle01";
	/**
	 * 传阅
	 */
	public static final String BPMN_REDING_TYPE_HQBPMN_TRANFORMREADING = "hqbpmn_tranformReading";
	/**
	 * 阅办
	 */
	public static final String BPMN_REDING_TYPE_HQBPMN_READING = "hqbpmn_reading";
	/**
	 * 自由流的分类
	 */
	public static final String BPMN_FREE_PROCESS = "hqbpmnFreeProcess";
	/**
	 * 启动流程选项名称
	 */
	public static final String PROCESS_DEF_BATCH_FLOW_NAME = "启动";
	/**
	 * 补正流程选项名称
	 */
	public static final String PROCESS_DEF_USERTASK_RU_SUPPLEMENT = "提交补正";
	
	/**
	 * 批量处理类型名称
	 */
	public static final String PROCESS_FLOW_BATHCOMPLETEVARIABLE = "bathCompleteVariable";
	
	/**
	 * 会签时每个参与会签的用户传的变量名，用以和会签节点配合使用以区分以下
	 * 会签的三种结果：通过，不通过(流程结束)、  退回修改(退回到发起人)
	 * (PPP项目组使用)
	 */
	public static final String PROCESS_COUNTERSIGN_VARIABLE = "countersignVariable";
	
	
	/*------------------------流程连接线类型------------------------*/
	/**
	 * 扩展连线普通连线
	 */
	public static final int PROCESS_DEF_SEQUENCEEXTEND_TYPE_GENERAL_SEQUENCEEXTEND = 0;	
	/**
	 * 扩展连线会签连线
	 */
	public static final int PROCESS_DEF_SEQUENCEEXTEND_TYPE_COUNTERSIGN_SEQUENCEEXTEND = 1;	
	/**
	 * 开始节点连线
	 */
	public static final int PROCESS_DEF_SEQUENCEEXTEND_TYPE_START_SEQUENCEEXTEND = 2;
	
	
	/*------------------------扩展任务类型------------------------*/
	/**
	 * 扩展任务类型开始节点
	 */
	public static final int PROCESS_DEF_TASK_TYPE_STARTEVENT = 0;
	/**
	 * 扩展任务类型普通任务节点
	 */
	public static final int PROCESS_DEF_TASK_TYPE_USERTASK = 1;
	/**
	 * 扩展任务类型子流程
	 */
	public static final int PROCESS_DEF_TASK_TYPE_SUBPROCESS = 2;
	/**
	 * 扩展任务类型排他节点
	 */
	public static final int PROCESS_DEF_TASK_TYPE_EXCLUSIVEGATEWAY = 3;
	/**
	 * 扩展任务类型会签节点
	 */
	public static final int PROCESS_DEF_TASK_TYPE_COUNTERSIGN = 4;
	/**
	 * 扩展任务类型引用子流程
	 */
	public static final int PROCESS_DEF_TASK_TYPE_CALLACTIVITY = 5;
	
	/**
	 * 扩展任务类型结束节点
	 */
	public static final int PROCESS_DEF_TASK_TYPE_ENDEVENT = 6;
	/**
	 * 扩展任务类型虚拟用户任务节点
	 */
	public static final int PROCESS_DEF_TASK_TYPE_VIRTUAL_USERTASK = 7;
	
	
	
	/*------------------------待办任务类型------------------------*/
	/**
	 * 待办类型-正常待办任务
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_NORMAL= "0";
	/**
	 * 待办类型-委托
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_DELEGATE= "1";
	/**
	 * 待办类型-转办
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_TRANFORM= "2";
	/**
	 * 待办类型-退回
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_RETURNED= "3";
	/**
	 * 待办类型-撤销
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_REVOKED= "4";
	
	/**
	 * 待办类型-补正
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_RU_SUPPLEMENT= "5";
	/**
	 * 待办类型-补正后
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_HI_SUPPLEMENT= "6";
	/**
	 * 待办类型-内部会签
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_INNERCOUNTERSIGN= "7";
	
	/**
	 * 待办类型-抽取
	 */
	public static final String PROCESS_TASK_DEAL_TYPE_EXTRACT = "8";
	
	
	/*------------------------流程状态值------------------------*/
	/**
	 * 子流程撤销时标识
	 */
	public static final String PROCESS_ACT_TASK_REVOKED_EXECUTION_ID = "01";
	/**
	 * 流程模板定义版本启用标识
	 */
	public static final int PROCESS_DEF_VERSION_STATE_OF_ENABLE = 1;
	/**
	 * 传阅标识
	 */
	public static final String BPMN_REDING_TYPE_READING = "1";
	/**
	 * 阅办标识
	 */
	public static final String BPMN_REDING_TYPE_READING_DEAL = "2";
	
	/*------------------------流程任务完成(创建)动作类型------------------------*/
	/**
	 * 跳转类型
	 */
	public static final String PROCESS_TASK_JUMP_TYPE_NAME = "jump";
	/**
	 * 撤销类型
	 */
	public static final String PROCESS_TASK_REVOKE_TYPE_NAME = "revoked";
	/**
	 * 完成类型
	 */
	public static final String PROCESS_TASK_COMPLETE_TYPE_NAME = "completed";
	/**
	 * 删除类型
	 */
	public static final String PROCESS_TASK_DELETE_TYPE_NAME = "deleted";
	/**
	 * 退回类型
	 */
	public static final String PROCESS_TASK_RETURN_TYPE_NAME = "returned";
	
	/**
	 * 抽取类型
	 */
	public static final String PROCESS_TASK_EXTRACT_TYPE_NAME = "jump";
	/**
	 * 正常创建类型
	 */
	public static final String PROCESS_TASK_CREATE_TYPE_NAME = "created";
	
	/**
	 * 挂起类型
	 */
	public static final String PROCESS_TASK_SUSPEND_TYPE_NAME = "suspended";
	
	/**
	 * 挂起类型
	 */
	public static final String PROCESS_TASK_ONESTEPCOMPLETE_TYPE_NAME = "onestepcomplete";
	
	
	/**
	 * 抽取类型
	 */
	//public static final String PROCESS_TASK_CREATE_TYPE_NAME = "create";
	
	/*------------------------流程任务创建类型------------------------*/
	
	/**
	 * 正常创建类型
	 */
	//public static final String PROCESS_TASK_CREATE_TYPE_NAME = "create";
	/**
	 * 抽取类型
	 */
//	public static final String PROCESS_TASK_CREATE_TYPE_EXTRACT = "extract";
//	/**
//	 * 跳转类型
//	 */
//	public static final String PROCESS_TASK_COMPLETE_TYPE_JUMP = "jump";
//	/**
//	 * 回退类型
//	 */
//	public static final String PROCESS_TASK_CREATE_TYPE_RETURNED = "returned";
//	/**
//	 * 撤销类型
//	 */
//	public static final String PROCESS_TASK_COMPLETE_TYPE_REVOKED = "revoked";
	
	/*------------------------任务动态权限------------------------*/
	/**
	 * 正常创建类型
	 */
	public static final  int PROCESS_TASK_HAS_NO_CANDIDATE = 0;
	/**
	 * 抽取类型
	 */
	public static final  int PROCESS_TASK_HAS_CANDIDATE = 1;
	
	/*------------------------流程状态------------------------*/
	/**
	 * 实例进行中
	 */
	public static final  int PROCESS_STATUS_EXECUTING = 0;
	/**
	 * 实例完成
	 */
	public static final  int PROCESS_STATUS_FINISHED = 1;
	
	/**
	 * 实例伪办结
	 */
	public static final  int PROCESS_STATUS_SUSPENDED = 2;
	
	/*------------------------处理过程常量------------------------*/

	/**
	 * 批处理一次处理个数
	 */
	public static final  int BATCH_PROCESS_MAX_NUM = 256;
	/**
	 * 批处理一次处理个数
	 */
	public static final  int BATCH_SQL_PROCESS_MAX_NUM = 1000;
	//public static final  int BATCH_SQL_PROCESS_MAX_NUM = 2;
	
	
	/*------------------------流程模板批处理类型------------------------*/

	/**
	 * 批处理模板类型全版
	 */
	public static final  int TEMPLATE_BATCH_TYPE_FULL = 0;
	
	/**
	 * 批处理模板类型简版
	 */
	public static final  int TEMPLATE_BATCH_TYPE_SIMPLE = 1;
	
	/**
	 * 批处理模板类型混合(需要再查数据库获得模板批处理类型)
	 */
	public static final  int TEMPLATE_BATCH_TYPE_BOTH = 2;
	

	/**
	 * 批处理模板类型
	 * if=0; 全版工作流,不再查数据库模板类型
	 * if=1; 简版工作流,不再查数据库模板类型
	 * if=2; 全版和简版混合工作流,需要再查数据库获得模板批处理类型
	 */
	public static final  int WORKFLOW_TEMPLATE_BATCH_TYPE = 2;
	
	/*------------------------用户传参变量------------------------*/

	/**
	 * 用户传参back
	 */
	public static final String BPMN_BATCH_TRANSATION_BACK = "back";
	
	public static String BPMN_DATABASE_TYPE = "";
	/**
	 * 系统属于服务端还是客户端标志
	 */

//	public static final String BPMN_INNER_COUNTERSIGN_OVER_ID = "over_innerCountersign";
//	public static final String BPMN_INNER_COUNTERSIGN_OVER_NAME = "结束会签";
//	public static final String BPMN_INNER_COUNTERSIGN_TRANSFORM_ID = "transform_innerCountersign";
//	public static final String BPMN_INNER_COUNTERSIGN_TRANSFORM_NAME = "转办";
//	public static final String BPMN_INNER_COUNTERSIGN_ID = "transform_innerCountersign";
//	public static final String BPMN_INNER_COUNTERSIGN_TRANSFORM_NAME = "转办";
//  public static final String COUNTERSIGN_COMPLETE_NUMBER = "bpmnCompletedNumberOfCountersign";
//	public static final String COUNTERSIGN_AGREE_NUMBER = "bpmnAgreeNumberOfCountersign";
//	public static final String COUNTERSIGN_DISAGREE_NUMBER = "bpmnDisagreeNumberOfCountersign";

}
