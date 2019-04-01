package com.hx.activiti.demo.activiti.listener;

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
public class ProcessCompleteListener implements EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProcessCompleteListener.class);
    @Override
    public void handle(ActivitiEvent event) {

    }
}
