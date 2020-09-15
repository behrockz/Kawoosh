package com.datastax.kawoosh.common;

public class Cluster {
    private String year;
    private String quarter;
    private String environmentType;
    private String clusterName;

    public Cluster() {
    }

    public Cluster(String year, String quarter, String environmentType, String clusterName) {
        this.year = year;
        this.quarter = quarter;
        this.environmentType = environmentType;
        this.clusterName = clusterName;
    }

    public String getYear() {
        return year;
    }

    public String getQuarter() {
        return quarter;
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public String getClusterName() {
        return clusterName;
    }
}
