package com.alnt.access.provisioning.services;

public interface FieldMapping {
    String getSourceFieldName();

    void setSourceFieldName(String sourceFieldName);

    String getDestinationFieldName();

    void setDestinationFieldName(String destinationFieldName);
}
