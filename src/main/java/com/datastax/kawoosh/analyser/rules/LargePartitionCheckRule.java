package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

public class LargePartitionCheckRule extends TableValueInRangeRule {
    public LargePartitionCheckRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "Large Partition", "Compacted partition maximum bytes",  0d, 100000000d);
    }
}
