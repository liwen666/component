/**
 * 
 * create by:jinwencheng
 * date:2017-05-16
 * desc:把原来noticdata.jsp中的js全部放置到此文件中
 *
 **/
var userguid;
var uploader;
var fileGuidStr = "";
var fileNameStr = "";
var deletedFileGuid = "";
var allFileNamesArr = [];
var allFileGuidArr = []; 
var countFiles = 0;
var _ROOT_PATH_='http://'+window.location.host;
var um;
var divHerght= 350;
var max_size = 1024*1024*1024+'kb';
var noticguid;
var noticId;
var pubLocation;
var updateFlag;
//var notictype;
var zTreeObj;
var _BASE_PATH;
var tokenid;

var kindEditor;
var pasteFlag = false;
var interval;
var uplodadGuid;

$(function(){
	userguid = tokenid.substr(0,32);
	/* add by jinwencheng on 2017-05-15 添加kindeditor在线编辑器  */
	KindEditor.ready(function(K){
		kindEditor = K.create("textarea[id='editorId']",{
			themeType : 'simple',
	        resizeType : 1,
	        allowPreviewEmoticons: false,
	        allowImageUpload:true,//允许上传图片
	        allowFileManager:true, //允许对上传图片进行管理
	        uploadJson:_BASE_PATH+"fpfportal/static/ui/kindeditor/jsp/upload_json.jsp?tokenid="+tokenid,
	        fileManagerJson:_BASE_PATH+"fpfportal/static/ui/kindeditor/jsp/file_manager_json.jsp?tokenid="+tokenid,
	        		//图片上传后，将上传内容同步到textarea中
					afterUpload : function() {
						this.sync();
					},
					//失去焦点时，将上传内容同步到textarea中
					afterBlur : function() {
						this.sync();
					},
					afterFocus : function(){
						/*var self = this;
						var count = 0;
						K.ctrl(self.edit.doc,'V',function(){
							count++;
							var noticContent = kindEditor.html();
							var i = noticContent.indexOf('<img>');
							if (i != -1) {
								alert(1);
							}else {
								alert(2);
							}
							//pasteImg();
						});*/
						this.sync();
						var self = this;
						var doc = self.edit.doc;
						K(doc.body).bind('keydown', function(e){
							if (e.ctrlKey && e.keyCode === 86) {
								pasteFlag = true;
							}
						});
					},
					//初始化编辑器工具栏
					items : 
						[
					        'source', '|', 'undo', 'redo', '|', 'preview', 'cut', 'copy', 'paste',
					        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
					        'superscript', 'selectall', '/',
					        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
					        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
					        'table', 'hr', 'emoticons', 'pagebreak', 'link', 'unlink', 'fullscreen'
					    ]
			});
	    //解决定时发布日历，点空白区域不消失 by wangqun
		$( "iframe" ).contents().click(function(){
			$dp.hide();
		});
	}); 
	/* add end */
	//add by jinwencheng on 2017-06-05 添加定时器调用函数
	/*if (!!window.ActiveXObject || "ActiveXObject" in window){
    }else{
    	//interval = window.setInterval(pasteImg,1000);
    }*/
	//add end
});
						
 		var zNodes =[];
	    function deleteUnupload(upuploadfileid,upuploadfilename) {
	    	var toremove = ""; 
	    	//先判断删除的是原有的文件还是本次新加的文件判断li节点是否有input孩子节点
	    	var linode = document.getElementById(upuploadfileid);
	    	var ishasinputchilds = linode.getElementsByTagName("input")[0]?true:false;
			if(ishasinputchilds){
				//有input孩子节点
				deletedFileGuid =deletedFileGuid+","+upuploadfileid;
				//allFileNamesArr,allFileGuidArr删除源文件
				for(var p=0;p<allFileNamesArr.length;p++){
					if(upuploadfilename == allFileNamesArr[p]){
						allFileNamesArr.splice(p, 1);
						break;
					}
				}
				for(var m=0;m<allFileGuidArr.length;m++){
					if(upuploadfileid == allFileGuidArr[m]){
						allFileGuidArr.splice(m, 1);
						break;
					}
				}
			}
			//删除该元素节点
	    	linode.parentNode.removeChild(linode);
	    	//从上传队列中删除
	    	if(!ishasinputchilds){
	    		for(var i in uploader.files){
	    			if(("file-"+uploader.files[i].id) == upuploadfileid){
	    				toremove = i;
	    			}
	    		}
	    		uploader.files.splice(toremove, 1);
	    	}
	    	//alert(deletedFileGuid);
	    	countFiles = countFiles-1;
	    	if(countFiles == 0){
				$("#file_upload1").val("未选择文件");
			}else{
				$("#file_upload1").val(countFiles+"个文件");
			}
	    }
	   
	    function getAllFileContents(){
	   		$("#divfilelist").slideToggle(200);
	   }
	    
	    /**
	     * 查询用户在通知公告授权弹窗中所拥有的页签
	     */
	    function queryUserNoticTabs(authtype){
	    	var authedTabs;//已授权标签
	    	var url = COMMONJS.addToken(_BASE_PATH+"/fpfportal/notic/queryUserAuthedTabs.do");
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		dataType:'json',
	    		data:{userguid:userguid,authtype:authtype},
	    		async:false,
	    		success:function(succ){
	    			if(succ.success == 'success'){
	    				authedTabs = succ.authedTabs;
	    			}else{
	    				alert("删除失败！");
	    			}
	    		},
	    		error:function(err){
	    		}
	    	});
	    	return authedTabs;
	    }
	  
	    
	  //记录tab页加载次数
	    var zeroFlag = false;
	    var firstFlag = false;
	    var secondFlag = false;
	    var thirdFlag = false;
	    var fourthFlag = false;
	    
	    //横向授权
	    function sendAcrossNotic(){
	    	if (noticId == undefined||noticId==null||noticId==''||noticId=='null') {
				alert("请先保存通知公告再进行授权！");
				return;
			}
			if(confirm("请确认页面数据已保存再授权！")){
		    	//记录tab页加载次数
				zeroFlag = false;
		    	firstFlag = false;
		    	secondFlag = false;
		    	thirdFlag = false;
		    	fourthFlag = false;
		    	
		    	//控制页签显示隐藏变量
		    	var ywxt = false;//业务系统
		    	var dw = false;//单位
		    	var czjg = false;//财政机构
		    	var czyh = false;//财政用户
		    	var czjs = false;//财政角色
		    	
		    	var ywxtFirst = false;
		    	var dwFirst = false;//单位为首个页签
		    	var czjgFirst = false;//财政机构为首
		    	var czyhFirst = false;//财政用户为首
		    	var czjsFirst = false;//财政角色为首
		    	
		    	//获取当前用户拥有权限的页签及页签下标
		    	var authedTabs = queryUserNoticTabs('0');
		    	//首个加载的页签下标
		    	var firstTab = 0;
		    	if(authedTabs != null && authedTabs.length > 0){
		    		for(var i=0;i<authedTabs.length;i++){
		    			//获取首个加载的页签
		    			if(i == 0){
		    				firstTab = authedTabs[i].ORDERID;
		    				if(authedTabs[i].TABGUID.indexOf('ywxttabguid') != -1){
		    					ywxtFirst = true;
		    					ywxt = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('dwtabguid') != -1){
		    					dwFirst = true;
		    					dw = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('czjgtabguid') != -1){
		    					czjgFirst = true;
		    					czjg = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('czyhtabguid') != -1){
		    					czyhFirst = true;
		    					czyh = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('czjstabguid') != -1){
		    					czjsFirst = true;;
		    					czjs = true;
		    				}
		    			}else{
		    				if(authedTabs[i].TABGUID.indexOf('ywxttabguid') != -1){
		    					ywxt = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('dwtabguid') != -1){
		    					dw = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('czjgtabguid') != -1){
		    					czjg = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('czyhtabguid') != -1){
		    					czyh = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('czjstabguid') != -1){
		    					czjs = true;
		    				}
		    			}
		    		}
		    	}else{
		    		alert("请配置通知公告发布渠道页签！");
		    		return;
		    	}
		    	
		    	//若加载了业务系统
		    	if(ywxt){
		    		zeroFlag = true;
		    	}
		    	
		    	var html = [];
		    	html.push("<div class='tabPanel'>");
		    	html.push("<ul class='tabUl'>");
		    	//若加载业务系统
		    	if(ywxt){
		    		html.push("<li class='hit'>业务系统");
		    	}else{
		    		html.push("<li class='tabHide'>业务系统");
		    	}
		    	html.push("</li>");
		    	//若加载单位
		    	if(dw){
		    		//若单位为首个加载
		    		if(dwFirst){
		    			html.push("<li class='hit'>单位");
		    		}else{
		    			html.push("<li>单位");
		    		}
		    	}else{
		    		html.push("<li class='tabHide'>单位");
		    	}
		    	html.push("</li>");
		    	//若加载财政机构
		    	if(czjg){
		    		if(czjgFirst){
		    			html.push("<li class='hit'>财政机构");
		    		}else{
		    			html.push("<li>财政机构");
		    		}
		    	}else{
		    		html.push("<li class='tabHide'>财政机构");
		    	}
		    	html.push("</li>");
		    	//若加载财政用户
		    	if(czyh){
		    		if(czyhFirst){
		    			html.push("<li class='hit'>财政用户");
		    		}else{
		    			html.push("<li>财政用户");
		    		}
		    	}else{
		    		html.push("<li class='tabHide'>财政用户");
		    	}
		    	html.push("</li>");
		    	//若加载财政角色
		    	if(czjs){
		    		if(czjsFirst){
		    			html.push("<li class='hit'>财政角色");
		    		}else{
		    			html.push("<li>财政角色");
		    		}
		    	}else{
		    		html.push("<li class='tabHide'>财政角色");
		    	}
		    	html.push("</li>");
		    	html.push("</ul>");
		    	html.push("<div class='panes'>");
		    	if(ywxtFirst){
		    		html.push("<div class='pane' style='display:block;'>");
		    	}else{
		    		html.push("<div class='pane'>");
		    	}
		    	html.push("<div class='sysOutLeft'>");
		    	html.push("<span>请选择</span>");
		    	html.push("<div class='sysLeft'>");
		    	html.push("<div class='noticelefttree' style='overflow:auto;'>");
		    	html.push("<ul id='tree0' class='ztree' style='overflow:auto;'></ul>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("<div class='sysOutRight'>");
		    	html.push("<span>已选择</span>");
		    	html.push("<div class='sysRight'  id='sysRight'>");
		    	html.push("<div style='height:400px;margin-top: 10px;'>");
		    	html.push("<table id='sysTable'></table>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	if(dwFirst){
		    		html.push("<div class='pane' style='display:block;'>");
		    	}else{
		    		html.push("<div class='pane'>");
		    	}
		    	html.push("<div class='sysOutLeft'>");
		    	html.push("<span>请选择</span>");
		    	html.push("<div class='sysLeft'>");
		    	html.push("<div class='noticelefttree' style='overflow:auto;'>");
		    	html.push("<ul id='tree1' class='ztree' style='overflow:auto;'></ul>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("<div class='sysOutRight'>");
		    	html.push("<span>已选择</span>");
		    	html.push("<div class='sysRight'  id='admRight'>");
		    	html.push("<div style='height:400px;margin-top: 10px;'>");
		    	html.push("<table id='admTable'></table>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	if(czjgFirst){
		    		html.push("<div class='pane' style='display:block;'>");
		    	}else{
		    		html.push("<div class='pane'>");
		    	}
		    	html.push("<div class='sysOutLeft'>");
		    	html.push("<span>请选择</span>");
		    	html.push("<div class='sysLeft'>");
		    	html.push("<div class='noticelefttree' style='overflow:auto;'>");
		    	html.push("<ul id='tree2' class='ztree' style='overflow:auto;'></ul>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("<div class='sysOutRight'>");
		    	html.push("<span>已选择</span>");
		    	html.push("<div class='sysRight'  id='departRight'>");
		    	html.push("<div style='height:400px;margin-top: 10px;'>");
		    	html.push("<table id='departTable'></table>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	if(czyhFirst){
		    		html.push("<div class='pane' style='display:block;'>");
		    	}else{
		    		html.push("<div class='pane'>");
		    	}
		    	html.push("<div class='sysOutLeft'>");
		    	html.push("<span>请选择</span>");
		    	html.push("<div class='sysLeft'>");
		    	html.push("<div class='noticelefttree' style='overflow:auto;'>");
		    	html.push("<ul id='tree3' class='ztree' style='overflow:auto;'></ul>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("<div class='sysOutRight'>");
		    	html.push("<span>已选择</span>");
		    	html.push("<div class='sysRight'  id='userRight'>");
		    	html.push("<div style='height:400px;margin-top: 10px;'>");
		    	html.push("<table id='userTable'></table>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	if(czjsFirst){
		    		html.push("<div class='pane' style='display:block;'>");
		    	}else{
		    		html.push("<div class='pane'>");
		    	}
		    	html.push("<div class='sysOutLeft'>");
		    	html.push("<span>请选择</span>");
		    	html.push("<div class='sysLeft'>");
		    	html.push("<div class='noticelefttree' style='overflow:auto;'>");
		    	html.push("<ul id='tree4' class='ztree' style='overflow:auto;'></ul>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("<div class='sysOutRight'>");
		    	html.push("<span>已选择</span>");
		    	html.push("<div class='sysRight'  id='roleRight'>");
		    	html.push("<div style='height:400px;margin-top: 10px;'>");
		    	html.push("<table id='roleTable'></table>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div>");
		    	html.push("</div><button class='btn_style' onclick='saveAcrossAutho()' style='margin-top:85px;margin-left:660px;'>保存</button> <button class='btn_style' onclick='clearAcrossChoose()'>清空</button> <button class='btn_style' onclick='layerClose()'>关闭</button>");
		    	html.push("</div>");
		    	layer.open({
		    	    type: 1, //page层
		    	    skin: 'layui-layer-lan', //加上边框
		    	    area: ['1000px', '660px'], //宽高
		    	    title:['授权（横向）','font-size:18px;'],
		    	    shade:0.1, //遮罩透明度
		    	    closeBtn: 1, //不显示关闭按钮
		    	    scrollbar:'no',
		    	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
		    	    shift: 0, //0-6的动画形式，-1不开启
		    	    content: html.join("")
		    	});
		    	
		    	//tab页切换
		    	$('.tabPanel ul li').click(function(){
		    		$(this).addClass('hit').siblings().removeClass('hit');
		    		$('.panes>div:eq('+$(this).index()+')').show().siblings().hide();
		    		if($(this).index() == '1'){
		    			if(!firstFlag){
		    				loadNoticeLeftTree($(this).index());
		    				loadAuthoedData($(this).index(),$(this).index()+1,noticId,2);
		    			}
		    			firstFlag = true;
		    		}else if($(this).index() == '2'){
		    			if(!secondFlag){
		    				loadNoticeLeftTree($(this).index());
		    				loadAuthoedData($(this).index(),$(this).index()+1,noticId,2);
		    			}
		    			secondFlag = true;
		    		}else if($(this).index() == '3'){
		    			if(!thirdFlag){
		    				loadNoticeLeftTree($(this).index());
		    				loadAuthoedData($(this).index(),$(this).index()+1,noticId,2);
		    			}
		    			thirdFlag = true;
		    		}else if($(this).index() == '4'){
		    			if(!fourthFlag){
		    				loadNoticeLeftTree($(this).index());
		    				loadAuthoedData($(this).index(),$(this).index()+1,noticId,2);
		    			}
		    			fourthFlag = true;
		    		}
		    	});
		    	//加载第一页签对应左侧树--业务系统
		    	loadNoticeLeftTree(firstTab);
		    	//若通知公告已授权 调用该方法进行数据回填
		    	loadAuthoedData(firstTab,firstTab+1,noticId,2);
		    	if(firstTab == '1'){
		    		firstFlag = true;
		    	}else if(firstTab == '2'){
		    		secondFlag = true;
		    	}else if(firstFlag == '3'){
		    		thirdFlag = true;
		    	}else if(firstFlag == '4'){
		    		fourthFlag = true;
		    	}
			}
	    }
	    
	    /**
	     * index 加载左侧树的排序序号从左至右为0-4
	     * type 授权类型 (1、业务系统 2、单位 3、财政机构 4、财政用户 5、财政角色 6、区划)
	     * item 选择的单条通知公告
	     * sendType 发布类型横向 1、纵向 2、横向
	     */
	    function loadAuthoedData(index,type,noticGuid,sendType){
	    	//授权类型
	    	var directType = type;
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/getCheckdData.do");
	    	$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:{noticGuid:noticGuid,sendType:sendType,directType:directType},
				success:function(succ){
					if(succ.length >0){
						//横向同步
			    		var params = {noticGuid:noticGuid,sendType:sendType,directType:directType,tokenid:tokenid};
						if(sendType == '2'){
							//根据方法参数中的左侧树排序序号  回填左侧树信息
							if(index == '0'){//业务系统左侧树信息回填
								//创建表格
								createSysTable(params);
								//数据回填
								showRightInfo0(succ,'0');
								
							}else if(index == '1'){//单位左侧树信息回填
								//创建表格
								createAdmTable(params);
								//数据回填
								showRightInfo1(succ);
							}else if(index == '2'){//处室左侧树信息回填
								//创建表格
								createDepartTable(params);
								//数据回填
								showRightInfo2(succ);
							}else if(index == '3'){//用户左侧树信息回填
								//创建表格
								createUserTable(params);
								//数据回填
								showRightInfo3(succ);
								
							}else if(index == '4'){//角色左侧树信息回填
								//创建表格
								createRoleTable(params);
								//数据回填
								showRightInfo4(succ,4);
							}
							//纵向同步
						}else if(sendType == '1'){
							if(index == '0'){//区划左侧树信息回填
								//创建表格
								createProvinceTable(params);
								//数据回填
								showRightInfoProvince(succ);
							}else if(index == '1'){//角色左侧树数据回填  同横向中角色方法
								//创建表格
								createRoleTable(params);
								//数据回填
								showRightInfo4(succ,1);
							}else if(index == '2'){//业务系统左侧树信息回填  同横向中业务系统方法
								//创建表格
								createSysTable(params);
								//数据回填
								showRightInfo0(succ,'2');
							}
						}
					}
				},
				error:function(err){
					
				}
			});
	    }
	    
	    
	    /**
	     * 通知公告--业务系统 已授权时  回填数据  并将数据展示到右侧div中
	     * @param rs 授权数据guid集合
	     * @param index 区分横纵向序号  横向中序号为0  纵向序号为2
	     * @returns
	     */
	    function showRightInfo0(rs,index){
	    	var tree = $.fn.zTree.getZTreeObj("tree"+index);
	    	//getNodes仅能获取根节点  
	    	var rootNodes = tree.getNodes();
	    	//获取根节点下 业务系统分类集合
	    	var nodes = rootNodes[0].children;
	    	for(var i=0;i<nodes.length;i++){
	    		//系统分类
	    		var child = nodes[i];
	    		if(child != undefined){
	    			var sChild = child.children;
	    			if(sChild != undefined && sChild.length > 0){
	    				for(var j=0;j<sChild.length;j++){
	    					//分别判断左侧树guid是否处于授权guid集合中，若存在设置该节点选中
	    					$.each(rs, function(index, value) {   
	    						if(value == sChild[j].GUID){
	    							sChild[j].checked = true;
	    						}
	    					});
	    				}
	    			}
	    		}
	    	}
	    }
	    /**
	     * 单位1  处室2  用户3  角色4 数据回填公用方法  用于设置节点状态选中 返回授权guid集合长度
	     * @param rs 授权guid集合
	     * @param index 
	     * @returns
	     */
	    function commonShowRightInfo(rs,index){
	    	var tree = $.fn.zTree.getZTreeObj("tree"+index);
	    	//获取左侧树全部节点 
	    	var nodes = tree.transformToArray(tree.getNodes());
	    	if(nodes != null && nodes.length > 0){
	    		for(var i=0;i<nodes.length;i++){
	    			//判断节点标志位flag为1时  才为需要统计的节点（例如左侧树结构为区划-角色  仅用统计角色节点）
	    			if(nodes[i].FLAG == '1'){
	    				//判断该节点是否处于授权guid集合中
	    				$.each(rs, function(index, value) {   
	    					if(value == nodes[i].GUID){
	    						nodes[i].checked = true;
	    					}
	    				});
	    			}
	    		}
	    	}
	    }
	    /**
	     * 回填区划左侧树
	     * @param rs
	     * @returns
	     */
	    function showRightInfoProvince(rs){
	    	var tree = $.fn.zTree.getZTreeObj("tree0");
	    	var nodes = tree.transformToArray(tree.getNodes());
	    	if(nodes != null && nodes.length > 0){
	    		for(var i=0;i<nodes.length;i++){
	    			$.each(rs, function(index, value) {   
	    				if(value == nodes[i].GUID){
	    					nodes[i].checked = true;
	    				}
	    			})
	    		}
	    	}
	    }
	    /**
	     * 单位左侧树 数据回填
	     * @param rs 授权guid集合
	     * @returns
	     */
	    function showRightInfo1(rs){
	    	commonShowRightInfo(rs,1);
	    }

	    /**
	     * 处室左侧树 数据回填
	     * @param rs
	     * @returns
	     */
	    function showRightInfo2(rs){
	    	commonShowRightInfo(rs,2);
	    }

	    /**
	     * 用户左侧树 数据回填
	     * @param rs
	     * @returns
	     */
	    function showRightInfo3(rs){
	    	commonShowRightInfo(rs,3);
	    }

	    /**
	     * 角色左侧树 数据回填
	     * @param rs
	     * @returns
	     */
	    function showRightInfo4(rs,index){
	    	commonShowRightInfo(rs,index);
	    }
	    /**
	     * 单位1  处室2  用户3  角色4  左侧树选中公用方法 用于统计选中节点数量
	     * @returns 选中有效节点数量
	     */
	    function commonzTreeOnChoose(index){
	    	var tree = $.fn.zTree.getZTreeObj("tree"+index);
	    	var nodes = tree.getCheckedNodes();
	    	var num = 0;
	    	for(var i=0;i<nodes.length;i++){
	    		if(nodes[i].FLAG == '1'){
	    			num += 1;
	    		}
	    	}
	    	return num;
	    }

	    //加载通知公告横向授权左侧树
	    function loadNoticeLeftTree(index){
	    	var setting = {
	    			//展示数据
	    			data:{
	    					key:{
	    						name: "NAME"
	    					},
	    					simpleData: {
	    						enable: true,
	    						idKey: "GUID",
	    						pIdKey: "PARENTID",
	    						rootPId: 'root'
	    					}
	    			},
	    			view:{
	    					selectedMulti: false,
	    					showIcon:true
	    			},
	    			//复选框
	    			check:{
	    					enable : true,
	    					chkboxType : {
	    						"Y" : "s",
	    						"N" : "s"
	    					},
	    					chkDisabledInherit : false
	    			}
	    	}
	    	
	    	//考虑到左侧树数据回填时需要树结构已存在  此处使用同步加载
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/queryNoticeTree.do");
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		dataType:'json',
	    		data:{index:index,tokenid:tokenid},
	    		async:false,
	    		success:function(succ){
	    			if(index == '0'){
	    				zTreeObj = $.fn.zTree.init($("#tree0"), setting, succ);
	    			}else if(index == '1'){
	    				zTreeObj = $.fn.zTree.init($("#tree1"), setting, succ);
	    			}else if(index == '2'){
	    				zTreeObj = $.fn.zTree.init($("#tree2"), setting, succ);
	    			}else if(index == '3'){
	    				zTreeObj = $.fn.zTree.init($("#tree3"), setting, succ);
	    			}else if(index == '4'){
	    				zTreeObj = $.fn.zTree.init($("#tree4"), setting, succ);
	    			}
	    		},
	    		error:function(err){
	    			
	    		}
	    	});
	    }

	    //清空tab页所有选中状态
	    function clearAcrossChoose(){
	    	//业务系统
	    	var sysNodes = '';
	    	if(zeroFlag){
	    		var tree0 = $.fn.zTree.getZTreeObj("tree0");
	    		sysNodes = tree0.getCheckedNodes();
	    	}
	    	var agencyNodes ='';
	    	var deapartNodes ='';
	    	var userNodes ='';
	    	var roleNodes ='';
	    	//判断授权后再次授权时，如果未做任何操作，直接清空，判断是否有授权信息   update by zhouqin 2017-07-08
	    	var sfsq = removeAutoedInfo(noticId,'2')
	    	//单位
	    	if(firstFlag){
	    		var tree1 = $.fn.zTree.getZTreeObj("tree1");
	    		agencyNodes = tree1.getCheckedNodes();
	    	}else{
	    		if(sfsq){
	    			agencyNodes = '已授权';
	    		}
	    	}
	    	//财政机构
	    	if(secondFlag){
	    		var tree2 = $.fn.zTree.getZTreeObj("tree2");
	    		deapartNodes = tree2.getCheckedNodes();
	    	}else{
	    		if(sfsq){
	    			deapartNodes = '已授权';
	    		}
	    	}
	    	//财政用户
	    	if(thirdFlag){
	    		var tree3 = $.fn.zTree.getZTreeObj("tree3");
	    		userNodes = tree3.getCheckedNodes();
	    	}else{
	    		if(sfsq){
	    			userNodes = '已授权';
	    		}
	    	}
	    	//财政角色
	    	if(fourthFlag){
	    		var tree4 = $.fn.zTree.getZTreeObj("tree4");
	    		roleNodes = tree4.getCheckedNodes();
	    	}else{
	    		if(sfsq){
	    			roleNodes = '已授权';
	    		}
	    	}
	    	//未选择授权信息
	    	if(sysNodes == '' && agencyNodes == '' && deapartNodes == '' && userNodes == '' && roleNodes == ''){
	    		alert("当前未授权！");
	    		return;
	    	}
	    	if(confirm("确定删除全部横向授权信息？")){
	    		//清空操作时,不走后台删除授权信息  update by zhouqin 2017-06-29
	    		/*if(!removeAutoedInfo(noticId,'2')){
		    		alert("清空授权信息失败！");
		    		return;
		    	}*/
		    	$("#sysRight").empty();
		    	$("#admRight").empty();
		    	$("#departRight").empty();
		    	$("#userRight").empty();
		    	$("#roleRight").empty();
		    	//清空第一个tab页左侧树选项
		    	if(zeroFlag){
					var treeObj = $.fn.zTree.getZTreeObj("tree0");
					treeObj.checkAllNodes(false);
				}
		    	if(firstFlag){
		    		var treeObj = $.fn.zTree.getZTreeObj("tree1");
		    		treeObj.checkAllNodes(false);
		    	}
		    	if(secondFlag){
		    		var treeObj = $.fn.zTree.getZTreeObj("tree2");
		    		treeObj.checkAllNodes(false);
		    	}
		    	if(thirdFlag){
		    		var treeObj = $.fn.zTree.getZTreeObj("tree3");
		    		treeObj.checkAllNodes(false);
		    	}
		    	if(fourthFlag){
		    		var treeObj = $.fn.zTree.getZTreeObj("tree4");
		    		treeObj.checkAllNodes(false);
		    	}
		    	alert("已清空全部横向授权信息，请重新授权！");
	    	}
	    }

	    /**
	     * 清空时 清楚授权表中对应数据
	     * @param noticguid
	     * @param sendtype
	     */
	    function removeAutoedInfo(noticId,sendType){
	    	var flag = false;
	    	//清除授权
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/clearAcross.do");
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		async:false,
	    		data:{
	    				noticId:noticId,
	    				sendType:sendType
	    		},
	    		success:function(succ){
	    			if(succ.success == "success"){
	    				flag =  true;
	    			}else{
	    				flag = false;
	    			}
	    		},
	    		error:function(err){
	    			flag = false;
	    		}
	    	});
	    	return flag;
	    }
	    
	    //保存授权信息
	    function saveAcrossAutho(){
	    	var params = {};
	    	//系统guid,以逗号分隔
	    	var sysGuids = '';
	    	if(zeroFlag){
	    		var tree0 = $.fn.zTree.getZTreeObj("tree0");
		    	var sysNodes = tree0.getCheckedNodes();
		    	for(var i=0;i<sysNodes.length;i++){
		    		if(sysNodes[i].level == '2'){
		    			sysGuids += sysNodes[i].GUID+",";
		    		}
		    	}
		    	if(sysGuids != "" && sysGuids.length > 0){
		    		sysGuids = sysGuids.substring(0,sysGuids.length-1);
		    	}
	    	}
	    	var directTypeStr = "'1',";
	    	//单位guid,以逗号分隔
	    	var agencyGuids = '';
	    	if(firstFlag){
	    		var tree1 = $.fn.zTree.getZTreeObj("tree1");
		    	var agencyNodes = tree1.getCheckedNodes();
		    	for(var i=0;i<agencyNodes.length;i++){
		    		if(agencyNodes[i].FLAG == '1'){
		    			agencyGuids += agencyNodes[i].GUID+",";
		    		}
		    	}
		    	if(agencyGuids != "" && agencyGuids.length > 0){
		    		agencyGuids = agencyGuids.substring(0,agencyGuids.length-1);
		    	}
		    	directTypeStr = directTypeStr + "'2',";
	    	}
	    	//财政机构guid,以逗号分隔
	    	var deapartGuids = '';
	    	if(secondFlag){
	    		var tree2 = $.fn.zTree.getZTreeObj("tree2");
	    		var deapartNodes = tree2.getCheckedNodes();
	    		for(var i=0;i<deapartNodes.length;i++){
	    			if(deapartNodes[i].FLAG == '1'){
	    				deapartGuids += deapartNodes[i].GUID+",";
	    			}
	    		}
	    		if(deapartGuids != "" && deapartGuids.length > 0){
	    			deapartGuids = deapartGuids.substring(0,deapartGuids.length-1);
	    		}
	    		directTypeStr = directTypeStr + "'3',";
	    	}
	    	//用户guid,以逗号分隔
	    	var userGuids = '';
	    	if(thirdFlag){
	    		var tree3 = $.fn.zTree.getZTreeObj("tree3");
	    		var userNodes = tree3.getCheckedNodes();
	    		for(var i=0;i<userNodes.length;i++){
	    			if(userNodes[i].FLAG == '1'){
	    				userGuids += userNodes[i].GUID+",";
	    			}
	    		}
	    		if(userGuids != "" && userGuids.length > 0){
	    			userGuids = userGuids.substring(0,userGuids.length-1);
	    		}
	    		directTypeStr = directTypeStr + "'4',";
	    	}
	    	//角色guid,以逗号分隔
	    	var roleGuids = '';
	    	if(fourthFlag){
	    		var tree4 = $.fn.zTree.getZTreeObj("tree4");
	    		var roleNodes = tree4.getCheckedNodes();
	    		for(var i=0;i<roleNodes.length;i++){
	    			if(roleNodes[i].FLAG == '1'){
	    				roleGuids += roleNodes[i].GUID+",";
	    			}
	    		}
	    		if(roleGuids != "" && roleGuids.length > 0){
	    			roleGuids = roleGuids.substring(0,roleGuids.length-1);
	    		}
	    		directTypeStr = directTypeStr + "'5',";
	    	}
	    	
	    	//未选择授权信息无法保存
	    	if(sysGuids == '' && roleGuids== '' && deapartGuids == '' && userGuids == '' && agencyGuids == ''){
	    		alert("请选择授权信息！");
	    		return;
	    	}
	    	directTypeStr = directTypeStr.substring(0, directTypeStr.length - 1);
	    	
	    	var sendType = 2;
	    	var publocations = $("#publocations").val();
	    	if(publocations == "" || publocations == null){
				$("#publocations").val(opublocation);
				publocations = $("#publocations").val();
	    	}
	    	//加载层
	    	var layerLoad = layer.load(1, {
	    		  shade: [0.1,'#fff'] //0.1透明度的白色背景
	    		});
	    	//开始授权
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/empower.do");
	    	$.ajax({
				url: url,
				type:'post',
				data:{
						noticId:noticId,
						sysGuids:sysGuids,
						agencyGuids:agencyGuids,
						deapartGuids:deapartGuids,
						userGuids:userGuids,
						roleGuids:roleGuids,
						sendType:sendType,
						publocations:publocations,
						directTypeStr:directTypeStr,
						userguid:userguid
				},
				success:function(succ){
					layer.close(layerLoad);
					if(succ.success == "success"){
						alert("授权成功!");
						//授权成功后，再去更新其他用户通知公告的缓存
						refreshCacheByAcrossAutho(noticId,sysGuids,agencyGuids,deapartGuids,userGuids,roleGuids,publocations,sendType,userguid);
					}else{
						alert("授权失败!");
						return ;
					}
				},
				error:function(err){
					layer.close(layerLoad);
					alert("授权失败!");
					return ;
				}
			});
	    }
	    
	    //授权成功后，再去更新其他用户通知公告的缓存  add by zhouqin 2017-07-17
	    function refreshCacheByAcrossAutho(noticId,sysGuids,agencyGuids,deapartGuids,userGuids,roleGuids,pubLocation,sendType,userguid){
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/updNoticCache.do");
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		data:{
	    			noticId:noticId,
	    			sysGuids:sysGuids,
	    			agencyGuids:agencyGuids,
	    			deapartGuids:deapartGuids,
	    			userGuids:userGuids,
	    			roleGuids:roleGuids,
	    			publocations:pubLocation,
	    			sendType:sendType,
	    			userguid:userguid
	    	    },
	    		success:function(succ){
	    		},
	    		error:function(err){
	    		}
	    	});
	    }

	    //关闭窗口
	    function layerClose(){
	    	layer.closeAll();
	    }

	    //加载通知公告纵向授权左侧树
	    function loadNoticeDownLeftTree(index){
	    	var setting = {
	    			data:{
	    					key:{
	    						name: "NAME"
	    					},
	    					simpleData: {
	    						enable: true,
	    						idKey: "GUID",
	    						pIdKey: "PARENTID",
	    						rootPId: 'root'
	    					}
	    			},
	    			view:{
	    					selectedMulti: false,
	    					showIcon:true
	    			},
	    			check:{
	    					enable : true,
	    					chkboxType : {
	    						"Y" : "s",
	    						"N" : "s"
	    					},
	    					chkDisabledInherit : false
	    			}	
	    	}
	    	
	    	//加载纵向左侧树信息  为保证已授权信息能够回填  此处采用同步方式调用  加载出完整左侧树
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/queryNoticeDownTree.do");
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		dataType:'json',
	    		data:{index:index,tokenid:tokenid},
	    		async:false,
	    		success:function(succ){
	    			if(index == '0'){
	    				zTreeObj = $.fn.zTree.init($("#tree0"), setting, succ);
	    			}else if(index == '1'){
	    				zTreeObj = $.fn.zTree.init($("#tree1"), setting, succ);
	    			}else if(index == '2'){
	    				zTreeObj = $.fn.zTree.init($("#tree2"), setting, succ);
	    			}
	    		},
	    		error:function(err){
	    			
	    		}
	    	});
	    }


	    //纵向授权
	    function sendDownNotic(){
	    	if (noticId == undefined||noticId==null||noticId==''||noticId=='null') {
				alert("请先保存通知公告再进行授权！");
				return;
			}
			if(confirm("请确认页面数据已保存再授权！")){
		    	//打开弹框时 设置tab页打开状态为false
				zeroFlag = false;//判断是否加载第一个页签
		    	firstFlag = false;
		    	secondFlag = false;
		    	
		    	//控制页签显示隐藏变量
		    	var czqh = false;//财政区划
		    	var czjs = false;//财政角色
		    	var ywxt = false;//业务系统
		    	
		    	var czqhFirst = false;//区划为首个页签
		    	var czjsFirst = false;//财政角色为首
		    	var ywxtFirst = false;//业务系统为首
		    	
		    	//获取当前用户拥有权限的页签及页签下标
		    	var authedTabs = queryUserNoticTabs('1');
		    	//首个加载的页签下标
		    	var firstTab = 0;
		    	if(authedTabs != null && authedTabs.length > 0){
		    		for(var i=0;i<authedTabs.length;i++){
		    			//获取首个加载的页签
		    			if(i == 0){
		    				firstTab = authedTabs[i].ORDERID;
		    				if(authedTabs[i].TABGUID.indexOf('vczqhtabguid') != -1){
		    					czqhFirst = true;
		    					czqh = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('vczjstabguid') != -1){
		    					czjsFirst = true;;
		    					czjs = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('vywxttabguid') != -1){
		    					ywxtFirst = true;
		    					ywxt = true;
		    				}
		    			}else{
		    				if(authedTabs[i].TABGUID.indexOf('vczqhtabguid') != -1){
		    					czqh = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('vczjstabguid') != -1){
		    					czjs = true;
		    				}else if(authedTabs[i].TABGUID.indexOf('vywxttabguid') != -1){
		    					ywxt = true;
		    				}
		    			}
		    		}
		    	}else{
		    		alert("请配置通知公告发布渠道页签！");
		    		return;
		    	}
		    	
		    	//若加载显示菜单中包括财政区划 
		    	if(czqh){
		    		zeroFlag = true;
		    	}
		    	
		    	var html = [];
		    	html.push("<div class='tabPanel'>                                                            ");
		    	html.push("    <ul class='tabUl'>                                                            ");
		    	if(czqh){
		    		html.push("        <li class='hit'>财政区划                                                  ");
		    		html.push("        </li>                                                                     ");
		    	}else{
		    		html.push("        <li  class='tabHide'>财政区划                                                  ");
		    		html.push("        </li>                                                                     ");
		    	}
		    	if(czjs){
		    		if(czjsFirst){
		    			html.push("        <li class='hit'>财政角色                                                              ");
		    			html.push("        </li>                                                                     ");
		    		}else{
		    			html.push("        <li>财政角色                                                              ");
		    			html.push("        </li>                                                                     ");
		    		}
		    	}else{
		    		html.push("        <li class='tabHide'>财政角色                                                              ");
		    		html.push("        </li>                                                                     ");
		    	}
		    	if(ywxt){
		    		if(ywxtFirst){
		    			html.push("        <li class='hit'>业务系统                                                              ");
		    			html.push("        </li>                                                                     ");
		    		}else{
		    			html.push("        <li>业务系统                                                              ");
		    			html.push("        </li>                                                                     ");
		    		}
		    	}else{
		    		html.push("        <li  class='tabHide'>业务系统                                                              ");
		    		html.push("        </li>                                                                     ");
		    	}
		    	html.push("    </ul>                                                                         ");
		    	html.push("    <div class='panes'>                                                           ");
		    	if(czqhFirst){
		    		html.push("<div class='pane' style='display:block;'>");
		    	}else{
		    		html.push("<div class='pane'>");
		    	}
		    	html.push("            <div class='sysOutLeft'>                                              ");
		    	html.push("                <span>请选择</span>                                               ");
		    	html.push("                <div class='sysLeft'>                                             ");
		    	html.push("                    <div class='noticelefttree' style='overflow:auto;'>           ");
		    	html.push("                        <ul id='tree0' class='ztree' style='overflow:auto;'></ul> ");
		    	html.push("                    </div>                                                        ");
		    	html.push("                </div>                                                            ");
		    	html.push("            </div>                                                                ");
		    	html.push("            <div class='sysOutRight'>                                             ");
		    	html.push("                <span>已选择</span>                                               ");
		    	html.push("                <div class='sysRight'  id='provinceRight'>                                            ");
		    	html.push("                    <div style='height:400px;margin-top: 10px;'>                  ");
		    	html.push("                        <table id='provinceTable'></table>                        ");
		    	html.push("                    </div>                                                        ");
		    	html.push("                </div>                                                            ");
		    	html.push("            </div>                                                                ");
		    	html.push("        </div>                                                                    ");
		    	if(czjsFirst){
		    		html.push("<div class='pane' style='display:block;'>");
		    	}else{
		    		html.push("<div class='pane'>");
		    	}
		    	html.push("            <div class='sysOutLeft'>                                              ");
		    	html.push("                <span><span>请选择</span></span>                                  ");
		    	html.push("                <div class='sysLeft'>                                             ");
		    	html.push("                    <div class='noticelefttree' style='overflow:auto;'>           ");
		    	html.push("                        <ul id='tree1' class='ztree' style='overflow:auto;'></ul> ");
		    	html.push("                    </div>                                                        ");
		    	html.push("                </div>                                                            ");
		    	html.push("            </div>                                                                ");
		    	html.push("            <div class='sysOutRight'>                                             ");
		    	html.push("                <span>已选择</span>                                               ");
		    	html.push("                <div class='sysRight'  id='roleRight'>                                            ");
		    	html.push("                    <div style='height:400px;margin-top: 10px;'>                  ");
		    	html.push("                        <table id='roleTable'></table>                            ");
		    	html.push("                    </div>                                                        ");
		    	html.push("                </div>                                                            ");
		    	html.push("            </div>                                                                ");
		    	html.push("        </div>                                                                    ");
		    	if(ywxtFirst){
		    		html.push("<div class='pane' style='display:block;'>");
		    	}else{
		    		html.push("<div class='pane'>");
		    	}
		    	html.push("            <div class='sysOutLeft'>                                              ");
		    	html.push("                <span><span>请选择</span></span>                                  ");
		    	html.push("                <div class='sysLeft'>                                             ");
		    	html.push("                    <div class='noticelefttree' style='overflow:auto;'>           ");
		    	html.push("                        <ul id='tree2' class='ztree' style='overflow:auto;'></ul> ");
		    	html.push("                    </div>                                                        ");
		    	html.push("                </div>                                                            ");
		    	html.push("            </div>                                                                ");
		    	html.push("            <div class='sysOutRight'>                                             ");
		    	html.push("                <span>已选择</span>                                               ");
		    	html.push("                <div class='sysRight'  id='sysRight'>                                            ");
		    	html.push("                    <div style='height:400px;margin-top: 10px;'>                  ");
		    	html.push("                        <table id='sysTable'></table>                             ");
		    	html.push("                    </div>                                                        ");
		    	html.push("                </div>                                                            ");
		    	html.push("            </div>                                                                ");
		    	html.push("        </div>                                                                    ");
		    	html.push("    </div>                                                                        ");
		    	html.push("<button class='btn_style' onclick='saveDownAutho()' style='margin-top:85px;margin-left:660px;'>保存</button><button class='btn_style' onclick='clearDownChoose()'>清空</button><button class='btn_style' onclick='layerClose()'>关闭</button>");
		    	html.push("</div>                                                                            ");
		    	layer.open({
		    	    type: 1, //page层
		    	    skin: 'layui-layer-lan', //加上边框
		    	    area: ['1000px', '660px'], //宽高
		    	    title:['授权（纵向）','font-size:18px;'],
		    	    shade:0.1, //遮罩透明度
		    	    closeBtn: 1, //不显示关闭按钮
		    	    scrollbar:'no',
		    	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
		    	    shift: 0, //0-6的动画形式，-1不开启
		    	    content: html.join("")
		    	});
		    	
		    	//tab页切换
		    	$('.tabPanel ul li').click(function(){
		    		$(this).addClass('hit').siblings().removeClass('hit');
		    		$('.panes>div:eq('+$(this).index()+')').show().siblings().hide();
		    		if($(this).index() == '1'){
		    			if(!firstFlag){
		    				loadNoticeDownLeftTree($(this).index());
		    				loadAuthoedData(1,5,noticId,1);
		    			}
		    			firstFlag = true;
		    		}else if($(this).index() == '2'){
		    			if(!secondFlag){
		    				loadNoticeDownLeftTree($(this).index());
		    				loadAuthoedData(2,1,noticId,1);
		    			}
		    			secondFlag = true;
		    		}
		    	});
		    	
		    	//加载区划左侧树
		    	loadNoticeDownLeftTree(firstTab);
		    	//区划左侧树信息回填
		    	var downType = 6;
		    	if(firstTab == 0){
		    		downType = 6;
		    	}else if(firstTab == 1){
		    		downType = 5;
		    		firstFlag = true;
		    	}else if(firstTab == 2){
		    		downType = 1;
		    		secondFlag = true;
		    	}
		    	loadAuthoedData(firstTab,downType,noticId,1);
	    	}
	    }

	    //清空tab页所有选中状态
	    function clearDownChoose(){
	    	//财政区划
	    	var provinceNodes = '';
	    	if(zeroFlag){
	    		var tree0 = $.fn.zTree.getZTreeObj("tree0");
	    		provinceNodes = tree0.getCheckedNodes();
	    	}
	    	var sysNodes = '';
	    	var roleNodes ='';
	    	//判断授权后再次授权时，如果未做任何操作，直接清空，判断是否有授权信息   update by zhouqin 2017-07-08
	    	var sfsq = removeAutoedInfo(noticId,'1')
	    	//财政角色
	    	if(firstFlag){
	    		var tree1 = $.fn.zTree.getZTreeObj("tree1");
	    		roleNodes = tree1.getCheckedNodes();
	    	}else{
	    		if(sfsq){
	    			roleNodes = '已授权';
	    		}
	    	}
	    	//业务系统
	    	if(secondFlag){
	    		var tree2 = $.fn.zTree.getZTreeObj("tree2");
	    		sysNodes = tree2.getCheckedNodes();
	    		if(sfsq){
	    			sysNodes = '已授权';
	    		}
	    	}
	    	//未选择授权信息
	    	if(provinceNodes == '' && sysNodes == '' && roleNodes == ''){
	    		alert("当前未授权！");
	    		return;
	    	}
	    	if(confirm("确定删除全部纵向授权信息？")){
	    		//清空操作时,不走后台删除授权信息  update by zhouqin 2017-06-29
	    		/*if(!removeAutoedInfo(noticId,'1')){
		    		alert("清空授权信息失败！");
		    		return;
		    	}*/
		    	$("#sysRight").empty();
		    	$("#roleRight").empty();
		    	$("#provinceRight").empty();
		    	//清空第一个tab页左侧树选项
		    	//清空第一个tab页左侧树选项
				if(zeroFlag){
					var treeObj = $.fn.zTree.getZTreeObj("tree0");
					treeObj.checkAllNodes(false);
				}
		    	if(firstFlag){
		    		var tree1 = $.fn.zTree.getZTreeObj("tree1");
		    		tree1.checkAllNodes(false);
		    	}
		    	if(secondFlag){
		    		var tree2 = $.fn.zTree.getZTreeObj("tree2");
		    		tree2.checkAllNodes(false);
		    	}
		    	alert("已清空全部纵向授权信息，请重新授权！");
	    	}
	    }

	    function saveDownAutho(){
	    	//区划
	    	var provinceGuids = '';
	    	if(zeroFlag){
	    		var tree0 = $.fn.zTree.getZTreeObj("tree0");
		    	var provinceNodes = tree0.getCheckedNodes();
		    	for(var i=0;i<provinceNodes.length;i++){
		    		provinceGuids += provinceNodes[i].GUID+",";
		    	}
		    	if(provinceGuids != "" && provinceGuids.length > 0){
		    		provinceGuids = provinceGuids.substring(0,provinceGuids.length-1);
		    	}
	    	}
	    	var directTypeStr = "'6',";
	    	//角色guid,以逗号分隔
	    	var roleGuids = '';
	    	if (firstFlag) {
	    		var tree1 = $.fn.zTree.getZTreeObj("tree1");
		    	var roleNodes = tree1.getCheckedNodes();
		    	for(var i=0;i<roleNodes.length;i++){
		    		if(roleNodes[i].FLAG == '1'){
		    			roleGuids += roleNodes[i].GUID+",";
		    		}
		    	}
		    	if(roleGuids != "" && roleGuids.length > 0){
		    		roleGuids = roleGuids.substring(0,roleGuids.length-1);
		    	}
		    	directTypeStr = directTypeStr + "'5',";
			}
	    	
	    	//系统guid,以逗号分隔
	    	var sysGuids = '';
	    	if (secondFlag) {
	    		var tree0 = $.fn.zTree.getZTreeObj("tree2");
		    	var sysNodes = tree0.getCheckedNodes();
		    	for(var i=0;i<sysNodes.length;i++){
		    		if(sysNodes[i].level == '2'){
		    			sysGuids += sysNodes[i].GUID+",";
		    		}
		    	}
		    	if(sysGuids != "" && sysGuids.length > 0){
		    		sysGuids = sysGuids.substring(0,sysGuids.length-1);
		    	}
		    	directTypeStr = directTypeStr + "'1',";
			}
	    	
	    	//未选择授权信息无法保存
	    	if(sysGuids == '' && roleGuids== '' && provinceGuids == ''){
	    		alert("请选择授权信息！");
	    		return;
	    	}
	    	directTypeStr = directTypeStr.substring(0, directTypeStr.length - 1);
	    	//
	    	var sendType = 1;
	    	var publocations = $("#publocations").val();
	    	if(publocations == "" || publocations == null){
				$("#publocations").val(opublocation);
				publocations = $("#publocations").val();
	    	}
	    	//加载层
	    	var layerLoad = layer.load(1, {
	    		  shade: [0.1,'#fff'] //0.1透明度的白色背景
	    		});
	    	//开始授权
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/empower.do");
	    	$.ajax({
				url:url,
				type:'post',
				data:{
						noticId:noticId,
						sysGuids:sysGuids,
						roleGuids:roleGuids,
						provinceGuids:provinceGuids,
						sendType:sendType,
						publocations:publocations,
						directTypeStr:directTypeStr,
						userguid:userguid
				},
				success:function(succ){
					layer.close(layerLoad);
					if(succ.success == "success"){
						alert("授权成功!");
						//授权成功后，再去更新其他用户通知公告的缓存
						refreshCacheByDownAutho(noticId,sysGuids,roleGuids,provinceGuids,publocations,sendType,userguid);
					}else{
						alert("授权失败!");
						return ;
					}
				},
				error:function(err){
					layer.close(layerLoad);
					alert("授权失败!");
					return ;
				}
			});
	    }
	    
	  //授权成功后，再去更新其他用户通知公告的缓存  add by zhouqin 2017-07-17
	    function refreshCacheByDownAutho(noticId,sysGuids,roleGuids,provinceGuids,pubLocation,sendType,userguid){
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/updNoticCache.do");
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		data:{
	    			noticId:noticId,
	    			sysGuids:sysGuids,
	    			roleGuids:roleGuids,
	    			provinceGuids:provinceGuids,
	    			publocations:pubLocation,
	    			sendType:sendType,
	    			userguid:userguid
	    	    },
	    		success:function(succ){
	    		},
	    		error:function(err){
	    		}
	    	});
	    }
	    

	    /**
	     * 展示选中区划表格
	     * @param param
	     */
	    function createProvinceTable(param){
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/loadAuthoedData.do");
	    	COMMONJS.createNoticTable("provinceTable",url,param, [{
	            field : 'RN',
	            title : '序号',
	            width : 50,
	            align : 'center'
	    	}, {
	            field : 'DATAGUID',
	            title : 'GUID',
	            width : 100,
	            align : 'center',
	            hidden : true
	            
	        }, {
	            field : 'NAME',
	            title : '财政区划',
	            width : 100,
	            align : 'center'
	        }]);
	    }

	    /**
	     * 展示角色表格
	     * @param param
	     */
	    function createRoleTable(param){
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/loadAuthoedData.do");
	    	COMMONJS.createNoticTable("roleTable",url,param, [{
	            field : 'RN',
	            title : '序号',
	            width : 50,
	            align : 'center'
	    	}, {
	            field : 'DATAGUID',
	            title : 'GUID',
	            width : 100,
	            align : 'center',
	            hidden : true
	        }, {
	            field : 'NAME',
	            title : '财政角色',
	            width : 100,
	            align : 'center'
	        }]);
	    }

	    /**
	     * 展示用户表格
	     * @param param
	     */
	    function createUserTable(param){
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/loadAuthoedData.do");
	    	COMMONJS.createNoticTable("userTable",url,param, [{
	            field : 'RN',
	            title : '序号',
	            width : 50,
	            align : 'center'
	    	}, {
	            field : 'DATAGUID',
	            title : 'GUID',
	            width : 100,
	            align : 'center',
	            hidden : true
	        }, {
	            field : 'NAME',
	            title : '财政用户',
	            width : 100,
	            align : 'center'
	        }]);
	    }

	    /**
	     * 单位
	     * @param param
	     */
	    function createAdmTable(param){
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/loadAuthoedData.do");
	    	COMMONJS.createNoticTable("admTable",url,param, [{
	            field : 'RN',
	            title : '序号',
	            width : 50,
	            align : 'center'
	    	}, {
	            field : 'DATAGUID',
	            title : 'GUID',
	            width : 100,
	            align : 'center',
	            hidden : true
	        }, {
	            field : 'NAME',
	            title : '单位',
	            width : 100,
	            align : 'center'
	        }]);
	    }

	    /**
	     * 处室
	     * @param param
	     */
	    function createDepartTable(param){
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/loadAuthoedData.do");
	    	COMMONJS.createNoticTable("departTable",url,param, [{
	            field : 'RN',
	            title : '序号',
	            width : 50,
	            align : 'center'
	    	}, {
	            field : 'DATAGUID',
	            title : 'GUID',
	            width : 100,
	            align : 'center',
	            hidden : true
	        }, {
	            field : 'NAME',
	            title : '财政机构',
	            width : 100,
	            align : 'center'
	        }]);
	    }

	    /**
	     * 系统
	     * @param param
	     */
	    function createSysTable(param){
	    	var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/loadAuthoedData.do");
	    	COMMONJS.createNoticTable("sysTable",url,param, [{
	            field : 'RN',
	            title : '序号',
	            width : 50,
	            align : 'center'
	    	}, {
	            field : 'DATAGUID',
	            title : 'GUID',
	            width : 100,
	            align : 'center',
	            hidden : true
	        }, {
	            field : 'NAME',
	            title : '业务系统',
	            width : 100,
	            align : 'center'
	        }]);
	    }
	   
	   //返回
		function goBack(){
			var url = _BASE_PATH+"fpfportal/page/notic/notic.jsp?tokenid="+tokenid+"&menuName=通知管理";
			//window.open(url);
			window.location.href = url;
		}
	   
	   /* 	  //设置编辑器无效
		function setDisabled() {
			UE.getEditor('noticEditor').addListener("ready", function () {
	       		UE.getEditor('noticEditor').setDisabled('fullscreen');
			});
    	}
    	//设置编辑器有效
    	function setEnabled() {
    		UE.getEditor('noticEditor').addListener("ready", function () {
	        	UE.getEditor('noticEditor').setEnabled();
			});
	    }   */
	   
	   	/**
	     *发布和定时发布
		 */
		function upnoticTopro(falg){
			//发布存草稿之前，先校验文件名和定时时间是否填写
			var noticTitle=$("#noticTitle").val();
			if(noticTitle==null||$.trim(noticTitle)==""){
				alert("请输入名称！");
				return;
			}
			//add by jinwencheng on 2017-05-18 添加了发布位置
			//add end
			
			/* if(falg==0){
				var noticDate=$("#noticDate").val();
				if(noticDate==null||noticDate.trim()==""){
					alert("请输入定时发布时间！");
					return false;
				}
			} */
			var isforprotype = $("#isforprotype option:selected").val();
			var noticSpecies = $("#noticSpecies option:selected").val();
			
			$("#isfortypeValue").val(isforprotype);
			if(falg=='0'){
				openTimeNotic();
			}else{
				saveNoticData(falg);
			}
			
		}
		
		 function openTimeNotic(){
	   		var html=[];
				
				html.push("<div>");
				html.push("  定时发布日期：<input style='width:200px;' name='noticTimeDate' class='Wdate' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})\" id='noticTimeDate' readonly='readonly'/>");
				html.push("	<div>");
				html.push("	 <div class='noticbtnleft'><a class='btnClass' style='width: 30px; margin-left: 50px; margin-right: 80px;' onclick='saveNoticData(0);'>保存</a> <a class='btnClass' style='width: 30px;' onclick='celcanupnoticTopro();'>取消</a></div>");
				html.push("	</div>");
				html.push("</div>");
				layer.open({
				    type: 1, //page层
				    skin: 'layui-layer-lan', //加上边框
				    area: ['300px', '300px'], //宽高
				    title:['定时发布通知公告','font-size:18px;'],
				    shade:0, //遮罩透明度
				    closeBtn: 1, //不显示关闭按钮
				    //offset: 'rb', //右下角弹出
				    scrollbar:'no',
				    moveType: 1, //拖拽风格，0是默认，1是传统拖动
				    shift: 0, //0-6的动画形式，-1不开启
				    content: html.join("")
				    //btn:['确认']
				});
	   
	   } 
	   
	   function celcanupnoticTopro(){
			layer.closeAll();
		}
	   /**
		 *保存数据方法
		 */
		function saveNoticData(falg){
           uplodadGuid=new Date().getTime();

           if(window.confirm("严禁违规上传、处理涉密文件资料，检查上传文件是否涉密")){
				//标题
				var noticTitle=$("#noticTitle").val();
				if(noticTitle==null||$.trim(noticTitle)==""){
					alert("请输入标题名称！");
					
					return;
				}else if(noticTitle.length>20){
					alert("标题名称长度不能超过20");
					return;
				}
				$("#noticContent").val(kindEditor.html());
				//是否置顶
				var orderid="";
				$("input[name='orderid']:checkbox").each(function(){
					if (true == $(this).attr("checked")) {
						orderid = $(this).val();
					}
				});
				
				//发布位置
				var s = "";
				$("input[name='noticSpecies']:checked").each(function(){
						s += $(this).val()+",";
				});
				if (s == null || s == "") {
					alert("请选择发布位置！");
					return;
				}
				$("#publocations").val(s);
				
				if(autoedFlag){//禁用时间控件
					$("#saveFalg").val("2");
				}else{
					var noticDate1=$("#noticDate").val();
					if(noticDate1 == null || noticDate1 == ""){
						//发布状态为 1
						$("#saveFalg").val("1");
						//若发布位置仅为登录页  则状态设置为已发布
						if(s == '1,'){
							$("#saveFalg").val("2");
						}
					}else{
						//前台校验定时器时间需大于当前时间5分钟
						if(checkTime(noticDate1)){
							//定时发布状态 为0
							$("#saveFalg").val("0");
						}else{
							return;
						}
					}
				}
				//文件类型
				$("#fileType").val(fileType);
				
				var url=window.location.search;
				var loginguid=url.substring(url.lastIndexOf("tokenid")+8,41);
				$("#addLoginGuid").val(loginguid);
				var url = _BASE_PATH+"fpfportal/notic/saveNotic.do?tokenid="+tokenid;
				
				//$("#noticForm").attr("action",url);
				$("#noticForm").attr("action","");
				if(uploader != null && uploader != undefined){
					//点击按钮之后，第一步做的是：上传文件，
                    uploader.setOption("url",uploader.getOption("url")+"&uplodadGuid="+uplodadGuid);

                    if(uploader.files != "" && uploader.files !=undefined ){
						 uploader.bind('StateChanged', function() {
							if(uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {  
	       						uploader.splice(0,uploader.files.length); 
	       						fileGuidStr = fileGuidStr.substring(0,fileGuidStr.length-1);
	       						fileNameStr = fileNameStr.substring(0,fileNameStr.length-1);
//	       						deletedFileGuid = deletedFileGuid.substring(0, deletedFileGuid.length-1);
	       						$("#fileGuidStr").val(fileGuidStr);
	       						$("#fileNameStr").val(fileNameStr);
	       						$("#deletedFileGuid").val(deletedFileGuid);
								//$("#noticForm").submit();
	      						formsubmit();
	      					}
	      				});
	      					uploader.start();  
					}else{
						//单独删除时，不能删除掉
						if(deletedFileGuid != "" && deletedFileGuid !=undefined ){
//							deletedFileGuid = deletedFileGuid.substring(0, deletedFileGuid.length-1);
							$("#deletedFileGuid").val(deletedFileGuid);
						}
						//$("#noticForm").submit();
						formsubmit();
					} 
					
				}else{
					//$("#noticForm").submit();
					formsubmit();
				}
			}
		}
		
		function checkTime(notictime){
			var myDate = new Date();
			var noticDate = new Date(notictime.replace(/-/g,"/"));
			if(myDate.getTime()>noticDate.getTime()){
				alert("发布时间不能小于当前时间！");
				return;
			}else if(noticDate.getTime()-myDate.getTime()<300000){
				alert("发布时间须大于当前时间五分钟以上！");
				return;
			}
			return true;
		}
		
		
		function formsubmit(){
			if(noticguid != null && noticguid != 'null'){
				$("#noticguid").val(noticguid);
			}
			if(opublocation != null && opublocation != 'null'){
				$("#opublocation").val(opublocation);
			}
			var pas = $("form").serialize();
			pas=pas+"&userguid="+userguid;
			//加载层
			var layerLoad = layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
            var url = COMMONJS.addToken( _BASE_PATH+"fportal/notic/saveNotic.do?uplodadGuid="+uplodadGuid);
            $.ajax({
				url:url,
				type:'post',
				data:pas,
				success:function(succ){
					layer.close(layerLoad);
					if(succ.success == "success"){
						layer.confirm('保存成功,是否留在此页？', {
							  btn: ['是','否'] //按钮
							}, function(){
								noticId = succ.noticId;
								pubLocation = succ.pubLocation;
							    //再次保存时
								noticguid = noticId;
								
								//截取title_name
								function getQueryString(name) { 
									var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
									var r = window.location.search.substr(1).match(reg); 
									if (r != null) return unescape(r[2]); return null; 
									} 
								//2017-08-09 rzq 帮助文档标题添加参数后保存跳转url参数添加
								//原代码  var url = _BASE_PATH+"fpfportal/page/notic/noticdata.jsp?tokenid="+tokenid+"&noticguid="+noticguid+"&updateFlag='2'&fileType="+fileType+"&opublocation="+pubLocation+"&noticId="+noticId;
								var url = _BASE_PATH+"fpfportal/page/notic/noticdata.jsp?tokenid="+tokenid+"&noticguid="+noticguid+"&updateFlag=2&fileType="+fileType+"&opublocation="+pubLocation+"&noticId="+noticId+"&title_name="+getQueryString("title_name");
								window.location.href=url;
							}, function(){
								 var menuName = "" ;
								 if(fileType=='notic00001'){
									 menuName = '通知管理' ;
								 }else{
									 menuName = '帮助文档' ;
								 }
								var url = _BASE_PATH+"fpfportal/page/notic/notic.jsp?menuName="+menuName+"&fileType="+fileType;
								window.open(COMMONJS.addToken(url),"_self");
							});
						//修改成功后，更新所有用户缓存信息
						var opublocation = succ.opublocation;
						if(opublocation != null && opublocation !="" && opublocation !="undefined"){
							refreshAllUserNoticCache(noticguid,succ.pubLocation,succ.status,opublocation,userguid);
						}
					}else{
						alert("保存失败");
						return ;
					}
				},
				error:function(err){
					layer.close(layerLoad);
					alert("保存失败");
					return ;
				}
			});
		}
		//修改成功后，更新所有用户缓存信息   add by zhouqin 2017-07-19
		function refreshAllUserNoticCache(noticguid,pubLocation,status,opublocation,userguid){
			var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/updAllUsersCacheByUpdate.do");
			$.ajax({
				url:url,
				type:'post',
				data:{
					noticguid:noticguid,
					publocations:pubLocation,
					status:status,
					opublocation:opublocation,
					userguid:userguid
			    },
				success:function(succ){
				},
				error:function(err){
				}
			});
		}
		
		
		/**
		 * add by jinwencheng on 2017-06-05 粘贴图片
		 */
		function pasteImg(){
			var noticContent = kindEditor.html();
			var i = noticContent.indexOf('<img');
			if (pasteFlag) {
				if (i != -1) {	//为-1表示粘贴中带有图片
					//加载层
					var layerLoad = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						});
					var url = COMMONJS.addToken(_BASE_PATH+"fpfportal/notic/pasteImg.do");
					$.ajax({
						url:url,
						type:'post',
						dataType:'json', 
						data:{noticContent:noticContent},
						success:function(succ){
							if("success"==succ.success){
								layer.close(layerLoad);
								kindEditor.html(succ.c);
							}else if("error"==succ.success){
								alert(succ.msg);
								layer.close(layerLoad);
								return ;
							}
						},
						error:function(err){
							layer.close(layerLoad);
							alert("保存失败");
							return;
						}
					});
					pasteFlag = false;
				}
			}
			//window.clearInterval(interval);
		}
		//add end
		
		//下载附件
		function downloadfile(fileguid){
			$("#fileguid").val(fileguid);
			$("#myformdowntic").submit();
			 /* $.ajax({
				url: _BASE_PATH+"fpfportal/notic/downloadFileData",
				type:'post',
				dataType:'json',
				data:{fileguid:fileguid},
				success:function(succ){
					if(succ){
						if(succ.success == "success"){
							alert("下载成功");
							history.go(0);
						}else{
							alert("下载失败");
							return ;
						}
					}else{
					
					}
				},
				error:function(err){
					alert("下载失败");
					return ;
				}
			});  */
			
		}
		
		var autoedFlag = false;
		function initOperation() {
			//$("#noticEditor").css("max-height",divHerght);
			//初始化加载通知公告树
			var appids;
			var allNoticType;
			
			if (noticguid != "null") {//修改
				//加载层
				var layerLoad = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
				var url = COMMONJS.addToken( _BASE_PATH + "fpfportal/notic/getNoticinfoById.do");
				$.ajax({
					url :url,
					type : 'post',
					dataType : 'json',
					data : {
						noticguid : noticguid,
						updateFlag : updateFlag
					},
					success : function(succ) {
						layer.close(layerLoad);
						if (succ.success == "success") {
							var allFileDatas = succ.allDatas.allFileDatas;
							var list = succ.allDatas.data;
							var treeData = succ.allDatas.treeData;
							var isForProName = succ.allDatas.isForProName;
							var isForProIDS = succ.allDatas.isForProIDS;
							if (list.length > 0) {
								var signForFile = "&fileidbyfqb";
								if (allFileDatas != ""
										&& allFileDatas != undefined) {
									countFiles = allFileDatas.length;
									for (var i = 0; i < allFileDatas.length; i++) {
										allFileNamesArr
												.push(allFileDatas[i].FILENAME);
										allFileGuidArr
												.push(allFileDatas[i].FILEGUID);
										var html1 = '';
										if (updateFlag == "1") {//查看全文
											html1 = '<li id="'+allFileDatas[i].FILEGUID+
											'"><input type="hidden" name="'+allFileDatas[i].FILEGUID+signForFile+'" id="'+allFileDatas[i].FILEGUID+signForFile+
											'" value="'+allFileDatas[i].FILEGUID+signForFile+'" /><p><a href='
													+ '"javascript:downloadfile('
													+ "'"
													+ allFileDatas[i].FILEGUID
													+ "'"
													+ ');">'
													+ allFileDatas[i].FILENAME
													+ '</a> </p><p></p></li>';
										} else {
											html1 = '<li id="'+allFileDatas[i].FILEGUID+
											'"><input type="hidden" name="'+allFileDatas[i].FILEGUID+signForFile+'" id="'+allFileDatas[i].FILEGUID+signForFile+
											'" value="'+allFileDatas[i].FILEGUID+signForFile+'" />'
//													+'<font style="color:#73ace5;">'
													+ allFileDatas[i].FILENAME
//													+'</font>'
													+ ' <a onclick="deleteUnupload('
													+ "'"
													+ allFileDatas[i].FILEGUID
													+ "','"
													+ allFileDatas[i].FILENAME
													+ "'"
													+ ')">删除</a></p><p></p></li>';
										}
										$(html1).appendTo('#file-list');

									}
								}

								if (countFiles == 0) {
									$("#file_upload1").val("未选择文件");
								} else {
									$("#file_upload1").val(
											countFiles + "个文件");
								}
								//guid
								$("#noticguid").val(list[0].GUID);
								//标题
								$("#noticTitle").val(list[0].FILENAME);
								//备注
								$("#remark").val(list[0].REMARK);

								//重要级别
								if(list[0].ORDERID == '1'){
									$("#orderid").attr("checked",true);
								}
								if(list[0].NOTICSTATUS.indexOf('2') != -1){
									autoedFlag = true;
									$("#noticDate").attr("disabled",true);
								}
								//通知公告类型
								$("#notictype").val(list[0].TYPENAME);
								$("#notictypeid").val(list[0].FILETYPE);
								
								//发布位置信息回填
								var publocations = list[0].PUBLOCATION;
								$("[name = noticSpecies]:checkbox").each(function () {
										if(publocations.indexOf($(this).val()) != -1){
											$(this).attr("checked", true);
										}
					            });
								
								//定时时间回填
								var status = list[0].NOTICSTATUS;
								if(status.indexOf('0') != -1){
									$("#noticDate").val(list[0].SENDDATE);
								}

								//正文
								var noticcontent = list[0].CONTENT;
								if (noticcontent != null
										&& noticcontent != "") {
									kindEditor.html(list[0].CONTENT);

								}
							}
						} else {
							alert("查询失败");
							return;
						}
					},
					error : function(err) {
						layer.close(layerLoad);
						alert("查询失败");
						return;
					}
				});
			} else {
				$("#file_upload1").val("未选择文件");

			}
			//新增页面：显示：未选择任何文件

			uploader = new plupload.Uploader({//实例化一个plupload上传对象
				browse_button : 'browse',
				url : _BASE_PATH + "fpfportal/notic/pluploadFile.do?tokenid="
						+ tokenid,
				flash_swf_url : _BASE_PATH+'fpfportal/static/ui/plupload/js/Moxie.swf',  
				silverlight_xap_url : _BASE_PATH +'fpfportal/static/ui/plupload/js/Moxie.xap',
				multi_selection : true,
				filters : {
					max_file_size : max_size,
//					prevent_duplicates : true
				}
			});
			uploader.init();
			//绑定文件添加进队列事件
			uploader.bind('FilesAdded',
					function(uploader, files) {
								for (var k = 0, len = files.length; k < len; k++) {
									if(files[k].size > (1024*1024*100)){
										alert("文件"+files[k].name+"大于100M，已跳过！");
										files.splice(k, 1);
										uploader.files.splice(k, 1);
										k=k-1;
										len = len - 1 ;
									}else{
										var file_name = files[k].name; //文件名
										//构造html来更新UI
										var html = '<li id="file-' + files[k].id +'"><p>'
												+ file_name
												+ '  <a onclick="deleteUnupload('
												+ "'"
												+ 'file-'
												+ files[k].id
												+ "','"
												+ file_name
												+ "'"
												+ ');">删除</a></p><p></p></li>';
										$(html).appendTo('#file-list');
									}
								}
								countFiles += files.length;
								var toremoveid = "";

								//增加文件名称重复时的操作
								var toremove = "";
								var thislinode = "";
								for (var j = 0, len1 = files.length; j < len1; j++) {
									//先判断上传的文件与队列中的重复的并提示用户；
									for ( var i in uploader.files) {
										if (uploader.files[i].name == files[j].name
												&& uploader.files[i].id != files[j].id) { //上传到队列中的文件与本次要上传的文件重复
											countFiles = countFiles - 1;
											if (confirm("上传的" + files[j].name
													+ "文件名重复，确定要覆盖吗？")) { //用户确定覆盖，则把之前的文件从队列中删除
												toremove = i;
												//需要把之前的文件从页面删除
												thislinode = document
														.getElementById("file-"
																+ uploader.files[i].id);
												thislinode.parentNode
														.removeChild(thislinode);
											} else { //用户不覆盖，则最后一次不上传，也不加到页面
												//toremove = uploader.files.length-1;
												toremoveid = files[j].id;
												//把已经上传的在页面上删掉
												thislinode = document
														.getElementById("file-"
																+ files[j].id);
												thislinode.parentNode
														.removeChild(thislinode);
											}
										}
									}

									//再判断上传的文件与本地存在的文件allFileNamesArr是否重复
									for (var p = 0; p < allFileNamesArr.length; p++) {
										if (files[j].name == allFileNamesArr[p]) { //上传的文件与之前fuds的文件名重复
											countFiles = countFiles - 1;
											if (confirm("上传的" + files[j].name
													+ "文件名重复，确定要覆盖吗？")) {//确定覆盖，则需要删除中间表数据，删除页面上的元素
												deletedFileGuid =deletedFileGuid+","+allFileGuidArr[p]
														;
												thislinode = document
														.getElementById(allFileGuidArr[p]);
												thislinode.parentNode
														.removeChild(thislinode);
											} else {//不覆盖，则删除队列，删除页面上的值即可
												toremoveid = files[j].id;

												thislinode = document
														.getElementById("file-"
																+ files[j].id);
												thislinode.parentNode
														.removeChild(thislinode);
											}
										}
									}

									if (toremoveid != "") {
										for ( var m in uploader.files) {
											if (uploader.files[m].id == toremoveid) {
												toremove = m;
											}
										}
									}

									if (toremove != "") {
										uploader.files.splice(toremove, 1);
									}
								}
								if (countFiles == 0) {
									$("#file_upload1").val("未选择文件");
								} else {
									$("#file_upload1").val(countFiles + "个文件");
								}
							});

			uploader.bind('Error', function (uploader, err) {
                alert("文件上传失败,错误信息: " + err.message);
                return ;
            });
			uploader.bind('FileUploaded', function(uploader, file, result) {
				var responStr = result.response;
				if (responStr != "") {
					if (responStr.indexOf("errmsgbyplupload") == -1) {
						if (responStr.indexOf("pluploadjlptbyfqbstart") == -1) {
							//空文件fileGuidStr、fileNameStr不做任何处理
						} else {
							var guidname = responStr.substring(responStr
									.indexOf("pluploadjlptbyfqbstart") + 25,
									responStr.indexOf("pluploadjlptbyfqbend"));
							fileGuidStr += guidname.substring(0, guidname
									.indexOf(",,"))
									+ ",";
							fileNameStr += guidname.substring(guidname
									.indexOf(",,") + 2)
									+ ",";
						}
					} else {
						var errmsgbyplupload = responStr.substring(responStr
								.indexOf("errmsgbyplupload") + 19, responStr
								.indexOf("errmsgbypluploadflag"));
						alert(errmsgbyplupload);
						uploader.stop();
					}
				}
			});

			document.body.onmouseover = function() {
				var divarea = document.getElementById("divfilelist");
				var textarea = document.getElementById("file_upload1");
				if (!divarea.contains(event.srcElement)
						&& !textarea.contains(event.srcElement)) {
					document.getElementById("divfilelist").style.display = "none";
					$("#divfilelist").slideUp(200);
				}
			}

			//初始化对定向框绑定change事件
			$("#isforprotype").change(function() {
				//$("#isforpro").val("");
				var isforprotype = $("#isforprotype option:selected").val();
				//加载层
				var layerLoad = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
				var url = COMMONJS.addToken( _BASE_PATH + "fpfportal/notic/getIsForProList.do");
				$.ajax({
					url :url,
					type:"post",
					dataType : 'json',
					data : {
						"isforprotype" : isforprotype
					},
					success : function(succ) {
						layer.close(layerLoad);
						if (succ.success == "success") {
							var datas = succ.isForProList;
							$.fn.zTree.init($("#treeDemo"), setting, datas);
						} else {
							alert("查询失败");
							return;
						}
					},
					error : function(err) {
					layer.close(layerLoad);
						alert("查询失败");
						return;
					}
				});
			});

		};
		
/**
 * 预览
 */
function preView(){
	if (noticId == undefined||noticId==null||noticId==''||noticId=='null') {
		alert("请先保存通知公告再进行预览！");
		return;
	}
	if(confirm("请确认页面数据已保存再预览！")){
//		var url=encodeURI(encodeURI(_BASE_PATH+"/fpfportal/page/notic/shownoticdata.jsp?noticguid="+noticId+"&tokenid="+tokenid+"&menuname=通知公告"));
		var url=encodeURI(encodeURI(_BASE_PATH+"/fpfportal/page/notic/shownoticdata.jsp?noticguid="+noticId+"&tokenid="+tokenid+"&menuname=通知公告"));
		window.open(url);
	}
}

