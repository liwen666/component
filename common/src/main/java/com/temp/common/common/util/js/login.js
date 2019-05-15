//加载CA随机数及是否显示验证码标志 0 隐藏验证码   1 显示验证码  页面初始化时加载
var verifyCodeFlag;

var showCaFlag;//0-密码; 1-UKey ;2-都行
var loginLayer;//是否启用登录页弹窗  0-不启用 ; 1-启用
var yearList;
var valiDateCode; //验证码
var jsorjava;
$(function() {
	$.ajax({
		url : _BASE_PATH + "/fpfportal/login/generate/random",
		type : 'post',
		dataType : 'json',
		success : function(succ) {
			if(succ.success == 'success'){
				//验证码
				//valiDateCode = succ.valiDateCode;
				
				DSign_Content = succ.random;
				verifyCodeFlag = succ.verifyCodeFlag;
				showCaFlag=succ.showCaFlag;
				loginLayer = succ.loginLayer;
				jsorjava = succ.jsorjava;
				//yearList = succ.yearList;
				if(showCaFlag=="0"){
					$("#cali").hide();
					$("#loginli").css({"width":"360px","text-align":"left","text-indent":"35px"});
					//$("#cahide").hide();
				}else{
					$("#cali").show();
					//$("#cahide").show();
				}
				//隐藏验证码
				if(verifyCodeFlag == '0'){
					$("#verifyCode").hide();
					$("#js_code").hide();
				}else{
					//加载验证码
					var html = [];
					if(jsorjava=='0'){//js方式
						html.push("<input type='text' class='number-Input fl' maxlength=4  id='TxtIdCode'style='margin-top: 15px;margin-bottom: 0px;'/>");
						html.push("<span id='idcode' class='number fl' style='margin-top: 17px;' ></span>");
						html.push("<div class='number-button' style=' padding-top: 25px;'><a href='javaScript:reSetCode();'>看不清？</a></div>");
						$("#verifyCode").append(html.join(''));
						//初始化验证码插件
						$.idcode.setCode();
					}else{
						html.push("<input type='text' id='TxtIdCode'  class='number-Input fl' maxlength=4 style='margin-top: 15px;margin-bottom: 0px;'  name='kaptcha' value='' /> <img src='/kaptcha.jpg' id='kaptchaImage' /> ");  
						html.push("<div class='number-button'><a href='javaScript:reSetCode();'>看不清？</a></div>");
						$("#verifyCode").append(html.join(''));
							// $('#kaptchaImage').click(function () { $(this).attr('src', '/kaptcha.jpg?' + Math.floor(Math.random()*100) ); })
						$('#kaptchaImage').click(function () { $(this).attr('src', '/fpfportal/captchaRandomCode/captchaimage?' + Math.floor(Math.random()*100) ); })
					}
				}
				if(loginLayer == '1'){
					layerInfo();
				}
				/*var yearHtml=[];
				if(yearList != null && yearList.length > 0){
					yearHtml.push("<div class='years'>年度切换：<select id='checkyear'>");
					for (var i = 0; i < yearList.length; i++) {
						if(yearList[i].ISDEFAULT == '1'){
							flag = true;
							yearHtml.push("<option selected = 'selected' value='"+yearList[i].YEAR+"'>"+yearList[i].YEAR+"</option>");
						}else{
							yearHtml.push("<option value='"+yearList[i].YEAR+"'>"+yearList[i].YEAR+"</option>");
						}
					}
					yearHtml.push("</select></div>");
					$("#tpbr").after(yearHtml.join(""));
				}*/
			}else{
				alert("加载系统参数异常！");
			}
		},
		error : function(err) {

		}
	});
	//兼容低版本IE placeholder
	initInput();
	// 加载CA控件
	createCaObjectToHead();	
	var username=$.cookie('username');
	if(username!=null&&username!=""&&username!=undefined&&username!='undefined'){
        // $("#username").val(Base64.decode(username));
        $("#username").val(username);
	}
	//控制输入框字体颜色
	ctrlInputColor();
	
	//加载登录页公告
	initLoginPageNotic();
	
	//给页面添加默认样式 @author wangqun 2017-7-20 09:37:27
//	var url = _BASE_PATH+"/fpfportal/sysTemplate/queryAllTypeConfig.do?t="+Math.random();
	/* $.getJSON(url,function(data){
		 var type = data.type;
         var useip = parseInt(data.useIp);
         var setsPool = data.setsPool;
         var set;
         for(var i=0;i<setsPool.length;i++){
         	if( setsPool[i].code == type){
         		set = setsPool[i];
         	}
          }
         var chose=useip?set.ipPool[mIp]?set.ipPool[mIp]:0:0;
		 $("#login_bckimg").css("background-image","url("+_BASE_PATH+set.set[chose].loginPicUrl+")");
		 $("#login_color").css("background-color",set.set[chose].loginBgcolor);
		 if($(window).width()<1280){//宽度度小于1280内容区居中显示
				var backgroundImageurl=$("#login_bckimg").css("backgroundImage");
				if(backgroundImageurl.indexOf("bg02")>-1){
					$("#login_bckimg").css({"backgroundPosition":"-122px center"});
				}
				$(".tab").css({"margin-right":"80px"});
			}
	 });*/
	var url = _BASE_PATH+"/fpfportal/sysTemplate/queryAllTypeConfig.do";
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{},
		/*beforeSend: function(e,xhr,o){
			var l = xhr.url.indexOf("tokenid");
			var urlToken = xhr.url.substring(l+8,l+48);
			if(urlToken!=tokenid){
				e.abort();
			}
		    },*/
		success:function(data){
			 var type = data.type;
	         var useip = parseInt(data.useIp);
	         var setsPool = data.setsPool;
	         var set;
	         for(var i=0;i<setsPool.length;i++){
	         	if( setsPool[i].code == type){
	         		set = setsPool[i];
	         	}
	          }
	         var chose=useip?set.ipPool[mIp]?set.ipPool[mIp]:0:0;
			 $("#login_bckimg").css("background-image","url("+_BASE_PATH+set.set[chose].loginPicUrl+")");
			 $("#login_color").css("background-color",set.set[chose].loginBgcolor);
			 $("#addLoginTitle").html(set.set[chose].loginTitle);
			 if($(window).width()<1280){//宽度度小于1280内容区居中显示
					var backgroundImageurl=$("#login_bckimg").css("backgroundImage");
					if(backgroundImageurl.indexOf("bg02")>-1){
						$("#login_bckimg").css({"backgroundPosition":"-122px center"});
					}
					$(".tab").css({"margin-right":"80px"});
				}
		},
		error:function(err){
			
		}
	});
	
	
	if($(window).height()>760){//高度超过760 版心垂直居中
		$(".box01").css({"top":"50%","margin-top":"-380px"});
	}
	
	/*$.ajax({
		url:_BASE_PATH+"/fpfportal/common/getSysTemplateInf",
		async: false,//同步
		type:'post',
		dataType:'json',
		success:function(succ){
			if(succ.success=='succ'){
				var template_inf=succ.sysinf;
				if(template_inf!=null){
					var base_img_path=_BASE_PATH+"/fpfportal/static/systemplateimg/"+template_inf.PROVINCE+"/";
						
					//登录页log
					if(template_inf.LANDINGLOG!=null&&template_inf.LANDINGLOG!=""){
						$("#login_log").attr("src",base_img_path+template_inf.LANDINGLOG);
					}
					//登录页标题
					if(template_inf.LANDINGTITLE!=null&&template_inf.LANDINGTITLE!=""){
						$("#login_title").text(template_inf.LANDINGTITLE);
					}
					//登录页背景图
					if(template_inf.LANDINGIMG!=null&&template_inf.LANDINGIMG!=""){
						$("#login_bckimg").css("background-image","url("+base_img_path+template_inf.LANDINGIMG+")");
					}
					//版权信息
					if(template_inf.LANDINGINF!=null&&template_inf.LANDINGINF!=""){
						$("#login_copyright").text(template_inf.LANDINGINF);
					}
					//背景色
					if(template_inf.LANDINGCOLOR!=null&&template_inf.LANDINGCOLOR!=""){
						$("#login_color").css("background-color",template_inf.LANDINGCOLOR);
					}
				}
			}else{
				alert("获取系统模板配置信息出错，恢复默认设置！");
				return;
			}
		},
		error:function(err){
			
		}
	});*/
});

