package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomForm;

import java.util.List;

/**
 * @date
 * @author liubin
 */
public interface ActCustomFormDao {
    /**
     * 获取自定义表单
     * @param id
     * @return
     */
    ActCustomForm getById(String id);

    /**
     * 获取表单列表
     * @return
     */
    List<ActCustomForm> getList();

    /**
     * 保存表单
     * @param form
     * @return
     */
    int save(ActCustomForm form);

    /**
     * 更新表单
     * @param form
     * @return
     */
    int update(ActCustomForm form);
}
