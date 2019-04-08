package com.hx.activiti.demo.activiti;

import java.util.Date;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-08
 */
public class ActivitiCallBackBean {

    public ActivitiCallBackBean() {
    }

    public ActivitiCallBackBean(String procinst_id,
                                String data,
                                String listdata,
                                String extradata,
                                String operator,
                                Date operatedate) {
        this.procinst_id = procinst_id;
        this.data = data;
        this.listdata = listdata;
        this.extradata = extradata;
        this.operator = operator;
        this.operatedate = operatedate;
    }

    private String procinst_id;
    private String data;
    private String listdata;
    private String extradata;
    private String operator;
    private Date operatedate;

    public String getProcinst_id() {
        return procinst_id;
    }

    public void setProcinst_id(String procinst_id) {
        this.procinst_id = procinst_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getListdata() {
        return listdata;
    }

    public void setListdata(String listdata) {
        this.listdata = listdata;
    }

    public String getExtradata() {
        return extradata;
    }

    public void setExtradata(String extradata) {
        this.extradata = extradata;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperatedate() {
        return operatedate;
    }

    public void setOperatedate(Date operatedate) {
        this.operatedate = operatedate;
    }
}