//登录页弹框信息
function layerInfo(){
	var title = '';
	var message = '';
	$.ajax({
		url:_BASE_PATH+"/fpfportal/login/getPopupInfo",
		type:'post',
		dataType:'json',
		async:false,
		success:function(succ){
			if(succ.success == 'success'){
				message = succ.message;
				title = succ.title;
			}else{
				alert("加载登录页弹窗信息异常！");
			}
		},
		error:function(err){
			
		}
	})
	layer.open({
		title:[title,'font-size:17px; text-align: left;line-height:40px;padding-left:15px;font-weight:300;font-family:黑体'],
		type: 1,
		skin: 'layui-layer-lan', //加上边框
		area: ['420px', '270px'], //宽高
		content: message
	});
}

window.onload=function(){
	
	//获取模板配置信息
	/*$.ajax({
		url:_BASE_PATH+"/fpfportal/common/getSysTemplateInf",
		async: false,//同步
		type:'post',
		dataType:'json',
		success:function(succ){
			if(succ.success=='succ'){
				var template_inf=succ.sysinf;
				if(template_inf!=null){
					var base_img_path=_BASE_PATH+"/fpfportal/static/systemplateimg/"+template_inf.PROVINCE+"/";
					alert("基本路径："+base_img_path);
					alert("登录页log"+template_inf.LANDINGLOG+"---登录页标题："+template_inf.LANDINGTITLE
							+"---登录页背景图："+template_inf.LANDINGIMG+"---登录页版权信息："+template_inf.LANDINGINF+"---登录页背景色"+template_inf.LANDINGCOLOR);	
				if(template_inf.LANDINGLOG!=null&&template_inf.LANDINGLOG!=""){
					alert(base_img_path+template_inf.LANDINGLOG);
					$("#login_log").attr("src",base_img_path+template_inf.LANDINGLOG);
				}else{
					$("#login_log").attr("src",_BASE_PATH+"/fpfportal/static/systemplateimg/default/logo.png");
				}
				
				
				}
			}else{
				alert("获取系统配置信息出错");
				return;
			}
		},
		error:function(err){
			
		}
	});*/
}
function initInput(){

	// 如果不支持placeholder，用jQuery来完成
	if (!isSupportPlaceholder()) {
		// 遍历所有input对象, 除了密码框
		$('input').not("input[type='password']").each(function() {
			var self = $(this);
			var val = self.attr("placeholder");
			input(self, val);
		});

		/**//*
			 * 对password框的特殊处理 1.创建一个text框 2.获取焦点和失去焦点的时候切换
			 */
		$('input[type="password"]').each(
				function() {
					var pwdField = $(this);
					var pwdVal = pwdField.attr('placeholder');
					var pwdId = pwdField.attr('id');
					// 重命名该input的id为原id后跟1
					pwdField.after('<input  id="' + pwdId
							+ '1"style="color:#BBBBBB" type="text" value=' + pwdVal
							+ '  class="Password-Input inp" />');
					var pwdPlaceholder = $('#' + pwdId + '1');
					pwdPlaceholder.show();
					pwdField.hide();

					pwdPlaceholder.focus(function() {
						pwdPlaceholder.hide();
						pwdField.show();
						pwdField.focus();
					});

					pwdField.blur(function() {
						if (pwdField.val() == '') {
							pwdPlaceholder.show();
							pwdField.hide();
						}
					});
				});
	}

}
/*$(function() {
	// 如果不支持placeholder，用jQuery来完成
	if (!isSupportPlaceholder()) {
		// 遍历所有input对象, 除了密码框
		$('input').not("input[type='password']").each(function() {
			var self = $(this);
			var val = self.attr("placeholder");
			input(self, val);
		});

		
			 * 对password框的特殊处理 1.创建一个text框 2.获取焦点和失去焦点的时候切换
			 
		$('input[type="password"]').each(
				function() {
					var pwdField = $(this);
					var pwdVal = pwdField.attr('placeholder');
					var pwdId = pwdField.attr('id');
					// 重命名该input的id为原id后跟1
					pwdField.after('<input  id="' + pwdId
							+ '1" type="text" value=' + pwdVal
							+ '  class="Password-Input inp" />');
					var pwdPlaceholder = $('#' + pwdId + '1');
					pwdPlaceholder.show();
					pwdField.hide();

					pwdPlaceholder.focus(function() {
						pwdPlaceholder.hide();
						pwdField.show();
						pwdField.focus();
					});

					pwdField.blur(function() {
						if (pwdField.val() == '') {
							pwdPlaceholder.show();
							pwdField.hide();
						}
					});
				});
	}
});
*/
// 判断浏览器是否支持placeholder属性
function isSupportPlaceholder() {
	var input = document.createElement('input');
	return 'placeholder' in input;
}

