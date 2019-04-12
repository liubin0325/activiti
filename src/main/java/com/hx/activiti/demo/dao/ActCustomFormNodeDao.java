package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomFormNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActCustomFormNodeDao {
    List<ActCustomFormNode> getByModelAndNode(@Param("model") String model, @Param("node") String node);
}
