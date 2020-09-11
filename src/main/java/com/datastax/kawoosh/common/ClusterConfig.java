package com.datastax.kawoosh.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClusterConfig {
    private String year;
    private String quarter;
    private String platform;
    private String group;
    private String clusterName;
    private String nodeIp;
    private String filename;
    private String confName;
    private String value;

    public ClusterConfig() {
    }

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

    @JsonIgnore
    public String getCollectionName(){
        return String.format("%s_%s_%s_%s_%s", year, quarter, platform, group, clusterName);
    }

    @JsonIgnore
    public String getId(){
        return confName;
    }

    @JsonIgnore
    public String getPropertyName(){
        return nodeIp;

    public ClusterConfig(){}


    public ClusterConfig(String year, String quarter, String platform, String group, String clusterName, String nodeIp, String filename, String confName, String value) {
        this.setYear(year);
        this.setQuarter(quarter);
        this.setPlatform(platform);
        this.setGroup(group);
        this.setClusterName(clusterName);
        this.setNodeIp(nodeIp);
        this.setFilename(filename);
        this.setConfName(confName);
        this.setValue(value);
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
                "year='" + getYear() + '\'' +
                ", quarter='" + getQuarter() + '\'' +
                ", platform='" + getPlatform() + '\'' +
                ", group='" + getGroup() + '\'' +
                ", clusterName='" + getClusterName() + '\'' +
                ", nodeIp='" + getNodeIp() + '\'' +
                ", filename='" + getFilename() + '\'' +
                ", confName='" + getConfName() + '\'' +
                ", value='" + getValue() + '\'' +
                '}';
    }

    public String PretyToString(){
        return getNodeIp() + " -> " +
                getConfName() + ": " +
               getValue();
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
