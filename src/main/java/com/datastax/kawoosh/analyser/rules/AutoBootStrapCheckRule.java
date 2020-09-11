package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

public class AutoBootStrapCheckRule extends SpecificValueForAconfigRule {
    public AutoBootStrapCheckRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "Auto Bootstrap", "auto_bootstrap", "true");
    }
}
