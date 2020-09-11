package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

public class AutoSnapshotCheckRule extends SpecificValueForAconfigRule {
    public AutoSnapshotCheckRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "Auto Snapshot", "auto_snapshot", "true");
    }
}
