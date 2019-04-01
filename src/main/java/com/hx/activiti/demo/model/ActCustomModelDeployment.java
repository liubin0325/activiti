package com.hx.activiti.demo.model;

/**
 * @description: 模型与部署流程绑定关系
 * @author: liubin
 * @date: 2019-03-29
 */
public class ActCustomModelDeployment {
    public ActCustomModelDeployment() {
    }

    public ActCustomModelDeployment(String deployment_id, String model_id) {
        this.deployment_id = deployment_id;
        this.model_id = model_id;
    }

    private String deployment_id;
    private String model_id;

    public String getDeployment_id() {
        return deployment_id;
    }

    public void setDeployment_id(String deployment_id) {
        this.deployment_id = deployment_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }
}
