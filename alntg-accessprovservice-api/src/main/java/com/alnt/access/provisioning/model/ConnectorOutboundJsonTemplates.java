package com.alnt.access.provisioning.model;

public class ConnectorOutboundJsonTemplates {
    private String name;
    private String templateKey;
    private String templateJsonString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public String getTemplateJsonString() {
        return templateJsonString;
    }

    public void setTemplateJsonString(String templateJsonString) {
        this.templateJsonString = templateJsonString;
    }
}
