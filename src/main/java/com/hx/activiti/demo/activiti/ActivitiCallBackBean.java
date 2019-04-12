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


    public ActivitiCallBackBean(String procInstId, String data, String listData, String extraData, String operator, Date operateDate) {
        this.procInstId = procInstId;
        this.data = data;
        this.listData = listData;
        this.extraData = extraData;
        this.operator = operator;
        this.operateDate = operateDate;
    }

    private String procInstId;
    private String data;
    private String listData;
    private String extraData;
    private String operator;
    private Date operateDate;

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getListData() {
        return listData;
    }

    public void setListData(String listData) {
        this.listData = listData;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }
}
