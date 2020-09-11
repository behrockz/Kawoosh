package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

public class SeedListRule extends AllEqualRule {
    public SeedListRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Similar Seed list", "seed_provider");
    }
}
