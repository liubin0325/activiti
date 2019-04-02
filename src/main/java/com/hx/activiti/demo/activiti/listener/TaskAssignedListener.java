package com.hx.activiti.demo.activiti.listener;

import com.hx.activiti.demo.activiti.EventHandler;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-29
 */
@Service("taskAssignedListener")
public class TaskAssignedListener extends AbstractEventHandler {

    @Override
    protected void handleEvent(ActivitiEvent event, TaskEntity entity) {

    }
}
