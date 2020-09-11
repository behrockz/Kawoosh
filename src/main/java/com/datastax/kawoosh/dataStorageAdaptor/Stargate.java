package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.ClusterConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.NotImplementedException;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AlreadyConnectedException;
import java.util.UUID;

import java.util.List;

public class Stargate implements DataStorage {

    private static String AUTH_TOKEN_ENDPOINT = "/api/rest/v1/auth";
    private static String COLLECTIONS_ENDPOINT =

    private static String ASTRA_CLUSTER_ID = "14c3fe6e-59d0-49eb-bf9a-2dee80b7de76";
    private static String ASTRA_CLUSTER_REGION = "europe-west1";
    private static String ASTRA_DB_USERNAME = "pierrotws";
    private static String ASTRA_DB_PASSWORD = "kawoosh";
    private static String ASTRA_CREDS =
            "{\"username\":\"" + ASTRA_DB_USERNAME +"\", \"password\":\"" + ASTRA_DB_PASSWORD + "\"}";
    private static String ASTRA_URL =
            "https://" + ASTRA_CLUSTER_ID + '-' + ASTRA_CLUSTER_REGION + ".apps.astra.datastax.com";

    //instance
    private ObjectMapper mapper = new ObjectMapper();
    private String authToken;

    private HttpURLConnection writer = null;
    private HttpURLConnection reader;

    private HttpURLConnection astraConnect(String method, String endpoint) throws IOException {
        URL url = new URL(ASTRA_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        switch(method){
            case "GET":
                conn.setRequestMethod("GET");
                break;
            case "POST":
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                break;
            default:
                throw new NotImplementedException("method not (yet?) implemented: "+method);
        }
        conn.setRequestProperty("x-cassandra-request-id", UUID.randomUUID().toString());
        return conn;
    }

    public String generateAuthToken(){
        String authTokenString = null;
        try {
            HttpURLConnection conn = astraConnect("POST", AUTH_TOKEN_ENDPOINT);

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = ASTRA_CREDS.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() / 100 != 2) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            authTokenString = mapper.readTree(conn.getInputStream()).get("authToken").asText();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authTokenString;
    }

    public void openWriter(){
        if(writer != null){
            throw new AlreadyConnectedException();
        }
        writer = astraConnect("POST", COLLECTIONS_ENDPOINT);
    }

    public static void main(String[] args){
        Stargate sg = new Stargate();
        System.out.println(sg.generateAuthToken());
    }

    @Override
    public List<ClusterConfig> read(String year, String quarter, String platform, String group, String clusterName, String confName) {
        return null;
    }

    @Override
    public void write(ClusterConfig conf) {
        Client client;
        //Response r = client.target(ASTRA_URL ).request(MediaType.APPLICATION_JSON).post(Entity.entity(conf, MediaType.APPLICATION_JSON));
        //handleResponse(r);
    }

    private void handleResponse(Response r){
        //TODO ..
    }

    public void close(){
        if (writer != null){
            writer.disconnect();
        }
        if (reader != null){
            reader.disconnect();
        }
    }
}
