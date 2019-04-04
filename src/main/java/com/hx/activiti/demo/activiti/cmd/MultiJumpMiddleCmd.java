package com.hx.activiti.demo.activiti.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;

/**
 * @description: 会签节点驳回-上一步
 * @author: liubin
 * @date: 2019-04-02
 */
public class MultiJumpMiddleCmd extends NeedsActiveTaskCmd<Void> {

    private String targetId;
    private String type;

    public MultiJumpMiddleCmd(String taskId, String targetId, String type) {
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
        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService.getDeployedProcessDefinition(procDefId);
        //获取需要提交的节点
        ActivityImpl toActivityImpl = processDefinitionImpl.findActivity(this.targetId);
        if (toActivityImpl == null)
            throw new ActivitiException(this.targetId + " to ActivityImpl is null!");

        ActivityImpl nowActivitiImpl = processDefinitionImpl.findActivity(taskEntity.getTaskDefinitionKey());
        boolean isValid = false;
        if (nowActivitiImpl.getActivityBehavior() != null) {
            if (nowActivitiImpl.getActivityBehavior() instanceof ParallelMultiInstanceBehavior) {
                ParallelMultiInstanceBehavior behavior = (ParallelMultiInstanceBehavior) nowActivitiImpl.getActivityBehavior();
                if (behavior != null && behavior.getCollectionExpression() != null) {
                    isValid = true;
                    execution = execution.getParent().getParent();
                }
            } else if (nowActivitiImpl.getActivityBehavior() instanceof SequentialMultiInstanceBehavior) {
                SequentialMultiInstanceBehavior behavior = (SequentialMultiInstanceBehavior) nowActivitiImpl.getActivityBehavior();
                if (behavior != null && behavior.getCollectionExpression() != null) {
                    isValid = true;
                    execution = execution.getParent();
                }
            }
        }
        execution.destroyScope(this.type);
        if (!isValid) {
            throw new ActivitiException("it's not MultiInstance");
        }

        taskEntity.fireEvent("complete");
        commandContext.getTaskEntityManager().deleteTask(taskEntity, this.type, false);
        execution.removeTask(taskEntity);//执行规划的线
        execution.executeActivity(toActivityImpl);
        return null;
    }
}
