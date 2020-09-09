package com.datastax.kawoosh;

public class ClusterConfig {
    String year;
    String quarter;
    String platform;
    String group;
    String clusterName;
    String nodeIp;
    String filename;
    String confName;
    String value;

    public ClusterConfig(String year, String quarter, String platform, String group, String clusterName, String nodeIp, String filename, String confName, String value) {
        this.year = year;
        this.quarter = quarter;
        this.platform = platform;
        this.group = group;
        this.clusterName = clusterName;
        this.nodeIp = nodeIp;
        this.filename = filename;
        this.confName = confName;
        this.value = value;
    }

    public String getYear() {
        return year;
    }

    public String getQuarter() {
        return quarter;
    }

    public String getPlatform() {
        return platform;
    }

    public String getGroup() {
        return group;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public String getFilename() {
        return filename;
    }

    public String getConfName() {
        return confName;
    }

    public String getValue() {
        return value;
    }
}