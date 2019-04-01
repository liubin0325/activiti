<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resource/common/js/header.jsp"%>
<%
	String webapp = request.getContextPath();
%>

<!--
 * mymacros  宏控件
 * @version 2016-12-24
 * @author wangym
-->

<html>
<head>
<title>宏控件</title>
<base target='_self' />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
    <link rel="stylesheet" href="../../third-party/bootstrap/css/bootstrap.css">
    <!--[if lte IE 6]>
    <link rel="stylesheet" type="text/css" href="../../third-party/bootstrap/css/bootstrap-ie6.css">
    <![endif]-->
    <!--[if lte IE 7]>
    <link rel="stylesheet" type="text/css" href="../../third-party/bootstrap/css/ie.css">
    <![endif]-->
<script type="text/javascript" src="../internal.js"></script>
</head>

<body class="mainContent" style="background-color: #fff">
	<div id="mainContent">
		<form id="wf_formForm">
			<div style="" class="searchZone">
				<div class="searchWrap">
					<span class="searchList">英文名称</span>
					<span class="searchCont">
						<hx:text property="param_name" required="true" maxlength="32" msgName="英文名称" size="30"/>
                    </span>
				</div>
				<div class="searchWrap">
					<span class="searchList">中文名称</span>
					<span class="searchCont">
						<hx:text property="param_namechn" required="true" maxlength="200" msgName="中文名称" size="30"/>
                    </span>
				</div>
				<div class="searchWrap">
					<span class="searchList">输入框宽度</span>
					<span class="searchCont">
						<hx:text property="text_size" required="true" maxlength="10" value="20" msgName="输入框宽度" size="10" mask="integer"/>
                    </span>
				</div>
				<div class="searchWrap">
					<span class="searchList">类型</span>
					<span class="searchCont">
						<select id="datadefault" name="datadefault" class="MySelect" style="width:auto">
							<optgroup label="----常用日期宏控件----">
		                        <option value="sys_datetime">当前日期+时间 [ 1997-01-01 12:30:30 ]</option>
		                        <option value="sys_date">当前日期 [ 1997-01-01 ]</option>
		                        <option value="sys_date_cn">当前日期 [ 1997年1月1日 ]</option>
		                        <option value="sys_date_cn_short1">当前月份 [ 199701 ]</option>
		                        <option value="sys_date_cn_short2">当前月份 [ 1997年1月 ]</option>
		                        <option value="sys_date_cn_short4">当前自然年度 [ 1997 ]</option>
		                        <option value="sys_date_cn_short5">当前供热年度 [ 1997 ]</option>
		                    </optgroup>
		                    <optgroup label="----系统参数宏控件----">
		                    	<option value="sys_random">系统流水号，年+月+日+时+分+秒+三位随机数，17位长度</option>
		                    	<option value="sys_uuid">系统UUID流水号，32位长度</option>
		                        <option value="sys_usrcode">当前用户登录账号，如 9527</option>
		                        <option value="sys_usrid">当前用户UUID，32位长度</option>
		                        <option value="sys_usrname">当前用户姓名，如 唐伯虎</option>
		                        <option value="sys_deptid">当前用户部门UUID，32位长度</option>
		                        <option value="sys_deptname">当前用户部门，如 华府</option>
		                    </optgroup>
						</select>
                    </span>
				</div>
			</div>
		</form>
	</div>
<script type="text/javascript">
	function createElement(type, name)
	{     
	    var element = null;     
	    try {        
	        element = document.createElement('<'+type+' name="'+name+'">');     
	    } catch (e) {}   
	    if(element==null) {     
	        element = document.createElement(type);     
	        element.name = name;
	        element.id = name;
	    } 
	    return element;     
	}
	
	var oNode = null,thePlugins = 'macros';
	window.onload = function() {
	    if( UE.plugins[thePlugins].editdom ){//修改时加载
	        oNode = UE.plugins[thePlugins].editdom;
	        $G('param_name').disabled = true;
	        $G('param_name').value = oNode.name;
	        $G('param_namechn').value = oNode.getAttribute('param_namechn');
	        $G('datadefault').value = oNode.getAttribute('datadefault');
	        $G('text_size').value = oNode.getAttribute('size');
	        $('#param_namechn').focus();
	    }else{
	    	$('#param_name').focus();
	    }
	}
	dialog.oncancel = function () {
	    if( UE.plugins[thePlugins].editdom ) {
	        delete UE.plugins[thePlugins].editdom;
	    }
	};
	
	/*
	检测是否英文名称重复
	*/
	var vUEContent = null;
	function checkDoubleParam(vName){
		if(!vUEContent) vUEContent = UE.getEditor('editor').getContent();
		//检测单个的控件
		if(vUEContent.indexOf('id="'+vName+'"')!=-1){
			alert("已经存在该英文名称的控件！");
			return true;
		}
		//检测列表的控件
		var vResult = false;
		vUEContent.replace(/listparam_name=\".*?(\")/gi, function(plugin){
			var vlistparam_name = plugin.substring(16,plugin.length-1).split(",");
			for(var i=0;i<vlistparam_name.length;i++){
				if(vName==vlistparam_name[i]){
					vResult = true;
				}
			}
		});
		if(vResult){
			alert("该英文名称同列表控件的英文名称重复！");
			return true;
		}
		return false;
	}
	dialog.onok = function (){
		//检测输入
		if(!validForm("wf_formForm")) return false;	    
		if($G('param_name').value.substring(0,1).isNumber()){
			alert("英文名称不能以数字开头！");
			return false;
		}
	    if( !oNode ) {//新建
	        try {
	        	//检测是否英文名称重复
	    		if(checkDoubleParam($G('param_name').value)){
	    			$('#param_name').focus();
	    			return false;
	    		}
	        	
	            oNode = createElement('input',$G('param_name').value);
	            oNode.setAttribute('type','text');
	            oNode.setAttribute('param_namechn',$G('param_namechn').value);
	            oNode.setAttribute('value','{宏控件：'+$G('datadefault').options[$G('datadefault').selectedIndex].text+'}');
	            oNode.setAttribute('datadefault',$G('datadefault').value);
	            oNode.setAttribute('size',$G('text_size').value);
	            oNode.setAttribute('myplugins',thePlugins);
	            editor.execCommand('insertHtml',oNode.outerHTML);
	        } catch (e) {
	            try {
	                editor.execCommand('error');
	            } catch ( e ) {
	                alert('控件异常，请与系统管理员联系！');
	            }
	            return false;
	        }
	    } else {//修改
	    	oNode.setAttribute('param_namechn',$G('param_namechn').value);
	        oNode.setAttribute('value','{宏控件：'+$G('datadefault').options[$G('datadefault').selectedIndex].text+'}');
	        oNode.setAttribute('datadefault',$G('datadefault').value);
	        oNode.setAttribute('size',$G('text_size').value);
	        oNode.setAttribute('myplugins',thePlugins);
	        delete UE.plugins[thePlugins].editdom;
	    }
	};
</script>
</body>
</html>