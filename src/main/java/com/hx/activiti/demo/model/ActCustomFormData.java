package com.hx.activiti.demo.model;

/**
 * @description:任务表单数据
 * @author: liubin
 * @date: 2019-03-27
 */
public class ActCustomFormData {
    private String procinst_id;
    private String business_key;
    private String form_id;
    private String data;
    private String data_extra;
    private Integer list_count;

    public ActCustomFormData() {
    }

    public ActCustomFormData(String procinst_id, String business_key, String data) {
        this.procinst_id = procinst_id;
        this.business_key = business_key;
        this.data = data;
    }

    public ActCustomFormData(String procinst_id, String business_key, String form_id, String data, String data_extra, Integer list_count) {
        this.procinst_id = procinst_id;
        this.business_key = business_key;
        this.form_id = form_id;
        this.data = data;
        this.data_extra = data_extra;
        this.list_count = list_count;
    }

    public String getProcinst_id() {
        return procinst_id;
    }

    public void setProcinst_id(String procinst_id) {
        this.procinst_id = procinst_id;
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

    public String getData_extra() {
        return data_extra;
    }

    public void setData_extra(String data_extra) {
        this.data_extra = data_extra;
    }

    public Integer getList_count() {
        return list_count;
    }

    public void setList_count(Integer list_count) {
        this.list_count = list_count;
    }
}
