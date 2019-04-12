package com.hx.activiti.demo.activiti;

import com.hx.activiti.demo.util.HxException;
import com.hx.activiti.demo.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-08
 */
public class ActivitiUtils {
    public static Map<String,Object> getCallbackMethod(String callback) throws HxException {
        if(StringUtils.isNotEmpty(callback)){
            String[] callbackArr = callback.split("\\.");
            if(callbackArr.length!=2){
                throw new HxException(-1, "回调方法不正确");
            }
            Object service = SpringUtil.getBean(callbackArr[0]);
            if(service==null) {
                throw new HxException(-1, "无法找到回调类");
            }
            try {
                Method method = service.getClass().getMethod(callbackArr[1], ActivitiCallBackBean.class);

                Map<String,Object> map = new HashMap<>(2);
                map.put("service", service);
                map.put("method", method);
                return map;
            } catch (NoSuchMethodException e) {
                throw new HxException(-1, "无法找到回调方法");
            }
        }
        return null;
    }
}
