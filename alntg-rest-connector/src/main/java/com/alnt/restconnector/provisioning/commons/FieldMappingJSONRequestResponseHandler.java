package com.alnt.restconnector.provisioning.commons;

import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alnt.access.provisioning.services.RESTTransformationMapper;
import com.alnt.access.provisioning.services.TypesFieldMappingsSet;

public class FieldMappingJSONRequestResponseHandler extends JSONRequestResponseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldMappingJSONRequestResponseHandler.class);

    private TypesFieldMappingsSet fieldMappingsSet;
    private RESTTransformationMapper transformationMapper;
    public FieldMappingJSONRequestResponseHandler(Map connectorParams) {
        super(connectorParams);
    }

    public FieldMappingJSONRequestResponseHandler(Map connectorParams,
                                                  Map<String, String> templates) {
        super(connectorParams, templates);
    }

    public FieldMappingJSONRequestResponseHandler(Map connectorParams, TypesFieldMappingsSet fieldMappingsSet,
                                                  RESTTransformationMapper transformationMapper) {
        super(connectorParams);
        this.fieldMappingsSet = fieldMappingsSet;
        this.transformationMapper = transformationMapper;
    }

    private JSONObject buildJSONObjectFromFieldMapping(Map<String,Object> parameters,
                                                       TypesFieldMappingsSet fieldMappingsSet) throws Exception{
        return new JSONObject(this.transformationMapper.map(parameters, fieldMappingsSet));
    }

    @Override
    public String handleRequest(Map<String, Object> parameters) throws Exception {
        if (this.transformationMapper == null || this.fieldMappingsSet == null) {
            return super.handleRequest(parameters);
        }
        return (String) this.transformationMapper.map(parameters, fieldMappingsSet);
    }
}
