<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resource/common/js/header.jsp"%>
<%
	String webapp = request.getContextPath();
%>

<!--
 * listctrl  列表控件
 * @version 2016-12-24
 * @author wangym
-->

<html>
<head>
<title>列表控件</title>
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
	    var new_td_node0 = new_tr_node.insertCell(0),
	    	new_td_node1 = new_tr_node.insertCell(1),
	    	new_td_node2 = new_tr_node.insertCell(2),
	    	new_td_node3 = new_tr_node.insertCell(3),
	    	new_td_node4 = new_tr_node.insertCell(4),
	    	new_td_node5 = new_tr_node.insertCell(5),
	    	new_td_node6 = new_tr_node.insertCell(6);
	
	    var sChecked = '';
	    if(obj.sumtype=='1') sChecked = 'checked="checked"';

	    new_td_node0.innerHTML = '<td valign="middle" align="center"><span class="badge">'+(oTable.rows.length-1)+'</span>';
	    new_td_node1.innerHTML = '<td><input type="text" style="font-size:12px;padding-left:6px;height:22px;" value="'+obj.param_name+'" name="'+obj.param_name+'" placeholder="英文名称" class="input-small span2"></td>';
	    new_td_node2.innerHTML = '<td><input type="text" style="font-size:12px;padding-left:6px;height:22px;" value="'+obj.param_namechn+'" name="'+obj.param_namechn+'" placeholder="标题" class="input-small span2" allowSpeChar="%,/,"></td>';
	    new_td_node3.innerHTML = '<td><input type="text" style="font-size:12px;padding-left:6px;height:22px;" value="'+obj.text_size+'" name="'+obj.text_size+'" placeholder="宽度(数值)" allowSpeChar="0123456789" denySpeWords="px|%" class="input-small span1"></td>';
	    new_td_node4.innerHTML = '<td><select id="aligntype" class="MySelect">'
								 +'       <option value="left" '+(obj.aligntype=='left'?'selected="selected"':'')+'>左对齐</option>'
								 +'       <option value="center" '+(obj.aligntype=='center'?'selected="selected"':'')+'>居中对齐</option>'
								 +'       <option value="right" '+(obj.aligntype=='right'?'selected="selected"':'')+'>右对齐</option>'
								 +'   </select></td>';
	    new_td_node5.innerHTML = '<td valign="middle" align="center"><input type="checkbox" '+sChecked+'></td>';
	    new_td_node6.innerHTML ='<td><div class="btn-group"><a title="上移" class="btn btn-small btn-info" href="javascript:void(0);" onclick="fnMoveUp(this)"><i class="icon-white icon-arrow-up"></i></a><a title="下移" class="btn btn-small btn-info" href="javascript:void(0);" onclick="fnMoveDown(this)"><i class="icon-white icon-arrow-down"></i></a><a title="删除" class="btn btn-small btn-default" href="javascript:void(0);" onclick="fnDeleteRow(this)"><i class="icon-ban-circle"></i></a></div></td>';
	    return true;
	}
	function fnAdd() {
	    fnAddComboTr({
	    	"param_name":'',
	    	"param_namechn":'',
	    	"text_size":'60',
	    	"aligntype":'left',
	    	"sumtype":'0'
	    });
	}
	</script>
</head>

<body class="mainContent" style="background-color: #fff">
	<div id="mainContent">
		<form id="wf_formForm">
			<table class="table table-hover table-condensed" id="options_table">
				<tr>
					<th>序号</th>
					<th>英文名称</th>
					<th>标题</th>
					<th>宽度(数值)</th>
					<th>对齐方式</th>
					<th>合计</th>
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
				<a title="添加列" class="btn btn-small btn-primary" onclick="fnAdd();">添加列</a>
			</div>
		</form>
	</div>
