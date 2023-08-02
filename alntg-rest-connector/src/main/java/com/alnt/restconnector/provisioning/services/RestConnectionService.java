package com.alnt.restconnector.provisioning.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alnt.access.provisioning.services.ConnectorActionsFieldMappings;
import com.alnt.access.provisioning.services.IRESTConnectorInterface;
import com.alnt.access.provisioning.services.RESTTransformationMapper;
import com.alnt.restconnector.provisioning.commons.IRequestResponseHandler;
import com.alnt.restconnector.provisioning.commons.RequestResponseHandlersFactory;

public class RestConnectionService extends RestConnectionInterface implements IRESTConnectorInterface {

    private static final Logger logger = LogManager.getLogger(RestConnectionService.class);

    private List<ConnectorActionsFieldMappings> typesFieldMappingsSets;

    private Map<String, ConnectorActionsFieldMappings> fieldMappingsByAction = new HashMap<>();

    private Map<String, IRequestResponseHandler> reqRespHandlerByAction = new HashMap<>();

    private RESTTransformationMapper transformationMapper;

    public RestConnectionService() {
    }

    public RestConnectionService(Map<String, String> conParams) {
        super(conParams);
    }

    public RestConnectionService(Map<String, String> conParams,
                                 List<ConnectorActionsFieldMappings> typesFieldMappingsSets,
                                 RESTTransformationMapper transformationMapper) {
        super(conParams);
        this.typesFieldMappingsSets = typesFieldMappingsSets;
        this.transformationMapper = transformationMapper;

    }

    @Override
    public void init() {
        try {
            super.init();
            for (ConnectorActionsFieldMappings connectorActionsFieldMappings : this.typesFieldMappingsSets) {
                fieldMappingsByAction.put(connectorActionsFieldMappings.getAction(), connectorActionsFieldMappings);
                reqRespHandlerByAction.put(connectorActionsFieldMappings.getAction(),
                        RequestResponseHandlersFactory.getRequestResponseHandler(
                                sysConnector.getConnectorParams(),
                                connectorActionsFieldMappings.getOutBoundFieldMappingsSet(),
                                this.transformationMapper));
            }
        } catch (Throwable exc) {
            logger.error(exc);
        }
    }

    @Override
    public IRequestResponseHandler getRequestResponseHandler(String action) {
        if (reqRespHandlerByAction.containsKey(action)) {
            return reqRespHandlerByAction.get(action);
        }
        return super.getRequestResponseHandler(action);
    }
}
