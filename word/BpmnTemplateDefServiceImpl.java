package com.hq.bpmn.templatedef.service.impl;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.IOParameter;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.InclusiveGateway;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.AddIdentityLinkForProcessDefinitionCmd;
import org.activiti.engine.impl.cmd.DeleteDeploymentCmd;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.hq.bpmn.common.bean.PRM;
import com.hq.bpmn.common.bean.ProcessResult;
import com.hq.bpmn.common.util.ActivitiHelper;
import com.hq.bpmn.common.util.JsonUtil;
import com.hq.bpmn.common.util.StringUtil;
import com.hq.bpmn.common.util.TreeJson;
import com.hq.bpmn.exception.BpmnException;
import com.hq.bpmn.processinstance.bean.BpmnCustomButton;
import com.hq.bpmn.processinstance.bean.BpmnProcessConstant;
import com.hq.bpmn.processinstance.bean.BpmnSequenceExtend;
import com.hq.bpmn.processinstance.bean.BpmnSequenceIdentityLink;
import com.hq.bpmn.processinstance.bean.BpmnTaskButtonExtend;
import com.hq.bpmn.processinstance.bean.BpmnTaskExtend;
import com.hq.bpmn.processinstance.dao.BpmnActRuIdentityLinkDao;
import com.hq.bpmn.processinstance.dao.BpmnSequenceExtendDao;
import com.hq.bpmn.processinstance.dao.BpmnSequenceIdentityLinkDao;
import com.hq.bpmn.processinstance.dao.BpmnTaskButtonExtendDao;
import com.hq.bpmn.processinstance.dao.BpmnTaskExtendDao;
import com.hq.bpmn.processinstance.service.CandidateUsersService;
import com.hq.bpmn.templatedef.bean.BpmnActIdentityLinkEntity;
import com.hq.bpmn.templatedef.bean.BpmnActResourceEntity;
import com.hq.bpmn.templatedef.bean.BpmnCode;
import com.hq.bpmn.templatedef.bean.BpmnField;
import com.hq.bpmn.templatedef.bean.BpmnIdGroup;
import com.hq.bpmn.templatedef.bean.BpmnIdUser;
import com.hq.bpmn.templatedef.bean.BpmnTable;
import com.hq.bpmn.templatedef.bean.BpmnTask;
import com.hq.bpmn.templatedef.bean.BpmnTempCategory;
import com.hq.bpmn.templatedef.bean.BpmnTemplateDef;
import com.hq.bpmn.templatedef.dao.BpmnActIdentityLinkEntityDao;
import com.hq.bpmn.templatedef.dao.BpmnActResourceDao;
import com.hq.bpmn.templatedef.dao.BpmnCodeDao;
import com.hq.bpmn.templatedef.dao.BpmnHqGroupDao;
import com.hq.bpmn.templatedef.dao.BpmnTempCategoryDao;
import com.hq.bpmn.templatedef.dao.BpmnTemplateDefDao;
import com.hq.bpmn.templatedef.service.BpmnTableService;
import com.hq.bpmn.templatedef.service.BpmnTempCategoryService;
import com.hq.bpmn.templatedef.service.BpmnTemplateDefService;
import com.hq.bpmn.unify.bean.BpmnFieldPrivilege;
import com.hq.bpmn.unify.bean.BpmnFieldSecu;
import com.hq.bpmn.unify.bean.BpmnTablePrivilege;
import com.hq.bpmn.unify.bean.BpmnTableSecu;
import com.hq.bpmn.unify.bean.BpmnTaskPrivilege;
import com.hq.bpmn.unify.bean.BpmnVariable;
import com.hq.bpmn.unify.dao.BpmnFieldPrivilegeDao;
import com.hq.bpmn.unify.dao.BpmnTablePrivilegeDao;
import com.hq.bpmn.unify.dao.BpmnVariableDao;

/**
 * 修改缓存bug
 * private void updateprocessDefinitionCache(String oldPdId,String newDeploymentId) {

 修改最后两行的逻辑
 private void modifyResourceByDeployId(String deploymentId,
 BpmnTemplateDef bpmnTemplateDef) throws BpmnException,
 UnsupportedEncodingException {

 */

public class BpmnTemplateDefServiceImpl implements BpmnTemplateDefService {
	Logger logger = Logger.getLogger(BpmnTemplateDefServiceImpl.class);
	private BpmnTemplateDefDao bpmnTemplateDefDao;
	private BpmnTaskExtendDao bpmnTaskExtendDao;
	private BpmnSequenceExtendDao bpmnSequenceExtendDao;
	private BpmnVariableDao bpmnVariableDao;
	private ProcessEngine processEngine;
	private BpmnTempCategoryDao bpmnTempCategoryDao;
	private BpmnTablePrivilegeDao bpmnTablePrivilegeDao;
	private BpmnFieldPrivilegeDao bpmnFieldPrivilegeDao;
	private BpmnSequenceIdentityLinkDao bpmnSequenceIdentityLinkDao;
	private BpmnTempCategoryService bpmnTempCategoryService;
	private BpmnActIdentityLinkEntityDao bpmnActIdentityLinkEntityDao;
	private BpmnTaskButtonExtendDao bpmnTaskButtonExtendDao;

	private BpmnActResourceDao bpmnActResourceDao;
	private BpmnHqGroupDao bpmnHqGroupDao;
	private BpmnCodeDao bpmnCodeDao;
	private BpmnTableService bpmnTableService;
	private CandidateUsersService candidateUsersService; 
	private BpmnActRuIdentityLinkDao bpmnActRuIdentityLinkDao;

	public void setBpmnTaskButtonExtendDao(
			BpmnTaskButtonExtendDao bpmnTaskButtonExtendDao) {
		this.bpmnTaskButtonExtendDao = bpmnTaskButtonExtendDao;
	}

	public void setBpmnTableService(BpmnTableService bpmnTableService) {
		this.bpmnTableService = bpmnTableService;
	}

	public void setBpmnCodeDao(BpmnCodeDao bpmnCodeDao) {
		this.bpmnCodeDao = bpmnCodeDao;
	}

	public void setBpmnHqGroupDao(BpmnHqGroupDao bpmnHqGroupDao) {
		this.bpmnHqGroupDao = bpmnHqGroupDao;
	}

	public void setBpmnActResourceDao(BpmnActResourceDao bpmnActResourceDao) {
		this.bpmnActResourceDao = bpmnActResourceDao;
	}

	public void setBpmnActIdentityLinkEntityDao(
			BpmnActIdentityLinkEntityDao bpmnActIdentityLinkEntityDao) {
		this.bpmnActIdentityLinkEntityDao = bpmnActIdentityLinkEntityDao;
	}

	public void setBpmnTempCategoryService(
			BpmnTempCategoryService bpmnTempCategoryService) {
		this.bpmnTempCategoryService = bpmnTempCategoryService;
	}

	public void setBpmnSequenceIdentityLinkDao(
			BpmnSequenceIdentityLinkDao bpmnSequenceIdentityLinkDao) {
		this.bpmnSequenceIdentityLinkDao = bpmnSequenceIdentityLinkDao;
	}

	public void setBpmnTempCategoryDao(BpmnTempCategoryDao bpmnTempCategoryDao) {
		this.bpmnTempCategoryDao = bpmnTempCategoryDao;
	}

	public void setBpmnTemplateDefDao(BpmnTemplateDefDao bpmnTemplateDefDao) {
		this.bpmnTemplateDefDao = bpmnTemplateDefDao;
	}

	public void setBpmnTaskExtendDao(BpmnTaskExtendDao bpmnTaskExtendDao) {
		this.bpmnTaskExtendDao = bpmnTaskExtendDao;
	}

	public void setBpmnSequenceExtendDao(
			BpmnSequenceExtendDao bpmnSequenceExtendDao) {
		this.bpmnSequenceExtendDao = bpmnSequenceExtendDao;
	}

