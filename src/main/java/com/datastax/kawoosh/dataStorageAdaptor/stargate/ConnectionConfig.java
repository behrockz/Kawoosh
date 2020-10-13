package com.datastax.kawoosh.dataStorageAdaptor.stargate;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ConnectionConfig {
    String clusterId;
    String clusterRegion;
    String username;
    String password;
    String keyspace;
    String urlTemplate;
    String authUrlTemplate;
    String restEndpointTemplate;
}
