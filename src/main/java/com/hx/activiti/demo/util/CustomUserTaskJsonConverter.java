package com.hx.activiti.demo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.editor.language.json.converter.ActivityProcessor;
import org.activiti.editor.language.json.converter.UserTaskJsonConverter;

import java.util.Map;

/**
 * User Task 自定义节点属性 解析器
 */
public class CustomUserTaskJsonConverter extends UserTaskJsonConverter {
    public void convertToJson(BaseElement baseElement,
                              ActivityProcessor processor, BpmnModel model,
                              FlowElementsContainer container, ArrayNode shapesArrayNode,
                              double subProcessX, double subProcessY) {
        super.convertToJson(baseElement, processor, model, container,
                shapesArrayNode, subProcessX, subProcessY);
    }

    @Override
    protected FlowElement convertJsonToElement(JsonNode elementNode,
                                               JsonNode modelNode, Map shapeMap) {
        return super.convertJsonToElement(elementNode, modelNode, shapeMap);
    }
}
