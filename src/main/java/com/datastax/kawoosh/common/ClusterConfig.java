package com.datastax.kawoosh.common;

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

    ClusterConfig(String year, String quarter, String platform, String group, String clusterName, String nodeIp, String filename, String confName, String value) {
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

    public String getCollectionName(){
        return String.format("{0}-{1}-{2}-{3}-{4}", year, quarter, platform, group, clusterName);
    }

    public String getId(){
        return confName;
    }

    public String getPropertyName(){
        return nodeIp;
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

    @Override
    public String toString() {
        return "ClusterConfig{" +
                "year='" + year + '\'' +
                ", quarter='" + quarter + '\'' +
                ", platform='" + platform + '\'' +
                ", group='" + group + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", nodeIp='" + nodeIp + '\'' +
                ", filename='" + filename + '\'' +
                ", confName='" + confName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String PretyToString(){
        return getNodeIp() + " -> " +
                getConfName() + ": " +
               getValue();
    }

}
