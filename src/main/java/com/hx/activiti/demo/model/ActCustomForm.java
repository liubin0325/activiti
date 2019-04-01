package com.hx.activiti.demo.model;

import java.util.Date;

/**
 * 自定义表单
 */
public class ActCustomForm {
    private String form_id;
    private String form_name;
    private Integer form_type;
    private Integer order_id;
    private String content;
    private String remark;
    private String operator;
    private Date operatedate;

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getForm_name() {
        return form_name;
    }

    public void setForm_name(String form_name) {
        this.form_name = form_name;
    }

    public Integer getForm_type() {
        return form_type;
    }

    public void setForm_type(Integer form_type) {
        this.form_type = form_type;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
