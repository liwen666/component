var COMMONJS={
	/**
	 * 初始化页面body标签的高度
	 * 去除75为页面引入头部html挤占高度
	 * 2017年6月7日11:37:53 by wq
	 */
	initPageBodyHeight:function(){
		$('body').css('height',$(window).height()-$("#header_color").height()-20);
	},
	/**
	 * 根据页面情况设置datagrid的table自适应高度
	 * @param toolheight 工具栏高度
	 * 2017年6月7日11:37:53 by wq
	 */
	toAdaptTableHeight:function(toolheight){
		this.initPageBodyHeight();
		if($("#tablearea").offset()){
			$("#tablearea").css("height",($(window).height()-$("#tablearea").offset().top-20)+"px");
		}
		if($("#lefttree").offset()){
			$("#lefttree").css("height",($(window).height()-$("#lefttree").offset().top-12)+"px");
		}
	},
	/**
	 * 展开设置类型修改下拉框
	 * 2017年6月6日16:00:33 by wq
	 */
	beforeupdUserMsg:function(a){
		var $this=a;
		var html=[];
		html.push("   <div class='common_upd_sel'  id='common_upd_sel'>  ");
		html.push("        <p name='0' style='font-family:Microsoft YaHei;font-size:14px;' onclick='commonUpdChange(this)'>修改密码</p>   ");
	/*	html.push("        <p name='1' style='font-family:Microsoft YaHei;font-size:14px;' onclick='changeHome(this)'>首页设置</p>   ");*/
		html.push("        <p name='2' style='font-family:Microsoft YaHei;font-size:14px;' onclick='commonUpdChange(this)'>个人设置</p>   ");
		html.push("   </div>");
		layer.open({
			  type: 1,
			  shade: 0.001,
			  title: false,//不显示标题
			  shadeClose:true,
			  area: ['90px', '62px'],
			  closeBtn:false,
			  scrollbar:true,
			  content: html.join(""), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
			  offset: [$($this).offset().top+20+"px", $($this).offset().left-80+"px"]
			});
	},
	/**
	 * 创建dataGrid
	 * @param id 标签ID
	 * @param url 后台跳转路径
	 * @param param 参数
	 * @param col 字段列表
	 * @returns
	 */
	createTable:function(id,url,param,col){
		 var newtable=$('#'+id).datagrid({
		        //title : '测试列表',
		        //iconCls : 'icon-a_detail',
		        iconCls:'icon-ok',  
		        fit : true,
		        fitColumns : true,
		        rownumbers : false,
		        pagination : true,
		        singleSelect : false,
		        border : false,
		        //交替显示
		        striped : true,
		        url:url,
		        queryParams:param,
		        columns : [col],
		        onLoadSuccess:function(data){
		        	$(".datagrid-header-rownumber,.datagrid-td-rownumber").css("width","40px");
		        	//add by jinwencheng on 2017-07-04 增加表头序号显示
		        	$(".datagrid-header-rownumber").html("序号");
		        	//add end
		        	$(".datagrid-cell-rownumber").css("width","39px");
		        	//add by jinwencheng on 2017-07-05 如果没有数据显示提示信息并隐藏底部分页信息
		        	if(data.total <= 0){
		        		var body = $(this).data().datagrid.dc.body2;
		        	    body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height:20px;text-align:center;color:red;"><h3>暂时没有相关记录！</h3></td></tr>');
		        	    //隐藏分页底部信息
		        		$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
		        	}
		        	//add end
		        }
		    });
		 //add by jinwencheng on 2017-06-21 刷新数据,解决分页之后数据不正确的问题
		 $('#'+id).datagrid('load');
		 //add end
		 $('#'+id).datagrid('getPager').pagination({
		        pageNumber: 1,  
		        pageList: [10, 20, 30, 40, 50],  
		        beforePageText: '第',//页数文本框前显示的汉字   
		        afterPageText: '页    共 {pages} 页',  
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		 });  
		 return newtable;
	},
	
	/**
	 * 通知公告弹框中无需分页
	 */
	createNoticTable:function(id,url,param,col){
		 var newtable=$('#'+id).datagrid({
		        iconCls:'icon-ok',  
		        fit : true,
		        fitColumns : true,
		        rownumbers : false,
		        pagination : false,
		        singleSelect : false,
		        border : false,
		        remoteSort:false,
				multiSort:true,
		        //交替显示
		        striped : true,
		        url:url,
		        queryParams:param,
		        columns : [col]
		    });
		 return newtable;
	},
	
	
	/**
	 * 创建dataGrid
	 * @param id 标签ID
	 * @param url 后台跳转路径
	 * @param param 参数
	 * @param col 处理合并列情况
	 * @returns
	 */
	createColsTable:function(id,url,param,col){
		 var newtable=$('#'+id).datagrid({
		        //title : '测试列表',
		        //iconCls : 'icon-a_detail',
		        iconCls:'icon-ok',  
		        fit : true,
		        fitColumns : true,
		        rownumbers : false,
		        pagination : true,
		        singleSelect : false,
		        border : false,
		        //交替显示
		        striped : true,
		        url:url,
		        queryParams:param,
		        columns : col,
		        onLoadSuccess:function(data){
		        	//add by jinwencheng on 2017-07-05 如果没有数据显示提示信息并隐藏底部分页信息
		        	if(data.total <= 0){
		        		var body = $(this).data().datagrid.dc.body2;
		        	    body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height:20px;text-align:center;color:red;"><h3>暂时没有相关记录！</h3></td></tr>');
		        	    //隐藏分页底部信息
		        		$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
		        	}
		        	//add end
		        }
		    });
		 $('#'+id).datagrid('getPager').pagination({  
		        pageSize: 10,  
		        pageNumber: 1,  
		        pageList: [10, 20, 30, 40, 50],  
		        beforePageText: '第',//页数文本框前显示的汉字   
		        afterPageText: '页    共 {pages} 页',  
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		   });  
		 return newtable;
	},
	
	/**
	 * 创建dataGrid-业务系统通知公告表格专用-add by zhouqin 2017-06-28
	 * @param id 标签ID
	 * @param url 后台跳转路径
	 * @param param 参数
	 * @param col 字段列表
	 * @returns
	 */
	createSysNoticTable:function(id,url,param,col,callback){
		 var flag=true;
		 var newtable=$('#'+id).datagrid({
		        //title : '测试列表',
		        //iconCls : 'icon-a_detail',
		        iconCls:'icon-ok',  
		        fit : true,
		        fitColumns : true,
		        rownumbers : true,
		        pagination : true,
		        singleSelect : false,
		        border : false,
		        //交替显示
		        striped : true,
		        url:url,
		        queryParams:param,
		        columns : [col],
		        onLoadSuccess:function(data){
		        	$(".datagrid-header-rownumber,.datagrid-td-rownumber").css("width","40px");
		        	//add by jinwencheng on 2017-07-04 增加表头序号显示
		        	$(".datagrid-header-rownumber").html("序号");
		        	//add end
		        	$(".datagrid-cell-rownumber").css("width","39px");
		        	//add by jinwencheng on 2017-07-05 如果没有数据显示提示信息并隐藏底部分页信息
		        	if(data.total <= 0){
		        		var body = $(this).data().datagrid.dc.body2;
		        	    body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height:20px;text-align:center;color:red;"><h3>暂时没有相关记录！</h3></td></tr>');
		        	    //隐藏分页底部信息
		        		$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
		        	}
		        	//add end
		        	//解决360浏览器新门户首页通知公告显示问题，添加回调函数 by wangqun
		        	if(callback&&flag){
		        		flag=false;
		        		callback();
		        	}
		        }
		    });
		 //add by jinwencheng on 2017-06-21 刷新数据,解决分页之后数据不正确的问题
		 $('#'+id).datagrid('load');
		 //add end
		 $('#'+id).datagrid('getPager').pagination({
		        pageSize: 10,  
		        pageNumber: 1,  
		        pageList: [10, 20, 30, 40, 50],  
		        beforePageText: '第',//页数文本框前显示的汉字   
		        afterPageText: '页    共 {pages} 页',  
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		 });  
		 
		 return newtable;
	},
	/**
	 * 退出登录
	 * @param tokenid
	 */
	loginout:function(flag){
		try{
			var bp=COMMONJS.getHost();
			var tokenid=COMMONJS.getTokenId();
			if(tokenid==null||tokenid==""||tokenid=='null'||tokenid==undefined||tokenid=='undefined'){
				layer.alert("您当前未登录系统，请返回登录！");
				return ;
			}
			if(flag){
				 if(confirm("是否退出登录？"))
				 {
					 var url = COMMONJS.addToken(bp+"/fportal/login/loginout.do");
					$.ajax({
				    		url:url,
				    		type:'post',
				    		dataType:'json',
				    		async: false,
				    		success:function(succ){
				    			//var url="http://"+window.location.host+"/fportal";
				    			window.location.href=bp+"fportal";
				    		},
				    		error:function(err){
				    			window.location.href=bp+"fportal";
				    		}
				    });
				 }
			}else{
				 var url = COMMONJS.addToken(bp+"/fportal/login/loginout.do");
				$.ajax({
		    		url:url,
		    		type:'post',
		    		dataType:'json',
		    		async: false,
		    		success:function(succ){
		    			//var url="http://"+window.location.host+"/fportal";
		    			window.location.href=bp+"fportal";
		    		},
		    		error:function(err){
		    			window.location.href=bp+"fportal";
		    		}
		    });
			}
		}catch(e){
			window.location.href=bp+"fportal";
		}
	},
		/**
		 * 检测IE版本
		 * @returns {Boolean}
		 */
    checkIeOper:function(){
			var browser=navigator.appName 
			var b_version=navigator.appVersion 
			var version=b_version.split(";"); 
			var trim_Version=version[1].replace(/[ ]/g,""); 
			if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0") 
			{ 
				return false;
			} 
			else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0") 
			{ 
				return false;
			} 
			else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0") 
			{ 
				return false;
			} 
			else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE9.0") 
			{ 
				return true;
			} else{
				return true;
			}
			
		},
		//给URL添加token
		addToken:function(url){
			var tokenid=COMMONJS.getTokenId();
			if(url.indexOf("?")>0){
				//return encodeURI(encodeURI(url+"&tokenid="+tokenid));
				return encodeURI(url+"&tokenid="+tokenid);
			}else{
				//return encodeURI(encodeURI(url+"?tokenid="+tokenid));
				return encodeURI(url+"?tokenid="+tokenid);
			}
		},
		getHost:function(){
			var host=window.location.host;
			return "http://"+host+"/"
		},
		/**
		 * 获取token值
		 * @returns
		 */
		getTokenId:function(){
			var reg = new RegExp("(^|&)tokenid=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)
		    	return unescape(r[2]);return null;
		},
		/**
		 * 从项目中下载文件下载文件
		 * （文件路径已定死：/fportal/static/file中）
		 * @param filename
		 */
		downloadLocal:function(filename){
			if(filename == '财政一体化门户用户手册.doc'){
				return;
			}
			var iframe,iframeId="downIframeId";
			iframe=document.getElementById(iframeId);
			var iframediv=document.getElementById("downIframeId_div");
			if(!iframe){			
				iframe=document.createElement('iframe');
				iframe.id=iframeId;
				iframe.name="mydown";
				document.body.appendChild(iframe);//把form放到页面里
				var div=document.createElement("div");
				div.id="downIframeId_div";
				div.innerHTML="<form action='' method='post' target='mydown'><input name ='filename' value=''/><input type='hidden' name ='hiddenVal' value='downloadLocal'/></form>";
				div.style.display='none';
				document.body.appendChild(div);
				iframediv=document.getElementById("downIframeId_div");
			}
			iframe.style.width=0;
			iframe.style.height=0;
			if(tokenid!=null&&tokenid!=""&&tokenid!=undefined&&tokenid!="null"&&tokenid!="undefined"){
	  			 url = COMMONJS.addToken("/fportal/notic/getNoticinfoById.do");
	  		}
			iframediv.firstChild.action = url;
			iframediv.firstChild.firstChild.value=encodeURIComponent(filename);
			iframediv.firstChild.submit();
			
	},
	/**
	 * 从文件服务器中下载文件
	 * @param filename
	 */
	downloadFuds:function(fileguid,appid){
		if(fileguid==null||fileguid==""){
			alert("参数错误！");
		}
		var param=fileguid+"_"+appid;
		var iframe,iframeId="downIframeId_fuds";
		iframe=document.getElementById(iframeId);
		var iframediv=document.getElementById("downIframeId_div_fuds");
		if(!iframe){			
			iframe=document.createElement('iframe');
			iframe.id=iframeId;
			iframe.name="mydown_fuds";
			document.body.appendChild(iframe);//把form放到页面里
			var div=document.createElement("div");
			div.id="downIframeId_div_fuds";
			div.innerHTML="<form  action='' method='post' target='mydown_fuds'><input name ='param' value=''/><input type='hidden' name ='hiddenVal' value='downloadFuds'/></form>";
			div.style.display='none';
			document.body.appendChild(div);
			iframediv=document.getElementById("downIframeId_div_fuds");
		}
		iframe.style.width=0;
		iframe.style.height=0;
		iframediv.firstChild.action="/fportal/common/downloadfileFuds";
		iframediv.firstChild.firstChild.value=encodeURIComponent(param);
		iframediv.firstChild.submit();
	},
	/**
	 * 从文件服务器中打包下载文件
	 * @param filename
	 */
	createZip:function(noticname,fileList,appid){
		
		var param =fileList[0].FILEGUID;
		for(var i=1;i < fileList.length;i++){
			param=param+"_"+fileList[i].FILEGUID;
		}

		param=param+"_"+appid+"_"+noticname;
		var iframe,iframeId="downIframeId_fuds_zip";
		iframe=document.getElementById(iframeId);
		var iframediv=document.getElementById("downIframeId_div_fuds_zip");
		if(!iframe){			
			iframe=document.createElement('iframe');
			iframe.id=iframeId;
			iframe.name="mydown_fuds_zip";
			document.body.appendChild(iframe);//把form放到页面里
			var div=document.createElement("div");
			div.id="downIframeId_div_fuds_zip";
			div.innerHTML="<form  action='' method='post' target='mydown_fuds_zip'><input name ='param_zip' value=''/><input type='hidden' name ='hiddenVal' value='createZip'/></form>";
			div.style.display='none';
			document.body.appendChild(div);
			iframediv=document.getElementById("downIframeId_div_fuds_zip");
		}
		iframe.style.width=0;
		iframe.style.height=0;
		iframediv.firstChild.action="/fportal/common/createZip";
		iframediv.firstChild.firstChild.value=encodeURIComponent(param);
		iframediv.firstChild.submit();
	},
	/** 
     * 格式化日期 
     * @param date 日期 
     * @param format 格式化样式,例如yyyy-MM-dd HH:mm:ss E 
     * @return 格式化后的数据 
     */
    formatDate : function (fDate, format) { 
      var v = ""; 
	  if(fDate==null||fDate==""||fDate==undefined||fDate=='undefined'){
		  return "";
	  }
      var fullDate = fDate.split("-");  

	  var date = new Date(Date.parse(fDate.replace(/-/g, "/")));
		
      var year  = date.getFullYear(); 
      var month  = date.getMonth()+1; 
      var day   = date.getDate(); 
      var hour  = date.getHours(); 
      var minute = date.getMinutes(); 
      var second = date.getSeconds(); 
      var ms   = date.getMilliseconds(); 
      var weekDayString = ""; 
        
      v = format; 
      //Year 
      v = v.replace(/yyyy/g, year); 
      v = v.replace(/YYYY/g, year); 
      v = v.replace(/yy/g, (year+"").substring(2,4)); 
      v = v.replace(/YY/g, (year+"").substring(2,4)); 
  
      //Month 
      var monthStr = ("0"+month); 
      v = v.replace(/MM/g, monthStr.substring(monthStr.length-2)); 
  
      //Day 
      var dayStr = ("0"+day); 
      v = v.replace(/dd/g, dayStr.substring(dayStr.length-2)); 
  
      //hour 
      var hourStr = ("0"+hour); 
      v = v.replace(/HH/g, hourStr.substring(hourStr.length-2)); 
      v = v.replace(/hh/g, hourStr.substring(hourStr.length-2)); 
  
      //minute 
      var minuteStr = ("0"+minute); 
      v = v.replace(/mm/g, minuteStr.substring(minuteStr.length-2)); 
  
      //Millisecond 
      v = v.replace(/sss/g, ms); 
      v = v.replace(/SSS/g, ms); 
        
      //second 
      var secondStr = ("0"+second); 
      v = v.replace(/ss/g, secondStr.substring(secondStr.length-2)); 
      v = v.replace(/SS/g, secondStr.substring(secondStr.length-2)); 
      return v; 
    }
	
}
////////////////////////////////////私有方法///////////////////////////////////////////////////////////////////////////////////////
///**
// * 修改类型下拉框改变 
// * 2017年6月6日16:06:26 注释by wq 
// */
//function commonUpdChange(obj){
//	var token=COMMONJS.getTokenId();
//	var userguid=token.substring(0,32);
//	var val=$(obj).val();
//	if(val=='0'){//修改用户信息
//		$("#update_login_user").show();
//		$("#common_open_way").hide();
//		$("#common_def_register").hide();
//	}else if(val=='1'){//打开方式
//		$("#update_login_user").hide();
//		$("#common_open_way").show();
//		$("#common_def_register").hide();
//	}else if(val=='2'){//默认跳转
//		$("#update_login_user").hide();
//		$("#common_open_way").hide();
//		$("#common_def_register").show();
//	}
//}

function changeHome(){
	layer.closeAll();
	var bp=COMMONJS.getHost();
	var token=COMMONJS.getTokenId();
	if(token==null||token==""||token=='null'||token==undefined||token=='undefined'){
		layer.alert("您当前未登录系统，请返回登录！");
		return;
	}
	var userguid=token.substring(0,32);
	var url = COMMONJS.addToken(bp+"fportal/common/queryUserCheckHome.do");
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		async: false,
		data:{userGuid:userguid},
		success:function(succ){
			if(succ.success=='success'){
				var checkhome = succ.checkhome;
				var html=[];
				html.push("<div id='common_home_div' class='common_upd_div'>");
				html.push(" <div id='common_def_home' class='update_login_user' >");
				html.push("		<table border='0' cellspacing='0' cellpadding='0' class='updateTblae'>");
				html.push("			<tr style='height:15px;'></tr>");
				html.push("			<tr id='commonOpenHome' style='display:block'>");
				html.push("				<td>");
				html.push("					<font class='titleframe'>选择首页:&nbsp;&nbsp;</font>");
				html.push("				</td>");
				html.push("				<td>");
				html.push("					<select id='checkhome' class='input_box_style'>");
				if(checkhome == '0'){
					html.push("						<option value='0' selected='selected'>新版首页</option>");
					html.push("						<option value='1'>老版首页</option>");
				}else{
					html.push("						<option value='0'>新版首页</option>");
					html.push("						<option value='1' selected='selected'>老版首页</option>");
				}
				html.push("					</select>");
				html.push("				</td>");
				html.push("			</tr>");
				html.push("			<tr style='height:15px;'></tr>");
				html.push("		</table>");
				html.push(" </div>");
				html.push("</div>");
				html.push("<div class='common_btn'>");
				html.push("		<a class='btn_style common_btn_div_left' href='javaScript:saveUserHome(\""+userguid+"\");'>确定</a>");
				html.push("		<a class='btn_style common_btn_div_right' href='javaScript:cancelUpdUserMsg();'>取消</a>");
				html.push("</div>");
				//弹框
				var mtitle="首页设置";
				var updLaye=layer.open({
				    type: 1, //page层
				    skin: 'layui-layer-lan', //加上边框
				    area: ['400px', '280px'], //宽高
				    offset: '200px',//位置垂直距离 水平居中
				    title:[mtitle,'font-size:17px; text-align: left;line-height:40px;padding-left:15px;font-weight:300;font-family:黑体'],
				    closeBtn: 1, //不显示关闭按钮
				    moveType: 1, //拖拽风格，0是默认，1是传统拖动
				    shift: 0, //0-6的动画形式，-1不开启
				    content: html.join("")
				});
			}
		},
		error:function(err){
			
		}
	});
}

