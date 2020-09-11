package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;

import java.util.List;

public class CompactionCheckRule extends AggregatorRule {
    public CompactionCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver, "Compaction Check",
                List.of("compaction_throughput_mb_per_sec",
                        "compaction_large_partition_warning_threshold_mb",
                        "concurrent_compactors"));
    }
}
