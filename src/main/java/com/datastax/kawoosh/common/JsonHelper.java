package com.datastax.kawoosh.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    ObjectMapper om = new ObjectMapper();

    public String ToJson(ClusterConfig clusterConfig) throws JsonProcessingException {
        return om.writeValueAsString(clusterConfig);
    }

    public ClusterConfig FromJson(String json) throws JsonProcessingException {
        return om.readValue(json, ClusterConfig.class);
    }
}
