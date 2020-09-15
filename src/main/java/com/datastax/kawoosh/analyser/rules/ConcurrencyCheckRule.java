package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

import java.util.Arrays;

public class ConcurrencyCheckRule extends AggregatorRule {
    public ConcurrencyCheckRule(ClusterConfigRetriever clusterConfigRetriver) {
        super(clusterConfigRetriver, "Concurrency Check",
                Arrays.asList("concurrent_reads",
                        "concurrent_writes",
                        "concurrent_counter_writes",
                        "concurrent_materialized_view_writes")

        );
    }
}
