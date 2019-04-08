package com.hx.activiti.demo.service.impl;

import com.hx.activiti.demo.activiti.ActivitiCallBackBean;
import com.hx.activiti.demo.service.EndCallBackTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-08
 */
@Service("endCallBackTestService")
public class EndCallBackTestServiceImpl implements EndCallBackTestService {

    private static final Logger logger = LoggerFactory.getLogger(EndCallBackTestServiceImpl.class);

    @Override
    public void callBackTest(ActivitiCallBackBean backBean) {
        logger.info("start call back");
    }
}
