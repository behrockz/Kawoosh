package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.Arrays;

public class ConcurrencyCheckRule extends AggregatorRule {
    public ConcurrencyCheckRule(DataStorage storage) {
        super(storage, "Concurrency Check",
                Arrays.asList("concurrent_reads",
                        "concurrent_writes",
                        "concurrent_counter_writes",
                        "concurrent_materialized_view_writes")

        );
    }
}
