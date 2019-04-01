package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomForm;

import java.util.List;

public interface ActCustomFormDao {
    ActCustomForm getById(String id);

    List<ActCustomForm> getList();

    int save(ActCustomForm form);

    int update(ActCustomForm form);
}
