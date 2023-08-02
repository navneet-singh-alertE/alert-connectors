package com.alnt.access.provisioning.services;

public interface ConnectorActionsFieldMappings {

    String getAction();

    void setAction(String action);

    TypesFieldMappingsSet getOutBoundFieldMappingsSet();

    void setOutBoundFieldMappingsSet(TypesFieldMappingsSet outBoundFieldMappingsSet);

    TypesFieldMappingsSet getInBoundFieldMappingsSet();

    void setInBoundFieldMappingsSet(TypesFieldMappingsSet inBoundFieldMappingsSet);
}
