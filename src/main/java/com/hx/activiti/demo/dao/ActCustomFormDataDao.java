package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomFormData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-27
 */
public interface ActCustomFormDataDao {
    /**
     * 根据任务key 获取表单所有数据
     * @param procinst_id
     * @param businessKey
     * @return
     */
    ActCustomFormData getByBusinessKey(@Param("procinst_id") String procinst_id, @Param("businessKey") String businessKey);

    /**
     * 修改表单数据
     * @param data
     * @return
     */
    int update(ActCustomFormData data);
    /**
     * 保存单条表单数据
     * @param data
     * @return
     */
    int save(ActCustomFormData data);

    /**
     * 批量保存表单数据
     * @param list
     * @return
     */
    int saveBatch(List<ActCustomFormData> list);
}
