package com.hx.activiti.demo.activiti.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;

/**
 * @description: 普通节点跳转开始节点
 * @author: liubin
 * @date: 2019-03-29
 */
public class TaskJumpStartCmd extends NeedsActiveTaskCmd<Void> {

    protected String type;
    protected String targetId;

    public TaskJumpStartCmd(String taskId, String targetId, String type) {
        super(taskId);
        this.type = type;
        this.targetId = targetId;
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity task) {

        ExecutionEntity execution = task.getExecution();
        //流程定义id
        String procDefId = execution.getProcessDefinitionId();
        //获取服务
        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) execution.getEngineServices().getRepositoryService();

        HistoryServiceImpl historyService = (HistoryServiceImpl) execution.getEngineServices().getHistoryService();
        //获取流程定义的所有节点
        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService.getDeployedProcessDefinition(procDefId);
        ActivityImpl activity = processDefinitionImpl.findActivity(this.targetId);
        if (activity == null)
            throw new ActivitiException(this.targetId + " not found");
        HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().
                processInstanceId(execution.getProcessInstanceId()).
                taskDefinitionKey(activity.getId()).singleResult();
        while (execution.getParent() != null) {
            execution = execution.getParent();
            if (execution.getId().equals(taskInstance.getExecutionId())) {
                break;
            }
        }
        execution.destroyScope(this.type);


        //获取需要提交的节点
        task.fireEvent("complete");
        commandContext.getTaskEntityManager().deleteTask(task, this.type, false);
        execution.removeTask(task);//执行规划的线
        execution.executeActivity(activity);
        return null;
    }
}
