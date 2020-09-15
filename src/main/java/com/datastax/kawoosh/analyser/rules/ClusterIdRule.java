package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.Arrays;

public class ClusterIdRule extends AggregatorRule {
    public ClusterIdRule(DataStorage storage) {
        super(storage, "ClusterId",

                Arrays.asList("bdp_version",
                        "cassandra_versions",
                        "cluster_cores",
                        "cluster_os",
                        "cluster_ram",
                        "dc_count",
                        "node_count",
                        "opscenter_version")


        );



    }
}
