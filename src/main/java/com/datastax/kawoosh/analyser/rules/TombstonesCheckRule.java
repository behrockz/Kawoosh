package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

public class TombstonesCheckRule extends TableValueInRangeRule {
    public TombstonesCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Tombstones", "Maximum tombstones per slice (last five minutes)",0d,1000d);
    }
}
