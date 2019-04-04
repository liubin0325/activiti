package com.hx.activiti.demo.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-03
 */
@Service
public class ActivitiEndService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String taskId = delegateExecution.getCurrentActivityId();
        delegateExecution.getProcessInstanceId();
        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl)delegateExecution.getEngineServices().getRepositoryService();
        repositoryService.createProcessDefinitionQuery().processDefinitionId(delegateExecution.getProcessDefinitionId()).singleResult();
        System.out.println(delegateExecution.getId());
    }
}
