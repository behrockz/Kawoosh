package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

public class NbOfSSTablesCheckRule extends TableValueInRangeRule {
    public NbOfSSTablesCheckRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "SSTable Count", "SSTable count",  0d, 40d);
    }
}