// jQuery替换placeholder的处理
function input(obj, val) {
	var $input = obj;
	var val = val;
	$input.attr({
		value : val
	});
	$input.focus(function() {
		if ($input.val() == val) {
			$(this).attr({
				value : ""
			});
		}
	}).blur(function() {
		if ($input.val() == "") {
			$(this).attr({
				value : val
			});
		}
	});
}

	
window.document.onkeydown=function(){
	if(event.keyCode=='13'){
		toLogin();
	}
}

/**
 * 登录页面修改初始密码
 * @param username  用户名
 */
function changePassword(token){
		var html=[];
		html.push("<div id='common_upd_div' class='common_upd_div'>");
			html.push(" <div class='update_login_user' id='update_login_user'>");
			html.push("		<table border='0' cellspacing='0' cellpadding='0' class='updateTblae'  align='center' > ");
			html.push("			<tr style='height:15px;'></tr>");
			html.push("			<tr id='updateuseroldpwdtr' style='display:block' >");
			html.push("				<td>");
			html.push("					<font class='titleframe'><span id='oldpwdspan' style='color: red;'>*</span>原   密  码 :&nbsp;&nbsp;</font>");
			html.push("				</td>");
			html.push("				<td>");
			html.push("					<input input type='text' autocomplete='off' onfocus='this.type=\"password\"' id='oldPlatUserpwd' class='input_box_style' name='oldpwd'></input> ");
			html.push("				</td>");
			html.push("			</tr>");
			html.push("			<tr style='height:15px;'></tr>");
			html.push("			<tr id='updateusernewpwdtr' style='display:block'>");
			html.push("				<td>");
			html.push("					<font class='titleframe'><span id='newpwdspan'  style='color: red;'>*</span>新   密  码 :&nbsp;&nbsp;</font>");
			html.push("				</td>");
			html.push("				<td>");
			html.push("					<input input type='text' autocomplete='off' onfocus='this.type=\"password\"' id='newPlatUserpwd' class='input_box_style' name='pwd'></input> ");
			html.push("				</td>");
			html.push("			</tr>");
			html.push("			<tr style='height:15px;'></tr>");
			html.push("			<tr id='updateusersurepwdtr' style='display:block'>");
			html.push("				<td>");
			html.push("					<font class='titleframe'><span id='newpwdspan'  style='color: red;'>*</span>确认密码 :&nbsp;&nbsp;</font>");
			html.push("				</td>");
			html.push("				<td>");
			html.push("					<input input type='text' autocomplete='off' onfocus='this.type=\"password\"' id='surePlatUserpwd' class='input_box_style' name='pwd'></input> ");
			html.push("				</td>");
			html.push("			</tr>");
			html.push("		</table>");
			html.push(" </div>");
			html.push("</div>");
			html.push(" <br/>");
			html.push("<div class='common_btn_div' align='center'>");
			html.push("		<a class='btn_style common_btn_div_left' href='javaScript:saveChangePassword(\""+token+"\");'>确定</a>");
			html.push("		<a class='btn_style common_btn_div_right' href='javaScript:cancelUpdUserMsg();'>取消</a>");
			html.push("</div>");
			//弹框
			layer.open({
			    type: 1, //page层
			    skin: 'layui-layer-lan', //加上边框
			    area: ['400px', '280px'], //宽高
			    offset: '200px',//位置垂直距离 水平居中
			    title:['修改密码','font-size:17px; text-align: left;line-height:40px;padding-left:15px;font-weight:300;font-family:黑体'],
			    closeBtn: 1, //不显示关闭按钮
			    moveType: 1, //拖拽风格，0是默认，1是传统拖动
			    shift: 0, //0-6的动画形式，-1不开启
			    content: html.join("")
			});
}
/**
 * 登录页面修改初始密码保存按钮
 * @param token
 */
