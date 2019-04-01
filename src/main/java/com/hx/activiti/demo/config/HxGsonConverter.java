package com.hx.activiti.demo.config;

import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-28
 */
public class HxGsonConverter extends GsonHttpMessageConverter {
    public HxGsonConverter() {
        super.setGson(new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create());
    }
}
