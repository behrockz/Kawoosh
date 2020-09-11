package com.datastax.kawoosh.analyser;

import com.datastax.kawoosh.common.ClusterConfig;

public interface Rule {
    public void check(ClusterConfig conf);
}