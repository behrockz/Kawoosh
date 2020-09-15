package com.datastax.kawoosh.analyser;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.List;

public class ClusterConfigRetriever {
    DataStorage dataStorage;
    Cluster cluster;

    public ClusterConfigRetriever(DataStorage dataStorage, Cluster cluster) {
        this.dataStorage = dataStorage;
        this.cluster = cluster;
    }

    public List<Config> queryStorage(String confName) {
        return dataStorage.read(confName);
    }

}
