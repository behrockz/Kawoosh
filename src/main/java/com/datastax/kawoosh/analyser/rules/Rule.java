package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

public abstract class Rule {
    protected ClusterConfigRetriever clusterConfigRetriver;

    public Rule(ClusterConfigRetriever clusterConfigRetriver) {
        this.clusterConfigRetriver = clusterConfigRetriver;
    }

    public abstract String check();
}