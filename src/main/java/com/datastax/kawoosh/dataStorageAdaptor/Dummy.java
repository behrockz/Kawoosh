package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.ClusterConfig;

import java.util.List;

public class Dummy implements DataStorage {
    @Override
    public List<ClusterConfig> read(String year, String program, String group, String clusterName, String confName) {
        return null;
    }

    @Override
    public void write(ClusterConfig conf) {
        System.out.println(conf.toString());
    }
}
