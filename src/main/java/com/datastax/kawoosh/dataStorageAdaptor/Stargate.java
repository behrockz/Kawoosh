package com.datastax.kawoosh.dataStorageAdaptor;

import com.datastax.kawoosh.common.ClusterConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jersey.repackaged.com.google.common.collect.Lists;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Stargate implements DataStorage {

    List<String> existingConf = Lists.newArrayList();


    @Override
    public List<ClusterConfig> read(String year, String quarter, String platform, String group, String clusterName, String confName) {
        List<ClusterConfig> list = Lists.newArrayList();
        String collectionName = String.format("%s_%s_%s_%s_%s", year, quarter, platform, group, clusterName);
        try {
            ObjectNode read = (ObjectNode) StargateHelper.read(collectionName, confName);
            JsonNode data = read.get("data");
            for(Iterator<String> it = data.fieldNames(); it.hasNext(); ) {
                String field = it.next();
                ClusterConfig conf = StargateHelper.mapper.treeToValue(data.get(field), ClusterConfig.class);
                list.add(conf);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void write(ClusterConfig conf) {
        try {
            JsonNode jnode = StargateHelper.mapper.createObjectNode()
                    .set(conf.getPropertyName(),StargateHelper.mapper.valueToTree(conf));

            //System.out.println(jnode.toPrettyString());
            StargateHelper.insert(jnode, conf.getCollectionName(), conf.getConfName());
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Stargate sg = new Stargate();
        ClusterConfig cg = new ClusterConfig("2020", "QUA1", "PLA1", "GRP1", "CL1", "IP1", "name1", "config1" , "val1");

        ClusterConfig cg2 = new ClusterConfig("2020", "QUA1", "PLA1", "GRP1", "CL1", "IP2", "name2", "config1" , "val2");
        sg.write(cg);

        sg.write(cg2);

        List<ClusterConfig> t = sg.read("2020", "QUA1", "PLA1", "GRP1", "CL1", "config1");
        for(ClusterConfig c : t){
            System.out.println(c);
        }
    }
}