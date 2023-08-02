package com.alnt.access.provisioning.services;

import java.util.List;

public interface TypesFieldMappingsSet {

    String getSourceEntityType();

    void setSourceEntityType(String sourceEntityType);

    String getDestinationEntityType();

    void setDestinationEntityType(String destinationEntityType);

    List<FieldMapping> getFieldMappings();

    void setFieldMappings(List<FieldMapping> fieldMappings);
}
