package com.datastax.kawoosh.dataStorageAdaptor.stargate;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jersey.repackaged.com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NewStargateStorage extends DataStorage {
    private final RestClient restClient;
    private final ObjectMapper mapper;

    public NewStargateStorage(Cluster cluster, RestClient restClient, ObjectMapper mapper) {
        super(cluster);
        this.restClient = restClient;
        this.mapper = mapper;
    }

    @SneakyThrows
    @Override
    public CompletableFuture<List<Config>> read(String confName) {
        confName = cleanseTheConfName(confName);

        CompletableFuture<JsonNode> jsonNodeCompletableFuture = restClient.get(confName);
        return jsonNodeCompletableFuture
                .thenApply(jsonNode -> jsonNode.get("data"))
                .thenApply(this::getListOfObjects)
                .exceptionally(throwable -> Lists.newArrayList());
    }

    private String cleanseTheConfName(String confName) {
        return  confName.replaceAll(" ", "_");
    }

    @SneakyThrows
    @Override
    public CompletableFuture<Boolean> write(Config conf) {
        JsonNode jnode = getJsonNode(conf);
        return restClient.insert(jnode, cleanseTheConfName(conf.getConfName()));
    }

    @SneakyThrows
    private List<Config> getListOfObjects(JsonNode data) {
        List<Config> list = Lists.newArrayList();
        for(Iterator<String> it = data.fieldNames(); it.hasNext(); ) {
            String field = it.next();
            Config conf = mapper.treeToValue(data.get(field), Config.class);
            list.add(conf);
        }
        return list;
    }

    private JsonNode getJsonNode(Config conf) {
        return mapper
                .createObjectNode()
                .set(conf.getNodeIp().replace('.', '+'), mapper.valueToTree(conf));
    }
}