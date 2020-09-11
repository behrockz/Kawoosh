package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

public class SeedListRule extends AllEqualRule {
    public SeedListRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "Similar Seed list", "seed_provider");
    }
}
