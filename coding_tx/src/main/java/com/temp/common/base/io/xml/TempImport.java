package com.temp.common.base.io.xml;

import com.temp.common.base.fileupload.base.EncodingDetect;
import com.temp.common.base.sqlscript.controller.JsonUtil;
import com.temp.common.base.sqlscript.controller.ProcessResult;
import com.temp.common.base.util.PrimaryKeyGeneratorByUuid;
import net.sf.json.JSONArray;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class TempImport {
    static class GetFile {
        public static void main(String[] args) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
                Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "lw_bpmn", "1");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from act_hq_tem_def where category='type1_2019'");
                while (resultSet.next()) {
                    String contentByte = resultSet.getString("category");
                    System.out.println(contentByte);
                    byte[] context=resultSet.getBytes("CONTENT_BYTES");
                    String javaEncode = EncodingDetect.getJavaEncode(context);
                    System.out.println(new String(context,javaEncode));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
    public static Map<String,String> findContext() {
        try {
            Map<String,String> params= new HashMap<String,String>();
            Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "lw_bpmn", "1");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from act_hq_tem_def where category='type1_2018'");
            resultSet.next();
                String category = resultSet.getString("category");
                String canvaswidth = resultSet.getString("CANVASWIDTH");
                String canvasheight = resultSet.getString("CANVASHEIGHT");
                String name = resultSet.getString("NAME");
                String init_num = resultSet.getString("INIT_NUM");
                System.out.println(category);
                byte[] contextByte=resultSet.getBytes("CONTENT_BYTES");
                String javaEncode = EncodingDetect.getJavaEncode(contextByte);
                System.out.println(new String(contextByte,javaEncode));
            params.put("contextByte",new String(contextByte,javaEncode));
            params.put("canvaswidth",canvaswidth);
            params.put("canvasheight",canvasheight);
            params.put("init_num",init_num);
            params.put("name",name);
            return params;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, SQLException {
//        使用xml方式导入时使用
//        /** 得到DOM解析器的工厂实例 */
//        DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
//        /** 从DOM工厂获得DOM解析器 */
//        DocumentBuilder dombuilder = domfac.newDocumentBuilder();
//        /** 把要解析的XML文档转化为输入流，以便DOM解析器解析它 */
//        String context = findContext();
//        /** 解析XML文档的输入流，得到一个Document */
//        Document doc = dombuilder.parse(context);
//        /** 得到XML文档的根节点 */
//        Element root = doc.getDocumentElement();
//        /** 得到节点的子节点 */
//        NodeList books = root.getChildNodes();
//
//        String contentStr = "";
//        String canvasWidth = "";
//        String canvasHeight = "";
//        for (int i = 0; i < books.getLength(); i++) {
//            Node node = books.item(i);
//            if (node.getNodeName().equals("content")) {
//                JSONObject jsonObjContent = new JSONObject(
//                        node.getTextContent());
//                JSONObject jsonObjDiagram = new JSONObject(jsonObjContent.get(
//                        "diagram").toString());
//                JSONObject jsonObjBpmn = new JSONObject(jsonObjDiagram.get(
//                        "bpmn").toString());
//                String category = ((String) jsonObjBpmn.get("category")).replace("2018", "2019");
//                String procDefKey = ((String) jsonObjBpmn.get("procDefKey")).replace("2018", "2019");
//                jsonObjBpmn.put("categroy", category);
//                jsonObjBpmn.put("procDefKey", procDefKey);
//                jsonObjBpmn.put("deployState", "0");
//                jsonObjBpmn.put("versionState", "0");
//                jsonObjBpmn.put("version", "0");
//                jsonObjDiagram.put("bpmn", jsonObjBpmn);
//                jsonObjContent.put("diagram", jsonObjDiagram);
//                contentStr = jsonObjContent.toString();
//            } else if (node.getNodeName().equals("canvasWidth")) {
//                canvasWidth = node.getTextContent();
//            } else if (node.getNodeName().equals("canvasHeight")) {
//                canvasHeight = node.getTextContent();
//            }
//        }
        Map<String, String> context = findContext();
        String contentStr = context.get("contextByte");
        String canvasWidth = context.get("canvaswidth");
        String canvasHeight = context.get("canvasheight");;
        String init_num = context.get("init_num");;
        String name = context.get("name");;
                JSONObject jsonObjContent = new JSONObject(
                        contentStr);
                JSONObject jsonObjDiagram = new JSONObject(jsonObjContent.get(
                        "diagram").toString());
                JSONObject jsonObjBpmn = new JSONObject(jsonObjDiagram.get(
                        "bpmn").toString());
        System.out.println(jsonObjDiagram.get(
                "bpmn").toString());
        System.out.println(jsonObjBpmn.get("categroy"));
                String category = ((String) jsonObjBpmn.get("categroy")).replace("2018", "2019");
                String procDefKey = ((String) jsonObjBpmn.get("procDefKey")).replace("2018", "2019");
                jsonObjBpmn.put("categroy", category);
                jsonObjBpmn.put("procDefKey", procDefKey);
                jsonObjBpmn.put("deployState", "0");
                jsonObjBpmn.put("versionState", "0");
                jsonObjBpmn.put("version", "0");
                jsonObjDiagram.put("bpmn", jsonObjBpmn);
                jsonObjContent.put("diagram", jsonObjDiagram);
                contentStr = jsonObjContent.toString();

        String id = String.valueOf(PrimaryKeyGeneratorByUuid.getNext());
        Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "lw_bpmn", "1");
        String old= "2018";
        String newYear="2019";
        String newContentStr = contentStr.replaceAll(old, newYear);

        String insertSql ="insert into act_hq_tem_def (ID, NAME, CATEGORY, VERSION, CREATE_BY, MODIFY_BY, CREATE_TIME, MODIFY_TIME, DEPLOY_STATE, VERSION_STATE, DEPLOYMENT_ID, CONTENT_BYTES, INIT_NUM, CANVASWIDTH, CANVASHEIGHT, TABLE_IDS, TABLE_NAMES, APP_ID_, DEPLOYMENT_ID_, DEPLOYMENT_STATE_)" +
                "values (?, ?,?, 0, null, null, sysdate, null, 0, 0, null, ?, ?, ?,?, null, null, null, null, 0)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        preparedStatement.setString(1,id);
        preparedStatement.setString(2,name);
        preparedStatement.setString(3,category);
        preparedStatement.setBytes(4,newContentStr.getBytes("gbk"));
        preparedStatement.setString(5,init_num);
        preparedStatement.setString(6,canvasWidth);
        preparedStatement.setString(7,canvasHeight);
        System.out.println("添加成功---"+category);
        preparedStatement.execute();
    }

    private static void conversionContentYear(String old, String newYear) {

    }

    public void saveTemplate(){
        String id =String.valueOf(PrimaryKeyGeneratorByUuid.getNext());
        String appId = "hqbpmn";
    }
    public ProcessResult<String> saveTemplateDef(String id, String contentBytes,
                                                 String deployState, String canvasWidth, String canvasHeight,String appId) {
        String str = "";
        boolean flag = true;
        BpmnTemplateDef bpmnTemplate = transformTemplateDefByStr(id, contentBytes);
        bpmnTemplate.setDeployState(0);
        bpmnTemplate.setVersionState(0);
        bpmnTemplate.setVersion(0);
        bpmnTemplate.setDeploymentState(0);
        bpmnTemplate.setAppId(appId);
        bpmnTemplate.setCanvasHeight(canvasHeight);
        bpmnTemplate.setCanvasWidth(canvasWidth);
        ProcessResult<String> pr = new ProcessResult<String>();
        pr.setResult(id);
        pr.setMessage(str);
        pr.setSuccess(flag);

        return pr;
    }


    public static void importToshowTemplateDef(String id, String bytes,
                                               String deployState, String canvasWidth, String canvasHeight) {
        String str = "";

        //新的流程定义模版对象
        BpmnTemplateDef bpmnTemplate = transformTemplateDefByStr(id, bytes);
        if (id == null || id.equals("")) {
            id = String.valueOf(PrimaryKeyGeneratorByUuid.getNext());
            bpmnTemplate.setId(id);
            bpmnTemplate.setCreateTime(new Date());
            bpmnTemplate.setCanvasHeight(canvasHeight);
            bpmnTemplate.setCanvasWidth(canvasWidth);
            bpmnTemplate.setContentBytesStr(bytes);

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");//这段必须有，加载类的时候就加载了驱动
                Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.100.120:1521:orcl", "lw_bpmn", "1");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from act_hq_tem_def where category='type1_2018'");
                while (resultSet.next()) {
                    String id_ = resultSet.getString("category");
                    System.out.println(id_);

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static BpmnTemplateDef transformTemplateDefByStr(String id, String jsonStr) {
        BpmnTemplateDef bpmnTemplateDef = new BpmnTemplateDef();
        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(jsonStr);
        StringBuffer strBuffer = new StringBuffer(jsonStr);
        strBuffer.indexOf("version");
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            if (k.equals("title")) {
                bpmnTemplateDef.setName(v.toString());
            } else if (k.equals("diagram")) {
                net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(v.toString());
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

    public BpmnModel transformTemplateDefStrToBpmnModel(String str)
             {
        // BpmnModel
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
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(str);
            for (Object k : json.keySet()) {
                Object v = json.get(k);
                if (k.equals("id")) {
                } else if (k.equals("title")) {
                    process.setName(v.toString());
                } else if (k.equals("diagram")) {
                    net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(v.toString());
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
                    net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(v.toString());
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
                            net.sf.json.JSONObject jsonExtend = net.sf.json.JSONObject.fromObject(vnode
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
                            net.sf.json.JSONObject jsonGeneral = net.sf.json.JSONObject
                                    .fromObject(vnode.get("general"));
                            userTask.setCandidateUsers(strToList(jsonGeneral
                                    .get("candidateUsers").toString(), ","));
                            userTask.setCandidateGroups(strToList(jsonGeneral
                                    .get("candidateGroups").toString(), ","));
                            userTask.setDefaultFlow(jsonGeneral.get(
                                    "defaultFlow").toString());
                            userTask.setDocumentation(jsonGeneral.get(
                                    "document").toString());
                            net.sf.json.JSONObject jsonListener = net.sf.json.JSONObject
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
                            net.sf.json.JSONObject jsonGeneral = net.sf.json.JSONObject
                                    .fromObject(vnode.get("general"));
                            callActivity.setCalledElement(jsonGeneral.get(
                                    "calledElement").toString());
                            net.sf.json.JSONObject parameter = net.sf.json.JSONObject.fromObject(vnode
                                    .get("parameter"));
                            callActivity
                                    .setInParameters(getCallActivityParameter(
                                            parameter, "inputParameter"));
                            callActivity
                                    .setOutParameters(getCallActivityParameter(
                                            parameter, "outputParameter"));
                            net.sf.json.JSONObject jsonListener = net.sf.json.JSONObject
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
                            net.sf.json.JSONObject jsonExtend = net.sf.json.JSONObject.fromObject(vnode
                                    .get("extendConfig"));
                            net.sf.json.JSONObject jsonGeneral = net.sf.json.JSONObject
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
//                                pr.setSuccess(false);
//                                pr.setMessage(PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS);
//                                pr.setCode(PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS_CODE);
//                                throw new BpmnException(
//                                        PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS_CODE,
//                                        PRM.BPMN_COUNTERSIGN_WITH_NONE_PARTICIPANTS);
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
                            net.sf.json.JSONObject jsonListener = net.sf.json.JSONObject
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
                    net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(v.toString());
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
                        net.sf.json.JSONObject jsonListener = net.sf.json.JSONObject.fromObject(vnode
                                .get("listener"));
                        if (jsonListener != null && !jsonListener.isEmpty()) {
                            List<ActivitiListener> executionListeners = getActivitiListeners(
                                    jsonListener, "executionListener");
                            flow.setExecutionListeners(executionListeners);
                        }
                        net.sf.json.JSONObject jsonGeneral = net.sf.json.JSONObject.fromObject(vnode
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

    private List<ActivitiListener> getActivitiListeners(
            net.sf.json.JSONObject jsonListener, String listenerType) {
        List<ActivitiListener> listeners = new ArrayList<ActivitiListener>();
        String executionListener = String.valueOf(jsonListener
                .get(listenerType));
        if (executionListener != null && !"".equals(executionListener)
                && !"null".equals(executionListener)) {
            JSONArray execution_Listeners = JSONArray.fromObject(jsonListener
                    .get(listenerType));
            ActivitiListener listener;
            for (int i = 0; i < execution_Listeners.size(); i++) {
                net.sf.json.JSONObject executionlist = (net.sf.json.JSONObject) execution_Listeners
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
                    net.sf.json.JSONObject field = (net.sf.json.JSONObject) fields.get(j);
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

    private List<IOParameter> getCallActivityParameter(
            net.sf.json.JSONObject jsonParameter, String type) {
        List<IOParameter> parameters = new ArrayList<IOParameter>();
        String parameter = String.valueOf(jsonParameter.get(type));
        IOParameter iOParameter;
        if (parameter != null && !"".equals(parameter)
                && !"null".equals(parameter)) {
            JSONArray parameterArray = JSONArray.fromObject(parameter);
            for (int i = 0; i < parameterArray.size(); i++) {
                net.sf.json.JSONObject para = (net.sf.json.JSONObject) parameterArray.get(i);
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

    private int getCollectionSize(String userIds, String groupIds) {
        Set<String> collectionSet = new HashSet<String>();
        String[] ids;
        if (userIds != null && userIds.length() > 0) {
            ids = userIds.split(",");
            for (String id : ids) {
                collectionSet.add(id);
            }
        }



        //创建一个引擎  配置  对象。
        ProcessEngineConfiguration configuration =
                ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //的到数据库四要素


        configuration.setJdbcUrl("jdbc:oracle:thin:@192.168.100.120:1521:orcl");
        configuration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
        configuration.setJdbcUsername("lw_bpmn");
        configuration.setJdbcPassword("1");
        //设置建表策略
        /*
         * 这个方法提供了三个参数（后两种都不常用，程序员只需要使用第一种  false 就可以了）
         * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE:数据库中没有表就报错
         * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE ：数据库中没有表就创建，有表就拉到
         * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE ：每次都删除表创建新表
         * */
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);

        //创建工作流引擎对象。
        /*
         * 这个对象被创建的同时，系统就会创建所有必要的表。
         * */
        ProcessEngine processEngine = configuration.buildProcessEngine();
        if (groupIds != null && groupIds.length() > 0) {
            UserQuery userQuery =processEngine.getIdentityService()
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
}
