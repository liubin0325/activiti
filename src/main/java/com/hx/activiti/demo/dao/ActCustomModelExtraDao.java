package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomModelExtra;

/**
 * @description: 流程模型扩展信息DAO
 * @author: liubin
 * @date: 2019-04-11
 */
public interface ActCustomModelExtraDao {
    /**
     * 新增
     *
     * @param extra
     * @return
     */
    int save(ActCustomModelExtra extra);

    /**
     * 更新
     *
     * @param extra
     * @return
     */
    int update(ActCustomModelExtra extra);

    /**
     * 根据模型ID获取扩展信息
     *
     * @param model
     * @return
     */
    ActCustomModelExtra getByModel(String model);
}
