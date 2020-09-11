package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VnodeCheckRule extends AggregatorRule {
    public VnodeCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Vnode Check", Arrays.asList(new String[]{"num_tokens", "allocate_tokens_for_keyspace", "allocate_tokens_for_local_replication_factor"}));
    }
}
