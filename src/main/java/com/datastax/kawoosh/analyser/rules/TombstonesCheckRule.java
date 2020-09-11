package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

public class TombstonesCheckRule extends TableValueInRangeRule {
    public TombstonesCheckRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "Tombstones", "Maximum tombstones per slice (last five minutes)",0d,1000d);
    }
}
