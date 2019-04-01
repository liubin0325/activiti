package com.hx.activiti.demo.model;

/**
 * @description:任务表单数据
 * @author: liubin
 * @date: 2019-03-27
 */
public class ActCustomFormData {
    private String procdef_id;
    private String business_key;
    private String form_id;
    private String data;
    private String list_data;
    private String data_extra;

    public ActCustomFormData() {
    }

    public ActCustomFormData(String procdef_id, String business_key, String form_id, String data, String list_data, String data_extra) {
        this.procdef_id = procdef_id;
        this.business_key = business_key;
        this.form_id = form_id;
        this.data = data;
        this.list_data = list_data;
        this.data_extra = data_extra;
    }

    public String getProcdef_id() {
        return procdef_id;
    }

    public void setProcdef_id(String procdef_id) {
        this.procdef_id = procdef_id;
    }

    public String getBusiness_key() {
        return business_key;
    }

    public void setBusiness_key(String business_key) {
        this.business_key = business_key;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getList_data() {
        return list_data;
    }

    public void setList_data(String list_data) {
        this.list_data = list_data;
    }

    public String getData_extra() {
        return data_extra;
    }

    public void setData_extra(String data_extra) {
        this.data_extra = data_extra;
    }
}