function saveChangePassword(token){
		//旧密码
		var oldpwd=$("#oldPlatUserpwd").val();
		//新密码
		var newpwd=$("#newPlatUserpwd").val();
		//确认密码
		var surepwd=$("#surePlatUserpwd").val();
		//guid
		var guid=$("#com_loginuserguid").val();
		if(oldpwd==""||oldpwd==null){
			layer.alert("请输入原密码！");
			return;
		}
		if(newpwd==""||newpwd==null){
			layer.alert("请输入新密码！");
			return;
		}
		if(surepwd==""||surepwd==null){
			layer.alert("请输入确认密码！");
			return;
		}
		var checkpwd=/^(?![^a-zA-Z]+$)(?!\D+$).{2,10}$/;
		if(newpwd!=surepwd){
			layer.alert("两次输入的密码不一致！");
			return;
		}else if(!checkpwd.test(newpwd)){
			layer.alert("密码格式错误！（必须包含字母和数字，长度为2~10之间）");
			return;
		}
		//确认保存
		$.ajax({
			url:_BASE_PATH+"/fpfportal/login/savaChangePwd",
			type:'post',
			dataType:'json',
			data:{token:token,oldpwd:oldpwd,newpwd:newpwd},
			success:function(succ){
				var result =  succ.success;
				if(result=="success"){
					alert("修改成功");
					cancelUpdUserMsg();
				}else{
					alert("修改失败");
				}
			},
			error:function(err){
				
			}
		});
}





