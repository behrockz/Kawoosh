package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

import java.util.Arrays;
import java.util.List;

public class ConcurrencyCheckRule extends AggregatorRule {
    public ConcurrencyCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Concurrency Check",
                Arrays.asList(new String[]{"concurrent_reads",
                        "concurrent_writes",
                        "concurrent_counter_writes",
                        "concurrent_materialized_view_writes"})

        );
    }
}
