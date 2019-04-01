package com.hx.activiti.demo.model;

/**
 * 表单与流程模型绑定关系表
 */
public class ActCustomModelForm {
    private String model_id;
    private ActCustomForm form;

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public ActCustomForm getForm() {
        return form;
    }

    public void setForm(ActCustomForm form) {
        this.form = form;
    }
}
