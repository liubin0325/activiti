package com.hx.activiti.demo.activiti.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;

/**
 * @description: 会签节点驳回-申请人
 * @author: liubin
 * @date: 2019-04-02
 */
public class MultiJumpStartCmd extends NeedsActiveTaskCmd<Void> {

    private String targetId;
    private String type;

    public MultiJumpStartCmd(String taskId, String targetId, String type) {
        super(taskId);
        this.targetId = targetId;
        this.type = type;
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity taskEntity) {
        ExecutionEntity execution = taskEntity.getExecution();
        while (execution.getParent() != null) {
            execution = execution.getParent();
        }
        execution.destroyScope(this.type);
        //流程定义id
        String procDefId = execution.getProcessDefinitionId();
        //获取服务
        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) execution.getEngineServices().getRepositoryService();
        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService.getDeployedProcessDefinition(procDefId);
        //获取需要提交的节点
        ActivityImpl toActivityImpl = processDefinitionImpl.findActivity(this.targetId);
        if (toActivityImpl == null) {
            throw new ActivitiException(this.targetId + " to ActivityImpl is null!");
        }
        taskEntity.fireEvent("complete");
        commandContext.getTaskEntityManager().deleteTask(taskEntity, this.type, false);
        execution.removeTask(taskEntity);//执行规划的线
        execution.executeActivity(toActivityImpl);
        return null;
    }
}
