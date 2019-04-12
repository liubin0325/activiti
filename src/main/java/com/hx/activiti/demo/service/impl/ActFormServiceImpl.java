package com.hx.activiti.demo.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hx.activiti.demo.dao.*;
import com.hx.activiti.demo.model.*;
import com.hx.activiti.demo.service.ActFormService;
import com.hx.activiti.demo.util.IDGenerate;
import com.hx.activiti.demo.util.Md5Tool;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-27
 */
@Service
public class ActFormServiceImpl implements ActFormService {

    @Autowired
    private ActCustomFormDao formDao;

    @Autowired
    private ActCustomFormFieldDao formFieldDao;

//    @Autowired
//    private ActCustomModelFormDao modelFormDao;

    @Autowired
    private ActCustomModelExtraDao modelExtraDao;

    @Autowired
    private ActCustomFormNodeDao nodeFormDao;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ActCustomFormDataDao formDataDao;

    @Autowired
    private ActCustomModelDeploymentDao modelDeploymentDao;


    @Override
    public List<ActCustomForm> getList() {
        return formDao.getList();
    }

    @Override
    public void saveActForm(String name, Integer order_id, String desc, Integer type) {
        ActCustomForm form = new ActCustomForm();
        form.setForm_id(IDGenerate.generateId());
        form.setForm_name(name);
        form.setOrder_id(order_id);
        form.setRemark(desc);
        form.setForm_type(type);
        formDao.save(form);
    }

