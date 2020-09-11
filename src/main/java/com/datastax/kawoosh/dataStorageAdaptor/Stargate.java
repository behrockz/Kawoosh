package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.ClusterConfig;
import com.fasterxml.jackson.databind.JsonNode;
import jersey.repackaged.com.google.common.collect.Lists;
import com.datastax.kawoosh.dataStorageAdaptor.StargateHelper.Endpoints;
import java.io.IOException;
import java.net.HttpURLConnection;

import java.util.List;

public class Stargate implements DataStorage {

    List<String> existingConf = Lists.newArrayList();


    @Override
    public List<ClusterConfig> read(String year, String quarter, String platform, String group, String clusterName, String confName) {
        return null;
    }

    


    @Override
    public void write(ClusterConfig conf) {
        JsonNode jnode = StargateHelper.mapper.valueToTree(conf);
        if(existingConf.contains(conf.getConfName())){
            StargateHelper.insert(jnode, Endpoints.COLLECTIONS);
        }
        else {
            StargateHelper.update(jnode, Endpoints.COLLECTIONS);
        }
    }

    public static void main(String[] args) {
        Stargate sg = new Stargate();
        sg.write(null);
    }
}