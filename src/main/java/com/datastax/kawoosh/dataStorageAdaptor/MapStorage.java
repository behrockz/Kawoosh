package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MapStorage extends DataStorage {
    HashMap<String, List<Config>> map = new HashMap<>();

    public MapStorage(Cluster cluster) {
        super(cluster);
    }

    @Override
    public List<Config> read(String confName) {
        return map.get(confName);
    }

    @Override
    public void write(Config conf) {
        if(!map.containsKey(getKey(conf)))
            map.put(getKey(conf), new ArrayList<>());

        map.get(getKey(conf)).add(conf);
    }

    private String getKey(Config clusterConfig){
        return clusterConfig.getConfName();
    }

}
