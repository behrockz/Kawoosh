package com.datastax.kawoosh.common;

public interface ClusterConfigBuilder {
    ClusterConfig Build(String clusterName, String nodeIp, String filename, String confName, String value);
}