function toLogin(){
    // var username =Base64.encode($("#username").val());
    // var password =Base64.encode($("#password").val());
    var username=$("#username").val();
    var  password=$("#password").val();
	//var checkyear = $("#checkyear").val();
	if(username.replace(/^ +| +$/g,'').length ==0 || username==null||username == ""||username=="请输入用户名"){
		alert("用户名不能为空！");
		return;
	}
	if(password.replace(/(^s*)|(s*$)/g, "").length ==0 || password==null||password == ""||password=="请输入密码"){
		alert("密码不能为空！");
		return;
	}
	//标志为0时  不显示验证码
	if(verifyCodeFlag == '1'){
		var TxtIdCode= $("#TxtIdCode").val();
		if(TxtIdCode==null||TxtIdCode==""){
			alert("验证码不能为空！");
			return;
		}
		if(jsorjava=='0'){
			//验证验证码
			var IsBy = $.idcode.validateCode();
			if(!IsBy){
				alert("验证码错误");
				$("#TxtIdCode").val("");
				return;
			}
		}
		
	}
	
	$.ajax({
		url:_BASE_PATH+"/fpfportal/login/toLogin",
		type:'post',
		dataType:'json',
		//data:{username:username,password:password,checkyear:checkyear},
//		data:{username:username,password:password,TxtIdCode:TxtIdCode,verifyCodeFlag:verifyCodeFlag},
		data:{username:username,password:password,TxtIdCode:TxtIdCode,verifyCodeFlag:verifyCodeFlag,jsorjava:jsorjava},
		success:function(succ){
			if(succ.success == "error"){
				reSetCode();
				if(succ.verifyCodeTxt == "verifyCodeTxt"){
					alert("验证码错误");
				}else{
					alert("登录有误，请重新登录");
				}
			}else{
				var token = succ.token ;
				if(isNaN(token) && token.length == 40){
					//登录页修改初始密码
					/*if(password=='1'){
						alert("请修改初始密码");
						changePassword(token);
					}else{*/
						$.cookie('username', username,{expires:5*365});
						forward(succ);
//					}
				}else{
						reSetCode();
						switch(token){		
						case "11010000":
							/*alert("无此用户!");*/
							alert("用户名或密码错误!");
							break;
						case "11010001":
							alert("用户已失效!");
							break;
						case "11010002":
							alert("用户被暂停使用!");
							break;
						case "11010003":
							alert("用户名或密码错误!");
							break;
						case "11010004":
							alert("系统错误，请重新登录!");
							break;
						case "11010005":
							alert("验证码错误!");
							break;
						case "11010006":
							alert("用户类型不匹配!");
							break;
						case "11010007":
							modifypwd(info);
							break;
						case "11010008":
							alert("不能重复登陆用户!");
							break;
						case "11010009":
							alert("用户已锁定,请稍后再试!");
							break;
						case "11010019":
							modifypwd(info);
							break;
						case "11010020":
							alert("未设置默认财年，不允许登录!");
							break;
						case "00000001":
							alert("CA用户，不能用普通登录!");
							break;
						default:
							alert("用户已锁定，请"+ token +"分钟后再试!");
					}
				}
			}
		},
		error:function(err){
			reSetCode();
			alert("登录有误，请重新登录！");
		}
	});
}
//加载CA控件
function createCaObjectToHead(){
	//--js动态加载activeX控件在IE11与低版本IE中存在差异,导致低版本不能自动下载CA插件,修改为以下方式
	if( typeof(Ocx)=="undefined" ){
	      tagHead =document.getElementsByTagName("head")[0];
	      var d=document.createElement("div");
	      tagHead.appendChild(d);
	      d.innerHTML = "<object classid='clsid:B0EF56AD-D711-412D-BE74-A751595F3633' id='JITDSignOcx' width='0' codebase='./JITComVCTK_S.cab#version=2,1,2,2'></object>";
		  Ocx = document.getElementById("JITDSignOcx");
		 }
}

/**
 * CA登录
 * @returns {Boolean}
 */
