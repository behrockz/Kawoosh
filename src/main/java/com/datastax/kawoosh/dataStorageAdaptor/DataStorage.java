package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.ClusterConfig;

import java.util.List;

public interface DataStorage {
    List<ClusterConfig> read(String year, String program, String group, String clusterName, String confName);

    void write(ClusterConfig conf);
}
