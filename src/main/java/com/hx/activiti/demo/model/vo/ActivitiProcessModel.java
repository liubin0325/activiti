package com.hx.activiti.demo.model.vo;

import org.activiti.engine.repository.ProcessDefinition;

/**
 * activiti 流程 model
 *
 * @Autor 李双利
 * @Date 2018/6/4
 */
public class ActivitiProcessModel {

    private String id;
    private String name;
    private String deploymentId;
    private String category;

    public ActivitiProcessModel(ProcessDefinition processDefinition) {
        this.id = processDefinition.getId();
        this.name = processDefinition.getName();
        this.category = processDefinition.getCategory();
        this.deploymentId = processDefinition.getDeploymentId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