function caLogin(){
	var info = {};
	var DSign_Subject = "";
	if(DSign_Content==""){
		alert("原文不能为空，请输入原文!");
	}else{
		//控制证书为一个时，不弹出证书选择框
		JITDSignOcx.SetCertChooseType(1);
		JITDSignOcx.SetCert("SC","","","","CN=Private Certificate Authority Of MOF, O=MOF, C=CN","");
		if(JITDSignOcx.GetErrorCode()!=0){
			alert("错误码："+JITDSignOcx.GetErrorCode()+"　错误信息："+JITDSignOcx.GetErrorMessage(JITDSignOcx.GetErrorCode()));
			return;
		}else {
			 var temp_DSign_Result = JITDSignOcx.DetachSignStr(DSign_Subject,DSign_Content);
			 if(JITDSignOcx.GetErrorCode()!=0){
					alert("错误码："+JITDSignOcx.GetErrorCode()+"　错误信息："+JITDSignOcx.GetErrorMessage(JITDSignOcx.GetErrorCode()));
					return;
			 }
			 info.signed_data = temp_DSign_Result;
		}
	}
	info.DSign_Content = DSign_Content;
	$.ajax({
		url:_BASE_PATH + "/fpfportal/login/calogin",
		type:'post',
		dataType:'json',
		data:info,
		success:function(resp){
			if(succ.success == 'success'){
				if (resp) {
					var token = resp.token;
					if (isNaN(token) && token.length == 40) {
						forward(resp);
					} else {
						switch (token) {
						case "11010201":
							alert("CA网关认证失败!");
							break;
						case "11010202":
							alert("无此用户或用户失效!");
							break;
						case "11010203":
							alert("CA网关未正确返回身份信息!");
							break;
						case "11010204":
							alert("用户已锁定,请稍后再试!");
							break;
						case "11010205":
							alert("系统错误,请稍后再试!");
							break;
						case "11010005":
							alert("验证码错误!");
							break;
						case "11010206":
							alert("未设置默认财年,不允许登录!");
							break;
						case "00000000":
							alert("普通用户,不允许登录!");
							break;
						default:
							alert(resp);
						}
					}
				}
			}else{
				alert("CA登录异常，请重新登录！");
			}
		},
		error:function(err){
			alert(err);
		}
	});
}
/**
 * 初始化首页通知公告
 */
function initLoginPageNotic(){

	$.ajax({
		url:_BASE_PATH+"/fpfportal/login/initLoginPageNotic",
		type:'post',
		dataType:'json',
		data:{},
		success:function(succ){
			var allNoticList = succ.allNoticList;
			if(allNoticList!=null&&allNoticList.length>0){
				var html=[];
				for(var i=0;i<allNoticList.length;i++){
					if(i>2) break ;
					var noticInfo=allNoticList[i];
					var noticName=formatNoticName(noticInfo.FILENAME)
					//是否置顶
					var noticOrder=noticInfo.ORDERID;
					html.push("<li>");
					if($.trim(noticOrder)=='1'){
						html.push("<a class='notic_top' title='"+noticInfo.FILENAME+"' href='javaScript:toNoticDetails(\""+noticInfo.GUID+"\",\"通知公告\")'>"+noticName+"</a>");
					}else{
						html.push("<a title='"+noticInfo.FILENAME+"' href='javaScript:toNoticDetails(\""+noticInfo.GUID+"\",\"通知公告\")'>"+noticName+"</a>");
					}
					
					html.push("</li>");
				}
				$("#initNotic").html(html.join(''));
				$("#div").show();
				if (allNoticList.length <= 3) {
					$("#login_notic_more").css("display","none");
				}
			}			
		},
		error:function(err){
			
		}
	});

}
function formatNoticName(name){
	if(name==null||name==""||name==undefined||name=="undefined"){
		return "";
	}
	if(name.length>15){
		name=name.substring(0,14)+"……";
		return name;
	}
	return name;
}
/**
 * 兼容IE8 input placeholder显示颜色效果问题
 * @author wangqun 2017-7-20 09:37:27
 */
function ctrlInputColor(){
	$("#username").val()==""||$("#username").val()=="请输入用户名"?$("#username").css("color","#BBBBBB"):$("#username").css("color","#000");
	$("#password").val()==""?$("#password").css("color","#BBBBBB"):$("#password").css("color","#000");
	$("#username").focus(function(){
		$(this).css("color","#000")
	})
	.on('blur',function(){
		$("#username").val()==""||$("#username").val()=="请输入用户名"?$(this).css("color","#BBBBBB"):$(this).css("color","#000");
	});
	$("#password").focus(function(){
		$(this).css("color","#000")
	})
	.on('blur',function(){
		$(this).val()==""?$(this).css("color","#BBBBBB"):$(this).css("color","#000");
	});
}
/*
 * 登录页重置按钮
 */

function toReSet(){
	for(i=0;i<document.all.tags("input").length;i++){  
	   if(document.all.tags("input")[i].type=="text"||document.all.tags("input")[i].type=="password"){  
	      document.all.tags("input")[i].value="";  
	   }  
   }  
	
}
/**
 * 下载文件
 * @param filename
 */
