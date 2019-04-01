package com.hx.activiti.demo.util;

import java.util.UUID;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-27
 */
public class IDGenerate {
    public static String generateId() {
        return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }
}
