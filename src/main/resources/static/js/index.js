var formTable;
var modelTable;
var processTable;
var taskTable;
layui.use(['form', 'table', 'element'], function () {
    var table = layui.table;
    var form = layui.form;
    var element = layui.element;
    form.on('submit(demo1)', function (data) {
        console.log(data.field);
        //TODO
        return true;
    });
    formTable = table.render({
        elem: '#formList'
        , url: '/forms/list'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , cols: [[
            {field: 'form_id', title: 'ID'}
            , {field: 'form_name',title: '名称'}
            , {field: 'remark', title: '描述'}
            , {fixed: 'right', align:'center', toolbar: '#formBar'}
        ]],
        response: {
            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        },
        parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": 200, //解析接口状态
                "message": res.message, //解析提示文本
                "data": res.data //解析数据列表
            };
        }
    });

    //Rest-api 参照 https://www.activiti.org/userguide/#_rest_api
    modelTable = table.render({
        elem: '#modelList'
        , url: '/repository/models?latest=true'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,page: true //开启分页
        , cols: [[
            {field: 'id', title: 'ID'}
            , {field: 'name',title: '名称'}
            , {field: 'version', title: '版本'}
            , {fixed: 'right', align:'center', toolbar: '#modelBar'}

        ]],

        response: {
            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        },
        parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": 200, //解析接口状态
                "message": res.message, //解析提示文本
                "data": res.data //解析数据列表
            };
        }
    });

    processTable = table.render({
        elem: '#processList'
        , url: '/repository/process-definitions?latest=true'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , cols: [[
            {field: 'id', title: 'ID'}
            , {field: 'name',title: '名称'}
            , {field: 'deploymentId', title: 'deploymentId'}
            , {fixed: 'right', align:'center', toolbar: '#processBar'}

        ]],
        response: {
            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        },
        parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": 200, //解析接口状态
                "message": res.message, //解析提示文本
                "data": res.data //解析数据列表
            };
        }
    });

    taskTable= table.render({
        elem: '#taskList'
        , url: '/runtime/tasks'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , cols: [[
            {field: 'id', title: 'ID'}
            , {field: 'name',title: '名称'}
            , {field: 'assignee', title: '受理人'}
            , {field: 'createTime', title: 'createTime'}
            , {field: 'owner', title: '委托人'}

            , {fixed: 'right', align:'center', toolbar: '#taskBar'}

        ]],
        response: {
            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        },
        parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": 200, //解析接口状态
                "message": res.message, //解析提示文本
                "data": res.data //解析数据列表
            };
        }
    });
    table.on("tool(formList)",function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var id = data.form_id;
        console.log(id);
        if(layEvent=="edit"){
            ue.setContent('');
            $.ajax({
                url: "/forms/detail",
                type: "get",
                dataType: "json",
                data:{id:id},
                success: function (data) {
                   if(data.code==0){
                       if(data.data){
                           ue.setContent(data.data.content);
                       }
                   }
                }
                // renderForm
            })
        }
    });
    table.on("tool(processList)",function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var procId = data.id;
        $("#procId").val(procId);
        if(layEvent=="startForm"){
            $("#renderForm").empty();
            $.ajax({
                url: "/activiti/startForm",
                type: "post",
                dataType: "json",
                data:{id:procId},
                success: function (data) {
                    $("#renderForm").append(data.data);
                    $("#renderForm").append('');
                }
                // renderForm
            })
        }
    });

    table.on("tool(taskList)",function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var taskId = data.id;
        $("#taskId").val(taskId);
        if(layEvent=="startForm"){
            $("#renderTaskForm").empty();
            $.ajax({
                url: "/activiti/taskForm",
                type: "post",
                dataType: "json",
                data:{id:taskId},
                success: function (data) {
                    $("#renderTaskForm").append(data.data);
                }
            })
        }
    });
    table.on("tool(modelList)",function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if(layEvent=="edit"){
            window.open("/editor?modelId="+data.id);
        }else if(layEvent=="deploy"){
            $.ajax({
                url: "/models/deploy",
                type: "post",
                dataType: "json",
                data:{id:data.id},
                success: function (data) {
                    if(data.code==0){
                        layer.msg("发布成功");
                    }else{
                        layer.msg(data.message);
                    }
                }
            });
        }else if(layEvent=="delete"){
            $.ajax({
                url: "/models/deploy",
                type: "post",
                dataType: "json",
                data:{id:data.id},
                success: function (data) {
                    if(data.code==0){
                        layer.msg("发布成功");
                    }else{
                        layer.msg(data.message);
                    }
                }
            });
        }
    });
    form.render();
});
$(function () {
    $.ajax({
        url: "/forms/list",
        type: "get",
        dataType: "json",
        success: function (data) {
            if(data.data){
                $.each(data.data,function (idx, obj) {
                    $("#modelForm").append('<option value="'+obj.form_id+'">'+obj.form_name+'</option>')
                });
                layui.form.render()
            }
        }
    });
});