function downloadfile(filename){
	/*var iframe,iframeId="downIframeId";
	iframe=document.getElementById(iframeId);
	var iframediv=document.getElementById("downIframeId_div");
	if(!iframe){			
		iframe=document.createElement('iframe');
		iframe.id=iframeId;
		iframe.name="mydown";
		document.body.appendChild(iframe);//把form放到页面里
		var div=document.createElement("div");
		div.id="downIframeId_div";
		div.innerHTML="<form  action='' method='post' target='mydown'><input name ='filename' value=''/></form>";
		div.style.display='none';
		document.body.appendChild(div);
		iframediv=document.getElementById("downIframeId_div");
	}
	iframe.style.width=0;
	iframe.style.height=0;
	iframediv.firstChild.action="/fpfportal/common/downloadfileLocal";
	iframediv.firstChild.firstChild.value=encodeURIComponent(filename);
	iframediv.firstChild.submit();*/
	var href = _BASE_PATH+"/fpfportal/common/downloadfileLocal?filename="+filename;
	var as=document.createElement("a");
	$(as).attr("href",href);
	as.click();

}


/*
 * 重刷验证码
 */
function reSetCode(){
	//初始化验证码插件
	if(jsorjava=='0'){
		$.idcode.setCode();
	}else{
		$('#kaptchaImage').attr('src', '/fpfportal/captchaRandomCode/captchaimage?' + Math.floor(Math.random()*100) );  
	}

//    $('#kaptchaImage').attr('src', '/kaptcha.jpg?' + Math.floor(Math.random()*100) );
}
/**
 * 通知公告跳转
 * @param guid
 */
function toNoticDetails(guid,menuname){
	var url=encodeURI(encodeURI(_BASE_PATH+"/fpfportal/page/notic/shownoticdata.jsp?noticguid="+guid+"&depname=1&menuname="+menuname+"&admdivname=1"));
	window.open(url);
}

//跳转首页
function forward(resp){
	//判断用户是否被授权登录，如果被授权登录，需选择后确认登录  add by zhouqin 2017-08-14
	if(resp.loinback == 'loinback'){
		loinback(resp.userList,resp.recipguid,resp.recipient,resp.token);
	}else{
		if(resp.isconfig){
			var isUrl = resp.isurl;
			if(isUrl=='1'){//跳转路径
				var configUrl = resp.configData;
				if(configUrl==null||configUrl==""||configUrl==undefined||$.trim(configUrl)==""||configUrl=="undefined"||configUrl=="null"){
					alert("登录失败，请检测配置的跳转路径！");
					return;
				}
				var vcode = null;
			 	var appid = null;
			 	if(configUrl.indexOf("/hqoa/singleLogin/toSingleLoginCheckJsp.do")>=0){
			 		
			 		var getVCode = COMMONJS.addToken(_BASE_PATH+"/fpfportal/ssovcode/getVCode");
					$.ajax({
						url:getVCode,
						type:'POST',
						data:{tokenid:tokenid},
						async: false,  
						success:function(succ){
							var result = succ.success;
							if(result=='success'){
								appid = succ.appid;
								vcode = succ.vcode;
							}
						},
						error:function(err){
							
						}
					});
			 	}
			 	 var hrefUrl;
				if(configUrl.indexOf("?")>=0){
					hrefUrl = configUrl+"&tokenid="+resp.token;
				}else{
					hrefUrl = configUrl+"?tokenid="+resp.token;
				}
			 	if(vcode!=null&&vcode!=""&&vcode!=undefined&&vcode!="undefined"&&appid!=null&&appid!=""&&appid!=undefined&&appid!="undefined"){
			 		hrefUrl = hrefUrl+"&vcode="+vcode;
					
				 }
				window.location.href=hrefUrl;
			}else{
				//业务类型对象
				var registerInfo=resp.registerInfo;
				var appname=registerInfo.APPNAME;
				var guid=registerInfo.GUID;
				var appsource=registerInfo.APPSOURCE;
				var showway=registerInfo.SHOWWAY;
				var appid=resp.appids;
				var allviewnum=resp.allViewNum;
				var uinf=resp.uname;
				var admdivname=resp.deptname;
				var waittodoNums='0';
				//window.location.href=encodeURI(encodeURI(_BASE_PATH+"fpfportal/page/waittodo/waittodo.jsp?registerName="+appname+"&registerAppid="+appid+"&registerId="+guid+"&allviewnum="+allviewnum+"&appSource="+appsource+"&abc="+uinf+"&tokenid="+resp.token+"&admdivname="+admdivname+"&waittodoNums="+waittodoNums+"&showway="+showway+"&year="+resp.checkyear));
				window.location.href=encodeURI(encodeURI(_BASE_PATH+"fpfportal/page/waittodo/waittodo.jsp?registerName="+appname+"&registerAppid="+appid+"&registerId="+guid+"&allviewnum="+allviewnum+"&appSource="+appsource+"&abc="+uinf+"&tokenid="+resp.token+"&admdivname="+admdivname+"&waittodoNums="+waittodoNums+"&showway="+showway));
			}
			
		}else{
			//window.location.href=_BASE_PATH+"/fpfportal/page/homepage/homepage.jsp?tokenid="+resp.token+"&year="+resp.checkyear;
			var checkhome = resp.checkhome;
			if(checkhome == '0'){
				window.location.href=_BASE_PATH+"/fpfportal/page/nmhomepage/nmhomepage.jsp?tokenid="+resp.token;
			}else{
				window.location.href=_BASE_PATH+"/fpfportal/page/homepage/homepage.jsp?tokenid="+resp.token;
			}
			//window.location.href=_BASE_PATH+"/fpfportal/page/nmhomepage/nmhomepage.jsp?tokenid="+resp.token+"&year="+resp.checkyear;
		}
	}
}

