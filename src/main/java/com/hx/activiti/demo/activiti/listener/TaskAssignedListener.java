package com.hx.activiti.demo.activiti.listener;

import com.hx.activiti.demo.activiti.EventHandler;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-29
 */
@Service("taskAssignedListener")
public class TaskAssignedListener implements EventHandler {
    @Override
    public void handle(ActivitiEvent event) {

    }
}
