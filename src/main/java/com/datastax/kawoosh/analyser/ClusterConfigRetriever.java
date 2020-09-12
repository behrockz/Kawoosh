package com.datastax.kawoosh.analyser;

import com.datastax.kawoosh.common.ClusterConfig;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.List;

public class ClusterConfigRetriever {
    DataStorage dataStorage;
    String year;
    String quarter;
    String platform;
    String group;
    String clusterName;

    public ClusterConfigRetriever(DataStorage dataStorage, String year, String quarter, String platform, String group, String clusterName) {
        this.dataStorage = dataStorage;
        this.year = year;
        this.quarter = quarter;
        this.platform = platform;
        this.group = group;
        this.clusterName = clusterName;
    }

    public List<ClusterConfig> queryStorage(String confName) {
        return dataStorage.read(year, quarter, platform, group, clusterName, confName);
    }

}
