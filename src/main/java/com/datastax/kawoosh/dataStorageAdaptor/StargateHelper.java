package com.datastax.kawoosh.dataStorageAdaptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class StargateHelper {

    //Also used by stargate (package-private)
    static ObjectMapper mapper = new ObjectMapper();

    public static class Endpoints {
        public static String AUTH_TOKEN = String.format("%s/api/rest/v1/auth", ASTRA_URL);
        public static String COLLECTIONS = String.format("%s/api/rest/v2/namespaces/%s/collections", ASTRA_URL, ASTRA_DB_NS);
    }

    private static final String ASTRA_CLUSTER_ID = "f2aa8aba-0308-4caf-a96e-b37d9c343f94";
    private static final String ASTRA_CLUSTER_REGION = "us-east1";
    private static final String ASTRA_DB_USERNAME = "kawoosh";
    private static final String ASTRA_DB_NS = "kawoosh";
    private static final String ASTRA_DB_PASSWORD = "kawoosh";

    public static final String ASTRA_URL =
            String.format("https://%s-%s.apps.astra.datastax.com", ASTRA_CLUSTER_ID, ASTRA_CLUSTER_REGION);

    private static String authToken = null;

    private static HttpURLConnection astraConnect(String method, String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        switch(method){
            case "GET":
                conn.setRequestMethod(method);
                break;
            case "PATCH":
                conn.setRequestMethod("POST");
                conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                break;
            case "PUT":
            case "POST":
                conn.setRequestMethod(method);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                break;
            default:
                throw new NotImplementedException("method not (yet?) implemented: "+method);
        }
        conn.setRequestProperty("x-cassandra-request-id", UUID.randomUUID().toString());
        return conn;
    }

    private static void generateAuthToken() throws IOException {
        ObjectNode node = mapper.createObjectNode();
        node.put("username", ASTRA_DB_USERNAME);
        node.put("password", ASTRA_DB_PASSWORD);
        HttpURLConnection conn = astraConnect("POST", Endpoints.AUTH_TOKEN);
        JsonNode ret = send(conn, node);
        authToken = ret.get("authToken").asText();
        conn.disconnect();
    }

    private static JsonNode send(HttpURLConnection conn, JsonNode data) throws IOException {
        System.out.println(data);
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = data.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode / 100 != 2 && responseCode != 409 ) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        return mapper.readTree(conn.getInputStream());
    }

    static JsonNode insert(JsonNode data, String collectionName, String docId) throws IOException {
        if(authToken==null) generateAuthToken();
        HttpURLConnection conn = astraConnect("PATCH", String.format("%s/%s/%s", Endpoints.COLLECTIONS, collectionName, docId));
        conn.setRequestProperty("x-cassandra-token", authToken);
        return send(conn, data);
    }


    static JsonNode read(String collectionName, String docId) throws IOException {
        HttpURLConnection conn = astraConnect("GET", String.format("%s/%s/%s", Endpoints.COLLECTIONS, collectionName, docId));
        conn.setRequestProperty("x-cassandra-token", authToken);
        int toto = conn.getResponseCode();
        
        if (conn.getResponseCode() / 100 != 2) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        return mapper.readTree(conn.getInputStream());
    }
}
