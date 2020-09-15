package com.datastax.kawoosh.dataStorageAdaptor.astra.mapper;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

@Entity
public class ConfigRow {
    @PartitionKey(1)
    private String year;

    @PartitionKey(2)
    private String quarter;

    @PartitionKey(3)
    private String environmentType;

    @PartitionKey(4)
    private String confName;

    @ClusteringColumn(1)
    private String nodeIp;

    private String value;
    private String filePath;

    public ConfigRow() {
    }

    public ConfigRow(String year, String quarter, String environmentType, String confName, String nodeIp, String value, String filePath) {
        this.year = year;
        this.quarter = quarter;
        this.environmentType = environmentType;
        this.confName = confName;
        this.nodeIp = nodeIp;
        this.value = value;
        this.filePath = filePath;
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

    public String getConfName() {
        return confName;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public String getValue() {
        return value;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public void setEnvironmentType(String environmentType) {
        this.environmentType = environmentType;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
