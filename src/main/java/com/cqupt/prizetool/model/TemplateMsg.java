package com.cqupt.prizetool.model;

import lombok.Data;

import java.util.Map;

@Data
public class TemplateMsg {
    private String touser;
    private String template_id;
    private String topcolor;
    private Map<String, TemplateData> data;
}
