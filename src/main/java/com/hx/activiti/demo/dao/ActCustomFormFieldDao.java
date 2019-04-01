package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomFormField;

import java.util.List;

public interface ActCustomFormFieldDao {
    List<ActCustomFormField> getByForm(String formId);

    ActCustomFormField getById(String fieldId);


    int save(ActCustomFormField field);

    int saveBatch(List<ActCustomFormField> list);

    int delByFormId(String formId);

    int update(ActCustomFormField field);
}