function startProcess() {
    var procId = $("#procId").val();
    if(procId==undefined)
        return;
    var arrFormData = $("#renderForm").serializeArray();
    var formData = JSON.stringify(arrFormData);

    $.ajax({
        url: "/activiti/startWorkflow",
        type: "post",
        dataType: "json",
        data: {id: procId, data:formData},
        success: function (data) {
            layer.msg(data.message);
        }
    });
}


function checkWorkFlow(){
    var status = $("#status").val();
    var comment = $("#comment").val();
    var taskId = $("#taskId").val();
    $.ajax({
        url: "/activiti/checkWorkFlow",
        type: "post",
        dataType: "json",
        data: {id: taskId, status:status, comment:comment},
        success: function (data) {
            layer.msg(data.message);
        }
    });
}














var ue = UE.getEditor('editor', {
        textarea: 'design_content',
        toolbars: [[
            'source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'removeformat', '|',
            'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', '|', 'fontfamily', 'fontsize', '|', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'horizontal', 'spechars', '|',
            'inserttable', 'deletetable', 'mergecells', 'splittocells', '|',
            'pagebreak', 'template', 'background', '|']],
        autoHeightEnabled: false,
        autoFloatEnabled: true//,
        //关闭字数统计
        //wordCount:false,
        //关闭elementPath
        //elementPathEnabled:false,
    }
);

