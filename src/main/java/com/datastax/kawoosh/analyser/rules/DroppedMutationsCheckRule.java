package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

public class DroppedMutationsCheckRule extends TableValueInRangeRule {
    public DroppedMutationsCheckRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "Dropped Mutations", "Dropped Mutations", 0d,0d);
    }
}
