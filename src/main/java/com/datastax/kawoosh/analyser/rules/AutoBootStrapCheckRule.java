package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

public class AutoBootStrapCheckRule extends SpecificValueForAconfigRule {
    public AutoBootStrapCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Auto Bootstrap", "auto_bootstrap", "true");
    }
}
