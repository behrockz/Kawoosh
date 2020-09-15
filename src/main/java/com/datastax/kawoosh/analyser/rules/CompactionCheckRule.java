package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.Arrays;

public class CompactionCheckRule extends AggregatorRule {
    public CompactionCheckRule(DataStorage storage) {
        super(storage,
                "Compaction Check",
                Arrays.asList("compaction_throughput_mb_per_sec",
                        "compaction_large_partition_warning_threshold_mb",
                        "concurrent_compactors"));




    }
}
