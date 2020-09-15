package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

import java.util.Arrays;

public class ClusterIdRule extends AggregatorRule {
    public ClusterIdRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "ClusterId",

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
