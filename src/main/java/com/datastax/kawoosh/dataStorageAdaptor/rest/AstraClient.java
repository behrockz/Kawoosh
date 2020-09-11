package com.datastax.kawoosh.dataStorageAdaptor.rest;

import com.datastax.kawoosh.common.ClusterConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class AstraClient {

    private static String ASTRA_CLUSTER_ID = "14c3fe6e-59d0-49eb-bf9a-2dee80b7de76";
    private static String ASTRA_CLUSTER_REGION = "europe-west1";
    private static String ASTRA_DB_USERNAME = "pierrotws";
    private static String ASTRA_DB_PASSWORD = "kawoosh";
    private static String ASTRA_CREDS =
            "{\"username\":\"" + ASTRA_DB_USERNAME +"\", \"password\":\"" + ASTRA_DB_PASSWORD + "\"}";

    private static String ASTRA_URL =
            "https://" + ASTRA_CLUSTER_ID + '-' + ASTRA_CLUSTER_REGION + ".apps.astra.datastax.com";

    private static String AUTH_TOKEN_ENDPOINT = "/api/rest/v1/auth";

    private ObjectMapper mapper = new ObjectMapper();
    private String authToken;

    public AstraClient() {
       // authToken = //generateAuthToken();
    }





}
