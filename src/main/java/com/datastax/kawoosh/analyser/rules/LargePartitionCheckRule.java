package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

public class LargePartitionCheckRule extends TableValueInRangeRule {
    public LargePartitionCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Large Partition", "Compacted partition maximum bytes", (Long) 0L,(Long) 100000000L);
    }
}