function parse_form(template, fields) {
    //正则  radios|checkboxs|select 匹配的边界 |--|  因为当使用 {} 时js报错
    var preg = /(\|-<span(((?!<span).)*myplugins=\"(radios|checkboxs)\".*?)>(.*?)<\/span>-\||<(input|textarea|select).*?(<\/select>|<\/textarea>|\/>))/gi,

        preg_attr = /(\w+)=\"(.?|.+?)\"/gi,
        preg_group = /<input.*?\/>/gi;
    if (!fields) fields = 0;
    //alert(preg);
    var template_parse = template, template_data = new Array(), add_fields = new Object(), checkboxs = 0;

    var pno = 0;
    template.replace(preg, function (plugin, p1, p2, p3, p4, p5, p6) {
        var parse_attr = new Array(), attr_arr_all = new Object(), name = '', select_dot = '', is_new = false;
        var p0 = plugin;
        var tag = p6 ? p6 : p4;
        //alert(plugin + " \n- t1 - "+p1 +" \n-2- " +p2+" \n-3- " +p3+" \n-4- " +p4+" \n-5- " +p5+" \n-6- " +p6);

        if (tag == 'radios' || tag == 'checkboxs') {
            plugin = p2;
        } else if (tag == 'select') {
            plugin = plugin.replace('|-', '');
            plugin = plugin.replace('-|', '');
        }
        plugin.replace(preg_attr, function (str0, attr, val) {
            if (attr == 'name') {
                if (val == 'leipiNewField') {
                    is_new = true;
                    fields++;
                    val = 'data_' + fields;
                }
                name = val;
            }

            if (tag == 'select' && attr == 'value') {
                if (!attr_arr_all[attr]) attr_arr_all[attr] = '';
                attr_arr_all[attr] += select_dot + val;
                select_dot = ',';
            } else {
                attr_arr_all[attr] = val;
            }
            var oField = new Object();
            oField[attr] = val;
            parse_attr.push(oField);
        })
        /*alert(JSON.stringify(parse_attr));return;*/
        if (tag == 'checkboxs') /*复选组  多个字段 */
        {
            plugin = p0;
            plugin = plugin.replace('|-', '');
            plugin = plugin.replace('-|', '');
            var name = 'checkboxs_' + checkboxs;
            attr_arr_all['parse_name'] = name;
            attr_arr_all['name'] = '';
            attr_arr_all['value'] = '';

            attr_arr_all['content'] = '<span myplugins="checkboxs"  title="' + attr_arr_all['title'] + '">';
            var dot_name = '', dot_value = '';
            p5.replace(preg_group, function (parse_group) {
                var is_new = false, option = new Object();
                parse_group.replace(preg_attr, function (str0, k, val) {
                    if (k == 'name') {
                        if (val == 'leipiNewField') {
                            is_new = true;
                            fields++;
                            val = 'data_' + fields;
                        }

                        attr_arr_all['name'] += dot_name + val;
                        dot_name = ',';

                    }
                    else if (k == 'value') {
                        attr_arr_all['value'] += dot_value + val;
                        dot_value = ',';

                    }
                    option[k] = val;
                });

                if (!attr_arr_all['options']) attr_arr_all['options'] = new Array();
                attr_arr_all['options'].push(option);
                //if(!option['checked']) option['checked'] = '';
                var checked = option['checked'] != undefined ? 'checked="checked"' : '';
                attr_arr_all['content'] += '<input type="checkbox" name="' + option['name'] + '" value="' + option['value'] + '"  ' + checked + '/>' + option['value'] + '&nbsp;';

                if (is_new) {
                    var arr = new Object();
                    arr['name'] = option['name'];
                    arr['myplugins'] = attr_arr_all['myplugins'];
                    add_fields[option['name']] = arr;

                }

            });
            attr_arr_all['content'] += '</span>';

            //parse
            template = template.replace(plugin, attr_arr_all['content']);
            template_parse = template_parse.replace(plugin, '{' + name + '}');
            template_parse = template_parse.replace('{|-', '');
            template_parse = template_parse.replace('-|}', '');
            template_data[pno] = attr_arr_all;
            checkboxs++;

        } else if (name) {
            if (tag == 'radios') /*单选组  一个字段*/
            {
                plugin = p0;
                plugin = plugin.replace('|-', '');
                plugin = plugin.replace('-|', '');
                attr_arr_all['value'] = '';
                attr_arr_all['content'] = '<span myplugins="radios" name="' + attr_arr_all['name'] + '" title="' + attr_arr_all['title'] + '">';
                var dot = '';
                p5.replace(preg_group, function (parse_group) {
                    var option = new Object();
                    parse_group.replace(preg_attr, function (str0, k, val) {
                        if (k == 'value') {
                            attr_arr_all['value'] += dot + val;
                            dot = ',';
                        }
                        option[k] = val;
                    });
                    option['name'] = attr_arr_all['name'];
                    if (!attr_arr_all['options']) attr_arr_all['options'] = new Array();
                    attr_arr_all['options'].push(option);
                    //if(!option['checked']) option['checked'] = '';
                    var checked = option['checked'] != undefined ? 'checked="checked"' : '';
                    attr_arr_all['content'] += '<input type="radio" name="' + attr_arr_all['name'] + '" value="' + option['value'] + '"  ' + checked + '/>' + option['value'] + '&nbsp;';

                });
                attr_arr_all['content'] += '</span>';

            } else {
                attr_arr_all['content'] = is_new ? plugin.replace(/leipiNewField/, name) : plugin;
            }
            //attr_arr_all['itemid'] = fields;
            //attr_arr_all['tag'] = tag;
            template = template.replace(plugin, attr_arr_all['content']);
            template_parse = template_parse.replace(plugin, '{' + name + '}');
            template_parse = template_parse.replace('{|-', '');
            template_parse = template_parse.replace('-|}', '');
            if (is_new) {
                var arr = new Object();
                arr['name'] = name;
                arr['myplugins'] = attr_arr_all['myplugins'];
                add_fields[arr['name']] = arr;
            }
            template_data[pno] = attr_arr_all;


        }
        pno++;
    })
    return JSON.stringify(template_data);
}