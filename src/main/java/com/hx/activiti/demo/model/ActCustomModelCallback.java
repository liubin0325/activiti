package com.hx.activiti.demo.model;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-08
 */
public class ActCustomModelCallback {
    private String model_id;
    private String callback;

    public ActCustomModelCallback() {
    }

    public ActCustomModelCallback(String model_id, String callback) {
        this.model_id = model_id;
        this.callback = callback;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
