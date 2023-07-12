package com.alnt.access.provisioning.model;

public class ConnectorOutboundJsonTemplatesBuilder {
    private String name;
    private String templateKey;
    private String templateJsonString;

    private ConnectorOutboundJsonTemplatesBuilder() {
    }

    public ConnectorOutboundJsonTemplatesBuilder withName(String val) {
        name = val;
        return this;
    }

    public ConnectorOutboundJsonTemplatesBuilder withTemplateKey(String val) {
        templateKey = val;
        return this;
    }

    public ConnectorOutboundJsonTemplatesBuilder withTemplateJsonString(String val) {
        templateJsonString = val;
        return this;
    }

    public ConnectorOutboundJsonTemplates build() {
        ConnectorOutboundJsonTemplates connectorOutboundJsonTemplates = new ConnectorOutboundJsonTemplates();
        connectorOutboundJsonTemplates.setTemplateJsonString(this.templateJsonString);
        connectorOutboundJsonTemplates.setTemplateKey(this.templateKey);
        connectorOutboundJsonTemplates.setName(this.name);
        return connectorOutboundJsonTemplates;
    }

    public static ConnectorOutboundJsonTemplatesBuilder newBuilder() {
        return new ConnectorOutboundJsonTemplatesBuilder();
    }

}
