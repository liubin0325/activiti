package com.hx.activiti.demo.service;

import com.hx.activiti.demo.model.vo.ActivitiTaskModel;

import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-10
 */
public interface ActivitiSearchService {

    List<ActivitiTaskModel> getUserTask();
}
