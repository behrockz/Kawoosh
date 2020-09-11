package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;
import com.datastax.kawoosh.common.ClusterConfig;

import java.util.List;
import java.util.stream.Collectors;

public class AutoSnapshotCheckRule extends SpecificValueForAconfigRule {
    public AutoSnapshotCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Auto Snapshot", "auto_snapshot", "true");
    }
}
