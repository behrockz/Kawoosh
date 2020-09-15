package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;

import java.util.List;

public abstract class DataStorage {
    protected Cluster cluster;

    public DataStorage(Cluster cluster) {
        this.cluster = cluster;
    }

    public abstract List<Config> read(String confName);

    public abstract void write(Config conf);
}
