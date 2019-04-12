package com.hx.activiti.demo.model;

import java.util.Date;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-09
 */
public class ActCustomProcinstExtra {
    private String procinst_id;
    private String business_key;
    private String model_id;
    private String inst_key;
    private String keyvalue;
    private String proc_content;
    private String apply_user;
    private Date apply_time;
    private String pre_task_user;
    private Date pre_task_time;
    private String dept_id;
    private Integer status;

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

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getInst_key() {
        return inst_key;
    }

    public void setInst_key(String inst_key) {
        this.inst_key = inst_key;
    }

    public String getKeyvalue() {
        return keyvalue;
    }

    public void setKeyvalue(String keyvalue) {
        this.keyvalue = keyvalue;
    }

    public String getProc_content() {
        return proc_content;
    }

    public void setProc_content(String proc_content) {
        this.proc_content = proc_content;
    }

    public String getApply_user() {
        return apply_user;
    }

    public void setApply_user(String apply_user) {
        this.apply_user = apply_user;
    }

    public Date getApply_time() {
        return apply_time;
    }

    public void setApply_time(Date apply_time) {
        this.apply_time = apply_time;
    }

    public String getPre_task_user() {
        return pre_task_user;
    }

    public void setPre_task_user(String pre_task_user) {
        this.pre_task_user = pre_task_user;
    }

    public Date getPre_task_time() {
        return pre_task_time;
    }

    public void setPre_task_time(Date pre_task_time) {
        this.pre_task_time = pre_task_time;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
