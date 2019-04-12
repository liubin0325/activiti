package com.hx.activiti.demo.model;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-09
 */
public class ActCustomFormListData {
    private String procinst_id;
    private String business_key;
    private Integer list_order;
    private String list_data;

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

    public Integer getList_order() {
        return list_order;
    }

    public void setList_order(Integer list_order) {
        this.list_order = list_order;
    }

    public String getList_data() {
        return list_data;
    }

    public void setList_data(String list_data) {
        this.list_data = list_data;
    }
}
