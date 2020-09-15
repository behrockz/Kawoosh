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

    List<String> existingConf = Lists.newArrayList();

    public StargateStorage(Cluster cluster) {
        super(cluster);
    }


    @Override
    public List<Config> read(String confName) {
        List<Config> list = Lists.newArrayList();
        String collectionName = getCollectionName();
        try {
            ObjectNode read = (ObjectNode) StargateHelper.read(collectionName, confName.replaceAll(" ", "_"));
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
            String collectionName = getCollectionName();
            StargateHelper.insert(jnode, collectionName, conf.getConfName().replaceAll(" ", "_"));
        } catch(IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    String getCollectionName(){
        return cluster.getClusterName();
    }

    public static void main(String[] args) {
        Cluster cluster = new Cluster("2020", "Q3", "Staging", "Sky_OVP_UMVDSE");
        StargateStorage sg = new StargateStorage(cluster);
        Config cg = new Config( "IP1", "name1", "config1" , "val1");

        Config cg2 = new Config("IP2", "name2", "config1" , "val2");
        sg.write(cg);

        sg.write(cg2);

        List<Config> t = sg.read( "config1");
        for(Config c : t){
            System.out.println(c);
        }
    }
}