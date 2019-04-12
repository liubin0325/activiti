package com.hx.activiti.demo.model;

/**
 * 表单字段与各个节点关系表
 */
public class ActCustomFormNode {
    private ActCustomFormField field;
    private String model_id;
    private String node_key;
    private Boolean isvisible;
    private Boolean isedit;
    private Boolean isnotnull;

    public ActCustomFormField getField() {
        return field;
    }

    public void setField(ActCustomFormField field) {
        this.field = field;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getNode_key() {
        return node_key;
    }

    public void setNode_key(String node_key) {
        this.node_key = node_key;
    }

    public Boolean getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(Boolean isvisible) {
        this.isvisible = isvisible;
    }

    public Boolean getIsedit() {
        return isedit;
    }

    public void setIsedit(Boolean isedit) {
        this.isedit = isedit;
    }

    public Boolean getIsnotnull() {
        return isnotnull;
    }

    public void setIsnotnull(Boolean isnotnull) {
        this.isnotnull = isnotnull;
    }
}
