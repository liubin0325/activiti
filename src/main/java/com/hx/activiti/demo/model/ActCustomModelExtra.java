package com.hx.activiti.demo.model;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-11
 */
public class ActCustomModelExtra {
    private String model_id;
    private ActCustomForm customForm;
    private String callback;

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public ActCustomForm getCustomForm() {
        return customForm;
    }

    public void setCustomForm(ActCustomForm customForm) {
        this.customForm = customForm;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
