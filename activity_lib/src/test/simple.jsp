<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%
        String realFirstPath = request.getServletPath().substring(1, request.getServletPath().length());
// realFirstPath=realFirstPath.substring(0, realFirstPath.indexOf("/"));
        request.setAttribute("myBasePath", request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/");

        request.setAttribute("realFirstPath", realFirstPath);
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <script src="${myBasePath}/fasp/hqbpmn/jslib/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var myBasePath = "${myBasePath}"
        $(function () {
            $("input[type='button']").click(function () {
                var action = this.id;
                var datas = $("#simple").find("input[type!='button']");
                var url = myBasePath + "/hqbpmn/processinstance/simpleTest.do?tokenid=${tokenid }";
                $.ajax({
                    url: url,
                    data: {
                        "userId": datas[0].value,
                        "bpmnType": datas[1].value,
                        "ticketIds": datas[2].value,
                        "bpmnBatchTransition": datas[3].value,
                        "action": action
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        $("#returnLog").append(JSON.stringify(data))
                    }
                });
            });
            $("#checkLog").click(function () {

                var datas = $("#simple").find("input[type!='button']");
                var bpmnType = datas[1].value
                var ticketIds = datas[2].value

                <%--$("#loadHtml").load('${myBasePath}fasp/hqbpmn/bpmnlog/scanBpmnLog.jsp?ticketId='+ticketIds+'&bpmnType='+bpmnType+'&isShowNodeMessage=1&isShowBackgroundGrid=1',--%>
                    <%--function () {--%>

<%--//                    alert('Content Successfully Loaded.')--%>

                    <%--}--%>
                <%--)--%>
                $("#loadHtml").attr("src",'${myBasePath}fasp/hqbpmn/bpmnlog/scanBpmnLog.jsp?ticketId='+ticketIds+'&bpmnType='+bpmnType+'&isShowNodeMessage=1&isShowBackgroundGrid=1');
            })
        });


    </script>

</head>
<>
简版测试界面
<br>


<input value="${myBasePath}"> &nbsp&nbsp&nbsp&nbsp&nbsptokenid=${tokenid }${myBasePath}
<form id="simple">
    <label>用户:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label> <input name="userId"></br>
    <label>流程类型:&nbsp&nbsp&nbsp</label> <input name="bpmnType"></br>
    <label>工单号:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label> <input name="ticketIds"></br>
    <label>批处理类型</label> <input name="bpmnBatchTransition"></br></br>
    <input type="button" id="create" value="创建流程"/>
    <input type="button" id="complete" value="流程流转"/>
    <input type="button" id="revoke" value="流程撤销"/>
    <input type="button" id="return" value="流程退回"/>
</form>
<div>返回值信息
    <div id="returnLog">


    </div>
</div>
<a id="checkLog" href="#">查看日志</a></br>

<%--<div  style="height: 300px"></div>--%>
<iframe id="loadHtml" style="height: 500px;width: 1000px"></iframe>
</body>
</html>