	public void setBpmnVariableDao(BpmnVariableDao bpmnVariableDao) {
		this.bpmnVariableDao = bpmnVariableDao;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public void setBpmnTablePrivilegeDao(
			BpmnTablePrivilegeDao bpmnTablePrivilegeDao) {
		this.bpmnTablePrivilegeDao = bpmnTablePrivilegeDao;
	}

	public void setBpmnFieldPrivilegeDao(
			BpmnFieldPrivilegeDao bpmnFieldPrivilegeDao) {
		this.bpmnFieldPrivilegeDao = bpmnFieldPrivilegeDao;
	}

	public void setCandidateUsersService(CandidateUsersService candidateUsersService) {
		this.candidateUsersService = candidateUsersService;
	}
	
	public void setBpmnActRuIdentityLinkDao(
			BpmnActRuIdentityLinkDao bpmnActRuIdentityLinkDao) {
		this.bpmnActRuIdentityLinkDao = bpmnActRuIdentityLinkDao;
	}

	/**
	 * 查询所有的流程模版定义。
	 * 
	 * @return 流程模版定义集合。
	 */
	public ProcessResult<List<BpmnTemplateDef>> queryTemplateDef() {
		ProcessResult<List<BpmnTemplateDef>> pr = new ProcessResult<List<BpmnTemplateDef>>();
		List<BpmnTemplateDef> list = bpmnTemplateDefDao.selectTemplateDef();
		pr.setResult(list);
		pr.setSuccess(true);
		pr.setMessage(PRM.QUERY_ALL_TEMP_DEF_SUCCESS);
		return pr;
	}

	/**
	 * 根据查询条件查询流程模版定义。
	 * 
	 * @param param
	 *            查询条件
	 * @return 流程模版定义集合。
	 */
	public ProcessResult<List<BpmnTemplateDef>> queryTemplateDefByQueryCondition(
			Map<String, Object> param) {
		ProcessResult<List<BpmnTemplateDef>> pr = new ProcessResult<List<BpmnTemplateDef>>();
		List<BpmnTemplateDef> list = bpmnTemplateDefDao
				.selectTemplateDefByQueryCondition(param);
		pr.setResult(list);
		pr.setSuccess(true);
		pr.setMessage(PRM.QUERY_TEMP_DEF_BY_PARAM_SUCCESS);
		return pr;
	}

	/**
	 * 通过分类查找流程模版定义
	 * 
	 * @param category
	 *            流程分类
	 * @return 流程模版定义集合
	 */
	public ProcessResult<List<BpmnTemplateDef>> queryTemplateDefByCategory(
			String category) {
		ProcessResult<List<BpmnTemplateDef>> pr = new ProcessResult<List<BpmnTemplateDef>>();
		List<BpmnTemplateDef> list = bpmnTemplateDefDao
				.selectTemplateDefByCategory(category);
		pr.setResult(list);
		pr.setSuccess(true);
		pr.setMessage(PRM.QUERY_TEMP_DEF_BY_TYPE_SUCCESS);
		return pr;
	}

	/**
	 * 通过代码类型查询代码记录
	 * 
	 * @param codeType
	 *            代码类型
	 * @return 代码集合
	 */
	public ProcessResult<List<BpmnCode>> queryBpmnCodeByType(String codeType) {
		ProcessResult<List<BpmnCode>> pr = new ProcessResult<List<BpmnCode>>();
		List<BpmnCode> list = bpmnTemplateDefDao.selectBpmnCodeByType(codeType);
		pr.setResult(list);
		pr.setSuccess(true);
		pr.setMessage(PRM.QUERY_HQ_CODE_INFO_BY_CODE_TYPE_SUCCESS);
		return pr;
	}

	public ProcessResult<List<BpmnCode>> getCustomButton(Map<String, Object> buttonType) {
		ProcessResult<List<BpmnCode>> pr = new ProcessResult<List<BpmnCode>>();
		List<BpmnCode> list = bpmnTemplateDefDao
				.selectCustomButtonInBpmnCodeResult(buttonType);
		pr.setResult(list);
		pr.setSuccess(true);
		pr.setMessage(PRM.BPMN_SELECT_TEMP_CUSTOM_BUTTOM_BY_BUTTON_TYPE_SUCCESS);
		return pr;
	}

	/**
	 * 保存流程模版定义。
	 * 
	 * @return
	 */
	public ProcessResult<String> saveTemplateDef(String id, String bytes) {
		return saveTemplateDef(id, bytes, null);
	}

	public ProcessResult<BpmnTemplateDef> queryTemplateDefById(String id) {
		ProcessResult<BpmnTemplateDef> pr = new ProcessResult<BpmnTemplateDef>();
		BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
				.selectTemplateDefById(id);
		if (bpmnTemplateDef == null) {
			pr.setCode(PRM.PROCESS_HAVE_NO_DEF_INFO_CODE);
			pr.setMessage(PRM.PROCESS_HAVE_NO_DEF_INFO);
			pr.setSuccess(false);
		} else {
			byte[] b = bpmnTemplateDef.getContentBytes();
			try {
				String contentBytesStr = new String(b, "GBK");
				bpmnTemplateDef.setContentBytesStr(contentBytesStr);
				pr.setResult(bpmnTemplateDef);
				pr.setSuccess(true);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				pr.setCode(PRM.PROCESS_CODE_TRANS_ERROR_CODE);
				pr.setMessage(PRM.PROCESS_CODE_TRANS_ERROR);
				pr.setSuccess(false);
			}
		}
		return pr;
	}
	
//	private String transformProcessForUserTask(String contentBytesStr){
//		JSONObject jsonObject = JSONObject.fromObject(contentBytesStr);
//		if(null!= jsonObject && null !=jsonObject.get("diagram")){
//			JSONObject nodeArray = (JSONObject) jsonObject.get("diagram");
//			Iterator it = nodeArray.keys();
//			while (it.hasNext()) {
//                String key = (String) it.next();
//                if("bpmn".equals(key)){
//                	JSONObject bpmnObj = (JSONObject) nodeArray.get(key);
//                	if(null!=bpmnObj.get("candidateStartUsers")&&(!"".equals(bpmnObj.getString("candidateStartUsers")) || !"".equals(bpmnObj.getString("candidateStartGroups")))&&bpmnObj.get("candidateHighGradeConfig")==null){
//                		Map<String,Object> map = new HashMap<String,Object>();
//                		map.put("dept", "");
//                		map.put("role", bpmnObj.getString("candidateStartGroups"));
//                		map.put("user", bpmnObj.getString("candidateStartUsers"));
//                		bpmnObj.put("candidateHighGradeConfig",JSONArray.fromObject(map).toString());
//                		bpmnObj.put("candidateHighGradeConfigShowValue",JSONArray.fromObject(map).toString());
//                	}
//                	break;
//                }
//			}
//		}
//		if(null!= jsonObject && null !=jsonObject.get("nodes")){
////			JSONArray nodeArray = jsonObject.getJSONArray("nodes");
//			JSONObject nodeArray = (JSONObject) jsonObject.get("nodes");
//			Iterator it = nodeArray.keys();
//			while (it.hasNext()) {
//                String key = (String) it.next();  
//                JSONObject nodeObj = (JSONObject) nodeArray.get(key);
//                if(null!=nodeObj.get("general")&&((JSONObject)nodeObj.get("general")).get("roleAndDep")==null){
//					JSONObject generalObj = (JSONObject)nodeObj.get("general");
//					Map<String,Object> map = new HashMap<String,Object>();
//					List<BpmnProcessor> list = new ArrayList<BpmnProcessor>();
//					if(null != generalObj.get("candidateUsers")&&!"".equals(generalObj.getString("candidateUsers"))){
//						BpmnProcessor bpmnProcessor = new BpmnProcessor();
//						bpmnProcessor.setId(generalObj.getString("candidateUsers"));
//						bpmnProcessor.setName(generalObj.getString("candidateUsersShowValue"));
//						bpmnProcessor.setType(BpmnProcessor.TYPE_USER);
//						bpmnProcessor.setTypeName("人员");
//						bpmnProcessor.setOrderNo("1");
//						list.add(bpmnProcessor);
//					}
//					if(null != generalObj.get("candidateGroups")&&!"".equals(generalObj.getString("candidateGroups"))){
//						BpmnProcessor bpmnProcessor = new BpmnProcessor();
//						bpmnProcessor.setId(generalObj.getString("candidateGroups"));
//						bpmnProcessor.setName(generalObj.getString("candidateGroupsShowValue"));
//						bpmnProcessor.setType(BpmnProcessor.TYPE_ROLE);
//						bpmnProcessor.setTypeName("角色");
//						bpmnProcessor.setOrderNo("2");
//						list.add(bpmnProcessor);
//					}
//					map.put("processor", JSONArray.fromObject(list).toString());
//					String processorExp = "";
//					if(list.size()>1){
//						for(int i=0;i<list.size();i++){
//							if(i!=0){
//								processorExp +=" or ";
//							}
//							processorExp += "("+i+")";
//						}
//					}
//					map.put("processorExp", processorExp);
//					generalObj.put("roleAndDep",JSONObject.fromObject(map).toString());
//				}
//	        }  
//		}
//		return jsonObject.toString();
//	}

	/**
	 * 根据id删除流程模版定义。
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public ProcessResult<String> deleteTemplateDefById(String id) {
		ProcessResult<String> pr = new ProcessResult<String>();
		if (id != null && !"".equals(id)) {
			BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
					.selectTemplateDefById(id);
			if (bpmnTemplateDef.getDeployState() == 1
					&& bpmnTemplateDef.getVersionState() == 1) {
				pr.setSuccess(false);
				pr.setCode(PRM.BPMN_DELETE_TEMP_IS_FAILURE_CODE);
				pr.setMessage(PRM.BPMN_DELETE_TEMP_IS_FAILURE);
				return pr;
			} else {
				// if(bpmnTemplateDef.getDeploymentId()!=null){
				// ProcessDefinition processDefinition
				// =processEngine.getRepositoryService().createProcessDefinitionQuery().deploymentId(bpmnTemplateDef.getDeploymentId()).singleResult();
				//
				// bpmnSequenceExtendDao.deleteBpmnSequenceExtendById(processDefinition.getId());
				// bpmnTaskExtendDao.deleteBpmnTaskExtend(processDefinition.getId());
				// bpmnSequenceIdentityLinkDao.deleteBpmnSequenceIdentityLinkByProDefId(processDefinition.getId());
				// bpmnFieldPrivilegeDao.deleteFieldPrivilegeByProDefId(processDefinition.getId());
				// bpmnTablePrivilegeDao.deleteTablePrivilegeByProDefId(processDefinition.getId());
				// bpmnVariableDao.deleteBpmnVariableByProDefId(processDefinition.getId());
				//
				// RepositoryService repositoryService =
				// processEngine.getRepositoryService();
				// repositoryService.deleteDeployment(bpmnTemplateDef.getDeploymentId(),
				// true);
				// }
				// bpmnTemplateDefDao.deleteTempDefById(id);

				//deleteBpmnTemplateDef(bpmnTemplateDef);
				if (bpmnTemplateDef.getDeploymentId() != null) {
					RuntimeService runtimeService = processEngine
							.getRuntimeService();
					ProcessInstanceQuery processInstanceQuery = runtimeService
							.createProcessInstanceQuery();
					ProcessDefinition processDefinition = processEngine
							.getRepositoryService()
							.createProcessDefinitionQuery()
							.deploymentId(bpmnTemplateDef.getDeploymentId())
							.singleResult();
					List<ProcessInstance> list = processInstanceQuery
							.processDefinitionId(processDefinition.getId())
							.list();
					if (list.size() > 0) {
						pr.setSuccess(false);
						pr.setCode(PRM.BPMN_DELETE_TEMP_IS_FAILURE2_CODE);
						pr.setMessage(PRM.BPMN_DELETE_TEMP_IS_FAILURE2);
					} else {
						deleteBpmnTemplateDef(bpmnTemplateDef);
						pr.setSuccess(true);
						pr.setMessage(PRM.BPMN_DELETE_TEMP_IS_SUCCESS);
					}
				} else {
					deleteBpmnTemplateDef(bpmnTemplateDef);
					pr.setSuccess(true);
					pr.setMessage(PRM.BPMN_DELETE_TEMP_IS_SUCCESS);
				}
				return pr;
			}

		}
		pr.setSuccess(true);
		pr.setCode(PRM.BPMN_DELETE_TEMP_IS_FAILURE3_CODE);
		pr.setMessage(PRM.BPMN_DELETE_TEMP_IS_FAILURE3);
		return pr;
	}

	public ProcessResult<String> deleteTemplateDefByCategory(String bpmnType)
			throws BpmnException {
		ProcessResult<String> pr = new ProcessResult<String>();
		if (bpmnType != null && !"".equals(bpmnType)) {
			ProcessResult<String> tempPr = bpmnTempCategoryService
					.selectTempCategoryByCategory(bpmnType);
			if (tempPr.isSuccess()) {
				List<BpmnTemplateDef> bpmnTemplateDefList = bpmnTemplateDefDao
						.queryTemplateDefByCategory(bpmnType);
				for (BpmnTemplateDef bpmnTemplateDef : bpmnTemplateDefList) {
					deleteBpmnTemplateDef(bpmnTemplateDef);
				}
				pr.setSuccess(true);
				pr.setMessage(PRM.BPMN_DELETE_TEMP_IS_SUCCESS);
			} else {
				throw new BpmnException("", PRM.BPMN_TEMP_CATEGORY_IS_NULL);
			}
		} else {
			throw new BpmnException("", PRM.BPMN_TEMP_CATEGORY_PARAM_IS_NULL);
		}
		return pr;
	}

	private void deleteBpmnTemplateDef(BpmnTemplateDef bpmnTemplateDef) {
		if (bpmnTemplateDef.getDeploymentId() != null) {
			ProcessDefinition processDefinition = processEngine
					.getRepositoryService().createProcessDefinitionQuery()
					.deploymentId(bpmnTemplateDef.getDeploymentId())
					.singleResult();
			bpmnSequenceExtendDao
					.deleteBpmnSequenceExtendById(processDefinition.getId());
			bpmnTaskExtendDao.deleteBpmnTaskExtend(processDefinition.getId());
			bpmnSequenceIdentityLinkDao
					.deleteBpmnSequenceIdentityLinkByProDefId(processDefinition
							.getId());
			bpmnFieldPrivilegeDao
					.deleteFieldPrivilegeByProDefId(processDefinition.getId());
			bpmnTablePrivilegeDao
					.deleteTablePrivilegeByProDefId(processDefinition.getId());
			bpmnVariableDao.deleteBpmnVariableByProDefId(processDefinition
					.getId());
			RepositoryService repositoryService = processEngine
					.getRepositoryService();
			repositoryService.deleteDeployment(
					bpmnTemplateDef.getDeploymentId(), true);
		}
		bpmnTemplateDefDao.deleteTempDefById(bpmnTemplateDef.getId());
	}

	public ProcessResult<String> modifyVersionStateById(String id,
			int versionState) {
		BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
				.selectTemplateDefById(id);
		ProcessResult<String> pr = new ProcessResult<String>();
		int state = bpmnTemplateDef.getVersionState();
		if (state == versionState) {
			pr.setCode(PRM.BPMN_TEMP_STATE_IS_NOT_CHANGED_CODE);
			pr.setMessage(PRM.BPMN_TEMP_STATE_IS_NOT_CHANGED);
			pr.setSuccess(false);
			return pr;
		} else {
			if (state == 0) {
				if (bpmnTemplateDef.getDeployState() == 0) {
					pr.setCode(PRM.BPMN_TEMP_START_OR_FORBID_FAILURE_CODE);
					pr.setMessage(PRM.BPMN_TEMP_START_OR_FORBID_FAILURE);
					pr.setSuccess(false);
				} else {
					if (versionState == 1) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("category", bpmnTemplateDef.getCategory());
						map.put("versionState", 1);
						List<BpmnTemplateDef> templateDefList = bpmnTemplateDefDao
								.selectTemplateDefByCategoryAndState(map);
						for (BpmnTemplateDef templateDef : templateDefList) {
							templateDef.setVersionState(0);
							bpmnTemplateDefDao
									.updateVersionStateById(templateDef);
						}
						bpmnTemplateDef.setVersionState(versionState);
						bpmnTemplateDefDao
								.updateVersionStateById(bpmnTemplateDef);
					} else {
						bpmnTemplateDef.setVersionState(versionState);
						bpmnTemplateDefDao
								.updateVersionStateById(bpmnTemplateDef);
					}
					pr.setMessage(PRM.BPMN_UPDATE_VERSION_STATE_SUCCESS);
					pr.setSuccess(true);
				}
			} else if (state == 1) {
				if (versionState == 2) {
					pr.setCode(PRM.BPMN_TEMP_FROM_START_TO_FORBID_FAILURE_CODE);
					pr.setMessage(PRM.BPMN_TEMP_FROM_START_TO_FORBID_FAILURE);
					pr.setSuccess(false);
				} else {
					bpmnTemplateDef.setVersionState(versionState);
					bpmnTemplateDefDao.updateVersionStateById(bpmnTemplateDef);
					pr.setMessage(PRM.BPMN_UPDATE_VERSION_STATE_SUCCESS);
					pr.setSuccess(true);
				}
			} else if (state == 2) {
				if (versionState == 1) {
					pr.setCode(PRM.BPMN_TEMP_FROM_FORBID_TO_START_FAILURE_CODE);
					pr.setMessage(PRM.BPMN_TEMP_FROM_FORBID_TO_START_FAILURE);
					pr.setSuccess(false);
				} else {
					bpmnTemplateDef.setVersionState(versionState);
					bpmnTemplateDefDao.updateVersionStateById(bpmnTemplateDef);
					pr.setMessage(PRM.BPMN_UPDATE_VERSION_STATE_SUCCESS);
					pr.setSuccess(true);
				}
			}
			return pr;
		}

	}

	@Override
	public ProcessResult<List<BpmnTempCategory>> deleteCategoryValidateByCategory(
			String Category) {
		ProcessResult<List<BpmnTempCategory>> pr = new ProcessResult<List<BpmnTempCategory>>();
		if (Category != null && !"".equals(Category)) {
			List<BpmnTemplateDef> listdef = bpmnTemplateDefDao
					.queryTemplateDefByCategory(Category);
			if (listdef == null || listdef.size() == 0) {
				bpmnTempCategoryDao.deleteTempCategoryByCategory(Category);
				pr.setSuccess(true);
				pr.setResult(bpmnTempCategoryDao.selectTempCategory());
				pr.setMessage(PRM.BPMN_DELETE_TEMP_CATEGORY_BY_CATEGORY_SUCCESS);
				return pr;
			} else {
				pr.setSuccess(false);
				pr.setResult(bpmnTempCategoryDao.selectTempCategory());
				pr.setCode(PRM.BPMN_DELETE_TEMP_CATEGORY_BY_CATEGORY_FAILURE_CODE);
				pr.setMessage(PRM.BPMN_DELETE_TEMP_CATEGORY_BY_CATEGORY_FAILURE);
				return pr;
			}
		}
		pr.setSuccess(false);
		pr.setCode(PRM.BPMN_TEMP_CATEGORY_PARAM_IS_NULL2_CODE);
		pr.setMessage(PRM.BPMN_TEMP_CATEGORY_PARAM_IS_NULL2);
		return pr;
	}

	@Override
	public ProcessResult<String> deployProcessModel(String id, String userId)
			throws BpmnException {
		BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
				.selectTemplateDefById(id);
		ProcessResult<String> pr = new ProcessResult<String>();
		if (bpmnTemplateDef.getDeployState() == 1) {
			pr.setSuccess(false);
			pr.setMessage(PRM.PROCESS_DEF_DEPLOYMENT_SUCCESS);
		} else {
			ProcessResult<Map<BpmnModel, String>> deployProcessByStr = deployProcessByStr(
					bpmnTemplateDef, 1);
			pr.setSuccess(deployProcessByStr.isSuccess());
			pr.setMessage(deployProcessByStr.getMessage());
		}
		return pr;
	}

	private BpmnTemplateDef transformTemplateDefByStr(String id, String jsonStr) {
		BpmnTemplateDef bpmnTemplateDef = new BpmnTemplateDef();
		JSONObject json = JSONObject.fromObject(jsonStr);
		StringBuffer strBuffer = new StringBuffer(jsonStr);
		strBuffer.indexOf("version");
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			if (k.equals("title")) {
				bpmnTemplateDef.setName(v.toString());
			} else if (k.equals("diagram")) {
				JSONObject jsonObj = JSONObject.fromObject(v.toString());
				for (Object key : jsonObj.keySet()) {
					if (key.toString().equals("bpmn")) {
						@SuppressWarnings("unchecked")
						Map<String, String> vnode = (Map<String, String>) jsonObj
								.get(key);
						bpmnTemplateDef.setCategory(vnode.get("categroy")
								.toString());
						String version = vnode.get("version");
						int versionInt = "".equals(version) ? 1 : Integer
								.valueOf(version);
						bpmnTemplateDef.setVersion(versionInt);
						bpmnTemplateDef.setCreateTime(new Date());
						bpmnTemplateDef.setDeployState(Integer.valueOf(0));
						bpmnTemplateDef.setVersionState(Integer.valueOf(0));
						bpmnTemplateDef.setTableIds(vnode
								.get("bpmnBusinessForms"));
						bpmnTemplateDef.setTableNames(vnode
								.get("bpmnBusinessFormsShowValue"));
					}
				}
			} else if (k.equals("initNum")) {
				bpmnTemplateDef.setInitNum(Integer.valueOf(v.toString()));
			}
		}
		bpmnTemplateDef.setId(id);
		byte[] b = null;
		try {
			BASE64Encoder encode = new BASE64Encoder();
			String base64 = encode.encode(jsonStr.getBytes("GBK"));
			BASE64Decoder decode = new BASE64Decoder();
			b = decode.decodeBuffer(base64);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bpmnTemplateDef.setContentBytes(b);
		return bpmnTemplateDef;
	}
	
	@SuppressWarnings("unused")
	private SubProcess json2SubProcess(String id){
		BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
				.selectTemplateDefById(id);
		return null;
	}

	public ProcessResult<Map<BpmnModel, String>> deployProcessByStr(
			BpmnTemplateDef bpmnTemplateDef, int deployOrReDeployState)
			throws BpmnException {
		// 部署
		ProcessResult<Map<BpmnModel, String>> pr = new ProcessResult<Map<BpmnModel, String>>();
		byte[] b = bpmnTemplateDef.getContentBytes();
		int deployState = bpmnTemplateDef.getDeployState();
		BpmnModel model = new BpmnModel();
		Process process = new Process();
		String categroy = "";
		@SuppressWarnings("unused")
		String procDefKey = "";
		List<BpmnVariable> list_variable = new ArrayList<BpmnVariable>();
		List<BpmnTaskExtend> list_task = new ArrayList<BpmnTaskExtend>();
		List<BpmnTaskButtonExtend> list_taskButton = new ArrayList<BpmnTaskButtonExtend>();
		List<BpmnSequenceExtend> list_sequence = new ArrayList<BpmnSequenceExtend>();
		List<BpmnTableSecu> bpmnTableSecuList = new ArrayList<BpmnTableSecu>();
		List<BpmnFieldSecu> bpmnFieldSecuList = new ArrayList<BpmnFieldSecu>();
		List<BpmnSequenceIdentityLink> sequenceIdentityLinkList = new ArrayList<BpmnSequenceIdentityLink>();
		Map<String, String> lineMap = new HashMap<String, String>();
		List<ExclusiveGateway> extendExclusiveGateways = new ArrayList<ExclusiveGateway>();
		Set<String> callActivitySet = new HashSet<String>();
		List<FlowElement> flowElementList = new ArrayList<FlowElement>();
		List<UserTask> extendCountersign = new ArrayList<UserTask>();
		Map<String, JSONArray> subProcessMap = new HashMap<String, JSONArray>();
		Map<String, String> subProcessKeyAndName = new HashMap<String, String>();
		boolean subProcessJsonGeneral = false;
		@SuppressWarnings("unused")
		boolean hasStartevent = true;
		String userIdAndRoleIdAndDeptIds = "";
		String userAndRoleIdAndDeptIdStarter = "";
		Map<String, String> subProcessStrategy = new HashMap<String, String>();
		Map<String, String> subProcessCallElement = new HashMap<String, String>();
		String contentBytesStr = "";
		List<String> subSequenceList = new ArrayList<String>();
		Map<String, ProcessDefinition> selectSubBpmnTemplateDef = new HashMap<String, ProcessDefinition>();

		Set<String> userSet = new HashSet<String>();
		Set<String> groupSet = new HashSet<String>();
		try {
			contentBytesStr = new String(b, "GBK");
			JSONObject json = JSONObject.fromObject(contentBytesStr);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				if (k.equals("id")) {
				} else if (k.equals("title")) {
					process.setName(v.toString());
				} else if (k.equals("diagram")) {
					JSONObject jsonObj = JSONObject.fromObject(v.toString());
					for (Object key : jsonObj.keySet()) {
						@SuppressWarnings("unchecked")
						Map<String, String> vnode = (Map<String, String>) jsonObj
								.get(key);
						if (key.toString().equals("bpmn")) {
							//categroy = vnode.get("categroy").toString();
							categroy = bpmnTemplateDef.getCategory();
							
							if (vnode.get("procDefKey") == null || "".equals(vnode.get("procDefKey").toString())) {
								pr.setSuccess(false);
								pr.setCode(PRM.BPMN_PRO_DEF_KEY_IS_NULL_CODE);
								pr.setMessage(PRM.BPMN_PRO_DEF_KEY_IS_NULL);
								return pr;
							}
							procDefKey = vnode.get("procDefKey").toString();
							process.setId(vnode.get("procDefKey").toString());
							process.setDocumentation(vnode.get("document")
									.toString());
							/*Set<String> userSet = new HashSet<String>();
							Set<String> groupSet = new HashSet<String>();*/
							  
							userSet.addAll(strToList(
									vnode.get("candidateStartUsers")
											.toString(), ","));
							groupSet.addAll(strToList(
									vnode.get("candidateStartGroups")
									.toString(), ","));
							if (vnode.get("candidateHighGradeConfig") != null && !"".equals(vnode.get("candidateHighGradeConfig"))) {
								JSONArray jsonArray = JSONArray.fromObject(vnode.get("candidateHighGradeConfig"));
								Object processorObject = null;
								Object processorExp = null;
								String candidateStartUsers = "";
								String candidateStartGroups = "";
								for (Object object : jsonArray) {
									JSONObject fromObject = JSONObject.fromObject(object);
									if(fromObject.containsKey("dept") && fromObject.containsKey("role") && fromObject.containsKey("user") && null == fromObject.get("processor")){
										JSONObject jsonObject = JSONObject.fromObject(object);
										if (jsonObject.get("user") != null
												&& !"".equals(jsonObject
														.get("user"))) {
											candidateStartUsers = jsonObject
													.getString("user");
											userIdAndRoleIdAndDeptIds = candidateStartUsers;
											userSet.addAll(strToList(
													candidateStartUsers, ","));
										}
										if (jsonObject.get("dept") != null
												&& !"".equals(jsonObject
														.get("dept"))) {
											candidateStartGroups = jsonObject
													.getString("dept");
											userIdAndRoleIdAndDeptIds = userIdAndRoleIdAndDeptIds + "," + candidateStartGroups;
											groupSet.addAll(strToList(
													candidateStartGroups, ","));
										}
										if (jsonObject.get("role") != null
												&& !"".equals(jsonObject
														.get("role"))) {
											candidateStartGroups = jsonObject
													.getString("role");
											userIdAndRoleIdAndDeptIds = userIdAndRoleIdAndDeptIds + "," + candidateStartGroups;
											groupSet.addAll(strToList(
													candidateStartGroups, ","));
										}
									}else {
										processorObject = fromObject.get("processor");
										processorExp = fromObject.get("processorExp");
									}
								}
								if (null != processorObject && !"".equals(processorObject)) {
									for (Object object : JSONArray.fromObject(processorObject)) {
										JSONObject jsonObj1 = JSONObject.fromObject(object);
										String id = jsonObj1.get("id").toString();
										String type = jsonObj1.get("type").toString();
										String name = jsonObj1.get("name").toString();
										System.out.println(111);
										if (type.equals("3") && "".equals(candidateStartUsers)) {
											candidateStartUsers = id;
										}else if(type.equals("3") && !"".equals(candidateStartUsers)){
											candidateStartUsers +=  "," + id;
										}else if ((type.equals("1") || type.equals("2")) && "".equals(candidateStartGroups)) {
											candidateStartGroups = id;
										}else if ((type.equals("1") || type.equals("2")) && !"".equals(candidateStartGroups)) {
											candidateStartGroups +=  "," + id;
										}
									}
									if (!"".equals(candidateStartGroups) ) {
										groupSet.addAll(strToList(
												candidateStartGroups, ","));
									}
									if (!"".equals(candidateStartUsers) ) {
										userSet.addAll(strToList(
												candidateStartUsers, ","));
									}
								}
							}
							if (vnode.get("candidateHighGradeConfigTempValue") != null && !"".equals(vnode.get("candidateHighGradeConfigTempValue"))) {
								JSONArray jsonObjBpmn = JSONArray
										.fromObject(vnode
												.get("candidateHighGradeConfigTempValue"));
								for (Object object : jsonObjBpmn) {
									JSONObject jsonObject = JSONObject
											.fromObject(object);
									if (jsonObject.get("processor") != null) {
										userAndRoleIdAndDeptIdStarter += jsonObject
												.get("processor");
									}
								}
							}
							process.setCandidateStarterUsers(new ArrayList<String>(
									userSet));
							process.setCandidateStarterGroups(new ArrayList<String>(
									groupSet));
							// 扩展信息
//							List<String> queryBpmnUserByUserAndGroupByList = queryBpmnUserByUserAndGroupByList(
//									strToList(vnode.get("candidateStartUsers")
//											.toString(), ","),
//									strToList(vnode.get("candidateStartGroups")
//											.toString(), ","));
//							String fireProcessor = JSONArray.fromObject(
//									queryBpmnUserByUserAndGroupByList)
//									.toString();
//							bpmnTaskExtend.setProcessor(fireProcessor);
//							bpmnTaskExtend.setProcessorExpression(categroy);
						} else if (key.toString().equals("variable")) {// 变量
							JSONArray jsonstr = JSONArray.fromObject(vnode
									.get("variable"));
							BpmnVariable[] array = (BpmnVariable[]) JSONArray
									.toArray(jsonstr, BpmnVariable.class);
							list_variable = Arrays.asList(array);
						} else if (key.toString().equals("listener")) {// listener
							JSONArray jsonListener = JSONArray.fromObject(vnode
									.get("bpmnListener"));
							List<ActivitiListener> listeners = new ArrayList<ActivitiListener>();
							if (null != jsonListener) {
								ActivitiListener listener;
								for (int i = 0; i < jsonListener.size(); i++) {
									JSONObject executionlist = (JSONObject) jsonListener
											.get(i);
									listener = new ActivitiListener();
									listener.setId((String) executionlist
											.get("id"));
									listener.setEvent((String) executionlist
											.get("event"));
									String LisImpType = (String) executionlist
											.get("type");
									String str = (LisImpType == null) ? ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION
											: (LisImpType.equals("class") ? ImplementationType.IMPLEMENTATION_TYPE_CLASS
													: ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
									listener.setImplementation((String) executionlist
											.get("listenerImplementation"));
									listener.setImplementationType(str);
									JSONArray fields = JSONArray.fromObject(executionlist
											.get("field"));
									List<FieldExtension> fieldExtensions = new ArrayList<FieldExtension>();
									FieldExtension fieldExtension;
									for (int j = 0; j < fields.size(); j++) {
										JSONObject field = (JSONObject) fields.get(j);
										fieldExtension = new FieldExtension();
										fieldExtension
												.setFieldName((String) field.get("fieldName"));
										fieldExtension.setStringValue(field.get("stringValue")
												.toString());
										fieldExtension.setExpression((String) field
												.get("expression"));
										fieldExtensions.add(fieldExtension);
									}
									listener.setFieldExtensions(fieldExtensions);
									listeners.add(listener);
								}
								process.setExecutionListeners(listeners);
							}
						}

					}
				} else if (k.equals("nodes")) {
					JSONObject jsonObj = JSONObject.fromObject(v.toString());
					for (Object knode : jsonObj.keySet()) {
						@SuppressWarnings("unchecked")
						Map<String, String> vnode = (Map<String, String>) jsonObj
								.get(knode);
						String type = vnode.get("type").toString();
						if (type.equals("StartEvent")) {
							StartEvent startEvent = new StartEvent();
							startEvent.setId(knode.toString());
							startEvent.setName(vnode.get("name").toString());
							if (vnode.get("extendConfig") != null
									&& !"".equals(vnode.get("extendConfig"))) {
								JSONObject jsonExtend = JSONObject
										.fromObject(vnode.get("extendConfig"));
								if (jsonExtend != null
										&& !jsonExtend.toString().equals("")) {
									setBigDataBusinessPrivilege(jsonExtend,
											knode.toString(),
											bpmnFieldSecuList,
											bpmnTableSecuList);
									//
									StringBuffer customButtonSb = new StringBuffer();
									boolean containsKey = jsonExtend.containsKey("customButton");
									if(containsKey){
										String customButtonStr = jsonExtend.get("customButton").toString();
										if(customButtonStr != null && !"".equals(customButtonStr)){
											try{//新版本
												JSONArray jsonObjBpmn = JSONArray.fromObject(customButtonStr);
												if (jsonObjBpmn.size() > 0) {
													for (int i = 0; i < jsonObjBpmn.size(); i++) {
														JSONObject jsonObject = JSONObject
																.fromObject(jsonObjBpmn.get(i));
														BpmnTaskButtonExtend bpmnTaskButtonExtend = new BpmnTaskButtonExtend();
														bpmnTaskButtonExtend.setTaskKey(knode
																.toString());
														bpmnTaskButtonExtend
														.setButtonKey(jsonObject.get(
																"buttonKey").toString());
														bpmnTaskButtonExtend
														.setButtonName(jsonObject.get(
																"buttonName")
																.toString());
														bpmnTaskButtonExtend
														.setButtonProcessor(jsonObject
																.get("buttonProcessor")
																.toString());
														if (jsonObject.get("buttonOrder") != null
																&& !"".equals(jsonObject.get(
																		"buttonOrder")
																		.toString())) {
															bpmnTaskButtonExtend
															.setButtonOrder(Integer
																	.parseInt(jsonObject
																			.get("buttonOrder")
																			.toString()));
														}
														if (jsonObject.get("lunchType") != null
																&& !"".equals(jsonObject.get(
																		"lunchType").toString())) {
															bpmnTaskButtonExtend
															.setLunchType(Integer
																	.parseInt(jsonObject
																			.get("lunchType")
																			.toString()));
														}
														list_taskButton
														.add(bpmnTaskButtonExtend);
														//添加虚拟的开始任务节点的信息与开始节点一致
														BpmnTaskButtonExtend bpmnTaskButtonExtend1 = new BpmnTaskButtonExtend();
														PropertyUtils.copyProperties(bpmnTaskButtonExtend1, bpmnTaskButtonExtend);
														bpmnTaskButtonExtend1.setTaskKey(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME);
														list_taskButton
														.add(bpmnTaskButtonExtend1);
														if (i > 0) {
															customButtonSb.append(",");
														}
														customButtonSb.append(jsonObject.get(
																"buttonKey").toString());
													}
												}
											}catch(Exception e){
											}
										}
										jsonExtend.put("customButton",
												customButtonSb.toString());
										//
										//BpmnTaskExtend bpmnTaskExtend = new BpmnTaskExtend();
										BpmnTaskExtend bpmnTaskExtend = (BpmnTaskExtend) JSONObject
												.toBean(jsonExtend, BpmnTaskExtend.class);
										String toBeRevoked = jsonExtend.get("toBeRevoked")+"";
										bpmnTaskExtend.setToBeRevoked("true".equals(toBeRevoked)?true:false);
										//bpmnTaskExtend.setProcessor(userIdAndRoleIdAndDeptIds);
										bpmnTaskExtend.setProcessor(userAndRoleIdAndDeptIdStarter);
										bpmnTaskExtend.setTaskKey("startevent");
										list_task.add(bpmnTaskExtend);
										BpmnTaskExtend bpmnTaskExtend1 = new BpmnTaskExtend();
										PropertyUtils.copyProperties(bpmnTaskExtend1, bpmnTaskExtend);
										bpmnTaskExtend1.setTaskKey(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME);
										list_task.add(bpmnTaskExtend1);
									}
								}
							}
							flowElementList.add(startEvent);
						} else if (type.equals("EndEvent")) {
							EndEvent endEvent = new EndEvent();
							endEvent.setId(knode.toString());
							endEvent.setName(vnode.get("name").toString());
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							if(!jsonGeneral.isNullObject()){
								endEvent.setDocumentation(jsonGeneral.get(
										"document").toString());
							}
							flowElementList.add(endEvent);
						} else if (type.equals("ExclusiveGateway")) {
							ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
							exclusiveGateway.setId(knode.toString());
							exclusiveGateway.setName(vnode.get("name")
									.toString());
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("general"));
							String defaultFlow = jsonExtend.get("defaultFlow") == null ? ""
									: jsonExtend.get("defaultFlow").toString();
							exclusiveGateway.setDefaultFlow(defaultFlow);
							flowElementList.add(exclusiveGateway);
						} else if (type.equals("UserTask")) {
							UserTask userTask = new UserTask();
							userTask.setName(vnode.get("name").toString());
							userTask.setId(knode.toString());
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							String processor = "";
							String processorExp = "";
							String showType = "";
							if (jsonGeneral.get("showType") != null
									&& jsonGeneral.get("showType").toString()
											.length() > 0) {
								showType += jsonGeneral.get("showType");
							}
							if (jsonGeneral.get("roleAndDep") != null
									&& jsonGeneral.get("roleAndDep").toString()
											.length() > 0) {
								JSONArray jsonObjBpmn = JSONArray
										.fromObject(jsonGeneral
												.get("roleAndDep"));
								for (Object object : jsonObjBpmn) {
									JSONObject jsonObject = JSONObject
											.fromObject(object);
									if (jsonObject.get("processor") != null) {
										processor += jsonObject
												.get("processor");
									}
									if (jsonObject.get("processorExp") != null) {
										processorExp += jsonObject
												.get("processorExp");
									}
								}
							}
							userTask.setCandidateUsers(strToList(jsonGeneral
									.get("candidateUsers").toString(), ","));
							userTask.setCandidateGroups(strToList(jsonGeneral
									.get("candidateGroups").toString(), ","));
							userTask.setDefaultFlow(jsonGeneral.get(
									"defaultFlow").toString());
							userTask.setDocumentation(jsonGeneral.get(
									"document").toString());
							JSONObject jsonListener = JSONObject
									.fromObject(vnode.get("listener"));
							if (jsonListener != null && !jsonListener.isEmpty()) {
								List<ActivitiListener> executionListeners = getActivitiListeners(
										jsonListener, "executionListener");
								userTask.setExecutionListeners(executionListeners);
								List<ActivitiListener> taskListeners = getActivitiListeners(
										jsonListener, "taskListener");
								userTask.setTaskListeners(taskListeners);
							}
							flowElementList.add(userTask);
							// 扩展信息
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("extendConfig"));
							setBigDataBusinessPrivilege(jsonExtend,
									knode.toString(), bpmnFieldSecuList,
									bpmnTableSecuList);
							StringBuffer customButtonSb = new StringBuffer();
							String customButtonStr = jsonExtend.get("customButton").toString();
							if(customButtonStr != null && !"".equals(customButtonStr)){
								try{//新版本
									JSONArray jsonObjBpmn = JSONArray.fromObject(customButtonStr);
									if (jsonObjBpmn.size() > 0) {
										for (int i = 0; i < jsonObjBpmn.size(); i++) {
											JSONObject jsonObject = JSONObject
													.fromObject(jsonObjBpmn.get(i));
											BpmnTaskButtonExtend bpmnTaskButtonExtend = new BpmnTaskButtonExtend();
											bpmnTaskButtonExtend.setTaskKey(knode
													.toString());
											bpmnTaskButtonExtend
											.setButtonKey(jsonObject.get(
													"buttonKey").toString());
											bpmnTaskButtonExtend
											.setButtonName(jsonObject.get(
													"buttonName")
													.toString());
											bpmnTaskButtonExtend
											.setButtonProcessor(jsonObject
													.get("buttonProcessor")
													.toString());
											if (jsonObject.get("buttonOrder") != null
													&& !"".equals(jsonObject.get(
															"buttonOrder")
															.toString())) {
												bpmnTaskButtonExtend
												.setButtonOrder(Integer
														.parseInt(jsonObject
																.get("buttonOrder")
																.toString()));
											}
											if (jsonObject.get("lunchType") != null
													&& !"".equals(jsonObject.get(
															"lunchType").toString())) {
												bpmnTaskButtonExtend
												.setLunchType(Integer
														.parseInt(jsonObject
																.get("lunchType")
																.toString()));
											}
											list_taskButton
											.add(bpmnTaskButtonExtend);
											if (i > 0) {
												customButtonSb.append(",");
											}
											customButtonSb.append(jsonObject.get(
													"buttonKey").toString());
										}
									}
								}catch(Exception e){
								}
							}
							jsonExtend.put("customButton",
									customButtonSb.toString());
							BpmnTaskExtend bpmnTaskExtend = (BpmnTaskExtend) JSONObject
									.toBean(jsonExtend, BpmnTaskExtend.class);
							bpmnTaskExtend.setTaskKey(knode.toString());
							bpmnTaskExtend.setProcessor(processor);
							bpmnTaskExtend.setProcessorExpression(processorExp);
							bpmnTaskExtend
									.setTaskType(String
											.valueOf(BpmnProcessConstant.PROCESS_DEF_TASK_TYPE_USERTASK));
							bpmnTaskExtend.setShowType(showType);
							if (jsonExtend.get("enabledAllUser") != null&& !jsonExtend.get("enabledAllUser").toString().equals("")) {
								bpmnTaskExtend.setEnabledAllUser(Boolean
										.parseBoolean(jsonExtend.get(
												"enabledAllUser").toString()));
							}
							list_task.add(bpmnTaskExtend);

						} else if (type.equals("Subprocess")) {
							JSONArray jsonstr = JSONArray.fromObject(vnode
									.get("childNodes"));
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							String subprocessNodeName = vnode.get("name");
							String processor = "";
							String processorExp = "";
							if (jsonGeneral.get("roleAndDep") != null
									&& jsonGeneral.get("roleAndDep").toString()
											.length() > 0) {
								JSONArray jsonObjBpmn = JSONArray
										.fromObject(jsonGeneral
												.get("roleAndDep"));
								for (Object object : jsonObjBpmn) {
									JSONObject jsonObject = JSONObject
											.fromObject(object);
									if (jsonObject.get("processor") != null) {
										processor += jsonObject
												.get("processor");
									}
									if (jsonObject.get("processorExp") != null) {
										processorExp += jsonObject
												.get("processorExp");
									}
								}
							}
							if (jsonGeneral.get("sequence") != null) {
								subProcessJsonGeneral = jsonGeneral
										.get("sequence").toString()
										.equals("yes") ? true : false;
							}
							boolean toBeAllUser=false;
							if (jsonGeneral.get("toAllBackUser") != null) {
								toBeAllUser = jsonGeneral
										.get("toAllBackUser").toString()
										.equals("1") ? true : false;
							}
							BpmnTaskExtend bpmnTaskExtend = new BpmnTaskExtend();
							bpmnTaskExtend.setTaskKey(knode.toString());
							bpmnTaskExtend.setProcessor(processor);
							bpmnTaskExtend.setProcessorExpression(processorExp);
							bpmnTaskExtend
									.setTaskType(String
											.valueOf(BpmnProcessConstant.PROCESS_DEF_TASK_TYPE_SUBPROCESS));
							bpmnTaskExtend.setToBeAllUser(toBeAllUser);
							if (jsonGeneral.get("enabledAllUser") != null&& !jsonGeneral.get("enabledAllUser").toString().equals("")) {
								bpmnTaskExtend.setEnabledAllUser(Boolean
										.parseBoolean(jsonGeneral.get(
												"enabledAllUser").toString()));
							}
							list_task.add(bpmnTaskExtend);
							/*subProcessStrategy.put(knode.toString(),
									jsonGeneral.get("defaultFlow").toString()
											.equals("1") ? "1" : "0");*/
							if (null != jsonGeneral.get("defaultFlow") && !"".equals(jsonGeneral.get("defaultFlow").toString())) {
								String defaultFlow = jsonGeneral.get("defaultFlow").toString();
								if (defaultFlow.equals("1")) {
									subProcessStrategy.put(knode.toString(),"1");
								}else if (defaultFlow.equals("0")) {
									subProcessStrategy.put(knode.toString(),"0");
								}else if (defaultFlow.equals("2")) {
									subProcessStrategy.put(knode.toString(),"2");
								}
							}
							subProcessMap.put(knode.toString(), jsonstr);
							subProcessKeyAndName.put(knode.toString(), subprocessNodeName);
							subProcessCallElement.put("subProcessCallElement", "");
							if (null != jsonGeneral.get("calledElement")
									&& !jsonGeneral.get("calledElement")
											.equals("")) {
								subProcessCallElement.put("subProcessCallElement", jsonGeneral.get("calledElement").toString());
								List<ProcessDefinition> ProcessDefinitionList = processEngine
										.getRepositoryService()
										.createProcessDefinitionQuery()
										.processDefinitionKey(
												jsonGeneral
														.get("calledElement")
														.toString()).list();
								// 通过类别查询流程定义
								ProcessDefinitionQuery processDefinitionQuery = processEngine
										.getRepositoryService()
										.createProcessDefinitionQuery();
								ActivitiHelper activitiHelper = new ActivitiHelper();
								processDefinitionQuery = activitiHelper
										.filterProcessDefinitionQuery(
												ProcessDefinitionList.get(0)
														.getCategory(),
												processDefinitionQuery,
												bpmnTemplateDefDao);
								if(processDefinitionQuery.count()<1){
									pr.setCode(PRM.PROCESS_TEMP_DEF_IS_DISABLED_CODE);
									pr.setMessage(PRM.PROCESS_TEMP_DEF_IS_DISABLED);
									pr.setSuccess(false);
									return pr;
								}
								try{
								ProcessDefinition result = processDefinitionQuery
										.singleResult();
								ProcessResult<BpmnTemplateDef> selectTemplateDefBydeploymentId = selectTemplateDefBydeploymentId(result
										.getDeploymentId());
								if (selectTemplateDefBydeploymentId.isSuccess()) {
									selectSubBpmnTemplateDef.put(
											knode.toString(), result);
								}
								}catch(Exception e){
									throw new BpmnException(PRM.BPMN_VALIDATOR_WITH_FLOWELEMENTS_NULL_CODE,PRM.BPMN_VALIDATOR_WITH_FLOWELEMENTS_NULL+"，请查看引用的子流程是否部署启用");
								}
							}

						} else if (type.equals("Parallelgateway")) {
							ParallelGateway parallelGateway = new ParallelGateway();
							parallelGateway.setId(knode.toString());
							parallelGateway.setName(vnode.get("name")
									.toString());
							flowElementList.add(parallelGateway);
						} else if (type.equals("InclusiveGateway")){
							InclusiveGateway inclusiveGateway = new InclusiveGateway();
							inclusiveGateway.setId(knode.toString());
							inclusiveGateway.setName(vnode.get("name").toString());
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("general"));
							String defaultFlow = jsonExtend.get("defaultFlow") == null ? ""
									: jsonExtend.get("defaultFlow").toString();
							inclusiveGateway.setDefaultFlow(defaultFlow);
							flowElementList.add(inclusiveGateway);
						} else if (type.equals("CallActivity")) {
							CallActivity callActivity = new CallActivity();
							callActivity.setId(knode.toString());
							callActivity.setName(vnode.get("name").toString());
							callActivitySet.add(knode.toString());
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							callActivity.setCalledElement(jsonGeneral.get(
									"calledElement").toString());
							JSONObject parameter = JSONObject.fromObject(vnode
									.get("parameter"));
							callActivity
									.setInParameters(getCallActivityParameter(
											parameter, "inputParameter"));
							callActivity
									.setOutParameters(getCallActivityParameter(
											parameter, "outputParameter"));
							MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
							// 串行或者并行
							loopCharacteristics
									.setSequential(jsonGeneral.get("sequence1")
											.toString().equals("yes") ? true
											: false);
							loopCharacteristics
									.setInputDataItem("${countersignService.setCallActivityCollection(execution,'"
											+ jsonGeneral.get("calledElement")
													.toString() + "')}");
							callActivity.setDefaultFlow(jsonGeneral.get(
									"defaultFlow").toString());
							callActivity.setCalledElement("${assignee}");
							loopCharacteristics.setElementVariable("assignee");
							loopCharacteristics
									.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= "
											+ 1 + "}");
							callActivity
									.setLoopCharacteristics(loopCharacteristics);
							JSONObject jsonListener = JSONObject
									.fromObject(vnode.get("listener"));
							if (jsonListener != null && !jsonListener.isEmpty()) {
								List<ActivitiListener> executionListeners = getActivitiListeners(
										jsonListener, "executionListener");
								callActivity
										.setExecutionListeners(executionListeners);
							}
							flowElementList.add(callActivity);
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("extendConfig"));
							setBigDataBusinessPrivilege(jsonExtend,
									knode.toString(), bpmnFieldSecuList,
									bpmnTableSecuList);
						} else if (type.equals("Countersign")) {
							UserTask userTask = new UserTask();
							userTask.setName(vnode.get("name").toString());
							userTask.setId(knode.toString());
//							userTask.setAssignee("${countersigner}");
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("extendConfig"));
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
							loopCharacteristics
									.setSequential(jsonGeneral.get("sequence")
											.toString().equals("yes") ? true
											: false);
