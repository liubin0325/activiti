package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomProcinstExtra;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-09
 */
public interface ActCustomProcinstExtraDao {
    int save(ActCustomProcinstExtra extra);

    int update(ActCustomProcinstExtra extra);

    ActCustomProcinstExtra getByBusinessKey(@Param("procinst_id") String procinst_id, @Param("businessKey") String businessKey);

}
