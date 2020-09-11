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
        public static String AUTH_TOKEN = "/api/rest/v1/auth";
        public static String COLLECTIONS = "/api/rest/v2/namespaces/" + ASTRA_DB_NS + "/collections/";
    }

    private static final String ASTRA_CLUSTER_ID = "886aa59f-29f9-4500-9330-2a05d8dc7ee8";
    private static final String ASTRA_CLUSTER_REGION = "us-east1";
    private static final String ASTRA_DB_USERNAME = "kawoosh";
    private static final String ASTRA_DB_NS = "kawoosh";
    private static final String ASTRA_DB_PASSWORD = "kawoosh";


    private static final String ASTRA_NAMESPACE = "myNameSpace";
    public static final String ASTRA_URL =
            "https://" + ASTRA_CLUSTER_ID + '-' + ASTRA_CLUSTER_REGION + ".apps.astra.datastax.com";
    private static final String COLLECTIONS_ENDPOINT =
            "/api/rest/v2/namespaces/" + ASTRA_NAMESPACE + "/collections"; // "/{collection-id}/{document-id}/{document-path}"


    private static String authToken = null;

    private static HttpURLConnection astraConnect(String method, String endpoint) throws IOException {
        URL url = new URL(StargateHelper.ASTRA_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        switch(method){
            case "GET":
                conn.setRequestMethod(method);
                break;
            case "PATCH":
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
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = data.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() / 100 != 2) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        return mapper.readTree(conn.getInputStream());
    }

    static JsonNode insert(JsonNode data, String endpoint) throws IOException {
        if(authToken==null) generateAuthToken();
        HttpURLConnection conn = astraConnect("POST", endpoint);
        conn.setRequestProperty("x-cassandra-token", authToken);
        return send(conn, data);
    }

    static JsonNode update(JsonNode data, String endpoint) throws IOException {
        HttpURLConnection conn = astraConnect("PATCH", endpoint);
        conn.setRequestProperty("x-cassandra-token", authToken);
        return send(conn, data);
    }
}