    @Override
    public void saveFormDesign(String formId, String tableData, String content, String design_content) {
        ActCustomForm form = formDao.getById(formId);
        form.setContent(content);
        formDao.update(form);//更新表单内容

        formFieldDao.delByFormId(formId);//删除原表单字段
        List<ActCustomFormField> formFields = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray jsonElements = parser.parse(tableData).getAsJsonArray();
        int orderInx = 0;
        for (JsonElement jsonElement : jsonElements) {
            ActCustomFormField formField = new ActCustomFormField();
            formField.setForm_id(formId);

            JsonObject object = jsonElement.getAsJsonObject();
            String type = object.get("myplugins").getAsString();
            String id = object.get("id").getAsString();
            formField.setField_id(Md5Tool.getMd5(formId + id));
            String param_namechn = object.get("param_namechn").getAsString();
            String datadefault = object.get("datadefault").getAsString();
            String oContent = object.get("content").getAsString();
            formField.setIslist(false);
            switch (type) {
                case "text":
                    formField.setParam_name(id);//英文名称
                    formField.setParam_namechn(param_namechn);//中文名称
                    formField.setDatadefault(datadefault);//默认值，默认选中的
                    formField.setDatatype(object.get("datatype").getAsString());//数据类型
                    formField.setText_size(object.get("size").getAsString());//输入框宽度
                    formField.setText_rows(object.get("maxlength").getAsString());//输入框高度
                    formField.setDatavalue("");//选择值，多个，以英文逗号分隔
                    formField.setContent(oContent);//html内容
                    break;
                case "textarea":
                    formField.setParam_name(id);//英文名称
                    formField.setParam_namechn(param_namechn);//中文名称
                    formField.setDatadefault(datadefault);//默认值，默认选中的
                    formField.setDatatype("");//数据类型
                    formField.setText_size(object.get("cols").getAsString());//输入框宽度
                    formField.setText_rows(object.get("rows").getAsString());//输入框高度
                    formField.setDatavalue("");//选择值，多个，以英文逗号分隔
                    formField.setContent(oContent);//html内容
                    break;
                case "date":
                    formField.setParam_name(id);//英文名称
                    formField.setParam_namechn(param_namechn);//中文名称
                    formField.setDatadefault(datadefault);//默认值，默认选中的
                    formField.setDatatype("");//数据类型
                    formField.setText_size(object.get("size").getAsString());//输入框宽度
                    formField.setText_rows("");//输入框高度
                    formField.setDatavalue("");//选择值，多个，以英文逗号分隔
                    formField.setContent(oContent);//html内容
                    break;
                case "radios":
                    formField.setParam_name(id);//英文名称
                    formField.setParam_namechn(param_namechn);//中文名称
                    formField.setDatadefault(datadefault);//默认值，默认选中的
                    formField.setDatatype("");//数据类型
                    formField.setText_rows("");//输入框宽度
                    formField.setText_size("");//输入框高度
                    formField.setDatavalue(object.get("value").getAsString());//选择值，多个，以英文逗号分隔
                    formField.setContent(oContent);//html内容
                    break;
                case "checkboxs":
                    formField.setParam_name(id);//英文名称
                    formField.setParam_namechn(param_namechn);//中文名称
                    formField.setDatadefault(datadefault);//默认值，默认选中的
                    formField.setDatatype("");//数据类型
                    formField.setText_size("");//输入框宽度
                    formField.setText_rows("");//输入框高度
                    formField.setDatavalue(object.get("value").getAsString());//选择值，多个，以英文逗号分隔
                    formField.setContent(oContent);//html内容
                    break;
                case "select":
                    formField.setParam_name(id);//英文名称
                    formField.setParam_namechn(param_namechn);//中文名称
                    formField.setDatadefault(datadefault);//默认值 默认select的
                    formField.setDatatype("");//数据类型
                    formField.setText_rows("");//输入框宽度
                    formField.setText_size("");//输入框高度
                    formField.setDatavalue(object.get("value").getAsString());//选择值，多个，以英文逗号分隔
                    formField.setContent(oContent);//html内容
                    break;
                case "mylink":
                    formField.setParam_name(id);//英文名称
                    formField.setParam_namechn(param_namechn);//中文名称
                    formField.setDatadefault(datadefault);//默认值
                    formField.setDatatype("");//数据类型
                    formField.setText_size("");//输入框宽度
                    formField.setText_rows("");//输入框高度
                    formField.setDatavalue(object.get("target").getAsString());//打开方式  target
                    formField.setContent(content);//html内容
                    break;
                case "macros":
                    formField.setParam_name(id);//英文名称 字段名称
                    formField.setParam_namechn(param_namechn);//中文名称
                    formField.setDatadefault(datadefault);//默认值
                    formField.setDatatype("");//数据类型
                    formField.setText_size(object.get("size").getAsString());//输入框宽度
                    formField.setText_rows("");//输入框高度
                    formField.setDatavalue("");//选择值
                    formField.setContent(oContent);//html内容
                    break;
                case "listctrl":
                    formField.setParam_name(id);//控件名称，mylistctr+序号
                    formField.setParam_namechn(param_namechn);//中文名称 标题名称，多个以英文逗号分隔
                    formField.setDatadefault("");//默认值
                    formField.setDatatype(object.get("aligntype").getAsString());// aligntype 对齐方式，多个以英文逗号分隔
                    formField.setText_size(object.get("text_size").getAsString());//宽度(%或px)，多个以英文逗号分隔
                    formField.setText_rows(object.get("sumtype").getAsString());//是否合计 1是0否，多个以英文逗号分隔
                    formField.setDatavalue(object.get("param_name").getAsString());//列的 英文名称，多个以英文逗号分隔
                    formField.setContent(oContent);//html内容
                    formField.setIslist(true);
                    break;
                default:
                    break;
            }
            formField.setOrder_id(orderInx);
            orderInx++;
            formFields.add(formField);
        }
        formFieldDao.saveBatch(formFields);


    }

    @Override
    public ActCustomForm getDetail(String formId) {
        return formDao.getById(formId);
    }

