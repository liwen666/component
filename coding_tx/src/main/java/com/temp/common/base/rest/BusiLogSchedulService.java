//package com.temp.common.base.rest;
//
//import com.hq.bpmn.bpmnanalysis.bean.BpmnLogIncrease;
//import com.hq.bpmn.bpmnanalysis.dao.BpmnLogAnalysisDao;
//import com.hq.bpmn.bpmnanalysis.service.BpmnLogAnalysisService;
//import com.hq.bpmn.common.util.DataConversion;
//import com.hq.bpmn.common.util.WfBeanFactory;
//import com.hq.bpmn.schedule.dao.ActBusiLogDao;
//import com.hq.bpmn.sclient.IBpmnCommonServiceWs;
//import com.hq.bpmn.sclient.PiAndTaskListDomain;
//import com.hq.bpmn.sclient.PipddeplyAnalyDomain;
//import com.hq.bpmn.sclient.TaskAnalysisDomain;
//import com.hq.bpmn.templatedef.bean.BpmnTempCategory;
//import com.hq.bpmn.templatedef.bean.BpmnTemplateDef;
//import com.hq.bpmn.templatedef.dao.BpmnTempCategoryDao;
//import com.hq.bpmn.templatedef.dao.BpmnTemplateDefDao;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.*;
//
//@Service
//public class BusiLogSchedulService {
//    private static final Logger logger = LoggerFactory.getLogger(BusiLogSchedulService.class);
//
//    @Autowired
//    @Qualifier("actBusiLogDao")
//    private ActBusiLogDao actBusiLogDao;
//    @Autowired
//    @Qualifier("bpmnLogAnalysisDao")
//    private BpmnLogAnalysisDao bpmnLogAnalysisDao;
//    @Autowired
//    @Qualifier("bpmnLogAnalysisServiceImpl")
//    private BpmnLogAnalysisService bpmnLogAnalysisServiceImpl;
//    @Autowired
//    @Qualifier("bpmnTemplateDefDao")
//    private BpmnTemplateDefDao bpmnTemplateDefDao;
//    @Autowired
//    @Qualifier("bpmnTempCategoryDao")
//    private BpmnTempCategoryDao bpmnTempCategoryDao;
//    //@Autowired
//    //@Qualifier("sClientBpmn")
//    private IBpmnCommonServiceWs bpmnCommonServiceWs;
//
//    private RestTemplate restTemplate = new RestTemplate();
//
//    public void addLocalLog() throws IOException {
//    }
//
//    @Transactional
//    public void sendBusiLogId() {
//        sychTempAndCategory();
//        if (bpmnCommonServiceWs == null)
//            bpmnCommonServiceWs = (IBpmnCommonServiceWs) WfBeanFactory.getBean("sClientBpmn");
//
//        PiAndTaskListDomain piAndTaskList = new PiAndTaskListDomain();
//        /**
//         * 查询临时表数据
//         */
//        List<BpmnLogIncrease> listBpmnLogIncrease = bpmnLogAnalysisDao.listBpmnLogIncrease();
//
//        List<PipddeplyAnalyDomain> insertPiData = piAndTaskList.getInsertPiData();
//        List<PipddeplyAnalyDomain> updatePiData = piAndTaskList.getUpdatePiData();
//        List<TaskAnalysisDomain> insertTaskData = piAndTaskList.getInsertTaskData();
//        List<TaskAnalysisDomain> updateTaskData = piAndTaskList.getUpdateTaskData();
//        List<String> idListWaitDelete = new ArrayList<String>();
//        List<String> idListInsertPiData = new ArrayList<String>();
//        List<String> idListUpdatePiData = new ArrayList<String>();
//        List<String> idListInsertTaskData = new ArrayList<String>();
//        List<String> idListUpdateTaskData = new ArrayList<String>();
//        for (BpmnLogIncrease b : listBpmnLogIncrease) {
//            idListWaitDelete.add(b.getId());
//            if (b.getDataType() == 1) {  //模板实例数据
//                if (b.getActionType() == 1) {
//                    idListInsertPiData.add(b.getDataId());
//                }
//                if (b.getActionType() == 3) {
//                    idListUpdatePiData.add(b.getDataId());
//                }
//            } else {
//                //任务数据
//                if (b.getActionType() == 1) {
//                    idListInsertTaskData.add(b.getDataId());
//                }
//                if (b.getActionType() == 3) {
//                    idListUpdateTaskData.add(b.getDataId());
//
//                }
//            }
//        }
//        List<com.hq.bpmn.bpmnanalysis.bean.PipddeplyAnalyDomain> insertPiDomains = bpmnLogAnalysisServiceImpl.listPiPdDeployAnalyDomainByPiIds(idListInsertPiData);
//        for (com.hq.bpmn.bpmnanalysis.bean.PipddeplyAnalyDomain p : insertPiDomains) {
//            insertPiData.add((PipddeplyAnalyDomain) DataConversion.ObjectToXmlObject(p, new PipddeplyAnalyDomain()));
//        }
//        List<com.hq.bpmn.bpmnanalysis.bean.PipddeplyAnalyDomain> updatePiDomains = bpmnLogAnalysisServiceImpl.listPiPdDeployAnalyDomainByPiIds(idListUpdatePiData);
//        for (com.hq.bpmn.bpmnanalysis.bean.PipddeplyAnalyDomain p : updatePiDomains) {
//            updatePiData.add((PipddeplyAnalyDomain) DataConversion.ObjectToXmlObject(p, new PipddeplyAnalyDomain()));
//        }
//        List<com.hq.bpmn.bpmnanalysis.bean.TaskAnalysisDomain> insertTaskDomains = bpmnLogAnalysisServiceImpl.listTaskAnalysisDomainByTaskIds(idListInsertTaskData);
//        for (com.hq.bpmn.bpmnanalysis.bean.TaskAnalysisDomain p : insertTaskDomains) {
//            insertTaskData.add((TaskAnalysisDomain) DataConversion.ObjectToXmlObject(p, new TaskAnalysisDomain()));
//        }
//        List<com.hq.bpmn.bpmnanalysis.bean.TaskAnalysisDomain> updateTaskDomains = bpmnLogAnalysisServiceImpl.listTaskAnalysisDomainByTaskIds(idListUpdateTaskData);
//        for (com.hq.bpmn.bpmnanalysis.bean.TaskAnalysisDomain p : updateTaskDomains) {
//            updateTaskData.add((TaskAnalysisDomain) DataConversion.ObjectToXmlObject(p, new TaskAnalysisDomain()));
//        }
//        final boolean deleteFlag = bpmnCommonServiceWs.timingSynLog(piAndTaskList);
//        if (!deleteFlag);
//        if (idListWaitDelete.size() > 0 && deleteFlag == true) {
//            bpmnLogAnalysisDao.deleteBpmnLogAnalysisByIds(idListWaitDelete);
//        }
//
//
//        if (logger.isDebugEnabled()) {
//            logger.debug("定时起动==================== ");
//        }
//
//
//    }
//    void sychTempAndCategory() {
//        Map<String, Object> tempData = new HashMap<String, Object>();
//        Map<String, Object> categoryData = new HashMap<String, Object>();
//        //查询模板和模板类型
//        final List<BpmnTemplateDef> bpmnTemplateDefs = bpmnTemplateDefDao.selectTemplateDefOnly();
//        final List<BpmnTempCategory> bpmnTempCategories = bpmnTempCategoryDao.selectTempCategory();
//        String appId = "hqbpmn";
//
////        tempData.put("bpmnTemplateDefList", bpmnTemplateDefs1);
//        tempData.put("bpmnTemplateDefList", bpmnTemplateDefs);
//        categoryData.put("bpmnTypeList", bpmnTempCategories);
//
//
//        tempData.put("appId", appId);
//        categoryData.put("appId", appId);
//
//
//        InputStream inStream = BusiLogSchedulService.class.getClassLoader().getResourceAsStream("application.properties");
//        Properties prop = new Properties();
//        try {
//            prop.load(inStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String url = prop.getProperty("serverAddress");
//        //调服务端删除模板和模板类型
//        restTemplate.delete(url + "/hqbpmn/bpmnAction/template/category/" + appId);
//        restTemplate.delete(url + "/hqbpmn/bpmnAction/template/" + appId);
//        //服务端插入模板和类型
//        ResponseEntity<Integer> integerResponseEntity = restTemplate.postForEntity(url + "/hqbpmn/bpmnAction/template", tempData, Integer.class);
//        HttpStatus statusCode = integerResponseEntity.getStatusCode();
//        System.out.println("HttpStatus = " + statusCode);
//        ResponseEntity<Integer> categoryResponseEntity = restTemplate.postForEntity(url + "/hqbpmn/bpmnAction/template/category", categoryData, Integer.class);
//        Integer category = categoryResponseEntity.getBody();
//
//    }
//
//}
