package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

public class NbOfSSTablesCheckRule extends TableValueInRangeRule {
    public NbOfSSTablesCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "SSTable Count", "SSTable count", (Long) 0L,(Long) 40L);
    }
}
