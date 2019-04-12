package com.hx.activiti.demo.service;

import org.activiti.engine.runtime.Execution;
import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-02
 */
public interface ActivitiExpressService {
    List<String> getByGroup();

    boolean testExclusiveGateWay(String key, String value, Execution task);
}
