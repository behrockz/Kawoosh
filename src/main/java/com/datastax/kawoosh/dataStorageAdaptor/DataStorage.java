package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.ClusterConfig;

public interface DataStorage {
    ClusterConfig read(String year, String program, String group, String clusterName, String confName);

    void write(ClusterConfig conf);
}
