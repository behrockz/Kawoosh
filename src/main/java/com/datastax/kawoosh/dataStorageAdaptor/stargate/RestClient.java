package com.datastax.kawoosh.dataStorageAdaptor.stargate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RestClient {
    private final ObjectMapper mapper;
    private final String collectionName ;

    private final HttpClient client;

    private final String authToken;
    private final String restEndPointURI;

    @SneakyThrows
    public RestClient(ObjectMapper mapper,
                      String collectionName) {
        this.mapper = mapper;
        this.collectionName = collectionName;

        client = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        ConnectionConfig connectionConfig = getConnectionConfig();
        String astraUrl = String.format(connectionConfig.getUrlTemplate(), connectionConfig.getClusterId(), connectionConfig.getClusterRegion());
        restEndPointURI = String.format(connectionConfig.getRestEndpointTemplate(), astraUrl, connectionConfig.getKeyspace());

        String authUri = String.format(connectionConfig.getAuthUrlTemplate(), astraUrl);
        authToken = generateAuthToken(authUri, connectionConfig.getUsername(), connectionConfig.getPassword());
    }

    private ConnectionConfig getConnectionConfig() {
        com.typesafe.config.Config load = ConfigFactory.parseResources("connection.properties");
        return ConfigBeanFactory.create(load, ConnectionConfig.class);
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

    CompletableFuture<JsonNode> get(String docId) throws URISyntaxException {
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
    private JsonNode getJsonNode(String body) {
        return mapper.readTree(body);
    }

    CompletableFuture<Boolean> insert(JsonNode data, String docId) throws URISyntaxException {
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