    @Override
    public String getStartForm(String procId) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procId).singleResult();
        Model model = repositoryService.createModelQuery().deploymentId(definition.getDeploymentId()).latestVersion().singleResult();
        return getHtml(model.getId(), "start", null);
    }

    @Override
    public String getTaskForm(String procdefId, String procinstId, String taskKey) {
        ProcessDefinition definition = repositoryService.getProcessDefinition(procdefId);
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(procinstId).singleResult();
        ActCustomModelDeployment modelDeployment = modelDeploymentDao.getByDeployment(definition.getDeploymentId());
        Model model = repositoryService.createModelQuery().modelId(modelDeployment.getModel_id()).singleResult();
        ActCustomFormData formData = formDataDao.getByBusinessKey(procinstId, instance.getBusinessKey());
        return getHtml(model.getId(), taskKey, formData);
    }


    private String getHtml(String modelId, String nodeKey, ActCustomFormData formData) {
        //TODO 数据填充 后台完成 or 前台完成？？
        try {
            ActCustomModelExtra modelForm = modelExtraDao.getByModel(modelId);
            ActCustomForm form = modelForm.getCustomForm();
            String html = form.getContent();
            Document doc = Jsoup.parse(html);
            Elements elements = doc.getElementsByAttribute("myplugins");
            List<ActCustomFormNode> nodeForms = nodeFormDao.getByModelAndNode(modelId, nodeKey);
            Map<String, ActCustomFormNode> nodeFormMap = tranListToMap(nodeForms);
            Map<String,String> formDataMap = getFormDataMap(formData);
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                String type = element.attr("myplugins");
                String id = element.attr("id");
                ActCustomFormNode nodeForm = nodeFormMap.get(id);
                boolean isvisible = true;//是否可见
                boolean isedit = true;//是否只读
                boolean isnotnull = false;//是否可为空
                if (nodeForm != null) {
                    isedit = nodeForm.getIsedit();
                    isvisible = nodeForm.getIsvisible();
                    isnotnull = nodeForm.getIsnotnull();
                }
                switch (type) {
                    case "text":
                        if(formDataMap.containsKey(id)) {
                            element.attr("value", formDataMap.get(id));
                        }else {
                            element.attr("value", "");
                        }if (!isedit) {
                            element.attr("readonly", "readonly");
                            element.attr("name","");
                        }
                        if (!isvisible) {
                            hideHtmlNode(element);
                        }
                        break;
                    case "textarea":
                        if(formDataMap.containsKey(id)) {
                            element.html(formDataMap.get(id));
                        }else {
                            element.html("");
                        }
                        if (!isedit) {
                            element.attr("readonly", "readonly");
                            element.attr("name","");
                        }
                        if (!isvisible){
                            hideHtmlNode(element);
                        }
                        break;
                    case "date":
                        if(formDataMap.containsKey(id)) {
                            element.attr("value", formDataMap.get(id));
                        }else {
                            element.attr("value", "");
                        }
                        if (!isedit) {
                            element.attr("readonly", "readonly");
                            element.attr("name","");
                        }
                        if (!isvisible) {
                            hideHtmlNode(element);
                        }
                        break;
                    case "radios":
                        if (!isvisible) {
                            hideHtmlNode(element);
                        }
                        break;
                    case "checkboxs":
                        if (!isvisible) {
                            hideHtmlNode(element);
                        }
                        break;
                    case "select":
                        if (!isvisible) {
                            hideHtmlNode(element);
                        }
                        break;
                    case "mylink":
                        if (!isvisible) {
                            hideHtmlNode(element);
                        }
                        break;
                    case "macros":
                        if (!isvisible) {
                            hideHtmlNode(element);
                        }
                        break;
                    default:
                        break;
                }
            }
            return doc.body().html();
        }catch (Exception ex){
            return "";
        }

    }

    private Map<String, String> getFormDataMap(ActCustomFormData formData) {
        Map<String, String> result = new HashMap<>();
        if (formData == null || StringUtils.isEmpty(formData.getData())) {
            return result;
        }

        String data = formData.getData();

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(data).getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            String name = object.get("name").getAsString();
            String value = object.get("value").getAsString();
            result.put(name, value);
        }
        return result;
    }

    /**
     * 转换list 为Map ,以fieldName 为Map key
     *
     * @param nodeForms
     * @return
     */
    private Map<String, ActCustomFormNode> tranListToMap(List<ActCustomFormNode> nodeForms) {
        if (nodeForms == null || nodeForms.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, ActCustomFormNode> result = new HashMap<>();
        for (ActCustomFormNode nodeForm : nodeForms) {
            ActCustomFormField formField = nodeForm.getField();
            if (formField != null) {
                result.put(formField.getParam_name(), nodeForm);
            }
        }
        return result;
    }

    /**
     * 隐藏表单html节点
     *
     * @param element
     */
    private void hideHtmlNode(Element element) {
        try {
            boolean isTd = "td".equals(element.parent().tagName().toLowerCase());
            boolean isTr = "tr".equals(element.parent().parent().tagName().toLowerCase());
            if (isTd && isTr) {
                element.parent().parent().addClass("display_none");//TODO 需前台配合显示隐藏
            }
        } catch (Exception ex) {
            element.addClass("display_none");
        }

    }
}
