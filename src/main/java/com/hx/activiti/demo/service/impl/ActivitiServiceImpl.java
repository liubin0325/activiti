package com.hx.activiti.demo.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hx.activiti.demo.activiti.ActivitiCallBackBean;
import com.hx.activiti.demo.activiti.ActivitiConstants;
import com.hx.activiti.demo.activiti.ActivitiUtils;
import com.hx.activiti.demo.activiti.cmd.*;
import com.hx.activiti.demo.dao.*;
import com.hx.activiti.demo.model.*;
import com.hx.activiti.demo.model.vo.ActivitiProcessModel;
import com.hx.activiti.demo.service.ActivitiExpressService;
import com.hx.activiti.demo.service.ActivitiService;
import com.hx.activiti.demo.util.HxException;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-28
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiService.class);


    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActCustomFormFieldDao formFieldDao;

    @Autowired
    private ActCustomFormDao formDao;

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ActCustomFormDataDao formDataDao;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ActCustomModelDeploymentDao modelDeploymentDao;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngineConfiguration engineConfiguration;

    @Autowired
    private ActCustomFormListDataDao listDataDao;

    @Autowired
    private ActCustomProcinstExtraDao extraDao;

    @Autowired
    private ActCustomModelExtraDao modelExtraDao;


    @Override
    public List<Model> getModelList() {
        return repositoryService.createModelQuery().latestVersion().list();
    }

    @Override
    public List<Deployment> getDeployment() {
        return repositoryService.createDeploymentQuery().list();
    }

    @Override
    public List<ActivitiProcessModel> getProcessDefinition() {
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        List<ActivitiProcessModel> entities = new ArrayList<>();
        if (definitions != null && !definitions.isEmpty()) {
            for (ProcessDefinition definition : definitions) {
                entities.add(new ActivitiProcessModel(definition));
            }
        }
        return entities;
    }

    @Override
    public String createModel(String name, String desc, String form, String callback) throws HxException {

        //验证回调方法
        Map map = ActivitiUtils.getCallbackMethod(callback);
        try {
            Model model = repositoryService.newModel();
            //设置一些默认信息
            String key = UUID.randomUUID().toString();
            ObjectNode modelNode = objectMapper.createObjectNode();
            modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, desc);
            modelNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            model.setName(name);
            model.setKey(key);
            model.setCategory("测试分类");
            model.setMetaInfo(modelNode.toString());
            repositoryService.saveModel(model);
            String id = model.getId();
            //完善ModelEditorSource
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace",
                    "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.set("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
            ActCustomForm customForm = formDao.getById(form);
            ActCustomModelExtra modelExtra = new ActCustomModelExtra();
            modelExtra.setCustomForm(customForm);
            modelExtra.setModel_id(id);
            if (map != null) {
                modelExtra.setCallback(callback);
            }
            modelExtraDao.save(modelExtra);
            return id;
        } catch (Exception ex) {
            throw new HxException(-1, "创建模型失败");
        }
    }

    @Override
    public void deployProcess(String id) throws Exception {
        //获取模型
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            throw new HxException(-1, "模型为空");
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);

        if (model.getProcesses().size() == 0) {
            throw new HxException(-1, "数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.name(modelData.getName());
        builder.category(modelData.getCategory());
        builder.addString(processName, new String(bpmnBytes, "UTF-8"));
        Deployment deployment = builder.deploy();
        modelDeploymentDao.save(new ActCustomModelDeployment(deployment.getId(), id));
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
    }

    @Override
    public Object getStartFormData(String procId) throws Exception {
        return formService.getRenderedStartForm(procId, "hx_form_engine");
    }

    @Override
    public Object getTaskFormData(String taskId) throws Exception {
        return formService.getRenderedTaskForm(taskId, "hx_form_engine");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void startWorkFlow(String procId, String formData, String listData, String extraData, String instKey, String keyvalue) {
        String businessKey = UUID.randomUUID().toString();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procId).latestVersion().singleResult();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) processDefinition;
        Model model = repositoryService.createModelQuery().deploymentId(definitionEntity.getDeploymentId()).latestVersion().singleResult();
        String modelId = model.getId();
        ActCustomModelExtra modelForm = modelExtraDao.getByModel(modelId);
        ActCustomForm form = modelForm.getCustomForm();
        String userId = "0";
        String userName = "用户_0";
        Map<String, Object> variables = new HashMap<>(2);
        variables.put("StartName", userName);
        variables.put("StartId", userId);
        //TODO 判断inst_key是否重复提交
        ActCustomProcinstExtra extra = new ActCustomProcinstExtra();
        extra.setApply_user(userId);
        extra.setBusiness_key(businessKey);
        extra.setApply_time(new Date());
        extra.setModel_id(modelId);
        identityService.setAuthenticatedUserId(userId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(procId, businessKey, variables);
        extra.setKeyvalue(keyvalue);
        extra.setInst_key(instKey);
        extra.setProcinst_id(processInstance.getId());
        extra.setDept_id("");
        List<ActCustomFormField> fields = formFieldDao.getByForm(form.getForm_id());
        extra.setProc_content(formDataToString(formData, fields));
        extraDao.save(extra);
        ActCustomFormData actCustomFormData = new ActCustomFormData(processInstance.getId(), businessKey, form.getForm_id(), formData, extraData, 0);
        formDataDao.save(actCustomFormData);
        /**
         * 判断是否是用户申请步骤，是则直接完成该任务
         */
        if (taskService.createTaskQuery().processInstanceId(processInstance.getId()).count() == 1) {
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            if (task != null && userId.equals(task.getAssignee())) {
                taskService.complete(task.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void checkWorkFlow(String taskId, Integer status, String comments, String formData) throws HxException {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new HxException(-1, "任务为空");
        }

        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        taskService.addComment(taskId, execution.getProcessInstanceId(), comments);
        ActCustomFormData customFormData = formDataDao.getByBusinessKey(execution.getProcessInstanceId(), processInstance.getBusinessKey());
        if (customFormData == null) {
            throw new HxException(-1, "表单数据不存在");
        } else {
            if (StringUtils.isNotEmpty(formData)) {
                JsonArray newData = parseFormData(formData);
                JsonArray oldData = parseFormData(customFormData.getData());
                JsonArray array = new JsonArray();
                for (int i = 0; i < newData.size(); i++) {
                    boolean exist = false;
                    String name = newData.get(i).getAsJsonObject().get("name").getAsString();
                    //TODO 双层循环 优化
                    for (int j = 0; j < oldData.size(); j++) {
                        String oldName = oldData.get(j).getAsJsonObject().get("name").getAsString();
                        if (name.equals(oldName)) {
                            exist = true;
                            oldData.get(j).getAsJsonObject().remove("value");
                            oldData.get(j).getAsJsonObject().add("value", newData.get(i).getAsJsonObject().get("value"));
                            break;
                        }
                    }
                    if (!exist) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.add("name", newData.get(i).getAsJsonObject().get("name"));
                        jsonObject.add("value", newData.get(i).getAsJsonObject().get("value"));
                        array.add(jsonObject);
                    }
                }
                if (array.size() > 0) {
                    oldData.addAll(array);
                }
                formDataDao.update(new ActCustomFormData(customFormData.getProcinst_id(), customFormData.getBusiness_key(), oldData.toString()));
            }
        }
        //取得流程定义
        HistoricTaskInstance hisTask = historyService
                .createHistoricTaskInstanceQuery().taskId(taskId)
                .singleResult();
        //取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(hisTask.getProcessDefinitionId());
        customFormData = formDataDao.getByBusinessKey(execution.getProcessInstanceId(), processInstance.getBusinessKey());

        ActCustomProcinstExtra extra = extraDao.getByBusinessKey(execution.getProcessInstanceId(), processInstance.getBusinessKey());

        int callbackStatus = 0;
        switch (status) {
            case 1://同意
                taskService.complete(taskId);
                updateComleteReason(execution.getProcessInstanceId(), ActivitiConstants.DELETE_REASON_COMPLETE);
                callbackStatus = callback(execution.getProcessInstanceId(), customFormData);
                break;
            case 2://退回上一个节点
                List<PvmTransition> pvmTransitions = definition.findActivity(hisTask.getTaskDefinitionKey()).getIncomingTransitions();
                PvmTransition pvmTransition = pvmTransitions.get(0);
                PvmActivity pvmActivity = pvmTransition.getSource();
                if ("startEvent".equals(pvmActivity.getProperty("type"))) {
                    throw new HxException("上一节点为开始节点，无法驳回");
                }
                if (!"userTask".equals(pvmActivity.getProperty("type"))) {
                    pvmActivity = getPreUserTask(definition, hisTask.getTaskDefinitionKey());
                }
                if (pvmActivity == null) {
                    throw new HxException("无法找到上一节可以驳回的节点");
                }
                if (isMultiInstance(taskId)) {//会签节点处理
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new MultiJumpMiddleCmd(taskId, pvmActivity.getId(), ActivitiConstants.DELETE_REASON_TURN_BACK));
                } else {
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new TaskJumpStartCmd(taskId, pvmActivity.getId(), ActivitiConstants.DELETE_REASON_TURN_BACK));
                }
                break;
            case 3://退回至申请人
                PvmActivity pvmActivity1 = findFirstActivity(definition);
                if (pvmActivity1 == null) {
                    throw new HxException("无法找到用户申请的节点");
                }
                if (isMultiInstance(taskId)) {//会签节点处理
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new MultiJumpStartCmd(taskId, pvmActivity1.getId(), ActivitiConstants.DELETE_REASON_JUMP));
                } else {
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new TaskJumpStartCmd(taskId, pvmActivity1.getId(), ActivitiConstants.DELETE_REASON_JUMP));
                }
                break;
            case 4://同意---并结束流程
                List<ActivityImpl> activities = definition.getActivities();
                ActivityImpl endActiviti;

                endActiviti = activities.stream().filter(activity ->
                        "endEvent".equals(activity.getProperty("type"))
                ).findFirst().get();

                if (endActiviti == null) {
                    throw new HxException("流程定义不完全，无法找到结束节点");
                }
                ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                        new TaskJumpEndCmd(taskId, endActiviti.getId(), ActivitiConstants.DELETE_REASON_COMPLETE));
                updateComleteReason(execution.getProcessInstanceId(), ActivitiConstants.DELETE_REASON_COMPLETE);
                callbackStatus = callback(execution.getProcessInstanceId(), customFormData);
                break;
            case 5://不同意---并强制结束流程
                List<ActivityImpl> activities1 = definition.getActivities();
                ActivityImpl endActiviti1;

                endActiviti1 = activities1.stream().filter(activity ->
                        "endEvent".equals(activity.getProperty("type"))
                ).findFirst().get();

                if (endActiviti1 == null) {
                    throw new HxException("流程定义不完全，无法找到结束节点");
                }
                ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                        new TaskJumpEndCmd(taskId, endActiviti1.getId(), ActivitiConstants.DELETE_REASON_FORCE_COMPLETE));
                ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(new UpdateHisProcessInstanceReasonCommand(execution.getProcessInstanceId(), ActivitiConstants.DELETE_REASON_FORCE_COMPLETE));
                callbackStatus = 2;
                break;
            default:
                break;
        }
        hisTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        extra.setStatus(callbackStatus);
        extra.setPre_task_user(hisTask.getAssignee());
        extra.setPre_task_time(hisTask.getEndTime());
        extraDao.update(extra);

    }

    @Override
    public List<Comment> getTaskComments(String taskId) {
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        List<Comment> comments = taskService.getProcessInstanceComments(task.getProcessInstanceId());
        Collections.sort(comments, (c1, c2) -> c1.getTime().getTime() > c2.getTime().getTime() ? 1 : -1);
        return comments;
    }

    @Override
    public InputStream getFlowImgByInstantId(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            return null;
        }
        // 获取流程图输入流
        InputStream inputStream = null;
        // 查询历史
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance.getEndTime() != null) { // 该流程已经结束
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(historicProcessInstance.getProcessDefinitionId())
                    .singleResult();
            inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
        } else {
            // 查询当前的流程实例
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
            ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
            List<String> highLightedFlows = new ArrayList<>();
            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceStartTime().asc().list();
            List<String> historicActivityInstanceList = new ArrayList<String>();
            for (HistoricActivityInstance hai : historicActivityInstances) {
                historicActivityInstanceList.add(hai.getActivityId());
            }
            List<String> highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
            historicActivityInstanceList.addAll(highLightedActivities);
            for (ActivityImpl activity : processDefinitionEntity.getActivities()) {
                int index = historicActivityInstanceList.indexOf(activity.getId());
                if (index >= 0 && index + 1 < historicActivityInstanceList.size()) {
                    List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();
                    for (PvmTransition pvmTransition : pvmTransitionList) {
                        String destinationFlowId = pvmTransition.getDestination().getId();
                        if (destinationFlowId.equals(historicActivityInstanceList.get(index + 1))) {
                            highLightedFlows.add(pvmTransition.getId());
                        }
                    }
                }
            }
            ProcessDiagramGenerator diagramGenerator = engineConfiguration.getProcessDiagramGenerator();
            List<String> activeActivityIds = new ArrayList<>();
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
            for (Task task : tasks) {
                activeActivityIds.add(task.getTaskDefinitionKey());
            }
            inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds, highLightedFlows, "宋体", "宋体", null, null, 1.0);
        }
        return inputStream;
    }

    /**
     * 更新实例完成状态
     *
     * @param procinstId
     * @param reason
     */
    private void updateComleteReason(String procinstId, String reason) {
        HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procinstId).singleResult();
        if (instance.getEndTime() == null) {
            return;
        }
        ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(new UpdateHisProcessInstanceReasonCommand(procinstId, reason));


    }

    private int callback(String procInstId, ActCustomFormData formData) {
        HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult();
        if (instance.getEndTime() == null) {
            return 0;
        }
        int status = 1;
        ActCustomModelDeployment modelDeployment = modelDeploymentDao.getByDeployment(instance.getDeploymentId());
        ActCustomModelExtra modelExtra = modelExtraDao.getByModel(modelDeployment.getModel_id());
        List<ActCustomFormListData> listDatas = listDataDao.getByProc(procInstId, formData.getBusiness_key());
        String listData = null;
        if (listDatas != null && !listDatas.isEmpty()) {
            JsonArray array = new JsonArray();
            JsonParser parser = new JsonParser();
            for (ActCustomFormListData data : listDatas) {
                String strData = data.getList_data();
                array.add(parser.parse(strData));
            }
            listData = array.toString();
        }
        if (modelExtra != null && StringUtils.isNotEmpty(modelExtra.getCallback())) {
            try {
                Map<String, Object> methodResult = ActivitiUtils.getCallbackMethod(modelExtra.getCallback());
                if (methodResult != null) {
                    Method method = (Method) methodResult.get("method");
                    Object service = methodResult.get("service");
                    ActivitiCallBackBean callBackBean = new ActivitiCallBackBean(instance.getId(),
                            formData.getData(),
                            listData,
                            formData.getData_extra(),
                            instance.getStartUserId(),
                            Calendar.getInstance().getTime());
                    try {
                        method.invoke(service, callBackBean);
                        status = 6;
                    } catch (Exception e) {
                        status = 7;
                        logger.error("invoke method error", e);
                    }

                } else {
                    status = 7;
                    logger.error(modelExtra.getCallback() + " not found");
                }
            } catch (HxException ex) {
                status = 7;
                logger.error("find callback method error", ex);
            }
        } else {
            logger.debug("model " + modelDeployment.getModel_id() + " not found ActCustomModelCallback");
        }
        return status;

    }

    /**
     * @param taskId 任务节点id
     * @return boolean 是否是会签
     * @Description:(根据任务节点id判断该节点是否为会签节点)
     */

    private boolean isMultiInstance(String taskId) {
        boolean flag = false;
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        if (task != null) {
            // 获取流程定义id
            String processDefinitionId = task.getProcessDefinitionId();

            ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
                    .getProcessDefinition(processDefinitionId);

            // 根据活动id获取活动实例
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(task.getTaskDefinitionKey());

            if (activityImpl.getActivityBehavior() instanceof ParallelMultiInstanceBehavior) {
                ParallelMultiInstanceBehavior behavior = (ParallelMultiInstanceBehavior) activityImpl.getActivityBehavior();
                if (behavior != null && behavior.getCollectionExpression() != null) {
                    flag = true;
                }
            } else if (activityImpl.getActivityBehavior() instanceof SequentialMultiInstanceBehavior) {
                SequentialMultiInstanceBehavior behavior = (SequentialMultiInstanceBehavior) activityImpl.getActivityBehavior();
                if (behavior != null && behavior.getCollectionExpression() != null) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    private PvmActivity findFirstActivity(ProcessDefinitionEntity definition) {

        ActivityImpl startActivity = definition.getInitial();
        PvmTransition pvmTransition = startActivity.getOutgoingTransitions().get(0);
        PvmActivity targetActivity = pvmTransition.getDestination();
        if (!"userTask".equals(targetActivity.getProperty("type"))) {
            return null;
        }
        return targetActivity;
    }

    private JsonArray parseFormData(String formData) {
        JsonParser parser = new JsonParser();
        return parser.parse(formData).getAsJsonArray();
    }

    /**
     * 获取之前最近的一个UserTask
     *
     * @param definition
     * @param taskDefinitionKey
     * @return
     */
    private PvmActivity getPreUserTask(ProcessDefinitionEntity definition, String taskDefinitionKey) {
        int whileCount = 0;
        PvmActivity pvmActivity = null;
        try {
            while (true) {
                whileCount++;
                ActivityImpl activity = definition.findActivity(taskDefinitionKey);
                if (activity.getIncomingTransitions().size() > 0) {
                    PvmTransition pvmTransition = activity.getIncomingTransitions().get(0);
                    pvmActivity = pvmTransition.getSource();
                    String type = (String) pvmActivity.getProperty("type");
                    taskDefinitionKey = pvmActivity.getId();
                    if ("userTask".equals(type) || whileCount > 5) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (Exception ex) {

        }
        return pvmActivity;
    }

    private String formDataToString(String formData, List<ActCustomFormField> fields) {
        if (StringUtils.isEmpty(formData)) {
            return null;
        }
        Map<String, String> fieldMap = new HashMap<>(fields.size());
        fields.forEach(formField -> {
            fieldMap.put(formField.getParam_name(), formField.getParam_namechn());
        });
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(formData).getAsJsonArray();
        List<String> contentList = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            String name = object.get("name").getAsString();
            String value = object.get("value").getAsString();

            if (fieldMap.containsKey(name)) {
                contentList.add(fieldMap.get(name) + ":" + value);
            } else {
                contentList.add(name + ":" + value);
            }
        }
        return String.join(",", contentList);

    }
}
