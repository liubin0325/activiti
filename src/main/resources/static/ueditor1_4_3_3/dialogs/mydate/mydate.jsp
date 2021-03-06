<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resource/common/js/header.jsp"%>
<%
	String webapp = request.getContextPath();
%>

<!--
 * mydate  日期框
 * @version 2016-12-24
 * @author wangym
-->

<html>
<head>
<title>日期框</title>
<base target='_self' />
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
					<span class="searchList">默认值</span>
					<span class="searchCont">
						<hx:codeselect property="datadefault" keyVals="thisdate_今天,lastdate_昨天,thismonth_当月第一天" emptyText="无" msgName="默认值" styleClass="MySelect"/>
                    </span>
				</div>
				<div class="searchWrap">
					<span class="searchList">输入框宽度</span>
					<span class="searchCont">
						<hx:text property="text_size" required="true" maxlength="10" value="10" msgName="输入框宽度" size="10" mask="integer"/>
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
	
	/**
		取当前日期
	*/
	function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = year + seperator1 + month + seperator1 + strDate;
	    return currentdate;
	}
	
	var oNode = null,thePlugins = 'date';
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
                oNode.setAttribute('class','MyText');
	            oNode.setAttribute('param_namechn',$G('param_namechn').value);
	            oNode.setAttribute('value','{日期框：'+getNowFormatDate()+'}');
	            oNode.setAttribute('datadefault',$G('datadefault').value);
	            oNode.setAttribute('size',$G('text_size').value);
	            //oNode.setAttribute('mask','date');
	            //oNode.className = 'MyDate';
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
            /**
             $G('id')方法直接返回document.getElementById('id')
             但是页面并没有id为class的控件：编辑功能没有提供修改class的功能
             导致$G('class').value会报错
             新建时每个控件样式固定，使用“编辑”也无法更改，不需要重新赋值，应该注掉
             考虑到以后可能提供class修改，修改为使用前先判断
             edit by duxiao
             */
            if ($G('class')) {
                oNode.setAttribute('class', $G('class').value);
            }
            oNode.setAttribute('param_namechn', $G('param_namechn').value);
            oNode.setAttribute('value','{日期框：'+getNowFormatDate()+'}');
            oNode.setAttribute('datadefault',$G('datadefault').value);
            oNode.setAttribute('size',$G('text_size').value);
            //oNode.setAttribute('mask','date');
            //oNode.setAttribute('class','MyDate');
            oNode.setAttribute('myplugins',thePlugins);
	        delete UE.plugins[thePlugins].editdom;
	    }
	};
</script>
</body>
</html>