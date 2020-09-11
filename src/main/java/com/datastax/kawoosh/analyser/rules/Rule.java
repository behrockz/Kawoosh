package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

public abstract class Rule {
    protected ClusterConfigRetriver clusterConfigRetriver;

    public Rule(ClusterConfigRetriver clusterConfigRetriver) {
        this.clusterConfigRetriver = clusterConfigRetriver;
    }

    public abstract String check();
}