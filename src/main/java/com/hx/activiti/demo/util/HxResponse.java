package com.hx.activiti.demo.util;

import java.io.Serializable;

/**
 * 统一返回值包装
 * Created by liubin on 2016/11/14.
 */
public class HxResponse<T> implements Serializable {

    private int code = 0;
    private String message = "success";
    private T data;

    public HxResponse() {
    }

    public HxResponse(T data) {
        this.data = data;
    }

    public HxResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
