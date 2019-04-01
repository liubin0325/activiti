<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resource/common/js/header.jsp"%>
<%
	String webapp = request.getContextPath();
%>

<!--
 * mytext  单行文本
 * @version 2016-12-24
 * @author wangym
-->

<html>
<head>
<title>单行文本</title>
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
						<hx:text property="param_namechn" required="true" maxlength="200" msgName="中文名称" size="30" allowSpeChar="%,/,"/>
                    </span>
				</div>
				<div class="searchWrap">
					<span class="searchList">数据类型</span>
					<span class="searchCont">
						<hx:codeselect property="datatype" keyVals="text_文本,integer_整数,double_小数,phone_电话,email_邮箱地址,zip_邮编,card_身份证号码,url_http地址" emptyText="" msgName="数据类型" styleClass="MySelect"/>
                    </span>
				</div>
				<div class="searchWrap">
					<span class="searchList">默认值</span>
					<span class="searchCont">
						<hx:text property="datadefault" maxlength="200" msgName="默认值" size="30"/>
                    </span>
				</div>
				<div class="searchWrap">
					<span class="searchList">输入框宽度</span>
					<span class="searchCont">
						<hx:text property="text_size" required="true" maxlength="10" value="20" msgName="输入框宽度" size="10" mask="integer"/>
                    </span>
				</div>
				<div class="searchWrap">
					<span class="searchList">数据长度</span>
					<span class="searchCont">
						<hx:text property="datalength" required="true" maxlength="10" value="20" msgName="数据长度" size="10" mask="integer"/>
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
	
	var oNode = null,thePlugins = 'text';
	window.onload = function() {
	    if( UE.plugins[thePlugins].editdom ){//修改时加载
	        oNode = UE.plugins[thePlugins].editdom;
	        $G('param_name').disabled = true;
	        $G('param_name').value = oNode.name;
	        $G('param_namechn').value = oNode.getAttribute('param_namechn');
	        $G('datadefault').value = oNode.getAttribute('datadefault');
	        $G('datatype').value = oNode.getAttribute('datatype');
	        $G('text_size').value = oNode.getAttribute('size');
	        $G('datalength').value = oNode.getAttribute('maxlength');
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
	            oNode.setAttribute('value','{单行文本：'+($G('datadefault').value==""?$G('param_namechn').value:$G('datadefault').value)+'}');
	            oNode.setAttribute('datadefault',$G('datadefault').value);
	            oNode.setAttribute('datatype',$G('datatype').value);
	            oNode.setAttribute('size',$G('text_size').value);
	            oNode.setAttribute('maxlength',$G('datalength').value);
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
	    	oNode.setAttribute('param_namechn',$G('param_namechn').value);
            oNode.setAttribute('value','{单行文本：'+($G('datadefault').value==""?$G('param_namechn').value:$G('datadefault').value)+'}');
            oNode.setAttribute('datadefault',$G('datadefault').value);
            oNode.setAttribute('datatype',$G('datatype').value);
            oNode.setAttribute('size',$G('text_size').value);
            oNode.setAttribute('maxlength',$G('datalength').value);
            oNode.setAttribute('myplugins',thePlugins);
	        delete UE.plugins[thePlugins].editdom;
	    }
	};
</script>
</body>
</html>