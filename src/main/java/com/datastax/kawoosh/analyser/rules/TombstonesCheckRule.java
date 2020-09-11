package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

public class TombstonesCheckRule extends TableValueInRangeRule {
    public TombstonesCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Tombstones", "Maximum tombstones per slice (last five minutes)", (Long) 0L,(Long) 1000L);
    }
}