function saveUserHome(userguid){
	var bp=COMMONJS.getHost();
	var checkhome = $("#checkhome").val();
	//确认保存
	var url = COMMONJS.addToken(bp+"/fportal/common/updateHome.do");
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		async: false,
		data:{userGuid:userguid,checkhome:checkhome},
		success:function(succ){
			if(succ){
				layer.closeAll();
				alert("设置成功！");
    		}else{
    			alert("设置失败！");
    		}
		},
		error:function(err){
			
		}
	});
}

/**
 * 修改类型下拉选项改变(首页)
 * 2017年6月6日15:59:15 by wq
 */
function commonUpdChange(obj){
	layer.closeAll();
	var val=$(obj).attr("name");
	$("#common_upd_sel").val(val);//将选择对应值存入父元素的value里方便其他方法获取
	var bp=COMMONJS.getHost();
	var token=COMMONJS.getTokenId();
	if(token==null||token==""||token=='null'||token==undefined||token=='undefined'){
		layer.alert("您当前未登录系统，请返回登录！");
		return;
	}
	var userguid=token.substring(0,32);
	var url = COMMONJS.addToken(bp+"fportal/common/queryUserMsg.do");
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		async: false,
		data:{userGuid:userguid},
		success:function(succ){
			if(succ.success=='success'){
				//var userdto=succ.userdto;
				//获取用户配置信息
				var configList=succ.configList;
				//获取用户当前有权限的业务类型
				var registerList=succ.registerlist;
				
				var html=[];
				html.push("<div id='common_upd_div' class='common_upd_div'>");
				if(val=='0'){//修改用户信息
					html.push(" <div class='update_login_user' id='update_login_user'>");
					html.push("		<table border='0' cellspacing='0' cellpadding='0' class='updateTblae'>");
					html.push("			<tr style='height:15px;'></tr>");
					html.push("			<tr id='updateuseroldpwdtr' style='display:block'>");
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
				}else if(val=='1'){//打开方式
					html.push(" <div id='common_open_way' class='update_login_user'   >");
					html.push("		<input id='common_open_way_guid' type='hidden' value='0'/>");
					html.push("		<table border='0' cellspacing='0' cellpadding='0' class='updateTblae'>");
					html.push("			<tr style='height:15px;'></tr>");
					html.push("			<tr id='commonOpenWayTr' style='display:block'>");
					html.push("				<td>");
					html.push("					<font class='titleframe'>打开方式:&nbsp;&nbsp;</font>");
					html.push("				</td>");
					html.push("				<td>");
					html.push("					<select id='commonOpenWayInp' class='input_box_style'>");
					html.push("						<option value='0'>当前窗口</option>");
					html.push("						<option value='1'>新窗口</option>");
					html.push("					</select>");
					html.push("				</td>");
					html.push("			</tr>");
					html.push("			<tr style='height:15px;'></tr>");
					html.push("		</table>");
					html.push(" </div>");
				}else if(val=='2'){//默认跳转
					html.push(" <div id='common_def_register' class='update_login_user' >");
					html.push("		<input id='common_def_register_guid' type='hidden' value='0'/>");
					html.push("		<table border='0' cellspacing='0' cellpadding='0' class='updateTblae'>");
					html.push("			<tr style='height:15px;'></tr>");
					html.push("			<tr id='commonOpenWayTr' style='display:block'>");
					html.push("				<td>");
					html.push("					<font class='titleframe'>登录跳转页:&nbsp;&nbsp;</font>");
					html.push("				</td>");
					html.push("				<td>");
					html.push("					<select id='commonRegisterSel' class='input_box_style'>");
					html.push("						<option value='0'>门户首页</option>");
					if(registerList!=null&&registerList!=""&&registerList.length>0){
						$.each(registerList,function(index,val){
							html.push("				<option value='"+val.GUID+"'>"+val.APPNAME+"</option>");
						});
					}
					html.push("					</select>");
					html.push("				</td>");
					html.push("			</tr>");
					html.push("			<tr style='height:15px;'></tr>");
					html.push("		</table>");
					html.push(" </div>");
				}
				html.push("</div>");
				html.push("<div class='common_btn_div'>");
				html.push("		<a class='btn_style common_btn_div_left' href='javaScript:sureUpdUserMsg("+val+");'>确定</a>");
				html.push("		<a class='btn_style common_btn_div_right' href='javaScript:cancelUpdUserMsg();'>取消</a>");
				html.push("</div>");
				//弹框
				var mtitle="";
				mtitle= val==0?"修改密码":"个人设置";
				var updLaye=layer.open({
				    type: 1, //page层
				    skin: 'layui-layer-lan', //加上边框
				    area: ['400px', '280px'], //宽高
				    offset: '200px',//位置垂直距离 水平居中
				    title:[mtitle,'font-size:17px; text-align: left;line-height:40px;padding-left:15px;font-weight:300;font-family:黑体'],
//				    shade:0.1, //遮罩透明度
				    closeBtn: 1, //不显示关闭按钮
				    //offset: 'rb', //右下角弹出
//				    scrollbar:true,
				    moveType: 1, //拖拽风格，0是默认，1是传统拖动
				    shift: 0, //0-6的动画形式，-1不开启
				    content: html.join("")
				});
				if(configList!=null&&configList!=""&&configList.length>0){
					$.each(configList,function(index,val){
						//0：默认打开的业务类型，1：业务系统打开方式
						var dataFlag=val.DATAFLAG;
						//dataflag为0时此数据为业务类型的GUID，为1时表示打开方式：0;当前页面打开、1：新窗口打开
						var configData=val.CONFIGDATA;
						var configGuid=val.GUID;
						if($.trim(dataFlag)=='0'){
							$("#commonRegisterSel").val($.trim(configData));
							$("#common_def_register_guid").val(configGuid);
						}else if($.trim(dataFlag)=='1'){
							$("#commonOpenWayInp").val($.trim(configData));
							$("#common_open_way_guid").val(configGuid);
						}
					});
				}
			}
		},
		error:function(err){
			
		}
	});
}
/**
 * 确认修改用户信息
 */
