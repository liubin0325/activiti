package com.hx.activiti.demo.activiti.listener;

import com.hx.activiti.demo.activiti.EventHandler;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-29
 */
@Service("taskCompleteListener")
public class TaskCompleteListener implements EventHandler {
    @Override
    public void handle(ActivitiEvent event) {
        if (!(event instanceof ActivitiEntityEventImpl)) {
            return;
        }
        ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
        Object entity = eventImpl.getEntity();
        if (!(entity instanceof TaskEntity)) {
            return;
        }
        TaskEntity taskEntity = (TaskEntity) entity;
    }


}
