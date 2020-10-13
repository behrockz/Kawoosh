package com.datastax.kawoosh.dataStorageAdaptor.stargate;

import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jersey.repackaged.com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

public class StargateStorage extends DataStorage {
    private String collectionName ;

    public StargateStorage(Cluster cluster) {
        super(cluster);
        collectionName = cluster.getClusterName();
    }

    @Override
    public List<Config> read(String confName) {
        List<Config> list = Lists.newArrayList();
        try {
            ObjectNode read = (ObjectNode) StargateHelper.read(collectionName, confName);
            if(read == null)
                return list;
            JsonNode data = read.get("data");
            for(Iterator<String> it = data.fieldNames(); it.hasNext(); ) {
                String field = it.next();
                Config conf = StargateHelper.mapper.treeToValue(data.get(field), Config.class);
                list.add(conf);
            }
        } catch(IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void write(Config conf) {
        try {
            JsonNode jnode = StargateHelper.mapper.createObjectNode()
                    .set(conf.getNodeIp().replace('.', '+'),StargateHelper.mapper.valueToTree(conf));
            System.out.println(conf.toString());
            StargateHelper.insert(jnode, collectionName, conf.getConfName().replaceAll(" ", "_"));
        } catch(IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }
}