package com.hx.activiti.demo;

import com.google.gson.Gson;
import com.hx.activiti.demo.dao.ActCustomFormDao;
import com.sun.tools.corba.se.idl.ExceptionEntry;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.el.UelExpressionCondition;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ScopeImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ActCustomFormDao formDao;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private FormService formService;

    private Gson gson = new Gson();

    @Autowired
    private ProcessEngineConfiguration engineConfiguration;

    @Test
    public void contextLoads() {

//        BpmnModel model = repositoryService.getBpmnModel("process_2:9:45034");
//        Map<String, List<ExtensionAttribute>> map =  model.getDefinitionsAttributes();
//        Process process = model.getMainProcess();
//
//        process.getFlowElements().forEach(flowElement -> {
//            flowElement.getAttributes();
//            flowElement.getId();
//        });

//        ProcessDefinitionEntity entity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition("process_7:2:175011");
//        List<ActivityImpl> activities = entity.getActivities();
//        List<ActivityImpl> excluseives = new ArrayList<>();
//        activities.forEach(activity -> {
//            if("exclusiveGateway".equals(activity.getProperty("type"))){
//                excluseives.add(activity);
//            }
//        });
//        List<UelExpressionCondition> uels = new ArrayList<>();
//        excluseives.forEach(activity -> {
//            List<PvmTransition> pvmTransitions = activity.getOutgoingTransitions();
//            pvmTransitions.forEach(pvmTransition -> {
//                UelExpressionCondition uel = (UelExpressionCondition) pvmTransition.getProperty(BpmnParse.PROPERTYNAME_CONDITION);
//                uels.add(uel);
//            });
//        });
//
//        System.out.println();
//
//
//        ExpressionManager manager = new ExpressionManager();

        JuelExpression expression = (JuelExpression)((ProcessEngineConfigurationImpl)engineConfiguration).getExpressionManager().createExpression("${ls==1}");
        System.out.println(expression.toString());
//        HistoricTaskInstance currTask = historyService
//                .createHistoricTaskInstanceQuery().taskId("80164")
//                .singleResult();

//        repositoryService.deleteDeployment("80072");

//        runtimeService.createProcessInstanceQuery().variableValueEquals("").endOr()
//        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition("process_3:2:80024");
//
//        List<ActivityImpl> activities = definition.getActivities();
//
//        List<ActivityImpl> activities1 = activities.stream().filter(activity -> {
//            if(activity.getProperty("type").equals("userTask")){
//                if(activity.getActivityBehavior() instanceof ParallelMultiInstanceBehavior){ //并行网关
//                    ParallelMultiInstanceBehavior behavior = (ParallelMultiInstanceBehavior) activity.getActivityBehavior();
//                    if (behavior != null && behavior.getCollectionExpression() != null) {
//                        return true;
//                    }
//                }else if(activity.getActivityBehavior() instanceof SequentialMultiInstanceBehavior){//串行网关
//                    SequentialMultiInstanceBehavior behavior = (SequentialMultiInstanceBehavior) activity.getActivityBehavior();
//                    if (behavior != null && behavior.getCollectionExpression() != null) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }).collect(Collectors.toList());
//        System.out.println();
//
//        Execution execution =  runtimeService.createExecutionQuery().executionId("9d014413-567c-11e9-851d-0862664737c1").singleResult();
//        ExceptionEntry exceptionEntry = (ExceptionEntry)execution;

//        runtimeService.deleteProcessInstance();

//        Context.getCommandContext().getDbSqlSession().delete(exceptionEntry)
//        exceptionEntry.
//        Context.getCommandContext().getExecutionEntityManager().delete();

//        ExecutionEntityManager manager = new ExecutionEntityManager();
//        Context.getCommandContext().getExecutionEntityManager().d
//        manager.deleteProcessInstanceCascade((ExceptionEntry) execution, "test", false);
//        manager.

//        runtimeService.de



//        String taskDefinitionKey = currTask.getTaskDefinitionKey();
//        int whileCount=0;
//        while (true) {
//            whileCount++;
//            ActivityImpl activity = definition.findActivity(taskDefinitionKey);
//            PvmTransition pvmTransition = activity.getIncomingTransitions().get(0);
//            PvmActivity pvmActivity = pvmTransition.getSource();
//            String type = (String) pvmActivity.getProperty("type");
//            taskDefinitionKey = pvmActivity.getId();
//            if(type.equals("userTask") || whileCount>5){
//                break;
//            }
//        }
//        System.out.println(taskDefinitionKey);






//        ProcessInstance instance = runtimeService
//                .createProcessInstanceQuery()
//                .processInstanceId(currTask.getProcessInstanceId())
//                .singleResult();
//        List<Task> taskList = taskService.createTaskQuery().processInstanceId(instance.getId()).list();
//        identityService.setAuthenticatedUserId("0");
//        taskService.addComment("57507", "57501", "哈哈哈1");
//        List<Comment> comments = taskService.getProcessInstanceComments("12501");
//        System.out.println(gson.toJson(comments));
//        List<User> users = identityService.createUserQuery().memberOfGroup("2").list();
//        System.out.println(gson.toJson(users));
//        historyService.createHistoricTaskInstanceQuery().taskDeleteReason()
//        repositoryService.deleteDeployment("25001", false);
//        historyService.createHistoricProcessInstanceQuery().processInstanceId("").list()

//        taskService.getTaskComments()

//        ((RuntimeServiceImpl)runtimeService).getCommandExecutor().execute(new TaskJumpStartCmd("65002", "flow4", null, "jump"));
//        runtimeService.g
//        taskService.create

        Group group = identityService.newGroup("1");
        group.setName("普通用户");
        identityService.saveGroup(group);

        Group group1 = identityService.newGroup("2");
        group1.setName("领导");
        identityService.saveGroup(group1);

        Group group2 = identityService.newGroup("3");
        group2.setName("hr");
        identityService.saveGroup(group2);
        for(int i = 0;i<10;i++){
            User user = identityService.newUser(i+"");
            user.setFirstName("普通用户_"+i);
            identityService.saveUser(user);
            identityService.createMembership(i+"", "1");

        }

        for(int i = 11;i<16;i++){
            User user = identityService.newUser(i+"");
            user.setFirstName("领导_"+i);
            identityService.saveUser(user);
            identityService.createMembership(i+"", "2");

        }

        for(int i = 16;i<20;i++){
            User user = identityService.newUser(i+"");
            user.setFirstName("hr_"+i);
            identityService.saveUser(user);
            identityService.createMembership(i+"", "3");

        }
//        runtimeService.createProcessInstanceQuery().processDefinitionCategory("测试分类").list();

//        List<Task> tasks = taskService.createTaskQuery().processInstanceId("125007").taskAssignee().orderByTaskCreateTime().asc().list();
//        tasks.forEach(task -> {
//            TaskEntity taskEntity = (TaskEntity) task;
//            System.out.println(1);
//        });
//        historyService.createNativeHistoricProcessInstanceQuery().sql("")
//        taskService.createTaskQuery().taskCandidateOrAssigned("aaa").list();
//        historyService.createHistoricProcessInstanceQuery().finished().list();
//        historyService.createNativeHistoricProcessInstanceQuery().sql("");
//
//        historyService.createHistoricProcessInstanceQuery().startedBy("");
//
//
//        historyService.createHistoricTaskInstanceQuery().taskAssignee()

//        historyService.createHistoricActivityInstanceQuery().


    }

}