<script type="text/javascript">
	function createElement(type, name)
	{
//	    这是原版代码，每次都会抛异常然后重新创建，原因不明
//		var element = null;
// 		try {
//	        element = document.createElement('<'+type+' name="'+name+'">');
//	    } catch (e) {}
//	    if(element==null) {
//	        element = document.createElement(type);
//	        element.name = name;
//	        element.id = name;
//	    }

        //创建一个input
        var element = document.createElement(type);
        //设置id和name
        element.id = name;
        element.name = name;

	    return element;     
	}
	
	var oNode = null,thePlugins = 'listctrl';
	window.onload = function() {
//	    console.log(UE);
	    if( UE.plugins[thePlugins].editdom ){//修改时加载
	        oNode = UE.plugins[thePlugins].editdom;
	        var vparam_name=oNode.getAttribute('param_name').split(","),
	        	vparam_namechn=oNode.getAttribute('param_namechn').split(","),
	        	vtext_size=oNode.getAttribute('text_size').split(","),
	        	valigntype=oNode.getAttribute('aligntype').split(","),
	        	vsumtype=oNode.getAttribute('sumtype').split(",");   
	    
	        for ( var i = 0 ; i < vparam_name.length; i++ ) {
	        	fnAddComboTr({
	    	    	"param_name":vparam_name[i],
	    	    	"param_namechn":vparam_namechn[i],
	    	    	"text_size":vtext_size[i],
	    	    	"aligntype":valigntype[i],
	    	    	"sumtype":vsumtype[i]
	    	    });
	        }
	    }else{
//        	if(UE.getEditor('editor').getContent().indexOf('id="mylistctrl"')!=-1){
//        		alert("本系统一个表单只支持最多一个列表控件！");
//        		dialog.close(false);
//        		return false;
//        	}
	    	fnAdd();//默认添加一个空行
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
			return true;
		}
		return false;
	}
	
	/**
		判断英文名称是否重复
	*/
	var vNameArray = null;
	function checkDoubleName(vName){
		if(!vNameArray) vNameArray = new Array();
		for(var i=0;i<vNameArray.length;i++){
			if(vNameArray[i] == vName)return true;
		}
		vNameArray.push(vName);
		return false;
	}

    /**
     *判断列表间是否重复
     */
    function validRepeat(vName) {
        //当前列表的id
        var me = "-1";
        if (oNode) {
            me = $(oNode).attr("id");
        }
        //获得所有的列表
        var $list = $($(parent.document).find("#ueditor_0")[0]).contents().find("input[id^='mylistctrl']");
        //each中使用return无法作为方法返回,仅仅是终止each,所以需要用变量值标记
        var isRepeat = false;
        //遍历
        $list.each(function (index, it) {
            //排除
            if ($(it).attr("id") == me) {
                //终止本次循环
                return true;
            }
            var params = $(it).attr("param_name").split(",");
            for (var i = 0; i < params.length; i++) {
                if (params[i] == vName) {
                    isRepeat = true;
                    break;
                }
            }
            if (isRepeat) {
                //终止整个循环
                return false;
            }
        });
        return isRepeat;
    }

	dialog.onok = function (){
		//检测输入
		if(!validForm("wf_formForm")) return false;
		
		//循环处理table中的值
        var oTable = document.getElementById('options_table');
        var nTr = oTable.getElementsByTagName('tr'),trLength = nTr.length;
        var vparam_name='',vparam_namechn='',vtext_size='',valigntype='',vsumtype='';
        if(trLength>60){
        	alert("系统支持的最多列数为60！");
        	return false;
        }
        vNameArray = new Array();
        for(var i=0;i<trLength;i++)
        {
            var inputs = nTr[i].getElementsByTagName('input');
            var selects = nTr[i].getElementsByTagName('select');
            if(inputs.length>0)
            {
            	if(!inputs[0].value){
                	alert("第"+(i)+"行英文名称不能为空！");
            		return false;
                };
                if(inputs[0].value.substring(0,1).isNumber()){
        			alert("第"+(i)+"行英文名称不能以数字开头！");
        			return false;
        		}
                if(!inputs[1].value){
                	alert("第"+(i)+"行标题不能为空！");
            		return false;
                };
                if(!inputs[2].value){
                	alert("第"+(i)+"行宽度不能为空！");
            		return false;
                };
                if(checkDoubleName(inputs[0].value)){
                	alert("第"+(i)+"行英文名称重复！");
            		return false;
                }
                if(checkDoubleParam(inputs[0].value)){
                	alert("第"+(i)+"行已经存在该英文名称的控件！");
            		return false;
                }
                if (validRepeat(inputs[0].value)) {
                    alert("第" + (i) + "行英文名称已经在其他列表中存在！");
                    return false;
                }
                if(vparam_name!='')vparam_name+=",";vparam_name+=inputs[0].value;
                if(vparam_namechn!='')vparam_namechn+=",";vparam_namechn+=inputs[1].value;
                if(vtext_size!='')vtext_size+=",";vtext_size+=inputs[2].value;
                if(valigntype!='')valigntype+=",";valigntype+=selects[0].value;
                if(vsumtype!='')vsumtype+=",";vsumtype+=(inputs[3].checked?"1":"0");
            }
        }
        if(vparam_name==""){
        	alert("至少设置一列！");
        	return false;
        }

	    if( !oNode ) {//新建
	        try {
                oNode = createElement('input', getMaxListNo());
	            oNode.setAttribute('value','{列表控件}');
	            oNode.setAttribute('param_name',vparam_name);
	            oNode.setAttribute('listparam_name',vparam_name);
	            oNode.setAttribute('param_namechn',vparam_namechn);
	            oNode.setAttribute('text_size',vtext_size);
	            oNode.setAttribute('aligntype',valigntype);
	            oNode.setAttribute('sumtype',vsumtype);
	            oNode.setAttribute('myplugins',thePlugins);
	            //oNode.setAttribute('style',"width:100%");
	            // 火狐&谷歌好用
//	            oNode.style = 'width:100%;';
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
	    	oNode.setAttribute('value','{列表控件}');
            //oNode.setAttribute('width','100%');
	    	oNode.setAttribute('param_name',vparam_name);
	    	oNode.setAttribute('listparam_name',vparam_name);
            oNode.setAttribute('param_namechn',vparam_namechn);
            oNode.setAttribute('text_size',vtext_size);
            oNode.setAttribute('aligntype',valigntype);
            oNode.setAttribute('sumtype',vsumtype);
            oNode.setAttribute('myplugins',thePlugins);
            //oNode.setAttribute('style',"width:100%");
            // 火狐&谷歌好用
//	            oNode.style = 'width:100%;';
            delete UE.plugins[thePlugins].editdom;
	    }
	};

    /**
     * 获取下一个个列表控件的id
     */
    function getMaxListNo() {
        //初始化列表id
        var id = "mylistctrl";
        var index = 1;
        //先判断初始id是否已经被占用
        while ($($(parent.document).find("#ueditor_0")[0]).contents().find("#" + id + index).length > 0) {
            //增加序号，直至没有被占用
            index++;
        }
        return id + index;
    }
	
</script>
</body>
</html>