package com.hx.activiti.demo.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hx.activiti.demo.activiti.ActivitiConstants;
import com.hx.activiti.demo.activiti.cmd.MultiJumpCmd;
import com.hx.activiti.demo.activiti.cmd.TaskJumpCmd;
import com.hx.activiti.demo.dao.ActCustomFormDao;
import com.hx.activiti.demo.dao.ActCustomFormDataDao;
import com.hx.activiti.demo.dao.ActCustomModelDeploymentDao;
import com.hx.activiti.demo.dao.ActCustomModelFormDao;
import com.hx.activiti.demo.model.ActCustomForm;
import com.hx.activiti.demo.model.ActCustomFormData;
import com.hx.activiti.demo.model.ActCustomModelDeployment;
import com.hx.activiti.demo.model.ActCustomModelForm;
import com.hx.activiti.demo.model.vo.ActivitiProcessModel;
import com.hx.activiti.demo.service.ActivitiService;
import com.hx.activiti.demo.service.ActivitiUserService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-28
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActCustomModelFormDao modelFormDao;

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
    private ActivitiUserService activitiUserService;
    @Autowired
    private ProcessEngineConfiguration engineConfiguration;


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
    public String createModel(String name, String desc, String form) throws HxException {

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
            editorNode.put("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
            ActCustomForm customForm = formDao.getById(form);
            ActCustomModelForm modelForm = new ActCustomModelForm();
            modelForm.setForm(customForm);
            modelForm.setModel_id(id);
            modelFormDao.save(modelForm);
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
    public List<Task> getTaskList() {
        return taskService.createTaskQuery().list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void startWorkFlow(String procId, String formData, String listData, String extraData) {
        String businessKey = UUID.randomUUID().toString();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procId).latestVersion().singleResult();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) processDefinition;
        Model model = repositoryService.createModelQuery().deploymentId(definitionEntity.getDeploymentId()).latestVersion().singleResult();
        String modelId = model.getId();
        ActCustomModelForm modelForm = modelFormDao.getByModelId(modelId);
        ActCustomForm form = modelForm.getForm();
        ActCustomFormData actCustomFormData = new ActCustomFormData(procId, businessKey, form.getForm_id(), formData, listData, extraData);
        String userId = "0";
        String userName = "用户_0";
        Map<String, Object> variables = new HashMap<>();
        variables.put("StartName", userName);
        variables.put("StartId", userId);
        variables.put("userService", activitiUserService);
        formDataDao.save(actCustomFormData);
        identityService.setAuthenticatedUserId(userId);//TODO 发起用户
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(procId, businessKey, variables);

        //第一步 默认为用户申请表单 直接跳过
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void checkWorkFlow(String taskId, Integer status, String comments, String formData) throws HxException {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null)
            throw new HxException(-1, "任务为空");

        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        taskService.addComment(taskId, execution.getProcessInstanceId(), comments);
        ActCustomFormData customFormData = formDataDao.getByBusinessKey(execution.getProcessDefinitionId(), processInstance.getBusinessKey());
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
                formDataDao.update(new ActCustomFormData(customFormData.getProcdef_id(), customFormData.getBusiness_key(), oldData.toString()));
            }
        }
        //取得流程定义
        HistoricTaskInstance hisTask = historyService
                .createHistoricTaskInstanceQuery().taskId(taskId)
                .singleResult();
        //取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(hisTask.getProcessDefinitionId());
        switch (status) {
            case 1://同意
                taskService.complete(taskId);
                break;
            case 2://退回上一个节点
                List<PvmTransition> pvmTransitions = definition.findActivity(hisTask.getTaskDefinitionKey()).getIncomingTransitions();
                PvmTransition pvmTransition = pvmTransitions.get(0);
                PvmActivity pvmActivity = pvmTransition.getSource();
                if (pvmActivity.getProperty("type").equals("startEvent")) {
                    throw new HxException("上一节点为开始节点，无法驳回");
                }
                if (!pvmActivity.getProperty("type").equals("userTask")) {
                    pvmActivity = getPreUserTask(definition, hisTask.getTaskDefinitionKey());
                }

                if (pvmActivity == null) {
                    throw new HxException("无法找到上一节可以驳回的节点");
                }

                if (isMultiInstance(taskId)) {//会签节点处理
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new MultiJumpCmd(taskId, pvmActivity.getId(), ActivitiConstants.DELETE_REASON_TURN_BACK));
                } else {
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new TaskJumpCmd(taskId, pvmActivity.getId(), null, ActivitiConstants.DELETE_REASON_TURN_BACK));
                }
                break;
            case 3://退回至申请人
                if (isMultiInstance(taskId)) {//会签节点处理
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new MultiJumpCmd(taskId, definition.getInitial().getId(), ActivitiConstants.DELETE_REASON_JUMP));
                } else {
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new TaskJumpCmd(taskId, definition.getInitial().getId(), null, ActivitiConstants.DELETE_REASON_JUMP));
                }
                break;
            case 4://同意---并结束流程
                List<ActivityImpl> activities = definition.getActivities();
                ActivityImpl endActiviti = null;
                endActiviti = activities.stream().filter(activity ->
                        activity.getProperty("type").equals("endEvent")
                ).findFirst().get();

                if (endActiviti == null)
                    throw new HxException("流程定义不完全，无法找到结束节点");

                if (isMultiInstance(taskId)) {//会签节点处理
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new MultiJumpCmd(taskId, endActiviti.getId(), ActivitiConstants.DELETE_REASON_FORCE_COMPLETE));
                } else {
                    ((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
                            new TaskJumpCmd(taskId, endActiviti.getId(), null, ActivitiConstants.DELETE_REASON_FORCE_COMPLETE));
                }
                break;
            default:
                break;
        }
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
            List<HistoricTaskInstance> instances = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
//            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
            for (HistoricTaskInstance instance : instances) {
                activeActivityIds.add(instance.getTaskDefinitionKey());
            }
            inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds, highLightedFlows, "宋体", "宋体", null, null, 1.0);
        }
        return inputStream;
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
            }
        }

        return flag;
    }

    private PvmActivity findFirstActivity(String processDefinitionId) {
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)
                repositoryService
                        .createProcessDefinitionQuery().processDefinitionId(processDefinitionId).latestVersion().singleResult();
        ActivityImpl startActivity = processDefinitionEntity.getInitial();
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
                    if (type.equals("userTask") || whileCount > 5) {
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
}
