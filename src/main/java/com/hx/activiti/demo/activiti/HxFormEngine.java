package com.hx.activiti.demo.activiti;

import com.hx.activiti.demo.service.ActFormService;
import com.hx.activiti.demo.util.SpringUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.FormEngine;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

/**
 * @description: 自定义activiti表单引擎
 * @author: liubin
 * @date: 2019-03-27
 */
@Service
public class HxFormEngine implements FormEngine {


    @Override
    public String getName() {
        return "hx_form_engine";
    }

    /**
     * 获取自定义开始表单
     *
     * @param startFormData
     * @return
     */
    @Override
    public Object renderStartForm(StartFormData startFormData) {
        ActFormService actFormService = SpringUtil.getBean(ActFormService.class);
        return actFormService.getStartForm(startFormData.getProcessDefinition().getId());
    }

    /**
     * 获取自定义任务表单
     *
     * @param taskFormData
     * @return
     */
    @Override
    public Object renderTaskForm(TaskFormData taskFormData) {
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        ActFormService actFormService = SpringUtil.getBean(ActFormService.class);
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(taskFormData.getTask().getExecutionId()).singleResult();
        Task task = taskFormData.getTask();
        return actFormService.getTaskForm(execution.getProcessDefinitionId(), execution.getProcessInstanceId(), task.getTaskDefinitionKey());
    }
}
