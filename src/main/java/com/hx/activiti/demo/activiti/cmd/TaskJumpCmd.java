package com.hx.activiti.demo.activiti.cmd;

import com.hx.activiti.demo.activiti.ActivitiConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-29
 */
public class TaskJumpCmd extends NeedsActiveTaskCmd<Void> {
    /**
     * 目标任务的定义Id
     */
    private String toTaskKey;
    /**
     * 参数
     */
    protected Map variables;
    /**
     * jump跳跃 ，turnback 退回（）
     */
    protected String type;

    public TaskJumpCmd(String taskId, String toTaskKey, Map variables, String type) {
        super(taskId);
        this.toTaskKey = toTaskKey;
        this.variables = variables;
        this.type = type;
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity task) {
        if (variables != null)
            task.setExecutionVariables(variables);

        ExecutionEntity execution = task.getExecution();
        //流程定义id
        String procDefId = execution.getProcessDefinitionId();
        //获取服务
        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) execution.getEngineServices().getRepositoryService();
        //获取流程定义的所有节点
        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService.getDeployedProcessDefinition(procDefId);

        RuntimeServiceImpl runtimeService = (RuntimeServiceImpl) execution.getEngineServices().getRuntimeService();

        TaskServiceImpl taskService = (TaskServiceImpl) execution.getEngineServices().getTaskService();

        //获取需要提交的节点
        ActivityImpl toActivityImpl = processDefinitionImpl.findActivity(this.toTaskKey);
        ProcessInstance instance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();


        if (toActivityImpl == null) {
            throw new ActivitiException(this.toTaskKey + " to ActivityImpl is null!");
        } else {
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(instance.getId()).list();
            if(taskList.size()>1){
                taskList.forEach(task1 -> {
                    if(!task1.getId().equals(taskId)) {
                        TaskEntity taskEntity = (TaskEntity) task1;
                        taskEntity.fireEvent("complete");
                        commandContext.getTaskEntityManager().deleteTask(taskEntity, ActivitiConstants.DELETE_REASON_AUTO_BACK, false);
                    }
                });
            }
            task.fireEvent("complete");
            commandContext.getTaskEntityManager().deleteTask(task, this.type, false);
            execution.removeTask(task);//执行规划的线
            execution.executeActivity(toActivityImpl);
        }
        return null;
    }
}