//							loopCharacteristics
//									.setElementVariable("countersigner");
							String processor = "";
							String processorExp = "";
							String processorUsers="";
							if (jsonExtend.get("roleAndDep") != null
									&& jsonExtend.get("roleAndDep").toString()
											.length() > 0) {
								JSONArray jsonObjBpmn = JSONArray
										.fromObject(jsonExtend
												.get("roleAndDep"));
								for (Object object : jsonObjBpmn) {
									JSONObject jsonObject = JSONObject
											.fromObject(object);
									if (jsonObject.get("processor") != null) {
										processor += jsonObject
												.get("processor");
									}
									if (jsonObject.get("processorExp") != null) {
										processorExp += jsonObject
												.get("processorExp");
									}
								}
								//processorUsers = candidateUsersService.getCandidateUserByProcessor( processor,processorExp,knode.toString(),false);
							}
//							if(!"".equals(jsonExtend.get("candidateUser").toString())){
//								processorUsers +=","+jsonExtend.get("candidateUser").toString();
//							}
							// 系统的是candidateUser
							String candidateUser = processorUsers;
							String collectionGroup = jsonExtend.get(
									"collectionGroup").toString();
							// 如果候选人和候选组为空则报错
//							if ("".equals(candidateUser)
//									&& "".equals(collectionGroup)) {
//								pr.setSuccess(false);
//								pr.setMessage(PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS);
//								pr.setCode(PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS_CODE);
//								throw new BpmnException(
//										PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS_CODE,
//										PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS);
//							}
							String candidateUserShowValue = "";
							if (jsonExtend.get("candidateUserShowValue") != null
									&& !"".equals(jsonExtend.get(
											"candidateUserShowValue")
											.toString())) {
								candidateUserShowValue = jsonExtend.get(
										"candidateUserShowValue").toString();
							}
							String collectionGroupShowValue = "";
							if (jsonExtend.get("collectionGroupShowValue") != null
									&& !"".equals(jsonExtend.get(
											"collectionGroupShowValue")
											.toString())) {

								collectionGroupShowValue = jsonExtend.get(
										"collectionGroupShowValue").toString();
							}

							if (candidateUserShowValue.contains("$")) {
								candidateUser = candidateUserShowValue;
							}
							if (collectionGroupShowValue.contains("$")) {
								collectionGroup = collectionGroupShowValue;
							}
							loopCharacteristics.setInputDataItem("${"
									+ userTask.getId()+"_"+ BpmnProcessConstant.PROCESS_COUNTERSIGN_INPUT_DATA
									+ "}");
							String completeConditionType = jsonGeneral.get(
									"completeConditionType").toString();
							String completeCondition = jsonGeneral.get(
									"completeCondition").toString();
							//int count = getCollectionSize(candidateUser, collectionGroup);
							// 结束条件
							double calculation = 1;
							String completeCountersignCondition = "";
							if (completeConditionType != null && !"".equals(completeConditionType)) {
								if (completeConditionType.equals("voteCount")) {
									// 投票的数量
									/*calculation = (Integer
											.valueOf(completeCondition) * 1.0 / count);*/
									/*calculation = (Integer
											.valueOf(completeCondition) * 1.0 / count);*/
									double valueOf = Integer.valueOf(completeCondition) * 1.0;
									loopCharacteristics
									.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= (" + valueOf + " / (" 
											+ userTask.getId()+"_"+ BpmnProcessConstant.PROCESS_COUNTERSIGN_INPUT_DATA
											+ ".size()))" + "}");
									completeCountersignCondition = valueOf + "_voteCount";
								} else if (completeConditionType
										.equals("completevotePercentage")) {
									// 通过百分比
									if (completeCondition.contains("%")) {
										calculation = new Double(
												completeCondition.substring(0,
														completeCondition
																.indexOf("%"))) / 100;
									} else {
										calculation = Double
												.valueOf(completeCondition);
									}
								} else if (completeConditionType
										.equals("votePercentage")) {
									if (completeCondition.contains("%")) {
										calculation = new Double(
												completeCondition.substring(0,
														completeCondition
																.indexOf("%"))) / 100;
									} else {
										calculation = Double
												.valueOf(completeCondition);
									}
								}else if (completeConditionType
										.equals("oneVoteVeto")) {
									//一票否决
									calculation = 1;
								}
							}
							if (!completeConditionType.equals("voteCount")) {
								//不是投票数量
								// 计算
								loopCharacteristics
										.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= "
												+ calculation + "}");
							}
							userTask.setLoopCharacteristics(loopCharacteristics);
							List<FieldExtension> fieldExtensions = new ArrayList<FieldExtension>();
							ActivitiListener listener = new ActivitiListener();
							listener.setId("Countersign");
							listener.setEvent("complete");
							listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
							listener.setImplementation("com.hq.bpmn.processinstance.eventlistener.CountersignExcutionListener");
							FieldExtension fieldExtension;
							fieldExtension = new FieldExtension();
							fieldExtension.setFieldName("positiveTransition");
							fieldExtension.setStringValue(jsonExtend.get(
									"positiveSequence").toString());
							fieldExtensions.add(fieldExtension);
							fieldExtension = new FieldExtension();
							fieldExtension.setFieldName("negativeTransition");
							fieldExtension.setStringValue(jsonExtend.get(
									"negativeSequence").toString());
							fieldExtensions.add(fieldExtension);
							// weight
							if (jsonExtend.get("weightShowValue") != null
									&& !"".equals(jsonExtend
											.get("weightShowValue"))) {
								Map<String, String> map = new HashMap<String, String>();
								fieldExtension = new FieldExtension();
								fieldExtension.setFieldName("weight");
								JSONArray jsonArr = JSONArray
										.fromObject(jsonExtend
												.get("weightShowValue"));
								String user[] = new String[jsonArr.size()];
								String userWeight[] = new String[jsonArr.size()];
								for (int i = 0; i < jsonArr.size(); i++) {
									user[i] = jsonArr.getJSONObject(i)
											.getString("user");
									userWeight[i] = jsonArr.getJSONObject(i)
											.getString("userWeight");
									map.put(user[i], userWeight[i]);
								}
								fieldExtension.setStringValue(map.toString());
								fieldExtensions.add(fieldExtension);
								listener.setFieldExtensions(fieldExtensions);
							}
							// completionConditionSatisfied
							fieldExtension = new FieldExtension();
							fieldExtension
									.setFieldName("completionConditionSatisfied");
							/*fieldExtension.setStringValue(String
									.valueOf(calculation));*/
							if(completeConditionType.equals("oneVoteVeto")){
								fieldExtension.setStringValue("1.0");
							}else if(completeConditionType.equals("voteCount")){
								fieldExtension.setStringValue(String
										.valueOf(completeCountersignCondition));
							}else {
								fieldExtension.setStringValue(String
										.valueOf(calculation));
							}
							fieldExtensions.add(fieldExtension);
							listener.setFieldExtensions(fieldExtensions);
							// approveCondition
							fieldExtension = new FieldExtension();
							fieldExtension.setFieldName("approveCondition");
							/*fieldExtension.setStringValue("".equals(jsonGeneral.get(
									"approveCondition").toString())?"1":jsonGeneral.get(
											"approveCondition").toString());*/
							if(completeConditionType.equals("oneVoteVeto")){
								fieldExtension.setStringValue("1");
							}else {
								fieldExtension.setStringValue("".equals(jsonGeneral.get(
										"approveCondition").toString())?"1":jsonGeneral.get(
												"approveCondition").toString());
							}
							fieldExtensions.add(fieldExtension);
							listener.setFieldExtensions(fieldExtensions);
							List<ActivitiListener> taskListeners = new ArrayList<ActivitiListener>();
							JSONObject jsonListener = JSONObject
									.fromObject(vnode.get("listener"));
							if (jsonListener != null && !jsonListener.isEmpty()) {
								List<ActivitiListener> executionListeners = getActivitiListeners(
										jsonListener, "executionListener");
								userTask.setExecutionListeners(executionListeners);
								taskListeners = getActivitiListeners(
										jsonListener, "taskListener");
							}
							taskListeners.add(listener);
							userTask.setTaskListeners(taskListeners);
							flowElementList.add(userTask);
							extendCountersign.add(userTask);
							ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
							String exclusiveGatewayId = userTask.getId() + "K";
							exclusiveGateway.setId(exclusiveGatewayId);
							exclusiveGateway.setName(exclusiveGatewayId);
							extendExclusiveGateways.add(exclusiveGateway);
							flowElementList.add(exclusiveGateway);
							BpmnTaskExtend bpmnTaskExtend = new BpmnTaskExtend();
							bpmnTaskExtend.setTaskKey(knode.toString());
							bpmnTaskExtend.setProcessor(processor);
							bpmnTaskExtend.setProcessorExpression(processorExp);
							bpmnTaskExtend
									.setTaskType(String
											.valueOf(BpmnProcessConstant.PROCESS_DEF_TASK_TYPE_COUNTERSIGN));

							if (jsonExtend.get("collectionGroup") != null && collectionGroup.length() > 0) {
								bpmnTaskExtend.setCandidateGroup(collectionGroup);
							}
							list_task.add(bpmnTaskExtend);
						}
					}
				} else if (k.equals("lines")) {
					JSONObject jsonObj = JSONObject.fromObject(v.toString());
					List<String> extendCountersignIdList = new ArrayList<String>();
					List<String> extendSequenceFlows = new ArrayList<String>();
					List<String> lineList = new ArrayList<String>();
					lineList.addAll(jsonObj.keySet());
					for (UserTask flowElement : extendCountersign) {
						extendCountersignIdList.add(flowElement.getId());
					}
					for (Object knode : jsonObj.keySet()) {
						@SuppressWarnings("unchecked")
						Map<String, String> vnode = (Map<String, String>) jsonObj
								.get(knode);
						String source = vnode.get("from").toString();
						
						if (!extendCountersignIdList.contains(source)) {
							SequenceFlow flow = new SequenceFlow();
							flow.setId(knode.toString());
							flow.setName(vnode.get("name").toString());
							flow.setSourceRef(vnode.get("from").toString());
							flow.setTargetRef(vnode.get("to").toString());
							lineMap.put(knode.toString(), vnode.get("to")
									.toString());
							JSONObject jsonListener = JSONObject
									.fromObject(vnode.get("listener"));
							if (jsonListener != null && !jsonListener.isEmpty()) {
								List<ActivitiListener> executionListeners = getActivitiListeners(
										jsonListener, "executionListener");
								flow.setExecutionListeners(executionListeners);
							}
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							String sequenceCondition = "";
							boolean flagTemp = false;
							if (jsonGeneral.has("customClassCondition")) {
								String customCondition = jsonGeneral.get("customClassCondition").toString();
								if(customCondition != null && !"".equals(customCondition)){
									 sequenceCondition = BpmnProcessConstant.PROCESS_BUSINESS_CUSTOM_CONDITION_VAR + "_" +customCondition;
								}
								if(jsonGeneral.has("customConditionValue") && jsonGeneral.get("customConditionValue").toString().equals("1")){
									flagTemp = true;
								}
							}
							String condition = jsonGeneral.get("condition")
									.toString();
							/**
							 * 多分枝出线时加默认条件 "bpmnTransitionId=='" + elementId + "'"
							 * 
							 */
							for(int i=0; i<lineList.size(); i++){
								@SuppressWarnings("unchecked")
								Map<String, String> vnode1 = (Map<String, String>) jsonObj
										.get(lineList.get(i));
								String source1 = vnode1.get("from").toString();
								if(source.equals(source1) && !condition.startsWith("${bpmnTransitionId") && condition.indexOf("bpmnTransitionId") < 0 
										&& !knode.toString().equals(lineList.get(i).toString())){
									if(condition != null && !"".equals(condition)){
										condition = condition.replace("${", "").replace("}", "");
										condition = "bpmnTransitionId=='" + knode.toString() + "' and (" + condition + ")";
									}else{
										condition = "bpmnTransitionId=='" + knode.toString() + "' " + condition;
									}
									if (condition != null && !"".equals(condition)  && 
											sequenceCondition != null && !"".equals(sequenceCondition)) {
										if (flagTemp) {
			                            	condition = "("+ condition + ") and (" + sequenceCondition + "==true)";
										}else {
											condition = "("+ condition + ") and (" + sequenceCondition + "==false)";
										}
		                            	
									}
									break;
								}
							}
							
							/**
							 * 判断condition 中是否以  ${ 开头且以  } 结尾 ,没有则默认添加
							 */
							if(!condition.startsWith("${") && !condition.endsWith("}") && !"".equals(condition)){
									String temCondition = "${" + condition + "}";
									condition = temCondition;
							}else if(!condition.startsWith("${") && condition.endsWith("}") && !"".equals(condition)){
								String temCondition = "${" + condition;
								condition = temCondition;
							}else if(condition.startsWith("${") && !condition.endsWith("}") && !"".equals(condition)){
								String temCondition = condition + "}";
								condition = temCondition;
							}
							if(condition != null && !"".equals(condition)){
								System.out.println("最终condition为 ：" + condition);
							}
							/*
							 * 如果批量处理类型不为空，那么添加批量处理类型作为线上条件拼接进去
							 * 
							 * */
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("extendConfig"));
							String batchVariableJsonObject = jsonExtend.get(BpmnProcessConstant.PROCESS_FLOW_BATHCOMPLETEVARIABLE).toString();
							if (null != batchVariableJsonObject && !"".equals(batchVariableJsonObject) && !source.startsWith("startevent")) {
								/**
								 * 当线上条件的变量设置了值的时候,且批量处理类型不为空时，两者才会组合部署到xml中，
								 * 如果线上条件为空，且批量处理类型不为空，批量处理类型不会部署到xml中，
								 * 开始节点的出线上的批量处理类型不会部署到xml中(不管线上条件是否为空)
								 */
								if (condition.indexOf("${") >= 0 && condition.indexOf("}") > 0) {
									String tempString = condition.substring(condition.indexOf("{")+1, condition.indexOf("}"));
									condition = "${(" + tempString + ")" + " and "+BpmnProcessConstant.PROCESS_FLOW_BATHCOMPLETEVARIABLE+"=='" + batchVariableJsonObject.trim() +"'}";
								}
							}
							System.out.println("线上配置批处理选项时最后的condition为:" + condition);
							flow.setConditionExpression(condition);
							process.addFlowElement(flow);
							/*JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("extendConfig"));*/
							BpmnSequenceIdentityLink bpmnSequenceIdentityLink;
							@SuppressWarnings("unused")
							List<String> processorList = new ArrayList<String>();
							if (jsonExtend.get("candidateUserAndGroup") != null
									&& !"".equals(jsonExtend.get(
											"candidateUserAndGroup").toString())) {
								Object candidateUserAndGroup=jsonExtend.get("candidateUserAndGroup");
								JSONArray fromObject = JSONArray.fromObject(candidateUserAndGroup);
								for (Object object : fromObject) {
									JSONObject jsonObject = JSONObject
										.fromObject(object);
									String userString = (String) jsonObject.get("user");
									String roleString = (String) jsonObject.get("role");
									String deptString = (String) jsonObject.get("dept");
									List<String> usersList=new ArrayList<String>();
									List<String> groupsList=new ArrayList<String>();
									
									String[] userStr;
									if (userString != null && !"".equals(userString)) {
										userStr= userString.split(",");
										for (String u : userStr) {
											usersList.add(u);
										}
									}
									//将线上的设置的处理人分别存进组和成员之中，即存组的ID和成员的ID
									//组的ID是deptString和roleString
									String[] roleStr;
									if (roleString != null && !"".equals(roleString)) {
										roleStr= roleString.split(",");
										for (String u : roleStr) {
											groupsList.add(u);
										}
									}
									String[] depStr;
									if (deptString != null && !"".equals(deptString)) {
										depStr= deptString.split(",");
										for (String u : depStr) {
											groupsList.add(u);
										}
									}
									for (String userId : usersList) {
										bpmnSequenceIdentityLink = new BpmnSequenceIdentityLink();
										bpmnSequenceIdentityLink.setUserId(userId);
										bpmnSequenceIdentityLink
												.setSequenceKey(knode.toString());
										sequenceIdentityLinkList
												.add(bpmnSequenceIdentityLink);
									}
									for (String groupId : groupsList) {
										bpmnSequenceIdentityLink = new BpmnSequenceIdentityLink();
										bpmnSequenceIdentityLink.setGroupId(groupId);
										bpmnSequenceIdentityLink
												.setSequenceKey(knode.toString());
										sequenceIdentityLinkList
												.add(bpmnSequenceIdentityLink);
									}
								}
							}
							if (jsonExtend.get("candidateUser") != null
									&& !"".equals(jsonExtend.get(
											"candidateUser").toString())) {
								List<String> userList = strToList(
										jsonExtend.get("candidateUser") == null ? ""
												: jsonExtend.get(
														"candidateUser")
														.toString(), ",");
								for (String userId : userList) {
									bpmnSequenceIdentityLink = new BpmnSequenceIdentityLink();
									bpmnSequenceIdentityLink.setUserId(userId);
									bpmnSequenceIdentityLink
											.setSequenceKey(knode.toString());
									sequenceIdentityLinkList
											.add(bpmnSequenceIdentityLink);
								}
							}
							if (jsonExtend.get("candidateGroup") != null
									&& !"".equals(jsonExtend.get(
											"candidateGroup").toString())) {
								List<String> groupList = strToList(jsonExtend
										.get("candidateGroup") == null ? ""
										: jsonExtend.get("candidateGroup")
												.toString(), ",");
								for (String groupId : groupList) {
									bpmnSequenceIdentityLink = new BpmnSequenceIdentityLink();
									bpmnSequenceIdentityLink
											.setGroupId(groupId);
									bpmnSequenceIdentityLink
											.setSequenceKey(knode.toString());
									sequenceIdentityLinkList
											.add(bpmnSequenceIdentityLink);
								}
							}
							Object object = jsonExtend.get("candidateUserAndGroup");
							if (object!=null&&!("").equals(object)) {
								JSONArray fromObject = JSONArray.fromObject(object);
								jsonExtend.remove("candidateUserAndGroup");
								jsonExtend.put("candidateUserAndGroup", fromObject.get(0));
							}
							if (jsonExtend.get("candidateUserAndGroupShowValue") != null
									&& !"".equals(jsonExtend.get(
											"candidateUserAndGroupShowValue")
											.toString())) {
								jsonExtend.remove("candidateUserAndGroupShowValue");
							}
							//自定义流转线属性start
							String customButtonStr = "";
							if (jsonExtend.get("customProperty") != null
									&& !"".equals(jsonExtend.get(
											"customProperty")
											.toString())) {
								customButtonStr = jsonExtend.get("customProperty").toString();
								jsonExtend.remove("customProperty");
							}
							StringBuffer customButtonSb = new StringBuffer();
							if(customButtonStr != null && !"".equals(customButtonStr)){
								try{//新版本
									JSONArray jsonObjBpmn = JSONArray.fromObject(customButtonStr);
									if (jsonObjBpmn.size() > 0) {
										for (int i = 0; i < jsonObjBpmn.size(); i++) {
											JSONObject jsonObject = JSONObject
													.fromObject(jsonObjBpmn.get(i));
											BpmnTaskButtonExtend bpmnTaskButtonExtend = new BpmnTaskButtonExtend();
											bpmnTaskButtonExtend.setTaskKey(knode
													.toString());
											bpmnTaskButtonExtend
											.setButtonKey(jsonObject.get(
													"buttonKey").toString());
											bpmnTaskButtonExtend
											.setButtonName(jsonObject.get(
													"buttonName")
													.toString());
											/*bpmnTaskButtonExtend
											.setButtonProcessor(jsonObject
													.get("buttonProcessor")
													.toString());*/
											if (jsonObject.get("buttonOrder") != null
													&& !"".equals(jsonObject.get(
															"buttonOrder")
															.toString())) {
												bpmnTaskButtonExtend
												.setButtonOrder(Integer
														.parseInt(jsonObject
																.get("buttonOrder")
																.toString()));
											}
											if (jsonObject.get("lunchType") != null
													&& !"".equals(jsonObject.get(
															"lunchType").toString())) {
												bpmnTaskButtonExtend
												.setLunchType(Integer
														.parseInt(jsonObject
																.get("lunchType")
																.toString()));
											}
											list_taskButton
											.add(bpmnTaskButtonExtend);
											if (i > 0) {
												customButtonSb.append(",");
											}
											customButtonSb.append(jsonObject.get(
													"buttonKey").toString());
										}
									}
								}catch(Exception e){
								}
							}
							BpmnSequenceExtend bpmnSequenceExtend = (BpmnSequenceExtend) JSONObject
									.toBean(jsonExtend,
											BpmnSequenceExtend.class);
							jsonExtend.put("customProperty",
									customButtonSb.toString());
							//自定义流转线属性end							
							if (jsonExtend.get("sortNumber") != null
									&& !"".equals(jsonExtend.get("sortNumber")
											.toString())) {
								bpmnSequenceExtend
										.setSequenceSortNumber(jsonExtend.get(
												"sortNumber").toString());
							}
							if (jsonExtend.get("enabledAllUser") != null
									&& !"".equals(jsonExtend.get(
											"enabledAllUser").toString())) {
								bpmnSequenceExtend.setEnabledAllUser(Boolean
										.parseBoolean(jsonExtend.get(
												"enabledAllUser").toString()));

							}
							bpmnSequenceExtend.setSequenceKey(knode.toString());
							bpmnSequenceExtend
									.setSequenceType(String
											.valueOf(BpmnProcessConstant.PROCESS_DEF_SEQUENCEEXTEND_TYPE_GENERAL_SEQUENCEEXTEND));
							list_sequence.add(bpmnSequenceExtend);
						} else {
							// 开始添加 会签的关口
							// 查获生成的排他关口
							ExclusiveGateway exclusiveGateway = null;
							for (ExclusiveGateway currentExclusiveGateway : extendExclusiveGateways) {
								if (currentExclusiveGateway.getId().equals(
										source + "K")) {
									exclusiveGateway = currentExclusiveGateway;
								}
							}
							// 生成连线
							if (!extendSequenceFlows.contains(exclusiveGateway
									.getId() + "a")) {
								SequenceFlow extendSequenceFlow = new SequenceFlow();
								extendSequenceFlow.setId(exclusiveGateway
										.getId() + "a");
								extendSequenceFlow.setName(exclusiveGateway
										.getName());
								extendSequenceFlow.setSourceRef(vnode.get(
										"from").toString());
								extendSequenceFlow
										.setTargetRef(exclusiveGateway.getId());
								extendSequenceFlows.add(exclusiveGateway
										.getId() + "a");
								process.addFlowElement(extendSequenceFlow);
							}
							SequenceFlow flow = new SequenceFlow();
							flow.setId(knode.toString());
							flow.setName(vnode.get("name").toString());
							flow.setSourceRef(exclusiveGateway.getId());
							flow.setTargetRef(vnode.get("to").toString());
							lineMap.put(knode.toString(), vnode.get("to")
									.toString());
							JSONObject jsonListener = JSONObject
									.fromObject(vnode.get("listener"));
							if (jsonListener != null && !jsonListener.isEmpty()) {
								List<ActivitiListener> executionListeners = getActivitiListeners(
										jsonListener, "executionListener");
								flow.setExecutionListeners(executionListeners);
							}
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							String condition = jsonGeneral.get("condition")
									.toString();
							/**
							 * 多分枝出线时加默认条件 "bpmnTransitionId=='" + elementId + "'"
							 * "bpmnTransitionId=='" + knode.toString() + "'"
							 */
							for(int i=0; i<lineList.size(); i++){
								@SuppressWarnings("unchecked")
								Map<String, String> vnode2 = (Map<String, String>) jsonObj
										.get(lineList.get(i));
								String source2 = vnode2.get("from").toString();
								if(source.equals(source2) && !condition.startsWith("${bpmnTransitionId") && condition.indexOf("bpmnTransitionId") < 0 
										&& !knode.toString().equals(lineList.get(i).toString())){
									if(condition != null && !"".equals(condition)){
										condition = condition.replace("${", "").replace("}", "");
										condition = "bpmnTransitionId=='" + knode.toString() + "' and (" + condition + ")";
									}else{
										condition = "bpmnTransitionId=='" + knode.toString() + "'" + condition;
									}
									System.out.println("会签——检验插入：" + condition);
									break;
								}
							}
							/**
							 * 会签
							 * 判断condition 中是否以  ${ 开头且以  } 结尾 ,没有则默认添加
							 */
							if(!condition.startsWith("${") && !condition.endsWith("}") && !"".equals(condition)){
									String temCondition = "${" + condition + "}";
									condition = temCondition;
							}else if(!condition.startsWith("${") && condition.endsWith("}") && !"".equals(condition)){
								String temCondition = "${" + condition;
								condition = temCondition;
							}else if(condition.startsWith("${") && !condition.endsWith("}") && !"".equals(condition)){
								String temCondition = condition + "}";
								condition = temCondition;
							}
							if(condition != null && !"".equals(condition)){
								System.out.println("最终会签出线condition为 ：" + condition);
							}
							flow.setConditionExpression(condition);
							process.addFlowElement(flow);
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("extendConfig"));
							BpmnSequenceIdentityLink bpmnSequenceIdentityLink;
							if (jsonExtend.get("candidateUser") != null
									&& !"".equals(jsonExtend.get(
											"candidateUser").toString())) {
								List<String> userList = strToList(
										jsonExtend.get("candidateUser") == null ? ""
												: jsonExtend.get(
														"candidateUser")
														.toString(), ",");
								for (String userId : userList) {
									bpmnSequenceIdentityLink = new BpmnSequenceIdentityLink();
									bpmnSequenceIdentityLink.setUserId(userId);
									bpmnSequenceIdentityLink
											.setSequenceKey(knode.toString());
									sequenceIdentityLinkList
											.add(bpmnSequenceIdentityLink);
								}
							}
							if (jsonExtend.get("candidateGroup") != null
									&& !"".equals(jsonExtend.get(
											"candidateGroup").toString())) {
								List<String> groupList = strToList(jsonExtend
										.get("candidateGroup") == null ? ""
										: jsonExtend.get("candidateGroup")
												.toString(), ",");
								for (String groupId : groupList) {
									bpmnSequenceIdentityLink = new BpmnSequenceIdentityLink();
									bpmnSequenceIdentityLink
											.setGroupId(groupId);
									bpmnSequenceIdentityLink
											.setSequenceKey(knode.toString());
									sequenceIdentityLinkList
											.add(bpmnSequenceIdentityLink);
								}
							}
							BpmnSequenceExtend bpmnSequenceExtend = (BpmnSequenceExtend) JSONObject
									.toBean(jsonExtend,
											BpmnSequenceExtend.class);
							bpmnSequenceExtend.setSequenceKey(knode.toString());
							bpmnSequenceExtend
									.setSequenceType(String
											.valueOf(BpmnProcessConstant.PROCESS_DEF_SEQUENCEEXTEND_TYPE_COUNTERSIGN_SEQUENCEEXTEND));
							list_sequence.add(bpmnSequenceExtend);
						}
					}

				}
			}
			List<String> subProcessProcessDefinitionIds = new ArrayList<String>();
			List<String> subProcessKeys = new ArrayList<String>();
			Iterator<String> it = subProcessMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				ProcessDefinition processDefinition = selectSubBpmnTemplateDef
						.get(key);
				if (processDefinition != null) {
					BpmnModel subBpmnModel = processEngine.getRepositoryService()
							.getBpmnModel(processDefinition.getId());
					String strategy = subProcessStrategy.get(key);
					Process selectSubProcess = subBpmnModel.getProcesses().get(
							0);
					subProcessProcessDefinitionIds.add(processDefinition.getId());
					subProcessKeys.add(key);
					SubProcess subProcess = new SubProcess();
					subProcess.setId(key);
					subProcess.setName(subProcessKeyAndName.get(key));
					Collection<FlowElement> flowElements = selectSubProcess
							.getFlowElements();
					String startEventId = "";
					boolean isHasStarteventUsertaskNode = false;
					for (FlowElement flowElement : flowElements) {
						if (flowElement instanceof StartEvent) {
							StartEvent startEvent = (StartEvent) flowElement;
							startEventId = startEvent.getId();
						}
						if(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME.equals(flowElement.getId())){
							isHasStarteventUsertaskNode = true;
						}
					}
					for (FlowElement flowElement : flowElements) {
						if(isHasStarteventUsertaskNode){
							if(flowElement instanceof SequenceFlow){
								if(!startEventId.equals(((SequenceFlow) flowElement).getSourceRef())){
									if(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME.equals(((SequenceFlow) flowElement).getSourceRef())){
										((SequenceFlow) flowElement).setSourceRef(startEventId);
									}
									if(null!=((SequenceFlow) flowElement).getConditionExpression()&&!"".equals(((SequenceFlow) flowElement).getConditionExpression())){
										((SequenceFlow) flowElement).setConditionExpression(((SequenceFlow) flowElement).getConditionExpression().replace(flowElement.getId(), flowElement.getId()+"_"+key));
									}
									flowElement.setId(flowElement.getId()+"_"+key);
									((SequenceFlow) flowElement).setSourceRef(((SequenceFlow) flowElement).getSourceRef()+"_"+key);
									((SequenceFlow) flowElement).setTargetRef(((SequenceFlow) flowElement).getTargetRef()+"_"+key);
									subProcess.addFlowElement(flowElement);
								}
							}
							if(flowElement instanceof UserTask){
								if(!BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME.equals(flowElement.getId())){
									flowElement.setId(flowElement.getId()+"_"+key);
									subProcess.addFlowElement(flowElement);
								}
							}
							if(flowElement instanceof SubProcess){
								SubProcess subProcessInner = (SubProcess) flowElement;
								for (FlowElement flowElementInner : subProcessInner.getFlowElements()) {
									if(flowElementInner instanceof SequenceFlow){
										if(null!=((SequenceFlow) flowElementInner).getConditionExpression()&&!"".equals(((SequenceFlow) flowElementInner).getConditionExpression())){
											((SequenceFlow) flowElementInner).setConditionExpression(((SequenceFlow) flowElementInner).getConditionExpression().replace(flowElementInner.getId(), flowElementInner.getId()+"_"+key));
										}
										((SequenceFlow) flowElementInner).setSourceRef(((SequenceFlow) flowElementInner).getSourceRef()+"_"+key);
										((SequenceFlow) flowElementInner).setTargetRef(((SequenceFlow) flowElementInner).getTargetRef()+"_"+key);
									}
									flowElementInner.setId(flowElementInner.getId()+"_"+key);
								}
								if(null!=subProcessInner.getLoopCharacteristics()&&null!=subProcessInner.getLoopCharacteristics().getInputDataItem()&&!"".equals(subProcessInner.getLoopCharacteristics().getInputDataItem())){
									String inputDataItemStr = subProcessInner.getLoopCharacteristics().getInputDataItem().replace(subProcessInner.getId(), subProcessInner.getId()+"_"+key);
									subProcessInner.getLoopCharacteristics().setInputDataItem(inputDataItemStr);
								}
								subProcessInner.setId(subProcessInner.getId()+"_"+key);
								subProcess.addFlowElement(subProcessInner);
							}
							if(!(flowElement instanceof UserTask) && !(flowElement instanceof SequenceFlow)&& !(flowElement instanceof SubProcess)){
								flowElement.setId(flowElement.getId()+"_"+key);
								subProcess.addFlowElement(flowElement);
							}
						}else{
							flowElement.setId(flowElement.getId()+"_"+key);
							subProcess.addFlowElement(flowElement);
						}
						//subProcess.addFlowElement(flowElement);
					}
					process.addFlowElement(subProcess);
					String candidateUserByTaskKey = "";
					for (BpmnTaskExtend taskExtend : list_task) {
						if (taskExtend != null
								&& taskExtend.getTaskKey().equals(key)&&!"".equals(taskExtend.getProcessor())) {
							candidateUserByTaskKey = candidateUsersService.getCandidateUserByProcessor(taskExtend.getProcessor(),taskExtend.getProcessorExpression(), taskExtend.getTaskKey(), true);
							break;
						}
					}
					MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
					loopCharacteristics.setSequential(subProcessJsonGeneral);
					// 计算
					if (strategy != null && strategy.equals("1")) {
						//等待
						loopCharacteristics
								.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= "
										+ 1 + "}");
					} else if (strategy.equals("2")) {
						//并行不等待
						loopCharacteristics
						.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances == "
								+ 2 + "}");
					}else {
						//不等待
						String callElementStr = subProcessCallElement.get("subProcessCallElement");
						if("".equals(callElementStr)){
							loopCharacteristics
							.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= "
									+ (1 / candidateUserByTaskKey
											.split(",").length) + "}");
						}else{
							loopCharacteristics
							.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= 0.0001}");
						}
					}
					loopCharacteristics.setInputDataItem("${"
							+ key+"_"+ BpmnProcessConstant.PROCESS_SUBPROCESS_INPUT_DATA
							+ "}");
					subProcess.setLoopCharacteristics(loopCharacteristics);
				} else {
					String strategy = subProcessStrategy.get(key);
					SubProcess subProcess = new SubProcess();
					String candidateUserByTaskKey = "";
					for (BpmnTaskExtend taskExtend : list_task) {
						if (taskExtend != null
								&& taskExtend.getTaskKey().equals(key)&&!"".equals(taskExtend.getProcessor())) {
							candidateUserByTaskKey = candidateUsersService.getCandidateUserByProcessor(taskExtend.getProcessor(),taskExtend.getProcessorExpression(), taskExtend.getTaskKey(), true);
							break;
						}
					}
					MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
					loopCharacteristics.setSequential(subProcessJsonGeneral);
					// 计算
					if (strategy != null && strategy.equals("1")) {
						loopCharacteristics
								.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= "
										+ 1 + "}");
					} else if (strategy.equals("2")) {
						//并行不等待
						loopCharacteristics
						.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances == "
								+ 2 + "}");
					}else {
						loopCharacteristics
								.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= "
										+ (1 / candidateUserByTaskKey
												.split(",").length) + "}");
					}
					loopCharacteristics.setInputDataItem("${"
							+ key+"_"+BpmnProcessConstant.PROCESS_SUBPROCESS_INPUT_DATA
							+ "}");
					subProcess.setLoopCharacteristics(loopCharacteristics);
					// 子流程的多实例
					subProcess.setId(key);
					JSONArray value = subProcessMap.get(key);
					for (int i = flowElementList.size() - 1; i > -1; i--) {
						FlowElement flowElement = flowElementList.get(i);
						for (int j = 0; j < value.size(); j++) {
							if (value.get(j).equals(flowElement.getId())) {
								Iterator<FlowElement> iterator = process
										.getFlowElements().iterator();
								while (iterator.hasNext()) {
									FlowElement flowElement2 = (FlowElement) iterator
											.next();
									if (flowElement2 instanceof SequenceFlow) {
										SequenceFlow sequenceFlow = (SequenceFlow) flowElement2;
										if (sequenceFlow.getSourceRef().equals(
												value.get(j))) {
											subProcess
													.addFlowElement(sequenceFlow);
											subSequenceList.add(sequenceFlow
													.getId());

										}
									}
								}
								subProcess.addFlowElement(flowElement);
								flowElementList.remove(flowElement);
							}
						}
					}

					process.addFlowElement(subProcess);
				}

			}
			for (String string : subSequenceList) {
				process.removeFlowElement(string);
			}
			String startEventId = "";
			String startEventName = "";
			for (FlowElement flowElement : flowElementList) {
				process.addFlowElement(flowElement);
				if (flowElement instanceof StartEvent) {
					StartEvent startEvent = (StartEvent) flowElement;
					startEventId = startEvent.getId();
					startEventName = startEvent.getName();
				}
			}
			if(!"".equals(startEventId)){
				UserTask userTask = new UserTask();
				userTask.setName(startEventName);
				userTask.setId(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME);
				process.addFlowElement(userTask);
				boolean isHasDefaultFlow = true;
				for(Object obj:process.getFlowElements()){
					if(obj instanceof SequenceFlow){
						SequenceFlow sequenceFlowElement = (SequenceFlow)obj;
						if(startEventId.equals(sequenceFlowElement.getSourceRef())){
							//sequenceFlowElement.setName(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_FLOW_NAME);
							sequenceFlowElement.setSourceRef(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME);
							if(isHasDefaultFlow){
								userTask.setDefaultFlow(sequenceFlowElement.getId());
								isHasDefaultFlow = false;
							}
						}
						if(startEventId.equals(sequenceFlowElement.getTargetRef())){
							sequenceFlowElement.setTargetRef(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME);
							if(groupSet != null && groupSet.size() > 0){
								for (String groupId : groupSet) {
									List<User> groupUserList = processEngine.getIdentityService().createUserQuery().memberOfGroup(groupId).list();
									if(groupUserList != null && groupUserList.size() > 0){
										for (User user : groupUserList) {
											userSet.add(user.getId());
										}
									}
								}
							}
							userTask.setCandidateUsers(new ArrayList<String>(userSet));
						}
					}
				}
				SequenceFlow startTaskflow = new SequenceFlow();
				startTaskflow.setId(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_FLOW_ID);
				startTaskflow.setName(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_FLOW_ID);
				startTaskflow.setSourceRef(startEventId);
				startTaskflow.setTargetRef(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME);
				process.addFlowElement(startTaskflow);
			}
			model.addProcess(process);
			model.setTargetNamespace(categroy);
			RepositoryService repositoryService = processEngine
					.getRepositoryService();
			String deploymentId = "";
			if (deployOrReDeployState == 1) {
				Deployment deployment = repositoryService.createDeployment()
						.addBpmnModel(categroy + ".bpmn", model)
						.category(categroy).deploy();
				deploymentId = deployment.getId();
			} else if (deployOrReDeployState == 2) {
				deploymentId = bpmnTemplateDef.getDeploymentId();
			}
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery().deploymentId(deploymentId)
					.singleResult();
			if (processDefinition == null) {
				pr.setSuccess(false);
				pr.setCode(PRM.BPMN_PROCESS_TEMP_DEF_IS_NULL_CODE);
				pr.setMessage(PRM.BPMN_PROCESS_TEMP_DEF_IS_NULL);
				return pr;
			}
			String processDefinitionId = processDefinition.getId();
			if (deployOrReDeployState == 2) {
				bpmnTaskExtendDao
						.deleteBpmnTaskExtendByProDefId(processDefinitionId);
				bpmnTaskButtonExtendDao
						.deleteBpmnTaskButtonByPdId(processDefinitionId);
				bpmnSequenceExtendDao
						.deleteBpmnSequenceExtendById(processDefinitionId);
				bpmnTablePrivilegeDao
						.deleteTablePrivilegeByProDefId(processDefinitionId);
				bpmnFieldPrivilegeDao
						.deleteFieldPrivilegeByProDefId(processDefinitionId);
				bpmnVariableDao
						.deleteBpmnVariableByProDefId(processDefinitionId);
				bpmnSequenceIdentityLinkDao
						.deleteBpmnSequenceIdentityLinkByProDefId(processDefinitionId);
			}
			if (null != subProcessProcessDefinitionIds
					&& subProcessProcessDefinitionIds.size() > 0) {
				for (int i = 0; i < subProcessProcessDefinitionIds.size(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("subProcessDefinitionId",
							subProcessProcessDefinitionIds.get(i));
					map.put("subProcessKey",subProcessKeys.get(i));
					map.put("mainProcessDefinitionId", processDefinitionId);
					bpmnTaskExtendDao.insertBpmnTaskExtendByProDefIdMap(map);
					bpmnTaskButtonExtendDao
							.insertBpmnTaskButtonExtendByProDefIdMap(map);
					bpmnSequenceExtendDao
							.insertBpmnSequenceExtendByProDefIdMap(map);
					bpmnTablePrivilegeDao
							.insertTablePrivilegeByProDefIdMap(map);
					bpmnFieldPrivilegeDao
							.insertFieldPrivilegeByProDefIdMap(map);
					bpmnVariableDao.insertBpmnVariableByProDefIdMap(map);
					bpmnSequenceIdentityLinkDao
							.insertBpmnSequenceIdentityLinkByProDefIdMap(map);
				}
			}
			if (null != list_task && list_task.size() > 0) {
				for (BpmnTaskExtend taskExtend : list_task) {
					taskExtend.setProcessDefinitionId(processDefinitionId);
				}
				bpmnTaskExtendDao.insertBpmnTaskExtendBatch(list_task);
			}

			if (null != list_taskButton && list_taskButton.size() > 0) {
				for (BpmnTaskButtonExtend taskButtonExtend : list_taskButton) {
					taskButtonExtend.setProcDefId(processDefinitionId);
				}
				bpmnTaskButtonExtendDao
						.insertBpmnTaskButtonExtendBatch(list_taskButton);
			}

			Set<String> linesSet = new HashSet<String>();
			for (String callActNode : callActivitySet) {
				Iterator<String> lineit = lineMap.keySet().iterator();
				while (lineit.hasNext()) {
					String key = (String) lineit.next();
					if (lineMap.get(key).equals(callActNode)) {
						linesSet.add(key);
					}
				}
			}
			if (null != list_sequence && list_sequence.size() > 0) {
				for (BpmnSequenceExtend sequenceExtend : list_sequence) {
					sequenceExtend.setProcessDefinitionId(processDefinitionId);
					for (String sequenceId : linesSet) {
						if (sequenceId.equals(sequenceExtend.getSequenceKey())) {
							sequenceExtend
									.setSequenceType(String
											.valueOf(BpmnProcessConstant.TURN_SUB_PROCESSINSTANCE));
						}
					}

				}
				bpmnSequenceExtendDao
						.insertBpmnSequenceExtendBatch(list_sequence);
			}
			if (null != bpmnTableSecuList && bpmnTableSecuList.size() > 0) {
				for (BpmnTableSecu bpmnTableSecu : bpmnTableSecuList) {
					bpmnTableSecu.setProDefId(processDefinitionId);
				}
				bpmnTablePrivilegeDao
						.insertTablePrivilegeBatch(bpmnTableSecuList);
			}

			if (null != bpmnFieldSecuList && bpmnFieldSecuList.size() > 0) {
				for (BpmnFieldSecu bpmnFieldSecu : bpmnFieldSecuList) {
					bpmnFieldSecu.setProDefId(processDefinitionId);
				}
				bpmnFieldPrivilegeDao
						.insertFieldPrivilegeBatch(bpmnFieldSecuList);
			}
			if (null != list_variable && list_variable.size() > 0) {
				for (BpmnVariable variable : list_variable) {
					variable.setProcessDefinitionId(processDefinitionId);
				}
				bpmnVariableDao.insertBpmnVariableBatch(list_variable);
			}
			if (null != sequenceIdentityLinkList
					&& sequenceIdentityLinkList.size() > 0) {
				for (BpmnSequenceIdentityLink sequenceIdentityLink : sequenceIdentityLinkList) {
					sequenceIdentityLink
							.setProcessDefinitionId(processDefinitionId);
				}
				bpmnSequenceIdentityLinkDao
						.insertBpmnSequenceIdentityLinkBatch(sequenceIdentityLinkList);
			}
			if (deployOrReDeployState == 1) {
				String id;
				int maxVersion = bpmnTemplateDefDao
						.selectMaxVersionByCategory(bpmnTemplateDef
								.getCategory());
				if (deployState != 1) {
					b = modifyContentBytes(contentBytesStr, bpmnTemplateDef,
							maxVersion + 1);
					bpmnTemplateDef.setDeploymentId(deploymentId);
					bpmnTemplateDef.setContentBytes(b);
					bpmnTemplateDef.setVersion(maxVersion + 1);
					bpmnTemplateDef.setDeployState(1);
					bpmnTemplateDef.setVersionState(0);
					bpmnTemplateDefDao.updateBpmnTempDef(bpmnTemplateDef);
				} else {
					BpmnTemplateDef bpmnTemplateDef_new = new BpmnTemplateDef();
					b = modifyContentBytes(contentBytesStr, bpmnTemplateDef,
							maxVersion + 1);
					bpmnTemplateDef_new = bpmnTemplateDef;
					bpmnTemplateDef_new.setCreateTime(new Date());
					bpmnTemplateDef_new.setContentBytes(b);
					bpmnTemplateDef_new.setDeployState(1);
					bpmnTemplateDef_new.setVersionState(0);
					bpmnTemplateDef_new.setDeploymentId(deploymentId);
					id = bpmnTemplateDefDao.selectSysId();
					bpmnTemplateDef_new.setId(id);
					bpmnTemplateDef_new.setVersion(maxVersion + 1);
					bpmnTemplateDefDao
							.insertBpmnTemplateDef(bpmnTemplateDef_new);
				}
				pr.setSuccess(true);
				pr.setMessage(PRM.PROCESS_DEF_DEPLOYMENT_SUCCESS);

			} else if (deployOrReDeployState == 2) {
				Map<BpmnModel, String> map1 = new HashMap<BpmnModel, String>();
				map1.put(model, categroy);
				pr.setResult(map1);
				pr.setSuccess(true);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pr;
	}


	/**
	 * 
	 * @param userIds
	 *            用户ID
	 * @param groupIds
	 *            组ID
	 * @return
	 */
	private int getCollectionSize(String userIds, String groupIds) {
		Set<String> collectionSet = new HashSet<String>();
		String[] ids;
		if (userIds != null && userIds.length() > 0) {
			ids = userIds.split(",");
			for (String id : ids) {
				collectionSet.add(id);
			}
		}
		if (groupIds != null && groupIds.length() > 0) {
			UserQuery userQuery = processEngine.getIdentityService()
					.createUserQuery();
			ids = groupIds.split(",");
			List<User> userList;
			for (String id : ids) {
				userList = userQuery.memberOfGroup(id).list();
				for (User user : userList) {
					if (!collectionSet.contains(user.getId())) {
						collectionSet.add(user.getId());
					}
				}
			}
		}
		return new ArrayList<String>(collectionSet).size();

	}

	private byte[] modifyContentBytes(String contentBytesStr,
			BpmnTemplateDef bpmnTemplateDef, int version) {
		byte[] b = bpmnTemplateDef.getContentBytes();
		String str = contentBytesStr
				.replaceFirst(
						"\"version\":\"" + bpmnTemplateDef.getVersion() + "\"",
						"\"version\":\"" + version + "\"")
				.replaceFirst(
						"\"deployState\":\"" + bpmnTemplateDef.getDeployState()
								+ "\"", "\"deployState\":\"1\"")
				.replaceFirst(
						"\"versionState\":\""
								+ bpmnTemplateDef.getVersionState() + "\"",
						"\"versionState\":\"0\"");
		try {
			BASE64Encoder encode = new BASE64Encoder();
			String base64 = encode.encode(str.getBytes("GBK"));
			BASE64Decoder decode = new BASE64Decoder();
			b = decode.decodeBuffer(base64);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	public static List<String> strToList(String str, String splitvalue) {
		List<String> list = new ArrayList<String>();
		if (str != null && !"".equals(str)) {
			String[] strs = str.split(splitvalue);
			for (int i = 0; i < strs.length; i++) {
				list.add(strs[i]);
			}
		}
		return list;
	}

	@Override
	public ProcessResult<List<BpmnIdUser>> queryBpmnIdUserByName(String name) {
		List<User> list = processEngine.getIdentityService().createUserQuery()
				.userFirstNameLike("%" + name + "%").list();
		List<BpmnIdUser> userList = new ArrayList<BpmnIdUser>();
		ProcessResult<List<BpmnIdUser>> pr = new ProcessResult<List<BpmnIdUser>>();
		if (list != null && list.size() > 0) {
			for (User user : list) {
				BpmnIdUser bpmnIdUser = new BpmnIdUser();
				bpmnIdUser.setId(user.getId());
				bpmnIdUser.setName(user.getFirstName());
				bpmnIdUser.setEmail(user.getEmail());
				bpmnIdUser.setFirst(user.getFirstName());
				bpmnIdUser.setLast(user.getLastName());
				bpmnIdUser.setPwd(user.getPassword());
				userList.add(bpmnIdUser);
			}
			pr.setSuccess(true);
			pr.setMessage(PRM.QUERY_USER_BY_UESER_NAME_SUCCESS);
		} else {
			pr.setSuccess(false);
			pr.setCode(PRM.QUERY_USER_BY_UESER_NAME_IS_FAILURE_CODE);
			pr.setMessage(PRM.QUERY_USER_BY_UESER_NAME_IS_FAILURE);
		}
		pr.setResult(userList);
		return pr;
	}

	@Override
	public ProcessResult<List<BpmnIdGroup>> queryBpmnIdGroup() {
		/*List<Group> list = processEngine.getIdentityService()
				.createGroupQuery().list();*/
		List<Group> list = processEngine.getIdentityService()
				.createGroupQuery().groupType("2").list();
		List<BpmnIdGroup> groupList = new ArrayList<BpmnIdGroup>();
		for (Group group : list) {
			BpmnIdGroup bpmnIdGroup = new BpmnIdGroup();
			bpmnIdGroup.setId(group.getId());
			bpmnIdGroup.setName(group.getName());
			bpmnIdGroup.setType(group.getType());
			groupList.add(bpmnIdGroup);
		}
		ProcessResult<List<BpmnIdGroup>> pr = new ProcessResult<List<BpmnIdGroup>>();
		pr.setMessage(PRM.PROCESS_QUERY_GROUP_SUCCESS);
		pr.setResult(groupList);
		pr.setSuccess(true);
		return pr;
	}

	@Override
	public ProcessResult<Integer> queryTempCountByCategory(String category) {
		ProcessResult<Integer> pr = new ProcessResult<Integer>();
		int count = bpmnTemplateDefDao.selectTempCountByCategory(category);
		pr.setMessage(PRM.QUERY_COUNT_OF_TEMP_BY_TYPE_SUCCESS);
		pr.setResult(count);
		pr.setSuccess(true);
		return pr;
	}

	private List<IOParameter> getCallActivityParameter(
			JSONObject jsonParameter, String type) {
		List<IOParameter> parameters = new ArrayList<IOParameter>();
		String parameter = String.valueOf(jsonParameter.get(type));
		IOParameter iOParameter;
		if (parameter != null && !"".equals(parameter)
				&& !"null".equals(parameter)) {
			JSONArray parameterArray = JSONArray.fromObject(parameter);
			for (int i = 0; i < parameterArray.size(); i++) {
				JSONObject para = (JSONObject) parameterArray.get(i);
				iOParameter = new IOParameter();
				iOParameter.setId((String) para.get("id"));
				iOParameter.setSource((String) para.get("source"));
				iOParameter.setSourceExpression((String) para
						.get("sourceExpression"));
				iOParameter.setTarget((String) para.get("target"));
				iOParameter.setTargetExpression((String) para
						.get("targetExpression"));
				parameters.add(iOParameter);
			}
		}
		return parameters;
	}

	private List<ActivitiListener> getActivitiListeners(
			JSONObject jsonListener, String listenerType) {
		List<ActivitiListener> listeners = new ArrayList<ActivitiListener>();
		String executionListener = String.valueOf(jsonListener
				.get(listenerType));
		if (executionListener != null && !"".equals(executionListener)
				&& !"null".equals(executionListener)) {
			JSONArray execution_Listeners = JSONArray.fromObject(jsonListener
					.get(listenerType));
			ActivitiListener listener;
			for (int i = 0; i < execution_Listeners.size(); i++) {
				JSONObject executionlist = (JSONObject) execution_Listeners
						.get(i);
				listener = new ActivitiListener();
				listener.setId((String) executionlist.get("id"));
				listener.setEvent((String) executionlist.get("event"));
				String LisImpType = (String) executionlist.get("type");
				if (LisImpType.equals("class")) {
					listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
				} else if (LisImpType.equals("delegateExpression")) {
					listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
				}
				listener.setImplementation((String) executionlist
						.get("listenerImplementation"));
				JSONArray fields = JSONArray.fromObject(executionlist
						.get("field"));
				List<FieldExtension> fieldExtensions = new ArrayList<FieldExtension>();
				FieldExtension fieldExtension;
				for (int j = 0; j < fields.size(); j++) {
					JSONObject field = (JSONObject) fields.get(j);
					fieldExtension = new FieldExtension();
					fieldExtension
							.setFieldName((String) field.get("fieldName"));
					fieldExtension.setStringValue(field.get("stringValue")
							.toString());
					fieldExtension.setExpression((String) field
							.get("expression"));
					fieldExtensions.add(fieldExtension);
				}
				listener.setFieldExtensions(fieldExtensions);
				listeners.add(listener);
			}
		}
		return listeners;
	}

	private void setBigDataBusinessPrivilege(JSONObject jsonExtend,
			String taskDefKey, List<BpmnFieldSecu> bpmnFieldSecuList,
			List<BpmnTableSecu> bpmnTableSecuList) {
		Object privilegeStr = jsonExtend.get("bpmnBigDataBusinessPrivilege");
		jsonExtend.remove("bpmnBigDataBusinessPrivilege");

		// 发布的时候业务权限存库
		if (null != privilegeStr && !"".equals(privilegeStr)) {
			@SuppressWarnings("unchecked")
			Map<String, JSONArray> map = JSONObject.fromObject(privilegeStr);
			Set<String> set = map.keySet();
			if (set.size() != 0) {
				for (String str : set) {
					JSONArray privilegeJsonArray = map.get(str);
					@SuppressWarnings("rawtypes")
					Iterator iteratorArray = privilegeJsonArray.iterator();
					while (iteratorArray.hasNext()) {
						JSONObject privilegeObj = (JSONObject) iteratorArray
								.next();
						Integer privilegeType = Integer.valueOf(privilegeObj
								.getString("type"));
						if (BpmnTableSecu.TABLE_PRIVILEGE == privilegeType
								.intValue()) {
							BpmnTableSecu bpmnTableSecu = new BpmnTableSecu();
							bpmnTableSecu.setTaskDefKey(taskDefKey);
							bpmnTableSecu.setRunTimeBpmnRole(str);
							bpmnTableSecu.setTableId(privilegeObj
									.getString("tId"));
							bpmnTableSecu.setTableDBName(privilegeObj
									.getString("tDBName"));
							bpmnTableSecu.setCanNotAdd(Integer
									.valueOf(privilegeObj.getString("a")));
							bpmnTableSecu.setCanNotDelete(Integer
									.valueOf(privilegeObj.getString("d")));
							bpmnTableSecu.setCanNotVisble(Integer
									.valueOf(privilegeObj.getString("v")));
							bpmnTableSecu.setCanNotModify(Integer
									.valueOf(privilegeObj.getString("m")));
							bpmnTableSecuList.add(bpmnTableSecu);
						} else if (BpmnFieldSecu.FIELD_PRIVILEGE == privilegeType
								.intValue()) {
							BpmnFieldSecu bpmnFieldSecu = new BpmnFieldSecu();
							bpmnFieldSecu.setTaskDefKey(taskDefKey);
							bpmnFieldSecu.setRunTimeBpmnRole(str);
							bpmnFieldSecu.setTableId(privilegeObj
									.getString("tId"));
							bpmnFieldSecu.setTableDBName(privilegeObj
									.getString("tDBName"));
							bpmnFieldSecu.setFieldId(privilegeObj
									.getString("fId"));
							bpmnFieldSecu.setFieldDBName(privilegeObj
									.getString("fDBName"));
							bpmnFieldSecu.setCanNotVisble(Integer
									.valueOf(privilegeObj.getString("v")));
							bpmnFieldSecu.setCanNotModify(Integer
									.valueOf(privilegeObj.getString("m")));
							if (null != privilegeObj.get("mfi")) {
								bpmnFieldSecu
										.setMustFillIn(Integer
												.valueOf(privilegeObj
														.getString("mfi")));
							}
							bpmnFieldSecuList.add(bpmnFieldSecu);
						}
					}
				}
			}
		}
	}

	@Override
	public ProcessResult<String> saveTemplateDef(String id, String bytes,
			String deployState, String canvasWidth, String canvasHeight) {
		String str = "";
		boolean flag = true;
		BpmnTemplateDef bpmnTemplate = transformTemplateDefByStr(id, bytes);
		bpmnTemplate.setDeployState(0);
		bpmnTemplate.setVersionState(0);
		bpmnTemplate.setVersion(0);
		if (id == null || id.equals("null")) {
			id = bpmnTemplateDefDao.selectSysId();
			bpmnTemplate.setId(id);
			bpmnTemplate.setCreateTime(new Date());
			bpmnTemplate.setCanvasHeight(canvasHeight);
			bpmnTemplate.setCanvasWidth(canvasWidth);
			bpmnTemplateDefDao.insertBpmnTemplateDef(bpmnTemplate);
			str = PRM.PROCESS_DEF_SAVE_SUCCESS;
		} else {
			BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
					.selectTemplateDefById(id);
			// 部署
			if (bpmnTemplateDef.getDeployState() == 1) {
				if (deployState.equals("1")
						|| (deployState == null || deployState.equals(""))) { // 另存
					id = bpmnTemplateDefDao.selectSysId();
					bpmnTemplate.setId(id);
					bpmnTemplate.setCreateTime(new Date());
					bpmnTemplate.setCanvasHeight(canvasHeight);
					bpmnTemplate.setCanvasWidth(canvasWidth);
					bpmnTemplateDefDao.insertBpmnTemplateDef(bpmnTemplate);
					str = PRM.PROCESS_DEF_SAVE_AS_SUCCESS;
				} else if (deployState.equals("2")) { // 重新部署
					BpmnTemplateDef selbpmnTemplate = transformTemplateDefByStr(
							id, bytes);
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("name", selbpmnTemplate.getName());
					param.put("tableIds", selbpmnTemplate.getTableIds());
					param.put("tableNames", selbpmnTemplate.getTableNames());
					param.put("modifyTime", new Date());
					byte[] b = null;
					try {
						BASE64Encoder encode = new BASE64Encoder();
						String base64 = encode.encode(bytes.getBytes("GBK"));
						BASE64Decoder decode = new BASE64Decoder();
						b = decode.decodeBuffer(base64);
						param.put("contentBytes", b);
						param.put("id", id);
						param.put("canvasWidth", canvasWidth);
						param.put("canvasHeight", canvasHeight);
						bpmnTemplateDefDao.updateBpmnTempDefWithDep(param);
						bpmnTemplateDef.setContentBytes(b);
						modifyResourceByDeployId(
								bpmnTemplateDef.getDeploymentId(),
								bpmnTemplateDef);
						str = PRM.PROCESS_DEF_DEPLOYMENT_SUCCESS;
					} catch (IOException e) {
						e.printStackTrace();
						str = "编码转换异常";
						flag = false;
					} catch (BpmnException e) {
						e.printStackTrace();
						str = e.getMessage();
						flag = false;
					}
				}
			} else { // 保存
				bpmnTemplate.setModifyTime(new Date());
				bpmnTemplate.setCanvasHeight(canvasHeight);
				bpmnTemplate.setCanvasWidth(canvasWidth);
				bpmnTemplateDefDao.updateBpmnTempDef(bpmnTemplate);
				str = PRM.PROCESS_DEF_SAVE_SUCCESS;
			}
		}
		ProcessResult<String> pr = new ProcessResult<String>();
		pr.setResult(id);
		pr.setMessage(str);
		pr.setSuccess(flag);
		return pr;
	}

	@Override
	public ProcessResult<String> saveTemplateDef(String id, String bytes,
			String deployState) {
		String str = "";
		boolean flag = true;
		BpmnTemplateDef bpmnTemplate = transformTemplateDefByStr(id, bytes);
		bpmnTemplate.setDeployState(0);
		bpmnTemplate.setVersionState(0);
		bpmnTemplate.setVersion(0);
		if (id == null || id.equals("null")) {
			id = bpmnTemplateDefDao.selectSysId();
			bpmnTemplate.setId(id);
			bpmnTemplate.setCreateTime(new Date());
			bpmnTemplateDefDao.insertBpmnTemplateDef(bpmnTemplate);
			str = PRM.PROCESS_DEF_SAVE_SUCCESS;
		} else {
			BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
					.selectTemplateDefById(id);
			// 部署
			if (bpmnTemplateDef.getDeployState() == 1) {

				if (deployState.equals("1")
						|| (deployState == null || deployState.equals(""))) { // 另存或者
																				// 保存
					id = bpmnTemplateDefDao.selectSysId();
					bpmnTemplate.setId(id);
					bpmnTemplate.setCreateTime(new Date());
					bpmnTemplateDefDao.insertBpmnTemplateDef(bpmnTemplate);
					str = PRM.PROCESS_DEF_SAVE_AS_SUCCESS;
				} else if (deployState.equals("2")) { // 重新部署
					BpmnTemplateDef selbpmnTemplate = transformTemplateDefByStr(
							id, bytes);
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("name", selbpmnTemplate.getName());
					param.put("modify_time", new Date().toString());
					byte[] b = null;
					try {
						BASE64Encoder encode = new BASE64Encoder();
						String base64 = encode.encode(bytes.getBytes("GBK"));
						BASE64Decoder decode = new BASE64Decoder();
						b = decode.decodeBuffer(base64);
						param.put("contentBytes", b);
						param.put("id", id);
						bpmnTemplateDefDao.updateBpmnTempDefWithDep(param);
						bpmnTemplateDef.setContentBytes(b);
						modifyResourceByDeployId(
								bpmnTemplateDef.getDeploymentId(),
								bpmnTemplateDef);
						str = PRM.PROCESS_DEF_DEPLOYMENT_SUCCESS;
					} catch (IOException e) {
						e.printStackTrace();
						str = "编码转换异常";
						flag = false;
					} catch (BpmnException e) {
						e.printStackTrace();
						str = e.getMessage();
						flag = false;
					}
				}
			} else { // 没有部署 更新模版
				bpmnTemplate.setModifyTime(new Date());
				bpmnTemplateDefDao.updateBpmnTempDef(bpmnTemplate);
				str = PRM.PROCESS_DEF_SAVE_SUCCESS;
			}
		}
		ProcessResult<String> pr = new ProcessResult<String>();
		pr.setResult(id);
		pr.setMessage(str);
		pr.setSuccess(flag);
		return pr;
	}

	/**
	 * 根据部署ID修改工作流模板定义表
	 * 
	 * @param deploymentId
	 *            流程部署ID
	 * @param bpmnTemplateDef
	 *            流程模板对象
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	private void modifyResourceByDeployId(String deploymentId,
			BpmnTemplateDef bpmnTemplateDef) throws BpmnException,
			UnsupportedEncodingException {
		RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) processEngine
				.getRepositoryService();
		ProcessResult<Map<BpmnModel, String>> result = deployProcessByStr(
				bpmnTemplateDef, 2);
		Map<BpmnModel, String> map = result.getResult();
		Iterator<Entry<BpmnModel, String>> iter = map.entrySet().iterator();
		// 新的对象
		BpmnModel newBpmnModel = null;
		String categroy = null;
		while (iter.hasNext()) {
			Entry<BpmnModel, String> entry = iter.next();
			newBpmnModel = entry.getKey();
			categroy = entry.getValue();
		}

		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService
				.createProcessDefinitionQuery().deploymentId(deploymentId)
				.singleResult();
		Process newProcess = newBpmnModel.getProcesses().get(0);
		String processDefinitionId = definitionEntity.getId();
		// BpmnModel oldBpmnModel = repositoryService
		// .getBpmnModel(processDefinitionId);
		// Process oldProcess = oldBpmnModel.getProcesses().get(0);
		/*
		 * Iterator<FlowElement> iterator = oldProcess.getFlowElements()
		 * .iterator(); List<BpmnActIdentityLinkEntity> insertGroupLinkEntities
		 * = new ArrayList<BpmnActIdentityLinkEntity>();
		 * List<BpmnActIdentityLinkEntity> insertLinkEntities = new
		 * ArrayList<BpmnActIdentityLinkEntity>();
		 */
		/*
		 * while (iterator.hasNext()) { FlowElement flowElement = (FlowElement)
		 * iterator.next(); if (flowElement instanceof UserTask) { UserTask
		 * oldUserTask = (UserTask) flowElement; UserTask newUserTask =
		 * (UserTask) newProcess .getFlowElement(flowElement.getId()); if
		 * (newUserTask == null) { throw new BpmnException(
		 * PRM.BPMN_DEF_WITH_DELETE_ALREADYEXISTS_USERTASK_CODE,
		 * PRM.BPMN_DEF_WITH_DELETE_ALREADYEXISTS_USERTASK); } List<String>
		 * oldXmlCandidateGroups = oldUserTask .getCandidateGroups();
		 * List<String> oldXmlCandidateUsers = oldUserTask .getCandidateUsers();
		 * List<String> newXmlCandidateUsers = newUserTask .getCandidateUsers();
		 * List<String> newXmlCandidateGroups = newUserTask
		 * .getCandidateGroups(); // 候选组或者候选人任意一个变化修改权限表的信息 if
		 * (!CollectionUtils.isEqualCollection(oldXmlCandidateGroups,
		 * newXmlCandidateGroups) || !CollectionUtils.isEqualCollection(
		 * oldXmlCandidateUsers, newXmlCandidateUsers)) { Map<String, Object>
		 * vMap = new HashMap<String, Object>(); vMap.put("proc_def_id_",
		 * processDefinitionId); vMap.put("task_def_key_", oldUserTask.getId());
		 * // 获取所有正在运行的当前节点的taskID List<String> taskList =
		 * bpmnActIdentityLinkEntityDao
		 * .selectIdentityLinkEntityByProcAndTaskKey(vMap); if (taskList == null
		 * || taskList.size() == 0) { taskList = bpmnActIdentityLinkEntityDao
		 * .selectIdentityLinkEntityByNoAuthorization(vMap); } // 候选组变化 if
		 * (!CollectionUtils.isEqualCollection( oldXmlCandidateGroups,
		 * newXmlCandidateGroups)) { for (String candidateGroups :
		 * newXmlCandidateGroups) { if (!oldXmlCandidateGroups
		 * .contains(candidateGroups)) { for (String task : taskList) {
		 * BpmnActIdentityLinkEntity actIdentityLinkEntity = new
		 * BpmnActIdentityLinkEntity(); actIdentityLinkEntity.setTaskId(task);
		 * actIdentityLinkEntity .setGroupId(candidateGroups);
		 * insertGroupLinkEntities .add(actIdentityLinkEntity); } } } } // 候选人变化
		 * if (!CollectionUtils.isEqualCollection( oldXmlCandidateUsers,
		 * newXmlCandidateUsers)) { for (String candidateUsers :
		 * newXmlCandidateUsers) { if
		 * (!oldXmlCandidateUsers.contains(candidateUsers)) { for (String task :
		 * taskList) { BpmnActIdentityLinkEntity actIdentityLinkEntity = new
		 * BpmnActIdentityLinkEntity(); actIdentityLinkEntity.setTaskId(task);
		 * actIdentityLinkEntity .setUserId(candidateUsers); insertLinkEntities
		 * .add(actIdentityLinkEntity); } } } } } } }
		 */
		//查询当前节点是否有待办  如果有就修改候选人start
		//modifyCandidateUserOGroupOfWaittingTask(processDefinitionId,newProcess);
		//查询当前节点是否有待办  如果有就修改候选人end
		// 部署新的工作流
		DeploymentBuilderImpl deploymentBuilder = (DeploymentBuilderImpl) repositoryService
				.createDeployment()
				.addBpmnModel(categroy + ".bpmn", newBpmnModel)
				.category(categroy);
		// 部署新的流程
		DeploymentEntity newDeployment = (DeploymentEntity) deploymentBuilder
				.deploy();
		// 获取旧的版本资源信息
		BpmnActResourceEntity oldresourceEntity = bpmnActResourceDao
				.selectResourcesByDeploymentId(deploymentId);
		// 新部署的DeploymentId
		String newDeploymentId = newDeployment.getId();
		String oldDeploymentId = oldresourceEntity.getId();
		BpmnActResourceEntity newResourceEntity = bpmnActResourceDao
				.selectResourcesByDeploymentId(newDeploymentId);
		if (newResourceEntity != null) {
			if (newResourceEntity != null
					&& newResourceEntity.getName().endsWith("bpmn")) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("oldDeploymentId", oldDeploymentId);
				param.put("newDeploymentId", newDeploymentId);
				bpmnActResourceDao
						.updateResourcesBytesByNewDeploymentIdAndOldDeploymentId(param);
			}
		}
		if((definitionEntity.getDescription() == null && newProcess.getDocumentation() != "") ||(definitionEntity.getDescription() != null && !definitionEntity.getDescription().equals(newProcess.getDocumentation()))){
			Map<String, Object> param1 = new HashMap<String, Object>();
			param1.put("procDefId", processDefinitionId);
			param1.put("newDescription", newProcess.getDocumentation());
			bpmnActResourceDao.updateProcessDefinitionDescriptionById(param1);
		}
		bpmnActIdentityLinkEntityDao
				.deleteIdentityInfoByProcDefId(processDefinitionId);
		if (newProcess.getCandidateStarterGroups() != null
				&& newProcess.getCandidateStarterGroups().size() > 0) {
			for (String candidateStarterGroup : newProcess
					.getCandidateStarterGroups()) {
				repositoryService.getCommandExecutor().execute(
						new AddIdentityLinkForProcessDefinitionCmd(
								processDefinitionId, null,
								candidateStarterGroup));
			}

		}
		if (newProcess.getCandidateStarterUsers() != null
				&& newProcess.getCandidateStarterUsers().size() > 0) {
			for (String candidateStarterUser : newProcess
					.getCandidateStarterUsers()) {
				repositoryService.getCommandExecutor()
						.execute(
								new AddIdentityLinkForProcessDefinitionCmd(
										processDefinitionId,
										candidateStarterUser, null));
			}

		}

		// 更新流程模板缓存
		updateprocessDefinitionCache(processDefinitionId, newDeploymentId);
		// 删除新的流程模板
		repositoryService.getCommandExecutor().execute(
				new DeleteDeploymentCmd(newDeploymentId, true));

	}
	private void modifyCandidateUserOGroupOfWaittingTask(String processDefinitionId,Process newProcess)throws BpmnException{
		BpmnModel oldBpmnModel = processEngine.getRepositoryService()
				.getBpmnModel(processDefinitionId);
		Process oldProcess = oldBpmnModel.getProcesses().get(0);
		Iterator<FlowElement> iterator = oldProcess.getFlowElements().iterator(); 
		List<BpmnActIdentityLinkEntity> insertGroupLinkEntities  = new ArrayList<BpmnActIdentityLinkEntity>();
		List<BpmnActIdentityLinkEntity> insertLinkEntities = new ArrayList<BpmnActIdentityLinkEntity>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		while (iterator.hasNext()) { 
			FlowElement flowElement = (FlowElement)iterator.next(); 
			if (flowElement instanceof UserTask) {
				UserTask oldUserTask = (UserTask) flowElement; 
				UserTask newUserTask =  (UserTask) newProcess.getFlowElement(flowElement.getId());
				if (newUserTask == null) {
					throw new BpmnException(PRM.BPMN_DEF_WITH_DELETE_ALREADYEXISTS_USERTASK_CODE, 
							PRM.BPMN_DEF_WITH_DELETE_ALREADYEXISTS_USERTASK); 
				} 
				Map<String, Object>  vMap = new HashMap<String, Object>(); 
				vMap.put("proc_def_id_", processDefinitionId); 
				vMap.put("task_def_key_", oldUserTask.getId());
				// 获取所有正在运行的当前节点的taskID 
				List<String> taskIdList =bpmnActIdentityLinkEntityDao
						.selectIdentityLinkEntityByProcAndTaskKey(vMap); 
				if (null != taskIdList && taskIdList.size() > 0) {
					List<String> oldXmlCandidateGroups = oldUserTask.getCandidateGroups();
					List<String> newXmlCandidateGroups = newUserTask.getCandidateGroups(); 
					Task singleResult = processEngine.getTaskService().createTaskQuery().taskId(taskIdList.get(0)).singleResult();
					String userString = candidateUsersService.getCandidateUserByTaskKeyAndUserId(
							processDefinitionId,
							singleResult.getProcessInstanceId(),
							singleResult.getId(),
							null,
							null,
							singleResult.getTaskDefinitionKey(), false);
					List<String> newXmlCandidateUsers = java.util.Arrays.asList(userString.split(","));
					List<String> oldXmlCandidateUsers = new ArrayList<String>();
					List<IdentityLink> identityLinksForTask = processEngine.getTaskService().getIdentityLinksForTask(singleResult.getId());
					for (int i = 0; i < identityLinksForTask.size(); i++) {
						IdentityLink identityLink = identityLinksForTask.get(i);
						if(identityLink.getUserId() != null){
							oldXmlCandidateUsers.add(identityLink.getUserId());
						}
					}
					// 候选组或者候选人任意一个变化修改权限表的信息 
					if(!CollectionUtils.isEqualCollection(oldXmlCandidateGroups,newXmlCandidateGroups) || !CollectionUtils.isEqualCollection(oldXmlCandidateUsers, newXmlCandidateUsers)) {
						if (taskIdList == null|| taskIdList.size() == 0) { 
							taskIdList = bpmnActIdentityLinkEntityDao
									.selectIdentityLinkEntityByNoAuthorization(vMap); 
						} 
						// 候选组变化 
						if(!CollectionUtils.isEqualCollection( oldXmlCandidateGroups, newXmlCandidateGroups)) { 
							for (String candidateGroups :newXmlCandidateGroups) { 
								for (String task : taskIdList) {
									BpmnActIdentityLinkEntity actIdentityLinkEntity = new BpmnActIdentityLinkEntity();
									actIdentityLinkEntity.setTaskId(task);
									actIdentityLinkEntity.setGroupId(candidateGroups);
									insertGroupLinkEntities.add(actIdentityLinkEntity); 
								}
							} 
							if (null != oldXmlCandidateGroups && oldXmlCandidateGroups.size() > 0) {
								paramMap.put("groupIds", oldXmlCandidateGroups);
								for (int i = 0; i < taskIdList.size(); i++) {
									paramMap.put("taskId", taskIdList.get(i));
									bpmnActRuIdentityLinkDao.deleteRuCandidateGroupByTaskId(paramMap);
								}
							} 
						} 
						// 候选人变化
						if (!CollectionUtils.isEqualCollection( oldXmlCandidateUsers,newXmlCandidateUsers)) { 
							for (String candidateUsers :newXmlCandidateUsers) { 
								for (String task : taskIdList) {
									BpmnActIdentityLinkEntity actIdentityLinkEntity = new BpmnActIdentityLinkEntity(); 
									actIdentityLinkEntity.setTaskId(task);
									actIdentityLinkEntity .setUserId(candidateUsers); 
									insertLinkEntities.add(actIdentityLinkEntity); 
								} 
							} 
							if (null != oldXmlCandidateUsers && oldXmlCandidateUsers.size() > 0) {
								paramMap.put("userIds", oldXmlCandidateUsers);
								for (int i = 0; i < taskIdList.size(); i++) {
									paramMap.put("taskId", taskIdList.get(i));
									bpmnActRuIdentityLinkDao.deleteRuCandidateUserByTaskId(paramMap);
								}
							}
						}
					 }
				}
			}
		}
		if (insertLinkEntities != null && insertLinkEntities.size() > 0 ) {
			bpmnActIdentityLinkEntityDao.addUserBatch(insertLinkEntities);
		}
		if (insertGroupLinkEntities != null && insertGroupLinkEntities.size() > 0) {
			bpmnActIdentityLinkEntityDao.addGroupBatch(insertGroupLinkEntities);
		} 
	}

	/**
	 * 更新流程定义中的缓存 引擎自动解析新更新的xml部署文件
	 */
	private void updateprocessDefinitionCache(String oldPdId,String newDeploymentId) {
		/*DeploymentCache<ProcessDefinitionEntity> processDefinitionCache = new DefaultDeploymentCache<ProcessDefinitionEntity>();
		RepositoryServiceImpl repositoryServiceImpl = (RepositoryServiceImpl) processEngine
				.getRepositoryService();
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryServiceImpl
				.createProcessDefinitionQuery().deploymentId(deploymentId)
				.singleResult();
		processDefinitionCache.add("1", definitionEntity);
		ProcessEngineImpl engineImpl = (ProcessEngineImpl) processEngine;
		ProcessEngineConfigurationImpl configurationImpl = engineImpl
				.getProcessEngineConfiguration();
		DeploymentManager deploymentManager = configurationImpl
				.getDeploymentManager();
		deploymentManager.setProcessDefinitionCache(processDefinitionCache);*/
		ProcessEngineImpl engineImpl = (ProcessEngineImpl) processEngine;
		ProcessEngineConfigurationImpl configurationImpl = engineImpl
				.getProcessEngineConfiguration();
		DeploymentManager deploymentManager = configurationImpl
				.getDeploymentManager();
		RepositoryServiceImpl repositoryServiceImpl = (RepositoryServiceImpl) processEngine
				.getRepositoryService();
		ProcessDefinitionEntity newPde = (ProcessDefinitionEntity) repositoryServiceImpl
				.createProcessDefinitionQuery().deploymentId(newDeploymentId)
				.singleResult();
		deploymentManager.getProcessDefinitionCache().remove(oldPdId);
	}
	/**
	 * 更新流程定义中的缓存 引擎自动解析新更新的xml部署文件
	 * 
	 * @param deploymentId
	 *            流程部署ID
	 */
	private void updateprocessDefinitionCache(String deploymentId) {
		DeploymentCache<ProcessDefinitionEntity> processDefinitionCache = new DefaultDeploymentCache<ProcessDefinitionEntity>();
		RepositoryServiceImpl repositoryServiceImpl = (RepositoryServiceImpl) processEngine
				.getRepositoryService();
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryServiceImpl
				.createProcessDefinitionQuery().deploymentId(deploymentId)
				.singleResult();
		processDefinitionCache.add("1", definitionEntity);
		ProcessEngineImpl engineImpl = (ProcessEngineImpl) processEngine;
		ProcessEngineConfigurationImpl configurationImpl = engineImpl
				.getProcessEngineConfiguration();
		DeploymentManager deploymentManager = configurationImpl
				.getDeploymentManager();
		deploymentManager.setProcessDefinitionCache(processDefinitionCache);

	}

	@Override
	public ProcessResult<List<String>> queryBpmnUserByUserAndGroup(
			String userIds, String groupIds) {
		ProcessResult<List<String>> pr = new ProcessResult<List<String>>();
		Set<String> collectionSet = new HashSet<String>();
		String[] ids;
		if (userIds != null && userIds.length() > 0) {
			ids = userIds.split(",");
			for (String id : ids) {
				collectionSet.add(id);
			}
		}
		if (groupIds != null && groupIds.length() > 0) {
			UserQuery userQuery = processEngine.getIdentityService()
					.createUserQuery();
			ids = groupIds.split(",");
			List<User> groupList;
			for (String id : ids) {
				groupList = userQuery.memberOfGroup(id).list();
				for (User user : groupList) {
					if (!collectionSet.contains(user.getId())) {
						collectionSet.add(user.getId());
					}
				}
			}
		}
		pr.setResult(new ArrayList<String>(collectionSet));
		return pr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProcessResult<Map<String, List<String>>> queryNodecandidateUsers(
			String bpmnType, String ticketId) {
		ProcessResult<Map<String, List<String>>> result = new ProcessResult<Map<String, List<String>>>();
		Map<String, List<String>> retuenMap = new HashMap<String, List<String>>();
		ActivitiHelper activitiHelper = new ActivitiHelper();
		Object[] objs = activitiHelper.queryDefinitionAndInstance(processEngine, bpmnType,ticketId);
		ProcessDefinition processDefinition  = (ProcessDefinition) objs[0];
		Process process = processEngine.getRepositoryService()
				.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
		if(null!=process &&null!=objs && null != objs[0] && objs[0] instanceof ProcessDefinition ){
			retuenMap.put("process",queryBpmnUserByUserAndGroup(StringUtil.ListToStr(process.getCandidateStarterUsers(), ","),StringUtil.ListToStr(process.getCandidateStarterGroups(), ",")).getResult());
			for (FlowElement flowElement : process.getFlowElements()) {
				if (flowElement instanceof UserTask){
					UserTask task = (UserTask) flowElement;
					if (task.getId().equals("startevent_userTask")) {
						break;
					}
					List<String> userList = queryBpmnUserByUserAndGroup(
							StringUtil.ListToStr(task.getCandidateUsers(), ","),
							StringUtil.ListToStr(task.getCandidateUsers(), ","))
							.getResult();
					String users = candidateUsersService
							.getCandidateUserByTaskKeyAndUserId(
									processDefinition.getId(), null, null,
									null, null, task.getId(), false);
					userList.addAll(strToList(users, ","));
					List<String> candidateGroups = task.getCandidateGroups();
					if (candidateGroups != null && candidateGroups.size() != 0) {
						for (String groupId : candidateGroups) {
							List<User> usersIngrouplist = processEngine
									.getIdentityService().createUserQuery()
									.memberOfGroup(groupId).list();
							List<String> userIdInGroup = new ArrayList<String>();
							for (User user : usersIngrouplist) {
								String id = user.getId();
								userIdInGroup.add(id);
							}
							userList = (List<String>) CollectionUtils.union(userList, userIdInGroup);
						}
					}
					retuenMap.put(task.getId(), userList);

				}
				if (flowElement instanceof SubProcess) {
					SubProcess subProcess = (SubProcess) flowElement;
					for (FlowElement subflowElement : subProcess
							.getFlowElements()) {
						if (subflowElement instanceof UserTask) {
							UserTask task = (UserTask) flowElement;
							List<String> userList = queryBpmnUserByUserAndGroup(
									StringUtil.ListToStr(
											task.getCandidateUsers(), ","),
									StringUtil.ListToStr(
											task.getCandidateUsers(), ","))
									.getResult();
							String users = candidateUsersService
									.getCandidateUserByTaskKeyAndUserId(
											processDefinition.getId(), null,
											null, null, null, task.getId(),
											false);
							userList.addAll(strToList(users, ","));
							retuenMap.put(task.getId(), userList);
						}
					}
				}
			}
			result.setResult(retuenMap);
			result.setSuccess(true);			
		}else {
			result.setResult(null);
			result.setSuccess(false);
			result.setCode(PRM.PROCESS_INST_TRANS_INFO_IS_NULL_CODE);
			result.setMessage(PRM.PROCESS_INST_TRANS_INFO_IS_NULL);
		}
		return result;
	}

	@Override
	public ProcessResult<List<Map<String, Object>>> getProcessIds() {
		ProcessResult<List<Map<String, Object>>> pr = new ProcessResult<List<Map<String, Object>>>();

		List<Map<String, Object>> processIdList = bpmnTemplateDefDao
				.selectProcessIds();
		// List<ProcessDefinition> list = processEngine
		// .getRepositoryService()
		// .createProcessDefinitionQuery()
		// .list();
		// Set<String> set = new HashSet<String>();
		// List<Map<String, Object>> listMap = new
		// ArrayList<Map<String,Object>>();
		// for (ProcessDefinition processDefinition : list) {
		// if(!set.contains(processDefinition.getKey())){
		// set.add(processDefinition.getKey());
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("processId", processDefinition.getKey());
		// listMap.add(map);
		// }
		// }
		pr.setResult(processIdList);
		pr.setSuccess(true);
		pr.setMessage(PRM.QUERY_PROCESS_DEF_ID_SUCCESS);
		return pr;
	}

	@Override
	public BpmnModel transformTemplateDefStrToBpmnModel(String str)
			throws BpmnException {
		// BpmnModel
		ProcessResult<String> pr = new ProcessResult<String>();
		BpmnModel model = new BpmnModel();
		Process process = new Process();
		String categroy = "";
		String procDefKey = "";
		Map<String, String> lineMap = new HashMap<String, String>();
		@SuppressWarnings("unused")
		List<ExclusiveGateway> extendExclusiveGateways = new ArrayList<ExclusiveGateway>();
		Set<String> callActivitySet = new HashSet<String>();
		List<FlowElement> flowElementList = new ArrayList<FlowElement>();
		List<UserTask> extendCountersign = new ArrayList<UserTask>();
		Map<String, JSONArray> subProcessMap = new HashMap<String, JSONArray>();
		Map<String, List<SequenceFlow>> flowLinesMap = new HashMap<String, List<SequenceFlow>>();
		@SuppressWarnings("unused")
		String contentBytesStr = "";
		try {
			JSONObject json = JSONObject.fromObject(str);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				if (k.equals("id")) {
				} else if (k.equals("title")) {
					process.setName(v.toString());
				} else if (k.equals("diagram")) {
					JSONObject jsonObj = JSONObject.fromObject(v.toString());
					for (Object key : jsonObj.keySet()) {
						@SuppressWarnings("unchecked")
						Map<String, String> vnode = (Map<String, String>) jsonObj
								.get(key);
						if (key.toString().equals("bpmn")) {
							categroy = vnode.get("categroy").toString();
							procDefKey = vnode.get("procDefKey").toString();
							process.setId("".equals(procDefKey) ? "1"
									: procDefKey);
							process.setDocumentation(vnode.get("document")
									.toString());
							process.setCandidateStarterUsers(strToList(vnode
									.get("candidateStartUsers").toString(), ","));
							process.setCandidateStarterGroups(strToList(vnode
									.get("candidateStartGroups").toString(),
									","));
						}
					}
				} else if (k.equals("nodes")) {
					JSONObject jsonObj = JSONObject.fromObject(v.toString());
					for (Object knode : jsonObj.keySet()) {
						@SuppressWarnings("unchecked")
						Map<String, Object> vnode = (Map<String, Object>) jsonObj
								.get(knode);
						String type = vnode.get("type").toString();
						if (type.equals("StartEvent")) {
							StartEvent startEvent = new StartEvent();
							startEvent.setId(knode.toString());
							startEvent.setName(vnode.get("name").toString());
							flowElementList.add(startEvent);
							GraphicInfo graphicInfo = new GraphicInfo();
							graphicInfo.setWidth(Double.parseDouble(vnode
									.get("width") + ""));
							graphicInfo.setHeight(Double.parseDouble(vnode
									.get("height") + ""));
							graphicInfo.setX(Double.parseDouble(vnode
									.get("left") + ""));
							graphicInfo.setY(Double.parseDouble(vnode
									.get("top") + ""));
							model.addGraphicInfo(knode.toString(), graphicInfo);
						} else if (type.equals("EndEvent")) {
							EndEvent endEvent = new EndEvent();
							endEvent.setId(knode.toString());
							endEvent.setName(vnode.get("name").toString());
							flowElementList.add(endEvent);
							GraphicInfo graphicInfo = new GraphicInfo();
							graphicInfo.setWidth(Double.parseDouble(vnode
									.get("width") + ""));
							graphicInfo.setHeight(Double.parseDouble(vnode
									.get("height") + ""));
							graphicInfo.setX(Double.parseDouble(vnode
									.get("left") + ""));
							graphicInfo.setY(Double.parseDouble(vnode
									.get("top") + ""));
							model.addGraphicInfo(knode.toString(), graphicInfo);
						} else if (type.equals("ExclusiveGateway")) {
							ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
							exclusiveGateway.setId(knode.toString());
							exclusiveGateway.setName(vnode.get("name")
									.toString());
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("general"));
							String defaultFlow = jsonExtend.get("defaultFlow") == null ? ""
									: jsonExtend.get("defaultFlow").toString();
							exclusiveGateway.setDefaultFlow(defaultFlow);
							flowElementList.add(exclusiveGateway);
							GraphicInfo graphicInfo = new GraphicInfo();
							graphicInfo.setWidth(Double.parseDouble(vnode
									.get("width") + ""));
							graphicInfo.setHeight(Double.parseDouble(vnode
									.get("height") + ""));
							graphicInfo.setX(Double.parseDouble(vnode
									.get("left") + ""));
							graphicInfo.setY(Double.parseDouble(vnode
									.get("top") + ""));
							model.addGraphicInfo(knode.toString(), graphicInfo);
						} else if (type.equals("UserTask")) {
							UserTask userTask = new UserTask();
							userTask.setName(vnode.get("name").toString());
							userTask.setId(knode.toString());
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							userTask.setCandidateUsers(strToList(jsonGeneral
									.get("candidateUsers").toString(), ","));
							userTask.setCandidateGroups(strToList(jsonGeneral
									.get("candidateGroups").toString(), ","));
							userTask.setDefaultFlow(jsonGeneral.get(
									"defaultFlow").toString());
							userTask.setDocumentation(jsonGeneral.get(
									"document").toString());
							JSONObject jsonListener = JSONObject
									.fromObject(vnode.get("listener"));
							if (jsonListener != null && !jsonListener.isEmpty()) {
								List<ActivitiListener> executionListeners = getActivitiListeners(
										jsonListener, "executionListener");
								userTask.setExecutionListeners(executionListeners);
								List<ActivitiListener> taskListeners = getActivitiListeners(
										jsonListener, "taskListener");
								userTask.setTaskListeners(taskListeners);
							}
							flowElementList.add(userTask);
							GraphicInfo graphicInfo = new GraphicInfo();
							graphicInfo.setWidth(Double.parseDouble(vnode
									.get("width") + ""));
							graphicInfo.setHeight(Double.parseDouble(vnode
									.get("height") + "") + 30.0);
							graphicInfo.setX(Double.parseDouble(vnode
									.get("left") + ""));
							graphicInfo.setY(Double.parseDouble(vnode
									.get("top") + ""));
							model.addGraphicInfo(knode.toString(), graphicInfo);
						} else if (type.equals("Subprocess")) {
							JSONArray jsonstr = JSONArray.fromObject(vnode
									.get("childNodes"));
							subProcessMap.put(knode.toString(), jsonstr);
							GraphicInfo graphicInfo = new GraphicInfo();
							graphicInfo.setWidth(Double.parseDouble(vnode
									.get("width") + ""));
							graphicInfo.setHeight(Double.parseDouble(vnode
									.get("height") + "") + 30.0);
							graphicInfo.setX(Double.parseDouble(vnode
									.get("left") + ""));
							graphicInfo.setY(Double.parseDouble(vnode
									.get("top") + ""));
							model.addGraphicInfo(knode.toString(), graphicInfo);
						} else if (type.equals("Parallelgateway")) {
							ParallelGateway parallelGateway = new ParallelGateway();
							parallelGateway.setId(knode.toString());
							parallelGateway.setName(vnode.get("name")
									.toString());
							flowElementList.add(parallelGateway);
							GraphicInfo graphicInfo = new GraphicInfo();
							graphicInfo.setWidth(Double.parseDouble(vnode
									.get("width") + ""));
							graphicInfo.setHeight(Double.parseDouble(vnode
									.get("height") + ""));
							graphicInfo.setX(Double.parseDouble(vnode
									.get("left") + ""));
							graphicInfo.setY(Double.parseDouble(vnode
									.get("top") + ""));
							model.addGraphicInfo(knode.toString(), graphicInfo);
						} else if (type.equals("CallActivity")) {
							CallActivity callActivity = new CallActivity();
							callActivity.setId(knode.toString());
							callActivity.setName(vnode.get("name").toString());
							callActivitySet.add(knode.toString());
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							callActivity.setCalledElement(jsonGeneral.get(
									"calledElement").toString());
							JSONObject parameter = JSONObject.fromObject(vnode
									.get("parameter"));
							callActivity
									.setInParameters(getCallActivityParameter(
											parameter, "inputParameter"));
							callActivity
									.setOutParameters(getCallActivityParameter(
											parameter, "outputParameter"));
							JSONObject jsonListener = JSONObject
									.fromObject(vnode.get("listener"));
							if (jsonListener != null && !jsonListener.isEmpty()) {
								List<ActivitiListener> executionListeners = getActivitiListeners(
										jsonListener, "executionListener");
								callActivity
										.setExecutionListeners(executionListeners);
							}
							flowElementList.add(callActivity);
							GraphicInfo graphicInfo = new GraphicInfo();
							graphicInfo.setWidth(Double.parseDouble(vnode
									.get("width") + ""));
							graphicInfo.setHeight(Double.parseDouble(vnode
									.get("height") + "") + 30.0);
							graphicInfo.setX(Double.parseDouble(vnode
									.get("left") + ""));
							graphicInfo.setY(Double.parseDouble(vnode
									.get("top") + ""));
							model.addGraphicInfo(knode.toString(), graphicInfo);
						} else if (type.equals("Countersign")) {
							UserTask userTask = new UserTask();
							userTask.setName(vnode.get("name").toString());
							userTask.setId(knode.toString());
							userTask.setAssignee("${countersigner}");
							JSONObject jsonExtend = JSONObject.fromObject(vnode
									.get("extendConfig"));
							JSONObject jsonGeneral = JSONObject
									.fromObject(vnode.get("general"));
							MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
							loopCharacteristics
									.setSequential(jsonGeneral.get("sequence")
											.toString().equals("yes") ? true
											: false);
							loopCharacteristics
									.setElementVariable("countersigner");
							// 如果候选人和候选组为空则报错
							if (jsonExtend.get("candidateUser").toString()
									.equals("")
									&& jsonExtend.get("collectionGroup")
											.toString().equals("")) {
								pr.setSuccess(false);
								pr.setMessage(PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS);
								pr.setCode(PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS_CODE);
								throw new BpmnException(
										PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS_CODE,
										PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS);
							}
							loopCharacteristics
									.setInputDataItem("${countersignService.setCollection(execution,'"
											+ jsonExtend.get("candidateUser")
													.toString()
											+ "',"
											+ "'"
											+ (jsonExtend
													.get("collectionGroup")
													.toString() + "'") + ")}");
							String completeConditionType = jsonGeneral.get(
									"completeConditionType").toString();
							String completeCondition = jsonGeneral.get(
									"completeCondition").toString();
							String userIds = jsonExtend.get("candidateUser")
									.toString();
							String groupIds = jsonExtend.get("collectionGroup")
									.toString();
							int count = getCollectionSize(userIds, groupIds);
							// 结束条件
							double calculation = 0;
							if (completeConditionType != null) {
								if (completeConditionType.equals("voteCount")) {
									// 投票的数量
									calculation = (Integer
											.valueOf(completeCondition) * 1.0 / count);
								} else if (completeConditionType
										.equals("completevotePercentage")) {
									// 通过百分比
									if (completeCondition.contains("%")) {
										calculation = new Double(
												completeCondition.substring(0,
														completeCondition
																.indexOf("%"))) / 100;
									} else {
										calculation = Double
												.valueOf(completeCondition);
									}
								} else if (completeConditionType
										.equals("votePercentage")) {
									if (completeCondition.contains("%")) {
										calculation = new Double(
												completeCondition.substring(0,
														completeCondition
																.indexOf("%"))) / 100;
									} else {
										calculation = Double
												.valueOf(completeCondition);
									}
								}
							}
							// 计算
							loopCharacteristics
									.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= "
											+ calculation + "}");
							userTask.setLoopCharacteristics(loopCharacteristics);
							List<FieldExtension> fieldExtensions = new ArrayList<FieldExtension>();
							ActivitiListener listener = new ActivitiListener();
							listener.setId("Countersign");
							listener.setEvent("complete");
							listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
							listener.setImplementation("com.hq.bpmn.processinstance.eventlistener.CountersignExcutionListener");
							FieldExtension fieldExtension;
							fieldExtension = new FieldExtension();
							fieldExtension.setFieldName("positiveTransition");
							fieldExtension.setStringValue(jsonExtend.get(
									"positiveSequence").toString());
							fieldExtensions.add(fieldExtension);
							fieldExtension = new FieldExtension();
							fieldExtension.setFieldName("negativeTransition");
							fieldExtension.setStringValue(jsonExtend.get(
									"negativeSequence").toString());
							fieldExtensions.add(fieldExtension);
							// weight
							if (jsonExtend.get("weightShowValue") != null
									&& !"".equals(jsonExtend
											.get("weightShowValue"))) {
								Map<String, String> map = new HashMap<String, String>();
								fieldExtension = new FieldExtension();
								fieldExtension.setFieldName("weight");
								JSONArray jsonArr = JSONArray
										.fromObject(jsonExtend
												.get("weightShowValue"));
								String user[] = new String[jsonArr.size()];
								String userWeight[] = new String[jsonArr.size()];
								for (int i = 0; i < jsonArr.size(); i++) {
									user[i] = jsonArr.getJSONObject(i)
											.getString("user");
									userWeight[i] = jsonArr.getJSONObject(i)
											.getString("userWeight");
									map.put(user[i], userWeight[i]);
								}
								fieldExtension.setStringValue(map.toString());
								fieldExtensions.add(fieldExtension);
								listener.setFieldExtensions(fieldExtensions);
							}
							// completionConditionSatisfied
							fieldExtension = new FieldExtension();
							fieldExtension
									.setFieldName("completionConditionSatisfied");
							fieldExtension.setStringValue(String
									.valueOf(calculation));
							fieldExtensions.add(fieldExtension);
							listener.setFieldExtensions(fieldExtensions);
							// approveCondition
							fieldExtension = new FieldExtension();
							fieldExtension.setFieldName("approveCondition");
							fieldExtension.setStringValue(jsonGeneral.get(
									"approveCondition").toString());
							fieldExtensions.add(fieldExtension);
							listener.setFieldExtensions(fieldExtensions);
							List<ActivitiListener> taskListeners = new ArrayList<ActivitiListener>();
							JSONObject jsonListener = JSONObject
									.fromObject(vnode.get("listener"));
							if (jsonListener != null && !jsonListener.isEmpty()) {
								List<ActivitiListener> executionListeners = getActivitiListeners(
										jsonListener, "executionListener");
								userTask.setExecutionListeners(executionListeners);
								taskListeners = getActivitiListeners(
										jsonListener, "taskListener");
							}
							taskListeners.add(listener);
							userTask.setTaskListeners(taskListeners);
							flowElementList.add(userTask);
							extendCountersign.add(userTask);
							// ExclusiveGateway exclusiveGateway = new
							// ExclusiveGateway();
							// String exclusiveGatewayId = userTask.getId() +
							// "K";
							// exclusiveGateway.setId(exclusiveGatewayId);
							// exclusiveGateway.setName(exclusiveGatewayId);
							// extendExclusiveGateways.add(exclusiveGateway);
							// flowElementList.add(exclusiveGateway);

							GraphicInfo graphicInfo = new GraphicInfo();
							graphicInfo.setWidth(Double.parseDouble(vnode
									.get("width") + ""));
							graphicInfo.setHeight(Double.parseDouble(vnode
									.get("height") + "") + 30.0);
							graphicInfo.setX(Double.parseDouble(vnode
									.get("left") + ""));
							graphicInfo.setY(Double.parseDouble(vnode
									.get("top") + ""));
							model.addGraphicInfo(knode.toString(), graphicInfo);
						}
					}
				} else if (k.equals("lines")) {
					JSONObject jsonObj = JSONObject.fromObject(v.toString());
					List<String> extendCountersignIdList = new ArrayList<String>();
					@SuppressWarnings("unused")
					List<String> extendSequenceFlows = new ArrayList<String>();
					for (UserTask flowElement : extendCountersign) {
						extendCountersignIdList.add(flowElement.getId());
					}
					for (Object knode : jsonObj.keySet()) {
						@SuppressWarnings("unchecked")
						Map<String, Object> vnode = (Map<String, Object>) jsonObj
								.get(knode);
						// String source = vnode.get("from").toString();
						// if (!extendCountersignIdList.contains(source)) {
						SequenceFlow flow = new SequenceFlow();
						flow.setId(knode.toString());
						flow.setName(vnode.get("name").toString());
						flow.setSourceRef(vnode.get("from").toString());
						flow.setTargetRef(vnode.get("to").toString());
						lineMap.put(knode.toString(), vnode.get("to")
								.toString());
						JSONObject jsonListener = JSONObject.fromObject(vnode
								.get("listener"));
						if (jsonListener != null && !jsonListener.isEmpty()) {
							List<ActivitiListener> executionListeners = getActivitiListeners(
									jsonListener, "executionListener");
							flow.setExecutionListeners(executionListeners);
						}
						JSONObject jsonGeneral = JSONObject.fromObject(vnode
								.get("general"));
						String condition = jsonGeneral.get("condition")
								.toString();
						flow.setConditionExpression(condition);
						process.addFlowElement(flow);
						GraphicInfo fromGraphicInfo = model
								.getGraphicInfo(vnode.get("from").toString());
						String elementId = vnode.get("from").toString();
						if (null == flowLinesMap.get(elementId)) {
							List<SequenceFlow> list = new ArrayList<SequenceFlow>();
							list.add(flow);
							flowLinesMap.put(elementId, list);
						} else {
							flowLinesMap.get(elementId).add(flow);
						}
						GraphicInfo graphicInfo = new GraphicInfo();
						List<GraphicInfo> graphicInfoList = new ArrayList<GraphicInfo>();
						GraphicInfo toGraphicInfo = model.getGraphicInfo(vnode
								.get("to").toString());
						GraphicInfo sequenceFlow1 = new GraphicInfo();
						sequenceFlow1.setX(fromGraphicInfo.getX()
								+ fromGraphicInfo.getWidth());
						sequenceFlow1.setY(fromGraphicInfo.getY()
								+ (fromGraphicInfo.getHeight() / 2));
						graphicInfoList.add(sequenceFlow1);
						if (null != vnode.get("M")
								&& !"".equals(vnode.get("M"))
								&& (Double.parseDouble(vnode.get("M") + "") < fromGraphicInfo
										.getY() || Double.parseDouble(vnode
										.get("M") + "") > (fromGraphicInfo
										.getY() + fromGraphicInfo.getHeight()))) {
							GraphicInfo sequenceFlowM1 = new GraphicInfo();
							sequenceFlowM1.setX(fromGraphicInfo.getX()
									+ fromGraphicInfo.getWidth());
							sequenceFlowM1.setY(Double.parseDouble(vnode
									.get("M") + ""));
							GraphicInfo sequenceFlowM2 = new GraphicInfo();
							sequenceFlowM2.setX(toGraphicInfo.getX());
							sequenceFlowM2.setY(Double.parseDouble(vnode
									.get("M") + ""));
							graphicInfoList.add(sequenceFlowM1);
							graphicInfoList.add(sequenceFlowM2);
						}
						GraphicInfo sequenceFlow11 = new GraphicInfo();
						sequenceFlow11.setX(toGraphicInfo.getX());
						sequenceFlow11.setY(toGraphicInfo.getY()
								+ (toGraphicInfo.getHeight() / 2));
						graphicInfo.setElement(flow);
						graphicInfoList.add(sequenceFlow11);
						model.addFlowGraphicInfoList(knode.toString(),
								graphicInfoList);
						if (null != flow.getName()
								|| !"".equals(flow.getName())) {
							GraphicInfo LabelGraphicInfo = new GraphicInfo();
							LabelGraphicInfo.setWidth(0.0);
							LabelGraphicInfo.setHeight(0.0);
							model.addLabelGraphicInfo(knode.toString(),
									LabelGraphicInfo);
						}
						// } else {
						// 开始添加 会签的关口
						// 查获生成的排他关口
						// ExclusiveGateway exclusiveGateway = null;
						// for (ExclusiveGateway currentExclusiveGateway :
						// extendExclusiveGateways) {
						// if (currentExclusiveGateway.getId().equals(
						// source + "K")) {
						// exclusiveGateway = currentExclusiveGateway;
						// }
						// }
						// 生成连线
						// if (!extendSequenceFlows.contains(exclusiveGateway
						// .getId() + "a")) {
						// SequenceFlow extendSequenceFlow = new SequenceFlow();
						// extendSequenceFlow.setId(exclusiveGateway
						// .getId() + "a");
						// extendSequenceFlow.setName(exclusiveGateway
						// .getName());
						// extendSequenceFlow.setSourceRef(vnode.get(
						// "from").toString());
						// extendSequenceFlow
						// .setTargetRef(exclusiveGateway.getId());
						// extendSequenceFlows.add(exclusiveGateway
						// .getId() + "a");
						// process.addFlowElement(extendSequenceFlow);
						//
						// }
						// SequenceFlow flow = new SequenceFlow();
						// flow.setId(knode.toString());
						// flow.setName(vnode.get("name").toString());
						// flow.setSourceRef(exclusiveGateway.getId());
						// flow.setTargetRef(vnode.get("to").toString());
						// lineMap.put(knode.toString(), vnode.get("to")
						// .toString());
						// JSONObject jsonListener = JSONObject
						// .fromObject(vnode.get("listener"));
						// if (jsonListener != null && !jsonListener.isEmpty())
						// {
						// List<ActivitiListener> executionListeners =
						// getActivitiListeners(
						// jsonListener, "executionListener");
						// flow.setExecutionListeners(executionListeners);
						// }
						// JSONObject jsonGeneral = JSONObject
						// .fromObject(vnode.get("general"));
						// String condition = jsonGeneral.get("condition")
						// .toString();
						// flow.setConditionExpression(condition);
						// process.addFlowElement(flow);
						// GraphicInfo fromGraphicInfo =
						// model.getGraphicInfo(vnode.get("from").toString());
						// String elementId = vnode.get("from").toString();
						//
						// if(null==flowLinesMap.get(elementId)){
						// List<SequenceFlow> list = new
						// ArrayList<SequenceFlow>();
						// list.add(flow);
						// flowLinesMap.put(elementId, list);
						// }else{
						// flowLinesMap.get(elementId).add(flow);
						// }
						// GraphicInfo graphicInfo = new GraphicInfo();
						// List<GraphicInfo> graphicInfoList=new
						// ArrayList<GraphicInfo>();
						// GraphicInfo sequenceFlow1 = new GraphicInfo();
						// sequenceFlow1.setX(fromGraphicInfo.getX());
						// sequenceFlow1.setY(fromGraphicInfo.getY());
						// GraphicInfo toGraphicInfo =
						// model.getGraphicInfo(vnode.get("to").toString());
						// GraphicInfo sequenceFlow11 = new GraphicInfo();
						// sequenceFlow11.setX(toGraphicInfo.getX());
						// sequenceFlow11.setY(toGraphicInfo.getY());
						// graphicInfo.setElement(flow);
						// graphicInfoList.add(sequenceFlow1);
						// graphicInfoList.add(sequenceFlow11);
						// model.addFlowGraphicInfoList(knode.toString(),
						// graphicInfoList);
						// }
					}

				}
			}
			List<FlowElement> subFlowElements = new ArrayList<FlowElement>();
			Iterator<String> it = subProcessMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				SubProcess subProcess = new SubProcess();
				subProcess.setId(key);
				subProcess.setName(key);
				JSONArray value = subProcessMap.get(key);
				for (int i = 0; i < flowElementList.size(); i++) {
					FlowElement flowElement = flowElementList.get(i);
					for (int j = 0; j < value.size(); j++) {
						if (value.get(j).equals(flowElement.getId())) {
							subProcess.addFlowElement(flowElement);
							subFlowElements.add(flowElement);
						}
					}
				}
				flowElementList.add(subProcess);
			}
			for (FlowElement flowElement : flowElementList) {
				if (!subFlowElements.contains(flowElement)) {
					process.addFlowElement(flowElement);
				}
				if (flowLinesMap.containsKey(flowElement.getId())) {
					if (flowElement instanceof StartEvent) {
						((StartEvent) flowElement)
								.setOutgoingFlows(flowLinesMap.get(flowElement
										.getId()));
					} else if (flowElement instanceof UserTask) {
						((UserTask) flowElement).setOutgoingFlows(flowLinesMap
								.get(flowElement.getId()));
					} else if (flowElement instanceof ExclusiveGateway) {
						((ExclusiveGateway) flowElement)
								.setOutgoingFlows(flowLinesMap.get(flowElement
										.getId()));
					} else if (flowElement instanceof CallActivity) {
						((CallActivity) flowElement)
								.setOutgoingFlows(flowLinesMap.get(flowElement
										.getId()));
					} else if (flowElement instanceof SubProcess) {
						SubProcess subProcess = (SubProcess) flowElement;
						subProcess.setOutgoingFlows(flowLinesMap
								.get(flowElement.getId()));
					} else if (flowElement instanceof ParallelGateway) {
						((ParallelGateway) flowElement)
								.setOutgoingFlows(flowLinesMap.get(flowElement
										.getId()));
					}
				}

			}
			model.addProcess(process);
			model.setTargetNamespace(categroy);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;

	}

	@Override
	public String transformTemplateDefBpmnModelToStr(BpmnModel bpmnModel)
			throws BpmnException {
		Process process = bpmnModel.getProcesses().get(0);
		JSONObject root = new JSONObject();
		root.put("title", process.getName());
		root.put("initNum", 0);
		root.put("areas", new JSONArray());

		JSONObject diagram = new JSONObject();
		JSONObject bpmn = new JSONObject();
		bpmn.put("categroy", "");
		bpmn.put("version", "");
		bpmn.put("createBy", "");
		bpmn.put("deployState", "");
		bpmn.put("versionState", "");
		bpmn.put("processDefId", "");
		bpmn.put("procDefKey", "");
		bpmn.put("candidateStartUsers", process.getCandidateStarterUsers());
		bpmn.put("candidateStartGroups", process.getCandidateStarterGroups());
		bpmn.put("document", process.getDocumentation());
		bpmn.put("candidateStartUsersShowValue", "");
		diagram.put("bpmn", bpmn);
		diagram.put("variable", new JSONArray());
		root.put("diagram", diagram);
		// 节点
		JSONObject nodes = new JSONObject();
		JSONObject lines = new JSONObject();
		List<FlowElement> flowElementList = (List<FlowElement>) process
				.getFlowElements();
		setNodesAddLines(nodes, lines, flowElementList, bpmnModel);
		root.put("nodes", nodes);
		root.put("lines", lines);
		String jsonStr = root.toString();
		return jsonStr;
	}

	private void setNodesAddLines(JSONObject nodes, JSONObject lines,
			List<FlowElement> flowElementList, BpmnModel bpmnModel) {
		for (FlowElement flowElement : flowElementList) {
			String type = "";
			if (flowElement instanceof StartEvent) {
				type = "StartEvent";
			} else if (flowElement instanceof EndEvent) {
				type = "EndEvent";
			} else if (flowElement instanceof UserTask) {
				type = "UserTask";
			} else if (flowElement instanceof ExclusiveGateway) {
				type = "ExclusiveGateway";
			} else if (flowElement instanceof CallActivity) {
				type = "CallActivity";
			} else if (flowElement instanceof SubProcess) {
				type = "Subprocess";
			} else if (flowElement instanceof ParallelGateway) {
				type = "ParallelGateway";
			}
			if (flowElement instanceof SequenceFlow) {// 连线
				SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
				JSONObject sequence = new JSONObject();
				sequence.put("type", "sl");
				sequence.put("from", sequenceFlow.getSourceRef());
				sequence.put("to", sequenceFlow.getTargetRef());
				sequence.put("name", sequenceFlow.getName() == null ? ""
						: sequenceFlow.getName());
				JSONObject general = new JSONObject();
				general.put("label", "");
				general.put("condition", "");
				sequence.put("general", general);

				JSONObject listener = new JSONObject();
				general.put("label", "");
				general.put("executionListener", "");
				sequence.put("listener", listener);

				JSONObject extendConfig = new JSONObject();
				general.put("label", "");
				general.put("candidateUser", "");
				general.put("candidateGroup", "");
				general.put("needComment", "");
				general.put("openPanel", "");
				general.put("candidateUsers", "");
				general.put("candidateGroups", "");
				general.put("bathCompleteVariable", "");
				sequence.put("extendConfig", extendConfig);
				sequence.put("alt", true);
				lines.put(flowElement.getId(), sequence);
			} else {
				JSONObject element = new JSONObject();
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowElement
						.getId());
				element.put("type", type);
				element.put("left", graphicInfo.getX());
				element.put("top", graphicInfo.getY());
				element.put("width", graphicInfo.getWidth());
				if (flowElement instanceof UserTask) {
					element.put("height", (graphicInfo.getHeight() - 30.0));
				} else {
					element.put("height", graphicInfo.getHeight());
				}
				element.put("name", flowElement.getName());
				if (flowElement instanceof SubProcess) {
					element.put("zIndex", "9");
					element.put("opacity", 0.4);
					SubProcess subProcess = (SubProcess) flowElement;
					List<FlowElement> flowElements = (List<FlowElement>) subProcess
							.getFlowElements();
					JSONArray childNodes = new JSONArray();
					for (FlowElement str : flowElements) {
						childNodes.add(str.getId());
					}
					element.put("childNodes", childNodes);
				} else {
					element.put("zIndex", 10);
					element.put("opacity", 1);
				}
				element.put("alt", true);
				nodes.put(flowElement.getId(), element);
				if (flowElement instanceof SubProcess) {
					SubProcess subProcess = (SubProcess) flowElement;
					List<FlowElement> flowElements = (List<FlowElement>) subProcess
							.getFlowElements();
					setNodesAddLines(nodes, lines, flowElements, bpmnModel);
				}
			}
		}
	}

	@Override
	public ProcessResult<List<BpmnIdGroup>> queryBpmnGroupByType(String typeId) {
		ProcessResult<List<BpmnIdGroup>> result = new ProcessResult<List<BpmnIdGroup>>();
		result.setResult(bpmnHqGroupDao.selectBpmnGroupByType(typeId));
		return result;
	}

	@Override
	public List<BpmnVariable> queryProcessVariableByProcessIds(String processIds) {
		// 根据流程Id查询流程变量
		List<BpmnVariable> BpmnVariables = new ArrayList<BpmnVariable>();
		String[] processIdArr = processIds.split(",");
		String bpmnType = "";
		for (String processId : processIdArr) {
			ProcessDefinitionQuery processDefinitionQuery = processEngine
					.getRepositoryService().createProcessDefinitionQuery()
					.processDefinitionKey(processId);
			List<ProcessDefinition> pdList = processDefinitionQuery.list();
			if (pdList.size() > 0) {
				bpmnType = pdList.get(0).getCategory();
			}
			ActivitiHelper activitiHelper = new ActivitiHelper();
			activitiHelper.filterProcessDefinitionQuery(bpmnType,
					processDefinitionQuery, bpmnTemplateDefDao);
			if(processDefinitionQuery.count()>0){
				ProcessDefinition processDefinition = processDefinitionQuery
						.singleResult();
				List<BpmnVariable> BpmnVariablesTemp = bpmnVariableDao
						.selectVariableByProDefId(processDefinition.getId());
				BpmnVariables.addAll(BpmnVariablesTemp);
			}
		}
		return BpmnVariables;
	}

	@Override
	public boolean validateProcessorExpression(String processorExpression,String lineSize) {
		//表达式以and或者or结尾的情况校验start
		if(processorExpression.trim().endsWith("and") || processorExpression.trim().endsWith("or")){
			return false;
		}
		//表达式以and或者or结尾的情况校验end
		if("行号必须写在小括号内……".equals(processorExpression)){
			return false;
		}
		//如果实际没有配置组织机构，校验start
		if (Integer.valueOf(lineSize) == 0) {
			return false;
		}
		//如果实际没有配置组织机构，校验end
		//正则表达式校验是否包含非法字符start
		String regEx="[`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(processorExpression);
		boolean find = m.find();
		if (find) {
			return false;
		}
		//正则表达式校验是否包含非法字符end
		//使用栈验证表达式小括号是否匹配start
		 Stack<String> stack=new Stack<String>();
		 char[] charArray = processorExpression.toCharArray();
		 for (int i = 0; i < charArray.length; i++) {
			 String singleValue = String.valueOf(charArray[i]);
			 if(i+1 < charArray.length){
				 String singleValue2 = String.valueOf(charArray[i+1]);
				 //匹配()情况start
				 if(singleValue.equals("(") && singleValue2.equals(")")){
					 return false;
				 }
				 //匹配()情况end
				 //匹配(1)(2)的情况start
				 if(singleValue.equals(")") && singleValue2.equals("(")){
					 return false;
				 }
				 //匹配(1)(2)的情况end
			 }
			 if (singleValue.equals("(")) {
				stack.push(singleValue);
			}else if (singleValue.equals(")")) {
				if (!stack.isEmpty()) {
					stack.pop();
				}else {
					//匹配其它括号匹配成功，表达式最后多出右括号
					return false;
				}
			}
		}
		 if (!stack.isEmpty()) {
			 return false;
		}
		//使用栈验证表达式小括号是否匹配end
		//校验行号和实际配置的行数是否一致start
		boolean isRight = false;
		for (int i = 1; i < 10; i++) {
			boolean flag = processorExpression.contains(String.valueOf(i));
			if (flag && i <= Integer.valueOf(lineSize)) {
				continue;
			}else if (flag && i > Integer.valueOf(lineSize)) {
				return false;
			}
		}
		//校验行号和实际配置的行数是否一致end
		try {
			for (int i = 1; i < 10; i++) {
				processorExpression = processorExpression.replace(i + "",
						" (select 1 from dual) ");
			}
			processorExpression = processorExpression.replace("and",
					" intersect ");// 交集
			processorExpression = processorExpression.replace("or", " union ");// 并集
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("querySql", processorExpression);
			@SuppressWarnings({ "rawtypes", "unused" })
			List list = bpmnCodeDao.queryForList(queryParams);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ProcessResult<List<BpmnIdGroup>> queryBpmnIdUser() {
		// 查询所有用户信息
		ProcessResult<List<BpmnIdGroup>> pr = new ProcessResult<List<BpmnIdGroup>>();
		List<BpmnIdGroup> userList = bpmnHqGroupDao
				.selectBpmnUserAndMembership();
		if (userList.size() > 0 && userList != null) {
			pr.setResult(userList);
			pr.setSuccess(true);
			pr.setMessage(PRM.QUERY_ALL_USER_SUCCESS);
		} else {
			pr.setSuccess(false);
			pr.setCode(PRM.QUERY_ALL_USER_FAILURE_CODE);
			pr.setMessage(PRM.QUERY_ALL_USER_FAILURE);
		}
		return pr;
	}

	@Override
	public ProcessResult<List<Map<String, String>>> queryVariableAndFormInfoByProDefId(
			String processIds) {
		ProcessResult<List<Map<String, String>>> pr = new ProcessResult<List<Map<String, String>>>();
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		String[] processIdArr = processIds.split(",");
		String bpmnType = "";
		for (String processId : processIdArr) {
			ProcessDefinitionQuery processDefinitionQuery = processEngine
					.getRepositoryService().createProcessDefinitionQuery()
					.processDefinitionKey(processId);
			List<ProcessDefinition> pdList = processDefinitionQuery.list();
			if (pdList.size() > 0) {
				bpmnType = pdList.get(0).getCategory();
			}
			ActivitiHelper activitiHelper = new ActivitiHelper();
			activitiHelper.filterProcessDefinitionQuery(bpmnType,
					processDefinitionQuery, bpmnTemplateDefDao);
			if(processDefinitionQuery.count()<1){
				pr.setCode(PRM.PROCESS_TEMP_DEF_IS_DISABLED_CODE);
				pr.setMessage(PRM.PROCESS_TEMP_DEF_IS_DISABLED);
				pr.setSuccess(false);
				return pr;
			}
			ProcessDefinition processDefinition = processDefinitionQuery
					.singleResult();
			List<Map<String, String>> bpmnVariableAndFormInfoTemp = bpmnVariableDao
					.selectVariableAndFormInfoByProDefId(processDefinition
							.getId());

			listMap.addAll(bpmnVariableAndFormInfoTemp);
		}
		pr.setResult(listMap);
		return pr;
	}

	@Override
	public ProcessResult<Map<String, List<String>>> queryNodecandidateUsersByType(
			String bpmnType) {
		ProcessResult<Map<String, List<String>>> result = new ProcessResult<Map<String, List<String>>>();
		Map<String, List<String>> retuenMap = new HashMap<String, List<String>>();
		ProcessDefinitionQuery processDefinitionQuery = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionCategory(bpmnType);
		ActivitiHelper activitiHelper = new ActivitiHelper();
		activitiHelper.filterProcessDefinitionQuery(bpmnType,
				processDefinitionQuery, bpmnTemplateDefDao);
		if(processDefinitionQuery.count()<1){
			result.setCode(PRM.PROCESS_TEMP_DEF_IS_DISABLED_CODE);
			result.setMessage(PRM.PROCESS_TEMP_DEF_IS_DISABLED);
			result.setSuccess(false);
			return result;
		}
		ProcessDefinition processDefinition = processDefinitionQuery
				.singleResult();
		BpmnModel bpmnModel = processEngine.getRepositoryService()
				.getBpmnModel(processDefinition.getId());
		Process process = bpmnModel.getProcesses().get(0);
		retuenMap.put(
				"process",
				queryBpmnUserByUserAndGroupByList(
						process.getCandidateStarterUsers(),
						process.getCandidateStarterGroups()));
		result.setResult(retuenMap);
		Collection<FlowElement> flowElements = process.getFlowElements();
		for (FlowElement flowElement : flowElements) {
			if (flowElement instanceof UserTask) {
				UserTask task = (UserTask) flowElement;
				retuenMap.put(
						task.getId(),
						queryBpmnUserByUserAndGroupByList(
								task.getCandidateUsers(),
								task.getCandidateGroups()));
				result.setResult(retuenMap);
				result.setSuccess(true);
			}

		}
		return result;
	}

	/**
	 * 根据候选人候选组查询 处理人
	 * 
	 * @param candidateUsers
	 * @param candidateGroups
	 * @return
	 */
	private List<String> queryBpmnUserByUserAndGroupByList(
			List<String> candidateUsers, List<String> candidateGroups) {
		Set<String> users = new HashSet<String>();
		List<User> groupList = new ArrayList<User>();
		for (String user : candidateUsers) {
			users.add(user);
		}
		for (String groupIds : candidateGroups) {
			if (groupIds != null && groupIds.length() > 0) {
				UserQuery userQuery = processEngine.getIdentityService()
						.createUserQuery();
				groupList = userQuery.memberOfGroup(groupIds).list();
				for (User user : groupList) {
					users.add(user.getId());

				}
			}
		}
		return new ArrayList<String>(users);

	}

	public ProcessResult<List<BpmnTask>> queryAllNodeInfoByType(String bpmnType,int queryType) {
		ProcessResult<List<BpmnTask>> result = new ProcessResult<List<BpmnTask>>();
		List<BpmnTask> bpmnTasks = new ArrayList<BpmnTask>();
		ProcessDefinitionQuery processDefinitionQuery = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionCategory(bpmnType);
		ActivitiHelper activitiHelper = new ActivitiHelper();
		activitiHelper.filterProcessDefinitionQuery(bpmnType,
				processDefinitionQuery, bpmnTemplateDefDao);
		if(processDefinitionQuery.count()<1){
			result.setCode(PRM.PROCESS_TEMP_DEF_IS_DISABLED_CODE);
			result.setMessage(PRM.PROCESS_TEMP_DEF_IS_DISABLED);
			result.setSuccess(false);
			return result;
		}
		ProcessDefinition processDefinition = processDefinitionQuery
				.singleResult();
		BpmnModel bpmnModel = processEngine.getRepositoryService()
				.getBpmnModel(processDefinition.getId());
		Process process = bpmnModel.getProcesses().get(0);
		Collection<FlowElement> flowElements = process.getFlowElements();
		List<BpmnTaskExtend> bpmnTaskExtends = bpmnTaskExtendDao
				.selectTaskExtendByProDefId(processDefinition.getId());
		List<BpmnTableSecu> tableSecuList = bpmnTablePrivilegeDao
				.selectTablePrivilegeByProDefId(processDefinition.getId());
		List<BpmnTable> tableList = bpmnTableService.selectBpmnTableAll();
		List<BpmnField> fieldList = bpmnTableService.selectBpmnFieldAll();
		List<BpmnFieldSecu> fieldSecuList = bpmnFieldPrivilegeDao
				.selectFieldPrivilegeByProDefId(processDefinition.getId());
		for (FlowElement flowElement : flowElements) {
			if (queryType==1) {
				if (flowElement instanceof UserTask) {
					UserTask task = (UserTask) flowElement;
					BpmnTaskExtend setBpmnTaskExtend = null;
					for (BpmnTaskExtend bpmnTaskExtend : bpmnTaskExtends) {
						if (bpmnTaskExtend.getTaskKey() != null
								&& bpmnTaskExtend.getTaskKey().equals(task.getId())) {
							setBpmnTaskExtend = bpmnTaskExtend;
						}
					}
					BpmnTask bpmnTask = new BpmnTask();
					bpmnTask.setKey(task.getId());
					bpmnTask.setName(task.getName());
					if (setBpmnTaskExtend != null) {
						bpmnTask.setBpmnTaskExtend(setBpmnTaskExtend);
					}
					BpmnTaskPrivilege bpmnTaskPrivilege = getBpmnTaskPrivilege(
							task.getId(), processDefinition.getId(), bpmnType,
							tableList, tableSecuList, fieldSecuList, fieldList);
					bpmnTask.setBpmnTaskPrivilege(bpmnTaskPrivilege);
					bpmnTasks.add(bpmnTask);
				}
				if (flowElement instanceof SubProcess) {
					SubProcess subProcess = (SubProcess) flowElement;
					Collection<FlowElement> flowElements2 = subProcess
							.getFlowElements();
					for (FlowElement flowElement2 : flowElements2) {
						if (flowElement2 instanceof UserTask) {
							UserTask task = (UserTask) flowElement2;
							BpmnTaskExtend setBpmnTaskExtend = null;
							for (BpmnTaskExtend bpmnTaskExtend : bpmnTaskExtends) {
								if (bpmnTaskExtend.getTaskKey() != null
										&& bpmnTaskExtend.getTaskKey().equals(
												task.getId())) {
									setBpmnTaskExtend = bpmnTaskExtend;
								}
							}
							BpmnTask bpmnTask = new BpmnTask();
							bpmnTask.setKey(task.getId());
							bpmnTask.setName(task.getName());
							if (setBpmnTaskExtend != null) {
								bpmnTask.setBpmnTaskExtend(setBpmnTaskExtend);
							}
							BpmnTaskPrivilege bpmnTaskPrivilege = getBpmnTaskPrivilege(
									task.getId(), processDefinition.getId(),
									bpmnType, tableList, tableSecuList,
									fieldSecuList, fieldList);
							bpmnTask.setBpmnTaskPrivilege(bpmnTaskPrivilege);
							bpmnTasks.add(bpmnTask);
						}
					}
				}
				if (flowElement instanceof StartEvent) {
					StartEvent startEvent = (StartEvent) flowElement;
					BpmnTask bpmnTask = new BpmnTask();
					bpmnTask.setName(startEvent.getName());
					bpmnTask.setKey(startEvent.getId());
					BpmnTaskPrivilege bpmnTaskPrivilege = getBpmnTaskPrivilege(
							startEvent.getId(), processDefinition.getId(),
							bpmnType, tableList, tableSecuList, fieldSecuList,
							fieldList);
					if (bpmnTaskPrivilege != null) {
						bpmnTask.setBpmnTaskPrivilege(bpmnTaskPrivilege);
					}
					bpmnTasks.add(bpmnTask);
				}
			}else {
				if (flowElement instanceof StartEvent) {
					StartEvent startEvent = (StartEvent) flowElement;
					BpmnTask bpmnTask = new BpmnTask();
					bpmnTask.setName(startEvent.getName());
					bpmnTask.setKey(startEvent.getId());
					BpmnTaskPrivilege bpmnTaskPrivilege = getBpmnTaskPrivilege(
							startEvent.getId(), processDefinition.getId(),
							bpmnType, tableList, tableSecuList, fieldSecuList,
							fieldList);
					if (bpmnTaskPrivilege != null) {
						bpmnTask.setBpmnTaskPrivilege(bpmnTaskPrivilege);
					}
					bpmnTasks.add(bpmnTask);
					break;
				}
			}
		}
		result.setResult(bpmnTasks);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 递归遍历所有的结点信息
	 * 
	 * @param bpmnType
	 * @return
	 */
	public ProcessResult<List<BpmnTask>> queryAllNodeInfoByType_recursive(
			String bpmnType) {

		ProcessResult<List<BpmnTask>> pr = new ProcessResult<List<BpmnTask>>();
		List<BpmnTask> bpmnTasks = new ArrayList<BpmnTask>();
		ProcessDefinitionQuery processDefinitionQuery = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionCategory(bpmnType);
		ActivitiHelper activitiHelper = new ActivitiHelper();
		activitiHelper.filterProcessDefinitionQuery(bpmnType,
				processDefinitionQuery, bpmnTemplateDefDao);
		if(processDefinitionQuery.count()<1){
			pr.setCode(PRM.PROCESS_TEMP_DEF_IS_DISABLED_CODE);
			pr.setMessage(PRM.PROCESS_TEMP_DEF_IS_DISABLED);
			pr.setSuccess(false);
			return pr;
		}
		ProcessDefinition processDefinition = processDefinitionQuery
				.singleResult();
		// 排序 Start
		BpmnModel bpmnModel = processEngine.getRepositoryService()
				.getBpmnModel(processDefinition.getId());
		Process process = bpmnModel.getProcesses().get(0);
		Collection<FlowElement> flowElements = process.getFlowElements();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) processEngine
				.getRepositoryService())
				.getDeployedProcessDefinition(processDefinition.getId());
		List<ActivityImpl> activitiList = def.getActivities();
		List<PvmActivity> allNode = new ArrayList<PvmActivity>();
		for (ActivityImpl activityImpl : activitiList) {
			if (activityImpl.getId().startsWith("start") && "startEvent".equals(activityImpl.getProperty("type"))) {
				List<PvmTransition> outgoingTransitions = activityImpl
						.getOutgoingTransitions();
				PvmActivity source = outgoingTransitions.get(0).getSource();
				allNode.add(source);
				recursiveProcessDefinitionTree(outgoingTransitions, allNode);
				for (PvmActivity pvmActivity : allNode) {
					String id = pvmActivity.getId();
					if(!BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME.equals(id)){
						BpmnTask bpmnTask = new BpmnTask();
						bpmnTask.setKey(id);
						bpmnTask.setDescription((String)pvmActivity.getProperty("documentation"));
						for (FlowElement flowElement : flowElements) {
							if (flowElement.getId().equals(id)) {
								bpmnTask.setName(flowElement.getName());
								break;
							}
							if (flowElement instanceof SubProcess) {
								Collection<FlowElement> flowElements2 = ((SubProcess) flowElement)
										.getFlowElements();
								for (FlowElement flowElement2 : flowElements2) {
									if (flowElement2.getId().equals(id)) {
										bpmnTask.setName(flowElement2.getName());
										break;
									}
								}
							}
						}
						bpmnTasks.add(bpmnTask);
					}
				}
			}
		}
		if (bpmnTasks != null && bpmnTasks.size() > 0) {
			pr.setResult(bpmnTasks);
			pr.setSuccess(true);
		} else {
			pr.setSuccess(false);
		}
		// 排序 end
		return pr;
	}

	/**
	 * 递归遍历流程定义树
	 * 
	 * @param outgoingTransitions
	 * @param allNode
	 * @return
	 */
	private void recursiveProcessDefinitionTree(
			List<PvmTransition> outgoingTransitions, List<PvmActivity> allNode) {
		List<PvmActivity> allNodeTemp = new ArrayList<PvmActivity>();
		List<PvmActivity> allNodeTempT = new ArrayList<PvmActivity>();
		List<PvmActivity> destinationSubProcessList = new ArrayList<PvmActivity>();
		for (PvmTransition pvmTransition : outgoingTransitions) {
			PvmActivity destination = pvmTransition.getDestination();
			if (destination.getId().startsWith("user") || "userTask".equals(destination.getProperty("type"))) { // 任务节点
				allNodeTemp.add(destination);
			} else if (destination.getId().startsWith("sub") && "subProcess".equals(destination.getProperty("type"))) { // 子任务结点
				destinationSubProcessList.add(destination);
				//会签节点特殊，单独处理
			}else if(destination.getId().startsWith("countersign"))
				allNodeTemp.add(destination);
		}
		if (destinationSubProcessList != null
				&& destinationSubProcessList.size() > 0) {
			for (PvmActivity destinationSubProcess : destinationSubProcessList) {
				List<? extends PvmActivity> activities = destinationSubProcess
						.getActivities();
				List<PvmActivity> allNodeTemp1 = new ArrayList<PvmActivity>();
				for (PvmActivity pvmActivity : activities) {
					if (pvmActivity.getId().startsWith("start") && "startEvent".equals(pvmActivity.getProperty("type"))) {
						List<PvmTransition> outgoingTransitions2 = pvmActivity
								.getOutgoingTransitions();
						recursiveProcessDefinitionTree(outgoingTransitions2,
								allNodeTemp1);
					}
				}
				if (allNodeTemp1 != null && allNodeTemp1.size() > 0) {
					allNode.addAll(allNodeTemp1);
					allNodeTempT.add(destinationSubProcess);
				}
			}
		}
		if (allNodeTemp != null && allNodeTemp.size() > 0) {
			for (PvmActivity pvmActivity : allNodeTemp) {
				if (!allNode.contains(pvmActivity)) {
					//会签节点特殊，增加了个虚拟节点，sequence是K结尾，usertask是Ka结尾
					if(!pvmActivity.getId().substring(pvmActivity.getId().length()-1,pvmActivity.getId().length()).equalsIgnoreCase("K")||
							!pvmActivity.getIncomingTransitions().get(0).getId().substring(pvmActivity.getIncomingTransitions().get(0).getId().length()-2, pvmActivity.getIncomingTransitions().get(0).getId().length()).equalsIgnoreCase("Ka")){
						allNode.add(pvmActivity);
					}
					allNodeTempT.add(pvmActivity);
				}
			}
		}
		List<PvmTransition> outgoingTransitions2 = new ArrayList<PvmTransition>();
		if (allNodeTempT != null && allNodeTempT.size() > 0) {
			for (PvmActivity pvmActivity : allNodeTempT) {
				List<PvmTransition> outgoingTransitions3 = pvmActivity
						.getOutgoingTransitions();
				outgoingTransitions2.addAll(outgoingTransitions3);
			}
		}
		if (outgoingTransitions2 != null && outgoingTransitions2.size() > 0) {
			recursiveProcessDefinitionTree(outgoingTransitions2, allNode);
		}
	}

	private BpmnTaskPrivilege getBpmnTaskPrivilege(String taskKey,
			String processDefinitionId, String bpmnType,
			List<BpmnTable> tableList, List<BpmnTableSecu> tableSecuList,
			List<BpmnFieldSecu> fieldSecuList, List<BpmnField> fieldList) {
		BpmnTaskPrivilege bpmnTaskPrivilege = new BpmnTaskPrivilege();
		bpmnTaskPrivilege = getProcessBusinessRole(bpmnTaskPrivilege, taskKey,
				processDefinitionId, "starter", bpmnType, tableList,
				tableSecuList, fieldSecuList, fieldList);
		if (bpmnTaskPrivilege.getStarterList().size() > 0) {
			bpmnTaskPrivilege.setStarter(true);
		}
		// 提交人
		bpmnTaskPrivilege = getProcessBusinessRole(bpmnTaskPrivilege, taskKey,
				processDefinitionId, "submitter", bpmnType, tableList,
				tableSecuList, fieldSecuList, fieldList);
		if (bpmnTaskPrivilege.getSubmitterList().size() > 0) {
			bpmnTaskPrivilege.setSubmitter(true);
		}

		bpmnTaskPrivilege = getProcessBusinessRole(bpmnTaskPrivilege, taskKey,
				processDefinitionId, "candidater", bpmnType, tableList,
				tableSecuList, fieldSecuList, fieldList);
		if (bpmnTaskPrivilege.getCandidaterList().size() > 0) {
			bpmnTaskPrivilege.setCandidater(true);
		}
		return bpmnTaskPrivilege;
	}

	private BpmnTaskPrivilege getProcessBusinessRole(
			BpmnTaskPrivilege taskPrivilege, String taskDefKey,
			String proDefId, String runTimeBpmnRole, String bpmnType,
			List<BpmnTable> tableList, List<BpmnTableSecu> tableSecuList,
			List<BpmnFieldSecu> fieldSecuList, List<BpmnField> fieldList) {
		// 表权限集合
		List<BpmnTablePrivilege> tableBpmnTablePrivileges = new ArrayList<BpmnTablePrivilege>();
		for (BpmnTableSecu tableSecu : tableSecuList) {
			if (!taskDefKey.equals(tableSecu.getTaskDefKey())
					|| !runTimeBpmnRole.equals(tableSecu.getRunTimeBpmnRole())) {
				continue;
			}
			BpmnTablePrivilege tablePrivilege = new BpmnTablePrivilege();
			tablePrivilege.setId(tableSecu.getId());
			tablePrivilege.setTableId(tableSecu.getTableId());
			for (BpmnTable bpmnTable : tableList) {
				if (tableSecu.getTableDBName().equals(bpmnTable.getDbName())) {
					tablePrivilege.setTableName(bpmnTable.getName());
					break;
				}
			}
			tablePrivilege.setTableDBName(tableSecu.getTableDBName());
			tablePrivilege.setCanNotAdd(tableSecu.getCanNotAdd());
			tablePrivilege.setCanNotDelete(tableSecu.getCanNotDelete());
			tablePrivilege.setCanNotModify(tableSecu.getCanNotModify());
			tablePrivilege.setCanNotVisible(tableSecu.getCanNotVisble());

			// 根据表id查询字段权限信息
			List<BpmnFieldPrivilege> fieldBpmnFieldPrivileges = new ArrayList<BpmnFieldPrivilege>();
			for (BpmnFieldSecu fieldSecu : fieldSecuList) {
				if (!taskDefKey.equals(fieldSecu.getTaskDefKey())
						|| !fieldSecu.getTableId().equals(
								fieldSecu.getTableId())
						|| !runTimeBpmnRole.equals(fieldSecu
								.getRunTimeBpmnRole())) {
					continue;
				}
				BpmnFieldPrivilege fieldPrivilege = new BpmnFieldPrivilege();
				fieldPrivilege.setId(fieldSecu.getId());
				fieldPrivilege.setFieldId(fieldSecu.getFieldId());
				for (BpmnField bpmnField : fieldList) {
					if (fieldSecu.getFieldDBName()
							.equals(bpmnField.getDbName())) {
						fieldPrivilege.setFieldName(bpmnField.getName());
						break;
					}
				}
				fieldPrivilege.setFieldDBName(fieldSecu.getFieldDBName());
				fieldPrivilege.setCanNotModify(fieldSecu.getCanNotModify());
				fieldPrivilege.setCanNotVisble(fieldSecu.getCanNotVisble());
				fieldPrivilege.setMustFillIn(fieldSecu.getMustFillIn());
				fieldBpmnFieldPrivileges.add(fieldPrivilege);
			}
			tablePrivilege.setBpmnFieldPrivilegeList(fieldBpmnFieldPrivileges);
			tableBpmnTablePrivileges.add(tablePrivilege);
		}

		if (runTimeBpmnRole.equals("starter")) {
			taskPrivilege.setTaskDefKey(taskDefKey);
			taskPrivilege.setProDefId(proDefId);
			taskPrivilege.setStarterList(tableBpmnTablePrivileges);
		} else if (runTimeBpmnRole.equals("candidater")) {
			taskPrivilege.setCandidaterList(tableBpmnTablePrivileges);
		} else if (runTimeBpmnRole.equals("submitter")) {
			taskPrivilege.setSubmitterList(tableBpmnTablePrivileges);
		}
		return taskPrivilege;
	}

	@Override
	public List<Map<String, String>> queryProcessFormInfo(String pdId) {
		ProcessDefinition processDefinition = processEngine
				.getRepositoryService().getProcessDefinition(pdId);
		String deploymentId = processDefinition.getDeploymentId();
		List<Map<String, String>> queryProcessFormInfo = bpmnTemplateDefDao
				.queryProcessFormInfo(deploymentId);
		return queryProcessFormInfo;
	}

	@Override
	public ProcessResult<List<BpmnCustomButton>> queryCustomButtonTaskByKey(
			String taskKey, String bpmnType) {
		ProcessResult<List<BpmnCustomButton>> result = new ProcessResult<List<BpmnCustomButton>>();
		List<BpmnCustomButton> bpmnCustomButtons = new ArrayList<BpmnCustomButton>();
		ProcessDefinitionQuery processDefinitionQuery = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionCategory(bpmnType);
		ActivitiHelper activitiHelper = new ActivitiHelper();
		activitiHelper.filterProcessDefinitionQuery(bpmnType,
				processDefinitionQuery, bpmnTemplateDefDao);
		if(processDefinitionQuery.count()<1){
			result.setCode(PRM.PROCESS_TEMP_DEF_IS_DISABLED_CODE);
			result.setMessage(PRM.PROCESS_TEMP_DEF_IS_DISABLED);
			result.setSuccess(false);
			return result;
		}
		ProcessDefinition processDefinition = processDefinitionQuery
				.singleResult();
		BpmnTaskButtonExtend bpmnTaskButtonExtend = new BpmnTaskButtonExtend();
		bpmnTaskButtonExtend.setTaskKey(taskKey);
		bpmnTaskButtonExtend.setProcDefId(processDefinition.getId());
		List<BpmnTaskButtonExtend> list = bpmnTaskButtonExtendDao
				.selectBpmnTaskButtonExtendByProDefIdAndTaskKey(bpmnTaskButtonExtend);
		for (BpmnTaskButtonExtend bpmnTaskButtonExtend2 : list) {
			BpmnCustomButton bpmnCustomButton = new BpmnCustomButton();
			bpmnCustomButton.setButtonKey(bpmnTaskButtonExtend2.getButtonKey());
			bpmnCustomButton.setButtonName(bpmnTaskButtonExtend2
					.getButtonName());
			bpmnCustomButtons.add(bpmnCustomButton);
		}
		result.setResult(bpmnCustomButtons);
		result.setSuccess(true);
		return result;
	}

	@Override
	public ProcessResult<BpmnTemplateDef> selectTemplateDefBydeploymentId(
			String deploymentId) {
		ProcessResult<BpmnTemplateDef> pr = new ProcessResult<BpmnTemplateDef>();
		BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
				.selectTemplateDefBydeploymentId(deploymentId);
		if (bpmnTemplateDef == null) {
			pr.setCode(PRM.PROCESS_HAVE_NO_DEF_INFO_CODE);
			pr.setMessage(PRM.PROCESS_HAVE_NO_DEF_INFO);
			pr.setSuccess(false);
		} else {
			byte[] b = bpmnTemplateDef.getContentBytes();
			try {
				String contentBytesStr = new String(b, "GBK");
				bpmnTemplateDef.setContentBytesStr(contentBytesStr);
				pr.setResult(bpmnTemplateDef);
				pr.setSuccess(true);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				pr.setCode(PRM.PROCESS_CODE_TRANS_ERROR_CODE);
				pr.setMessage(PRM.PROCESS_CODE_TRANS_ERROR);
				pr.setSuccess(false);
			}
		}
		return pr;
	}

	@Override
	public List<TreeJson> selectBpmnUserAndMembershipTree() {
		return bpmnHqGroupDao.selectBpmnUserAndMembershipTree();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProcessResult<List<BpmnTask>> queryNodeInfoByUserId(String userId,
			String bpmnType, String ticketId) {
		ProcessResult<List<BpmnTask>> result = new ProcessResult<List<BpmnTask>>();
		List<BpmnTask> bpmnTasks = new ArrayList<BpmnTask>();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//根据流程类型查询流程定义
		ProcessDefinitionQuery processDefinitionQuery = repositoryService
				.createProcessDefinitionQuery().processDefinitionCategory(
						bpmnType);
		List<ProcessDefinition> processDefinitions = processDefinitionQuery.list();
		//通过工单ID和流程定义ID查找相应的流程实例
		ProcessInstanceQuery processInstanceQuery=runtimeService
				.createProcessInstanceQuery().processInstanceBusinessKey(ticketId);
		ProcessInstance processInstance=null;
		ProcessDefinition processDef = null;
		BpmnModel bpmnModel = null;
		String processDefinitionId="";
		for (ProcessDefinition processDefinition : processDefinitions) {
			processInstance=processInstanceQuery.processDefinitionId(processDefinition.getId()).singleResult();
			if (processInstance != null) {
				processDef = processDefinition;
				break;
			}
		}
		if (processInstance != null) {
			bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDef.getId());
			processDefinitionId = processDef.getId();
		}else {
			ActivitiHelper activitiHelper = new ActivitiHelper();
			activitiHelper.filterProcessDefinitionQuery(bpmnType,
					processDefinitionQuery, bpmnTemplateDefDao);
			if(processDefinitionQuery.count()<1){
				result.setCode(PRM.PROCESS_TEMP_DEF_IS_DISABLED_CODE);
				result.setMessage(PRM.PROCESS_TEMP_DEF_IS_DISABLED);
				result.setSuccess(false);
				return result;
			}
			processDefinitionId =processDefinitionQuery.singleResult().getId();
			bpmnModel = processEngine.getRepositoryService()
					.getBpmnModel(processDefinitionId);
		}
		Process process = bpmnModel.getProcesses().get(0);
		Collection<FlowElement> flowElements = process.getFlowElements();
		for (FlowElement flowElement : flowElements) {
			if (flowElement instanceof UserTask && !flowElement.getId().equals(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME)) {
				UserTask task = (UserTask) flowElement;
				String currentTaskDefinitionKey = task.getId();
				BpmnTask bpmnTask = new BpmnTask();
				Map<String, Object> map = new HashMap<String, Object>();
				Task currentTask = processEngine.getTaskService()
						.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
				map.put("processDefinitionId", processDefinitionId);
				map.put("taskKey", currentTaskDefinitionKey);
				BpmnTaskExtend bpmnTaskExtend = bpmnTaskExtendDao
						.selectTaskExtendByProDefIdAndTaskKey(map);
				List<String> userList = candidateUsersService.getNodeProcessor(
						bpmnTaskExtend.getProcessor(),
						bpmnTaskExtend.getProcessorExpression(),
						currentTask.getProcessInstanceId(),
						null, ticketId,
						currentTask.getTaskDefinitionKey(),"");//上一个历史任务的处理人
				List<String> candidateGroups = task.getCandidateGroups();
				List<String> usersInAllgroups = new ArrayList<String>();
				for (String groupId : candidateGroups) {
					 List<User> usersIngrouplist = processEngine.getIdentityService().createUserQuery().memberOfGroup(groupId).list();
					// List<String> userIdInGroup = new ArrayList<String>();
					 for (User user : usersIngrouplist) {
						String id = user.getId();
						//userIdInGroup.add(id);
						usersInAllgroups.add(id);
					}
					 //userList = (List<String>) CollectionUtils.union(userList, userIdInGroup);
				}
				if (null != userList && userList.size() > 0) {
					 userList = (List<String>) CollectionUtils.union(userList, usersInAllgroups);
				}
				if ((null != userList && userList.contains(userId)) || (usersInAllgroups.size() > 0 && usersInAllgroups.contains(userId))) {
					bpmnTask.setKey(task.getId());
					bpmnTask.setName(task.getName());
					bpmnTasks.add(bpmnTask);
				}
				
			} else if (flowElement instanceof SubProcess) {
				SubProcess subProcess = (SubProcess) flowElement;
				Collection<FlowElement> flowElements2 = subProcess
						.getFlowElements();
				for (FlowElement flowElement2 : flowElements2) {
					if (flowElement2 instanceof UserTask) {
						UserTask task = (UserTask) flowElement2;
						String currentTaskDefinitionKey = task.getId();
						BpmnTask bpmnTask = new BpmnTask();
						Map<String, Object> map = new HashMap<String, Object>();
						Task currentTask = processEngine.getTaskService()
								.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
						map.put("processDefinitionId", processDefinitionId);
						map.put("taskKey", currentTaskDefinitionKey);
						BpmnTaskExtend bpmnTaskExtend = bpmnTaskExtendDao
								.selectTaskExtendByProDefIdAndTaskKey(map);
						List<String> userList = candidateUsersService.getNodeProcessor(
								bpmnTaskExtend.getProcessor(),
								bpmnTaskExtend.getProcessorExpression(),
								currentTask.getProcessInstanceId(),
								null, ticketId,
								currentTask.getTaskDefinitionKey(),"");//上一个历史任务的处理人
						if (userList.contains(userId)) {
							bpmnTask.setKey(task.getId());
							bpmnTask.setName(task.getName());
							bpmnTasks.add(bpmnTask);
						}
					}
				}
			} 
		}
		result.setResult(bpmnTasks);
		result.setSuccess(true);
		return result;
	}
	public ProcessResult<List<BpmnIdGroup>> queryBpmnIdGroupByName(String name,String type) {
		//如果type==1，则查询部门；如果type==2，则查询角色
		List<Group> list = processEngine.getIdentityService()
				.createGroupQuery().groupNameLike("%" + name + "%").list();
		List<BpmnIdGroup> groupList = new ArrayList<BpmnIdGroup>();
		ProcessResult<List<BpmnIdGroup>> pr = new ProcessResult<List<BpmnIdGroup>>();
		if (list != null && list.size() > 0) {
			for (Group group : list) {
				BpmnIdGroup bpmnIdGroup = new BpmnIdGroup();
				bpmnIdGroup.setId(group.getId());
				bpmnIdGroup.setName(group.getName());
				bpmnIdGroup.setType(group.getType());
			    if (group.getType().equals(type)) {
			    	groupList.add(bpmnIdGroup);
				}
			}
		}else {
			pr.setSuccess(false);
			pr.setCode(PRM.PROCESS_QUERY_GROUP_IS_NULL_CODE);
			pr.setMessage(PRM.PROCESS_QUERY_GROUP_IS_NULL);
		}
		if(groupList != null && groupList.size() > 0){
			pr.setResult(groupList);
			pr.setSuccess(true);
		}else {
			pr.setSuccess(false);
			pr.setCode(PRM.PROCESS_QUERY_GROUP_IS_NULL_CODE);
			pr.setMessage(PRM.PROCESS_QUERY_GROUP_IS_NULL);
		}
		return pr;
	}
	@Override
	public ProcessResult<String> importTemplateDef(String id, String bytes,
			String deployState, String canvasWidth, String canvasHeight) {
		String str = "";
		boolean flag = true;
		//新的流程定义模版对象
		BpmnTemplateDef bpmnTemplate = transformTemplateDefByStr(id, bytes);
		//旧的流程定义模版对象
		BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
				.selectTemplateDefById(id);
		//把旧模版的信息设置给新模版
		bpmnTemplate.setCategory(bpmnTemplateDef.getCategory());
		bpmnTemplate.setName(bpmnTemplateDef.getName());
		bpmnTemplate.setDeployState(bpmnTemplateDef.getDeployState());
		bpmnTemplate.setCreateTime(bpmnTemplateDef.getCreateTime());
		bpmnTemplate.setVersion(bpmnTemplateDef.getVersion());
		bpmnTemplate.setDeploymentId(bpmnTemplateDef.getDeploymentId());
		bpmnTemplate.setModifyTime(new Date());
		bpmnTemplate.setCanvasHeight(canvasHeight);
		bpmnTemplate.setCanvasWidth(canvasWidth);
		bpmnTemplateDefDao.updateBpmnTempDef(bpmnTemplate);
		str = PRM.PROCESS_DEF_SAVE_SUCCESS;
		ProcessResult<String> pr = new ProcessResult<String>();
		pr.setResult(id);
		pr.setMessage(str);
		pr.setSuccess(flag);
		return pr;
	}
	@Override
	public ProcessResult<BpmnTemplateDef> importToshowTemplateDef(String id, String bytes,
			String deployState, String canvasWidth, String canvasHeight) {
		String str = "";
		
		//新的流程定义模版对象
		BpmnTemplateDef bpmnTemplate = transformTemplateDefByStr(id, bytes);
		if (id == null || id.equals("")) {
			id = bpmnTemplateDefDao.selectSysId();
			bpmnTemplate.setId(id);
			bpmnTemplate.setCreateTime(new Date());
			bpmnTemplate.setCanvasHeight(canvasHeight);
			bpmnTemplate.setCanvasWidth(canvasWidth);
			bpmnTemplate.setContentBytesStr(bytes);
			bpmnTemplateDefDao.insertBpmnTemplateDef(bpmnTemplate);
			str = PRM.PROCESS_DEF_SAVE_SUCCESS;
		}else {
			//旧的流程定义模版对象
			BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
					.selectTemplateDefById(id);
			if(bpmnTemplateDef.getDeploymentId() != null){
				ProcessDefinition singleResult = processEngine.getRepositoryService().createProcessDefinitionQuery()
						.deploymentId(bpmnTemplateDef.getDeploymentId()).singleResult();
				String procDefKey = singleResult.getKey();
				org.activiti.engine.impl.util.json.JSONObject jsonObjContent = new org.activiti.engine.impl.util.json.JSONObject(bytes);
				org.activiti.engine.impl.util.json.JSONObject jsonObjDiagram = new org.activiti.engine.impl.util.json.JSONObject(jsonObjContent.get(
						"diagram").toString());
				org.activiti.engine.impl.util.json.JSONObject jsonObjBpmn = new org.activiti.engine.impl.util.json.JSONObject(jsonObjDiagram.get(
						"bpmn").toString());
				jsonObjBpmn.put("procDefKey", procDefKey);
				jsonObjBpmn.put("categroy", bpmnTemplateDef.getCategory());
				jsonObjDiagram.put("bpmn", jsonObjBpmn);
				jsonObjContent.put("diagram", jsonObjDiagram);
				bytes = jsonObjContent.toString();
			}
			//把旧模版的信息设置给新模版
			bpmnTemplate.setContentBytesStr(bytes);
			bpmnTemplate.setName(bpmnTemplateDef.getName());
			bpmnTemplate.setDeployState(bpmnTemplateDef.getDeployState());
			bpmnTemplate.setCreateTime(bpmnTemplateDef.getCreateTime());
			bpmnTemplate.setVersion(bpmnTemplateDef.getVersion());
			bpmnTemplate.setDeploymentId(bpmnTemplateDef.getDeploymentId());
			bpmnTemplate.setModifyTime(new Date());
			bpmnTemplate.setCanvasHeight(canvasHeight);
			bpmnTemplate.setCanvasWidth(canvasWidth);
			str = PRM.PROCESS_DEF_SAVE_SUCCESS;
		}
		
		ProcessResult<BpmnTemplateDef> pr = new ProcessResult<BpmnTemplateDef>();
		pr.setResult(bpmnTemplate);
		pr.setMessage(str);
		pr.setSuccess(true);
		return pr;
	}
	@Override
	@SuppressWarnings("unchecked")
	public ProcessResult<List<String>> selectUserAndDeptAndRoleName(String deptIds, String roleIds, String userIds) {
		// TODO Auto-generated method stub
		ProcessResult<List<String>> pr = new ProcessResult<List<String>>();
		List<Map<String, Object>> deptIdsAndNames  = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> roleIdsAndNames  = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> userIdsAndNames  = new ArrayList<Map<String,Object>>();
		if (null != deptIds && deptIds.length() > 0) {
			List<String> deptIdsList = new ArrayList<String>();
			String[] split = deptIds.split(",");
			for (String string : split) {
				deptIdsList.add(string);
			}
			deptIdsAndNames = bpmnActIdentityLinkEntityDao.selectDeptName(deptIdsList);
		}
		if (null != roleIds && roleIds.length() > 0) {
			List<String> roleIdsList = new ArrayList<String>();
			String[] split = roleIds.split(",");
			for (String string : split) {
				roleIdsList.add(string);
			}
			roleIdsAndNames = bpmnActIdentityLinkEntityDao.selectRoleName(roleIdsList);
		}
		if (null != userIds && userIds.length() > 0) {
			List<String> userIdsList =  new ArrayList<String>();
			String[] split = userIds.split(",");
			for (String string : split) {
				userIdsList.add(string);
			}
			userIdsAndNames = bpmnActIdentityLinkEntityDao.selectUserName(userIdsList);
		}
		List<String> arryList = new ArrayList<String>();
		
		List<String> temp = (List<String>)CollectionUtils.union(arryList, deptIdsAndNames);
		temp = (List<String>)CollectionUtils.union(temp, roleIdsAndNames);
		arryList = (List<String>)CollectionUtils.union(temp, userIdsAndNames);
		
		pr.setResult(arryList);
		pr.setSuccess(true);
		
		return pr;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProcessResult<List<BpmnTask>> queryNodeInfoByUserIdAndBpmnType(
			String userId, String bpmnType) {
		ProcessResult<List<BpmnTask>> result = new ProcessResult<List<BpmnTask>>();
		List<BpmnTask> bpmnTasks = new ArrayList<BpmnTask>();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//根据流程类型查询流程定义
		ProcessDefinitionQuery processDefinitionQuery = repositoryService
				.createProcessDefinitionQuery().processDefinitionCategory(
						bpmnType);
		BpmnModel bpmnModel = null;
		String processDefinitionId="";
		ActivitiHelper activitiHelper = new ActivitiHelper();
		activitiHelper.filterProcessDefinitionQuery(bpmnType, processDefinitionQuery, bpmnTemplateDefDao);
		if(processDefinitionQuery.count()<1){
			result.setCode(PRM.PROCESS_TEMP_DEF_IS_DISABLED_CODE);
			result.setMessage(PRM.PROCESS_TEMP_DEF_IS_DISABLED);
			result.setSuccess(false);
			return result;
		}
		processDefinitionId =processDefinitionQuery.singleResult().getId();
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDefinitionId);
		Process process = bpmnModel.getProcesses().get(0);
		Collection<FlowElement> flowElements = process.getFlowElements();
		for (FlowElement flowElement : flowElements) {
			if (flowElement instanceof UserTask && !flowElement.getId().equals(BpmnProcessConstant.PROCESS_DEF_STARTEVENT_USERTASK_NAME)) {
				UserTask task = (UserTask) flowElement;
				String currentTaskDefinitionKey = task.getId();
				BpmnTask bpmnTask = new BpmnTask();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("processDefinitionId", processDefinitionId);
				map.put("taskKey", currentTaskDefinitionKey);
				BpmnTaskExtend bpmnTaskExtend = bpmnTaskExtendDao
						.selectTaskExtendByProDefIdAndTaskKey(map);
				List<String> userList = candidateUsersService.getNodeProcessor(
						bpmnTaskExtend.getProcessor(),
						bpmnTaskExtend.getProcessorExpression(),
						"",null,"",currentTaskDefinitionKey,"");//上一个历史任务的处理人
				List<String> candidateGroups = task.getCandidateGroups();
				for (String groupId : candidateGroups) {
					 List<User> usersIngrouplist = processEngine.getIdentityService().createUserQuery().memberOfGroup(groupId).list();
					 List<String> userIdInGroup = new ArrayList<String>();
					 for (User user : usersIngrouplist) {
						String id = user.getId();
						userIdInGroup.add(id);
					}
					 if (userList == null ) {
						 userList = new ArrayList<String>();
					}
					 userList = (List<String>) CollectionUtils.union(userList, userIdInGroup);
				}
				if (userList != null && userList.size() > 0) {
					if (userList.contains(userId)) {
						bpmnTask.setKey(task.getId());
						bpmnTask.setName(task.getName());
						String description = (String) pde.findActivity(task.getId()).getProperty("documentation");
						if(description != null){
							bpmnTask.setDescription(description);
						}
						bpmnTasks.add(bpmnTask);
					}
				}
			} else if (flowElement instanceof SubProcess) {
				SubProcess subProcess = (SubProcess) flowElement;
				Collection<FlowElement> flowElements2 = subProcess
						.getFlowElements();
				for (FlowElement flowElement2 : flowElements2) {
					if (flowElement2 instanceof UserTask) {
						UserTask task = (UserTask) flowElement2;
						String currentTaskDefinitionKey = task.getId();
						BpmnTask bpmnTask = new BpmnTask();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("processDefinitionId", processDefinitionId);
						map.put("taskKey", currentTaskDefinitionKey);
						BpmnTaskExtend bpmnTaskExtend = bpmnTaskExtendDao
								.selectTaskExtendByProDefIdAndTaskKey(map);
						List<String> userList = candidateUsersService.getNodeProcessor(
								bpmnTaskExtend.getProcessor(),
								bpmnTaskExtend.getProcessorExpression(),
								"",null, "",currentTaskDefinitionKey,"");//上一个历史任务的处理人
						if (userList.contains(userId)) {
							bpmnTask.setKey(task.getId());
							bpmnTask.setName(task.getName());
							String description = (String) pde.findActivity(task.getId()).getProperty("documentation");
							if(description != null){
								bpmnTask.setDescription(description);
							}
							bpmnTasks.add(bpmnTask);
						}
					}
				}
			} 
		}
		result.setResult(bpmnTasks);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public ProcessResult<List<String>> selectUserAndDeptAndRoleNameByGoupIds(String groupIds) {
		// TODO Auto-generated method stub
		ProcessResult<List<String>> pr = new ProcessResult<List<String>>();
		List<Map<String, Object>> groupIdsAndNamesAndType  = new ArrayList<Map<String,Object>>();
		if (null != groupIds && groupIds.length() > 0) {
			List<String> groupIdsList = new ArrayList<String>();
			String[] split = groupIds.split(",");
			for (String string : split) {
				groupIdsList.add(string);
			}
			groupIdsAndNamesAndType = bpmnActIdentityLinkEntityDao.selectUserAndDeptAndRoleNameByGroupId(groupIdsList);
		}
		List<String> tempList = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<String>  arryList = (List<String>)CollectionUtils.union(tempList, groupIdsAndNamesAndType);
		
		pr.setResult(arryList);
		pr.setSuccess(true);
		
		return pr;
	}


	@Override
	public ProcessResult<List<BpmnTemplateDef>> queryTemplateDefByIdsList(
			List<String> temDefIdList) {
		
		ProcessResult<List<BpmnTemplateDef>> pr = new ProcessResult<List<BpmnTemplateDef>>();
		List<BpmnTemplateDef> bpmnTemplateDefs = new ArrayList<BpmnTemplateDef>();
		for (int i = 0; i < temDefIdList.size(); i++) {
			String id = temDefIdList.get(i);
			BpmnTemplateDef bpmnTemplateDef = bpmnTemplateDefDao
					.selectTemplateDefById(id);
			if (bpmnTemplateDef == null) {
				pr.setCode(PRM.PROCESS_HAVE_NO_DEF_INFO_CODE);
				pr.setMessage(PRM.PROCESS_HAVE_NO_DEF_INFO);
				pr.setSuccess(false);
			} else {
				byte[] b = bpmnTemplateDef.getContentBytes();
				try {
					String contentBytesStr = new String(b, "GBK");
					bpmnTemplateDef.setContentBytesStr(contentBytesStr);
					bpmnTemplateDefs.add(bpmnTemplateDef);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					pr.setMessage(PRM.PROCESS_CODE_TRANS_ERROR);
					pr.setSuccess(false);
				}
			}
		}		
		pr.setResult(bpmnTemplateDefs);
		pr.setSuccess(true);
		return pr;
	}

	@Override
	public ProcessResult<String> changeVersionMode(Map<String, Object> map) {
		ProcessResult<String> pr = new ProcessResult<String>();
		
		try {
			int num = bpmnTemplateDefDao.updateVersionMode(map);
			if(num > 0){
				pr.setSuccess(true);
			}else{
				pr.setSuccess(false);
				pr.setMessage("该流程类型没有相对应的流程定义模板信息。请先新建流程定义模板！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pr.setSuccess(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}
