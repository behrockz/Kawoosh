package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.ClusterConfig;

import java.util.List;

public interface DataStorage {
    List<ClusterConfig> read(String year, String quarter, String platform, String group, String clusterName, String confName);

    void write(ClusterConfig conf);
}
