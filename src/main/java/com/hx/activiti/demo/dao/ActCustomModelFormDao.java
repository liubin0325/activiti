package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomModelForm;

public interface ActCustomModelFormDao {
    ActCustomModelForm getByModelId(String modelId);

    int save(ActCustomModelForm form);

    int update(ActCustomModelForm form);
}
