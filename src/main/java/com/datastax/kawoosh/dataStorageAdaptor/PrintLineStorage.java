package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;

import java.util.List;

public class PrintLineStorage extends DataStorage {
    public PrintLineStorage(Cluster cluster) {
        super(cluster);
    }

    @Override
    public List<Config> read(String confName) {
        return null;
    }

    @Override
    public void write(Config conf) {
        System.out.println(conf.toString());
    }
}
