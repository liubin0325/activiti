package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomFormListData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-09
 */
public interface ActCustomFormListDataDao {
    int save(ActCustomFormListData data);

    int saveBatch(List<ActCustomFormListData> list);

    List<ActCustomFormListData> getByProc(@Param("procinstId") String procinstId, @Param("businessKey") String businessKey);
}
