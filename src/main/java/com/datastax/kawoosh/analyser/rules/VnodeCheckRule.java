package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

import java.util.List;

public class VnodeCheckRule extends AggregatorRule {
    public VnodeCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Vnode Check",
                List.of("num_tokens",
                        "allocate_tokens_for_keyspace",
                        "allocate_tokens_for_local_replication_factor"));
    }
}
