<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String realFirstPath = request.getServletPath().substring(1,request.getServletPath().length());
// realFirstPath=realFirstPath.substring(0, realFirstPath.indexOf("/"));
request.setAttribute("myBasePath", request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath() + "/");

request.setAttribute("realFirstPath",realFirstPath);
 %> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="${myBasePath}/fasp/hqbpmn/jslib/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
var myBasePath = "${myBasePath}"
$(function(){
	$("#initH2DataSource").click(function(){
		var url= myBasePath+"/hqbpmn/initTemDef/initH2DataSource.do?tokenid=${tokenid }";
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.message);
			}
		});
	});
	$("#updateH2DataSource").click(function(){
		var url= myBasePath+"/hqbpmn/initTemDef/updateH2Data.do?tokenid=${tokenid }";
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.message);
			}
		});
	});
	$("#initAutoDeployTempdef").click(function(){
		var url= myBasePath+"/hqbpmn/initTemDef/initAutoDeployTempdef.do?tokenid=${tokenid }";
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.message);
			}
		});
	});
	$("#initTemDefByAppId").click(function(){
		var url= myBasePath+"/hqbpmn/initTemDef/initTemDefByAppId.do?tokenid=${tokenid }&appId=1";
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.message);
			}
		});
	});
	$("#chartAnalyzeBpmn").click(function(){
// 		window.location.href=myBasePath+"/fasp/hqbpmn/chart/chartanalyze.html"
		window.open(myBasePath+"/fasp/hqbpmn/chart/chartanalyze.jsp");
	});
});



</script>

</head>
<body>
    主页测试界面
    <br>
    
    
    <input value="${myBasePath}"> &nbsp&nbsp&nbsp&nbsp&nbsptokenid=${tokenid }${myBasePath}
<a href="${myBasePath}fasp/hqbpmn/template/tempList.jsp?tokenid=${tokenid }" target="_blank" >流程定义列表</a>${myBasePath}fasp/hqbpmn/template/tempList.jsp    7002端口为拷贝项目
<br>
<br>
<a href="${myBasePath}fasp/hqbpmn/template/tempListCoustomButton.jsp?tokenid=${tokenid } " target="_blank">业务按钮</a>${myBasePath}fasp/hqbpmn/template/tempListCoustomButton.jsp
<br>
<br>
<a href="${myBasePath}fasp/hqbpmn/template/tempListCode.jsp?tokenid=${tokenid }" target="_blank">代码维护</a>${myBasePath}fasp/hqbpmn/template/tempListCode.jsp
<br>
<br>
<a href="${myBasePath}fasp/hqbpmn/template/personnelAllocationList.jsp?tokenid=${tokenid } " target="_blank">人员关系</a>

${myBasePath}fasp/hqbpmn/template/personnelAllocationList.jsp
<br>
<br>
<a href="${myBasePath}fasp/hqbpmn/simulation/simulationInfor.jsp?tokenid=${tokenid }&bpmnType=bptest1&ticketId=100&userName=gonzo
 " target="_blank">仿真</a>
    <a href="${myBasePath}simple.jsp
 " target="_blank">简版测试</a>


    ${myBasePath}fasp/hqbpmn/simulation/simulationInfor.jsp?bpmnType=bptest1&ticketId=100&userName=gonzo
<br>
<br>
<a href="${myBasePath}fasp/hqbpmn/bpmnlog/scanBpmnLog.jsp?ticketId=31&bpmnType=ddd&isShowNodeMessage=1&isShowBackgroundGrid=1
 " target="_blank">流程日志</a>

${myBasePath}fasp/hqbpmn/bpmnlog/scanBpmnLog.jsp?ticketId=31&bpmnType=ddd&isShowNodeMessage=1&isShowBackgroundGrid=1
  
    <br>
    <input type="button" value="初始化H2资源"  id="initH2DataSource">
    <br>
    <input type="button" value="更新H2资源"  id="updateH2DataSource">
    
    <br>
    <input type="button" value="初始化并自动部署"  id="initAutoDeployTempdef">
    
    <br>
    <input type="button" value="分析流程数据"  id="chartAnalyzeBpmn">
    <br>
    <input type="button" value="资源同步"  id="initTemDefByAppId">
    
    
</body>
</html>