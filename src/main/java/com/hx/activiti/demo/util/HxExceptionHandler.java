package com.hx.activiti.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常拦截器类
 * 拦截类必须在sping的mvc中有扫描
 */
@ControllerAdvice
public class HxExceptionHandler {

    /** 属性相关 */
    /**
     * 日志相关
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HxExceptionHandler.class);

    /**
     * 处理未期望的服务异常
     *
     * @param throwable 抛出异常
     * @return 司机端应答
     */
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public HxResponse handleUnexpectedServerError(Throwable throwable) {
        // 打印日志
        if (throwable instanceof HxException) {
            if (((HxException) throwable).getCode() != 1000)
                LOGGER.warn("IyyException|message:{}", throwable.getMessage());
        } else {
            LOGGER.error("exception", throwable);
        }

        // 处理异常
        HxResponse response = new HxResponse();
        if (throwable instanceof HxException) {
            HxException exception = (HxException) throwable;
            response.setCode(exception.getCode());
            response.setMessage(exception.getMessage());
        } else {
            response.setCode(9999);
            response.setMessage("未知异常");
        }

        // 默认消息
        if (response.getMessage() == null) {
            response.setMessage("未知异常");
        }
        // 返回数据
        return response;
    }

}
