package com.hx.activiti.demo.activiti.listener;

import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import com.hx.activiti.demo.activiti.EventHandler;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-29
 */
@Service("processCompleteListener")
public class ProcessCompleteListener extends AbstractEventHandler {

    @Override
    protected void handleEvent(ActivitiEvent event, TaskEntity entity) {

    }
}
