package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PrintLineStorage extends DataStorage {
    public PrintLineStorage(Cluster cluster) {
        super(cluster);
    }

    @Override
    public CompletableFuture<List<Config>> read(String confName) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> write(Config conf) {
        System.out.println(conf.toString());
        return null;
    }
}
