package com.hx.activiti.demo.activiti.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-02
 */
public class MultiJumpCmd extends NeedsActiveTaskCmd<Void> {

    private String targetId;
    private String type;

    public MultiJumpCmd(String taskId, String targetId, String type) {
        super(taskId);
        this.targetId = targetId;
        this.type = type;
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity taskEntity) {
        ExecutionEntity execution = taskEntity.getExecution();
        //流程定义id
        String procDefId = execution.getProcessDefinitionId();
        //获取服务
        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) execution.getEngineServices().getRepositoryService();
        TaskServiceImpl taskService = (TaskServiceImpl) execution.getEngineServices().getTaskService();
        HistoryServiceImpl historyService = (HistoryServiceImpl) execution.getEngineServices().getHistoryService();
        RuntimeServiceImpl runtimeService = (RuntimeServiceImpl) execution.getEngineServices().getRuntimeService();
        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService.getDeployedProcessDefinition(procDefId);

        HistoricTaskInstance currTask = historyService
                .createHistoricTaskInstanceQuery().taskId(taskId)
                .singleResult();
        // 取得流程实例
        ProcessInstance instance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(currTask.getProcessInstanceId())
                .singleResult();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(instance.getId()).list();
        //获取需要提交的节点
        ActivityImpl toActivityImpl = processDefinitionImpl.findActivity(this.targetId);
        if (toActivityImpl == null)
            throw new ActivitiException(this.targetId + " to ActivityImpl is null!");
        taskList.forEach(task -> {
            if (!task.getId().equals(taskId)) {
                commandContext.getTaskEntityManager().deleteTask((TaskEntity)task, this.type, false);
            }
        });
        taskEntity.fireEvent("complete");
        commandContext.getTaskEntityManager().deleteTask(taskEntity, this.type, false);
        execution.removeTask(taskEntity);//执行规划的线
        execution.executeActivity(toActivityImpl);


        return null;
    }
}
