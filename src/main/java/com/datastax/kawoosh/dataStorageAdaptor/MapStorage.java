package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.ClusterConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapStorage implements DataStorage {
    HashMap<String, List<ClusterConfig>> map = new HashMap<>();

    @Override
    public List<ClusterConfig> read(String year, String program, String group, String clusterName, String confName) {
        return map.get(confName);
    }

    @Override
    public void write(ClusterConfig conf) {
        if(!map.containsKey(getKey(conf)))
            map.put(getKey(conf), new ArrayList<>());

        map.get(getKey(conf)).add(conf);
    }

    private String getKey(ClusterConfig clusterConfig){
        return clusterConfig.getConfName();
    }

}
