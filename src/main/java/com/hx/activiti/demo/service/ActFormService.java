package com.hx.activiti.demo.service;

import com.hx.activiti.demo.model.ActCustomForm;

import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-27
 */
public interface ActFormService {

    /**
     * 获取表单列表
     * @return
     */
    List<ActCustomForm> getList();

    /**
     * 保存自定义模板
     *
     * @param name
     * @param order_id
     * @param desc
     * @param type
     */
    void saveActForm(String name,
                     Integer order_id,
                     String desc,
                     Integer type);

    /**
     * 保存表单设计
     *
     * @param formId
     * @param tableData
     * @param content
     * @param design_content
     */
    void saveFormDesign(String formId,
                        String tableData,
                        String content,
                        String design_content);

    /**
     * 获取表单详情
     *
     * @param formId
     * @return
     */
    ActCustomForm getDetail(String formId);

    /**
     * 获取开始表单
     *
     * @param procId
     * @return
     */
    String getStartForm(String procId);

    /**
     * 获取任务节点表单 包含数据 TODO 如何适配各种类型控件
     *
     * @param procdefId
     * @param procinstId
     * @param taskKey
     * @return
     */
    String getTaskForm(String procdefId, String procinstId, String taskKey);


}