function sureUpdUserMsg(updFlag){
	var bp=COMMONJS.getHost();
	//修改类型
	//var updFlag=$("#common_upd_sel").val();
	//参数
	var param;
	if(updFlag=='0'){//修改用户信息
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
		param={updFlag:updFlag,userGuid:userguid,oldpwd:oldpwd,newpwd:newpwd,surepwd:surepwd};
	}else if(updFlag=='1'){//打开方式
		var configData=$("#commonOpenWayInp").val();
		//数据GUID 为0则为新增，否则为修改
		var configGuid=$("#common_open_way_guid").val();
		param={updFlag:updFlag,userGuid:userguid,configData:configData,configGuid:configGuid};
	}else if(updFlag=='2'){//默认跳转
		var configData=$("#commonRegisterSel").val();
		//数据GUID为0则为新增，否则为修改
		var configGuid=$("#common_def_register_guid").val();
		param={updFlag:updFlag,userGuid:userguid,configData:configData,configGuid:configGuid};
	}
	
	//确认保存
	var url = COMMONJS.addToken(bp+"/fportal/common/updUserMsg.do");
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		async: false,
		data:param,
		success:function(succ){
			if(succ){
				if(succ.success=="isoldpwd"){
					alert("原密码不正确,请确认！");
					return;
				}else if(succ.success=='success'){
					layer.closeAll();
					if(updFlag=='0'&&succ.isupd=='true'){
						alert("您已修改密码请重新登录");
						COMMONJS.loginout(false);
					}else{
						alert("修改成功！");
						window.location.reload();
					}
					
    			}else{
    				alert("修改失败！");
    			}
			}
		},
		error:function(err){
			
		}
	});
}

/**
 * 验证是否是数字
 * @param input
 */
function checkRate(val)
{
     var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
     if (!re.test(val))
    {
        return false;
     }
     return true;
}

/**
 * 取消修改用户信息
 */
function cancelUpdUserMsg(){
	layer.closeAll();
}
/*function my_isUpdPwdFlagSel(){
	var selVal=$("#isUpdPwdFlagSel").val();
	if(selVal=='0'){
		$("#updateuseroldpwdtr").hide();
		$("#updateusernewpwdtr").hide();
		$("#updateusersurepwdtr").hide();
	}else if(selVal=='1'){
		$("#updateuseroldpwdtr").show();
		$("#updateusernewpwdtr").show();
		$("#updateusersurepwdtr").show();
	}
}*/