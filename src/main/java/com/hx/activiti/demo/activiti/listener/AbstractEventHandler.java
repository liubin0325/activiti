package com.hx.activiti.demo.activiti.listener;

import com.hx.activiti.demo.activiti.EventHandler;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-02
 */
public abstract class AbstractEventHandler implements EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEventHandler.class);

    @Override
    public void handle(ActivitiEvent event) {
        handleEvent(event, getCurrTaskEntity(event));
    }

    private TaskEntity getCurrTaskEntity(ActivitiEvent event) {
        logger.debug(event.getType().name());
        if (!(event instanceof ActivitiEntityEventImpl)) {
            return null;
        }
        ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
        Object entity = eventImpl.getEntity();
        if (!(entity instanceof TaskEntity)) {
            return null;
        }
        TaskEntity taskEntity = (TaskEntity) entity;
        return taskEntity;
    }

    protected abstract void handleEvent(ActivitiEvent event, TaskEntity entity);
}
