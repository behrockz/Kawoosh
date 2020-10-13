package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class DataStorage {
    protected Cluster cluster;

    public DataStorage(Cluster cluster) {
        this.cluster = cluster;
    }

    public abstract CompletableFuture<List<Config>> read(String confName);

    public abstract CompletableFuture<Boolean> write(Config conf);
}
