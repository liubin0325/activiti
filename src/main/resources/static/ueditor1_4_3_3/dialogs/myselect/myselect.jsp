<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resource/common/js/header.jsp"%>
<%
	String webapp = request.getContextPath();
%>

<!--
 * myselect  下拉框
 * @version 2016-12-24
 * @author wangym
-->

<html>
<head>
<title>下拉框</title>
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
    <script type="text/javascript">
    //checkboxs
	function isIE()
	{
	    if(window.attachEvent){   
	        return true;
	    }
	    return false;
	}
	//moveRow在IE支持而在火狐里不支持！以下是扩展火狐下的moveRow
	if (!isIE()) {
	    function getTRNode(nowTR, sibling) {
	        while (nowTR = nowTR[sibling]) if (nowTR.tagName == 'TR') break;
	        return nowTR;
	    }
	    if (typeof Element != 'undefined') {
	        Element.prototype.moveRow = function(sourceRowIndex, targetRowIndex) //执行扩展操作
	        {
	            if (!/^(table|tbody|tfoot|thead)$/i.test(this.tagName) || sourceRowIndex === targetRowIndex) return false;
	            var pNode = this;
	            if (this.tagName == 'TABLE') pNode = this.getElementsByTagName('tbody')[0]; //firefox会自动加上tbody标签，所以需要取tbody，直接table.insertBefore会error
	            var sourceRow = pNode.rows[sourceRowIndex],
	            targetRow = pNode.rows[targetRowIndex];
	            if (sourceRow == null || targetRow == null) return false;
	            var targetRowNextRow = sourceRowIndex > targetRowIndex ? false: getTRNode(targetRow, 'nextSibling');
	            if (targetRowNextRow === false) pNode.insertBefore(sourceRow, targetRow); //后面行移动到前面，直接insertBefore即可
	            else { //移动到当前行的后面位置，则需要判断要移动到的行的后面是否还有行，有则insertBefore，否则appendChild
	                if (targetRowNextRow == null) pNode.appendChild(sourceRow);
	                else pNode.insertBefore(sourceRow, targetRowNextRow);
	            }
	        }
	    }
	}
	
	/*删除tr*/
	function fnDeleteRow(obj)
	{
	    var oTable = document.getElementById("options_table");
	    while(obj.tagName !='TR')
	    {
	        obj = obj.parentNode;
	    }
	    oTable.deleteRow(obj.rowIndex);
	}
	/*上移*/
	function fnMoveUp(obj)
	{
	    var oTable = document.getElementById("options_table");
	    while(obj.tagName !='TR')
	    {
	        obj = obj.parentNode;
	    }
	    var minRowIndex = 1;
	    var curRowIndex = obj.rowIndex;
	    if(curRowIndex-1>=minRowIndex)
	    {
	        oTable.moveRow(curRowIndex,curRowIndex-1); 
	    }
	    
	}
	/*下移*/
	function fnMoveDown(obj)
	{
	    var oTable = document.getElementById("options_table");
	    while(obj.tagName !='TR')
	    {
	        obj = obj.parentNode;
	    }
	    var maxRowIndex = oTable.rows.length;
	    var curRowIndex = obj.rowIndex;
	    if(curRowIndex+1<maxRowIndex)
	    {
	        oTable.moveRow(curRowIndex,curRowIndex+1); 
	    }
	}
	
	/*生成tr*/
	function fnAddComboTr(obj)
	{
	    var oTable = document.getElementById('options_table');
	    var new_tr_node= oTable.insertRow(oTable.rows.length);
	    var new_td_node0 = new_tr_node.insertCell(0),new_td_node1 = new_tr_node.insertCell(1),new_td_node2 = new_tr_node.insertCell(2),new_td_node3 = new_tr_node.insertCell(3);
	
	    var sChecked = '';
	    if(obj.checked) sChecked = 'checked="checked"';
	    if(!obj.name) obj.name = '';
	    if(!obj.value) obj.value = '';

	    new_td_node0.innerHTML = '<td valign="middle" align="center"><span class="badge">'+(oTable.rows.length-1)+'</span>';
	    new_td_node1.innerHTML = '<td valign="middle" align="center"><input type="checkbox" '+sChecked+'></td>';
	    new_td_node2.innerHTML = '<td><input type="text" style="font-size:12px;padding-left:6px;height:22px;" value="'+obj.value+'" name="'+obj.name+'" placeholder="选项值"></td>';
	    new_td_node3.innerHTML ='<td><div class="btn-group"><a title="上移" class="btn btn-small btn-info" href="javascript:void(0);" onclick="fnMoveUp(this)"><i class="icon-white icon-arrow-up"></i></a><a title="下移" class="btn btn-small btn-info" href="javascript:void(0);" onclick="fnMoveDown(this)"><i class="icon-white icon-arrow-down"></i></a><a title="删除" class="btn btn-small btn-default" href="javascript:void(0);" onclick="fnDeleteRow(this)"><i class="icon-ban-circle"></i></a></div></td>';
	    return true;
	}
	function fnAdd() {
	    fnAddComboTr({
	        "checked":false,
	        "name":'leipiNewField',
	        "value":''
	    });
	}
	
	/**
		从字典加载
	*/
	function addFromDic(){
		var url_host = "<%=webapp%>/sys_dic/loadDicData.act?dicname="+$("#dicname").val();
		ajaxRequest(url_host,"setDicData",null);
	}
	
	/**
		字典名称加载函数加载成功
	*/
	function setDicData(data){
		var oTable = document.getElementById('options_table');
        var nTr = oTable.getElementsByTagName('tr'),trLength = nTr.length;
        var hasChecked = null;
        for(var i=trLength-1;i>0;i--)
        {
        	fnDeleteRow(nTr[i]);
        }
		 for(var i=0;i<data.length;i++){
			 fnAddComboTr({
			        "checked":false,
			        "name":'leipiNewField',
			        "value":data[i].dicdataname
			    });
		 }
	}

	/**
		触发字典名称加载函数
	*/
	function loadDicName(){
		var url_host = "<%=webapp%>/sys_dic/loadDicNameAll.act";
		ajaxRequest(url_host,"setDicName",null);
	}
	
	/**
		字典名称加载函数加载成功
	*/
	function setDicName(data){
		var child = $("#dicname")[0];
		 child.options.length = 0;//清空第二级列表的项
		 for(var i=0;i<data.length;i++){
			 child.options.add(new Option(data[i].dicchiname,data[i].dicname));
		 }
		 //默认选择第一项
		 child.options[0].selected = true;
	}
	
	/**
		页面初始化
	*/
	$(document).ready(function(){
		//触发字典名称加载函数
		loadDicName();
	});
	</script>
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
			</div>
			<table class="table table-hover table-condensed" id="options_table">
				<tr>
					<th>序号</th>
					<th>默认选中</th>
					<th>选项值</th>
					<th>操作</th>
				</tr>
				<!--tr>
					<td><input type="checkbox" checked="checked"></td>
					<td><input type="text" value="选项一"></td>
					<td>
						<div class="btn-group">
							<a title="上移" class="btn btn-small btn-info" href="#"><i class="icon-white icon-arrow-up"></i></a>
							<a title="下移" class="btn btn-small btn-info" href="#"><i class="icon-white icon-arrow-down"></i></a>
							<a title="删除" class="btn btn-small btn-default"><i class="icon-ban-circle"></i></a>
						</div>
					</td>
				</tr-->
			</table>
			<div class="searchBtnWrap no-cont">
				<a title="添加选项" class="btn btn-small btn-primary" onclick="fnAdd();" style="margin-right: 8px;">添加选项</a>
				<hx:codeselect property="dicname" emptyText="" msgName="字典选项"/>
				<a title="从字典加载" class="btn btn-small btn-primary" onclick="addFromDic();">从字典加载</a>
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
	function fnSetAttribute( element, attName, attValue ) {
	    if ( attValue == null || attValue.length == 0 ){
	        element.removeAttribute( attName, 0 ) ;        
	    } else {
	        element.setAttribute( attName, attValue, 0 ) ;    
	    }
	}
	function fnHTMLEncode( text ) {
	    if ( !text ) {
	        return '' ;
	    }
	    text = text.replace( /&/g, '&amp;' ) ;
	    text = text.replace( /</g, '&lt;' ) ;
	    text = text.replace( />/g, '&gt;' ) ;
	    return text ;
	}
	// Add a new option to a SELECT object (combo or list)
	function fnAddComboOption( combo, optionText, optionValue, documentObject, index ) {
	    var oOption ;
	    if ( documentObject ) {
	        oOption = documentObject.createElement("option") ;
	    } else {
	        oOption = document.createElement("option") ;
	    }
	    if ( index != null ) {
	        combo.options.add( oOption, index ) ;
	    } else {
	        combo.options.add( oOption ) ;
	    }
	    oOption.innerHTML = optionText.length > 0 ? fnHTMLEncode( optionText ) : '&nbsp;' ;
	    oOption.value     = optionValue ;
	    return oOption ;
	}
	var oNode = null,thePlugins = 'select';
	window.onload = function() {
	    if( UE.plugins[thePlugins].editdom ){//修改时加载
	        oNode = UE.plugins[thePlugins].editdom;
	        $G('param_name').disabled = true;
	        $G('param_name').value = oNode.name;
	        $G('param_namechn').value = oNode.getAttribute('param_namechn');
	       
	        var hasChecked = oNode.getAttribute('datadefault');
	        for ( var i = 0 ; i < oNode.options.length ; i++ ) {
	            var sText    = oNode.options[i].value ;	            
	            fnAddComboTr({
			        "checked":(hasChecked==sText),
			        "name":'leipiNewField',
			        "value":sText
			    });
	        }
	        $('#param_namechn').focus();
	    }else{
	    	fnAdd();//默认添加一个空行
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
	            oNode = createElement('select',$G('param_name').value);
                oNode.setAttribute('class','MySelect');
	            oNode.setAttribute('param_namechn',$G('param_namechn').value);
	            oNode.setAttribute('myplugins',thePlugins);
	            
	            //循环处理table中的值
	            var oTable = document.getElementById('options_table');
	            var nTr = oTable.getElementsByTagName('tr'),trLength = nTr.length;
	            var hasChecked = null;
	            for(var i=0;i<trLength;i++)
	            {
	                var inputs = nTr[i].getElementsByTagName('input');
	                if(inputs.length>0)
	                {
	                	if(!inputs[1].value){
	                    	alert("第"+(i)+"行选项值不能为空！");
	                		return false;
	                    };
	                    if(hasChecked && inputs[0].checked){
	                		alert("默认选中只能有一个！");
	                		return false;
	                	}else if(inputs[0].checked){
	                		hasChecked = inputs[1].value;
	                	}
	                    
	                    var oOption = fnAddComboOption( oNode, inputs[1].value, inputs[1].value ) ;
	                    if (inputs[0].checked) {
	                        fnSetAttribute( oOption, 'selected', 'selected' ) ;
	                    }
	                }
	            }
	            if(hasChecked)
	            	oNode.setAttribute('datadefault',hasChecked);
	            else
	            	oNode.setAttribute('datadefault',"");
	            if(oNode.options.length==0){
	            	alert("至少设置一项选项值！");
	            	return false;
	            }
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
            oNode.setAttribute('myplugins',thePlugins);
            
         	//Remove all options.
            while ( oNode.options.length > 0 ){
                oNode.remove(0) ;
            }
         	
          //循环处理table中的值
            var oTable = document.getElementById('options_table');
            var nTr = oTable.getElementsByTagName('tr'),trLength = nTr.length;
            var hasChecked = null;
            for(var i=0;i<trLength;i++)
            {
                var inputs = nTr[i].getElementsByTagName('input');
                if(inputs.length>0)
                {
                	if(!inputs[1].value){
                    	alert("第"+(i)+"行选项值不能为空！");
                		return false;
                    };
                    if(hasChecked && inputs[0].checked){
                		alert("默认选中只能有一个！");
                		return false;
                	}else if(inputs[0].checked){
                		hasChecked = inputs[1].value;
                	}
                    
                    var oOption = fnAddComboOption( oNode, inputs[1].value, inputs[1].value ) ;
                    if (inputs[0].checked) {
                        fnSetAttribute( oOption, 'selected', 'selected' ) ;
                    }
                }
            }
            if(hasChecked)
            	oNode.setAttribute('datadefault',hasChecked);
            else
            	oNode.setAttribute('datadefault',"");
            if(oNode.options.length==0){
            	alert("至少设置一项选项值！");
            	return false;
            }
	        delete UE.plugins[thePlugins].editdom;
	    }
	};
</script>
</body>
</html>