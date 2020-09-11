package com.datastax.kawoosh.common;

public class ClusterConfigImpl implements ClusterConfigBuilder {
    String year;
    String quarter;
    String platform;
    String group;

    public ClusterConfigImpl(String year, String quarter, String platform, String group) {
        this.year = year;
        this.quarter = quarter;
        this.platform = platform;
        this.group = group;
    }

    @Override
    public ClusterConfig Build(String clusterName, String nodeIp, String filename, String confName, String value) {
        return new ClusterConfig(year, quarter, platform, group, clusterName, nodeIp, filename, confName, value);
    }
}
