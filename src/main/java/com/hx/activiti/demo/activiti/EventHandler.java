package com.hx.activiti.demo.activiti;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * @description: Activiti 全局事件监听处理器
 * @author: liubin
 * @date: 2019-03-29
 */
public interface EventHandler {
    /**
     * 事件处理
     *
     * @param event
     */
    void handle(ActivitiEvent event);
}
