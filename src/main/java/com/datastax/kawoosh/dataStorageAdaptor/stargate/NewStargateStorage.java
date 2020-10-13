package com.datastax.kawoosh.dataStorageAdaptor.stargate;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import jersey.repackaged.com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NewStargateStorage extends DataStorage {
    private String collectionName ;

    private final String restEndPointURI;
    private final HttpClient client;
    static ObjectMapper mapper = new ObjectMapper();
    private static String authToken = null;

    public NewStargateStorage(Cluster cluster) throws InterruptedException, ExecutionException, URISyntaxException, JsonProcessingException {
        super(cluster);
        collectionName = cluster.getClusterName();
        client = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        ConnectionConfig connectionConfig = getConnectionConfig();
        String astraUrl = String.format(connectionConfig.getUrlTemplate(), connectionConfig.getClusterId(), connectionConfig.getClusterRegion());
        String authUri = String.format(connectionConfig.getAuthUrlTemplate(), astraUrl);
        authToken = generateAuthToken(authUri, connectionConfig.getUsername(), connectionConfig.getPassword());

        restEndPointURI = String.format(connectionConfig.getRestEndpointTemplate(), astraUrl, connectionConfig.getKeyspace());
    }

    private ConnectionConfig getConnectionConfig() {
        com.typesafe.config.Config load = ConfigFactory.parseResources("connection.properties");
        ConnectionConfig connectionConfig = ConfigBeanFactory.create(load, ConnectionConfig.class);
        return connectionConfig;
    }

    private String generateAuthToken(String authUri, String username, String password) throws ExecutionException, InterruptedException, JsonProcessingException, URISyntaxException {
        ObjectNode authDetail = mapper.createObjectNode()
                .put("username", username)
                .put("password", password);

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(authUri))
                .setHeader(HttpHeaders.ACCEPT, "*/*")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(authDetail.toString())).build();
        HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
        return mapper.readTree(response.body()).get("authToken").asText();
    }

    @SneakyThrows
    @Override
    public List<Config> read(String confName) {
        confName = cleanseTheConfName(confName);

        CompletableFuture<JsonNode> jsonNodeCompletableFuture = get(collectionName, confName);
        return jsonNodeCompletableFuture
                .thenApply(jsonNode -> jsonNode.get("data"))
                .thenApply(jsonNode -> getListOfObjects(jsonNode))
                .exceptionally(throwable -> Lists.newArrayList())
                .get();
    }

    private String cleanseTheConfName(String confName) {
        return  confName.replaceAll(" ", "_");
    }

    private CompletableFuture<JsonNode> get(String collectionName, String docId) throws ExecutionException, InterruptedException, JsonProcessingException, URISyntaxException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(String.format("%s/%s/%s", restEndPointURI, collectionName, docId)))
                .setHeader(HttpHeaders.ACCEPT, "*/*")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader("x-cassandra-request-id", UUID.randomUUID().toString())
                .setHeader("x-cassandra-token", authToken)
                .GET()
                .build();
        return client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> getJsonNode(response.body()));
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

    @lombok.SneakyThrows
    private JsonNode getJsonNode(String body) {
        return mapper.readTree(body);
    }

    @SneakyThrows
    @Override
    public void write(Config conf) {
        JsonNode jnode = getJsonNode(conf);
        boolean result = insert(jnode, collectionName, cleanseTheConfName(conf.getConfName())).get();
        return;
    }

    private JsonNode getJsonNode(Config conf) {
        JsonNode jnode = mapper
                .createObjectNode()
                .set(conf.getNodeIp().replace('.', '+'), mapper.valueToTree(conf));
        return jnode;
    }

    private CompletableFuture<Boolean> insert(JsonNode data, String collectionName, String docId) throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(String.format("%s/%s/%s", restEndPointURI, collectionName, docId)))
                .setHeader(HttpHeaders.ACCEPT, "*/*")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader("x-cassandra-request-id", UUID.randomUUID().toString())
                .setHeader("x-cassandra-token", authToken)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(data.toString()))
                .build();
        return client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.statusCode() < 300);
    }






}