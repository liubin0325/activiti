package com.hx.activiti.demo.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-29
 */
@Service("taskCompleteListener")
public class TaskCompleteListener extends AbstractEventHandler {

    @Override
    protected void handleEvent(ActivitiEvent event, TaskEntity entity) {

    }
}
