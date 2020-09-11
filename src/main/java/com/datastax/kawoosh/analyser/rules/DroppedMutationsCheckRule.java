package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

public class DroppedMutationsCheckRule extends TableValueInRangeRule {
    public DroppedMutationsCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Dropped Mutations", "Dropped Mutations", 0d,0d);
    }
}
