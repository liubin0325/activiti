package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomNodeForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActCustomNodeFormDao {
    List<ActCustomNodeForm> getByModelAndNode(@Param("model") String model, @Param("node") String node);
}