//登录页弹框信息
function loinback(userList,recipguid,recipient,tokenid){
	var title = '用户信息';
	var html = [];
	html.push("<div id='checkUser' style='padding: 20px'>");
	html.push("<div class='input_style' style='vertical-align: middle;'>");
	html.push("<span style='padding-top: 3px;font-size:15px;'>请选择您要登录的用户：</span>");
	html.push("<span style='display:inline-block;vertical-align: top;font-size:14px;'>");
	html.push("<select style='font-size:14px;' id='checkUserName' name='checkUserName'>");
	for(var i=0;i<userList.length;i++){
		html.push("<option value='"+userList[i].AUTHGUID+"'>"+userList[i].AUTHORIZER+"</option>");
	}
	html.push("<option value='"+recipguid+"'>"+recipient+"</option>");
	html.push("</select></span>");
	html.push("</div>");
	html.push("<div class='input_style' style='vertical-align: middle;'>");
	html.push("<button class='btn_style' onclick='checkLogin(\""+tokenid+"\");'");
	html.push("style='margin-top: 53px; margin-left: 120px;font-size:14px;'>确认登录</button>");
	html.push("</div>");
	html.push("</div>");
	//console.log(html.join(""));
	layer.open({
		title:[title,'font-size:17px; text-align: left;line-height:40px;padding-left:15px;font-weight:300;font-family:黑体'],
		type: 1,
		skin: 'layui-layer-lan', //加上边框
		area: ['360px', '200px'], //宽高
		content:  html.join("")
	});
}

//确认登录    add by zhouqin 2017-08-10
function checkLogin(tokenid){
	var checkUser =  $("#checkUserName").val();
	//var checkyear = $("#checkyear").val();
	$.ajax({
		url:_BASE_PATH+"/fpfportal/login/checkLogin",
		type:'post',
		dataType:'json',
		//data:{tokenid:tokenid,checkUser:checkUser,checkyear:checkyear},
		data:{tokenid:tokenid,checkUser:checkUser},
		success:function(succ){
			debugger;
			if(succ.success == "error"){
				reSetCode();
				alert("登录有误，请重新登录");
			}else{
				var token = succ.token ;
				if(isNaN(token) && token.length == 40){
					forward(succ);
				}else{
					reSetCode();
					switch(token){		
					case "11010000":
						/*alert("无此用户!");*/
						alert("用户名或密码错误!");
						break;
					case "11010001":
						alert("用户已失效!");
						break;
					case "11010002":
						alert("用户被暂停使用!");
						break;
					case "11010003":
						alert("用户名或密码错误!");
						break;
					case "11010004":
						alert("系统错误，请重新登录!");
						break;
					case "11010005":
						alert("验证码错误!");
						break;
					case "11010006":
						alert("用户类型不匹配!");
						break;
					case "11010007":
						modifypwd(info);
						break;
					case "11010008":
						alert("不能重复登陆用户!");
						break;
					case "11010009":
						alert("用户已锁定,请稍后再试!");
						break;
					case "11010019":
						modifypwd(info);
						break;
					case "11010020":
						alert("未设置默认财年，不允许登录!");
						break;
					default:
						alert("用户已锁定，请"+ token +"分钟后再试!");
				}
			}
		}
		},
		error:function(err){
			reSetCode();
			alert("登录有误，请重新登录！");
		}
	});
}

//add by jinwencheng on 2017-05-26
/**
 * 更多跳转
 * @param flag
 */
function commonMore(flag){
	//var depname=$("#uname").html();
	//var admdivname=$("#icon_admdivname").html();
	var url;
	if(flag=='0'){//常用功能
		url=encodeURI(encodeURI(_BASE_PATH+"/fpfportal/page/morepage/morenotice.jsp?flag="+flag));
	}
	window.open(url);
}
//add end