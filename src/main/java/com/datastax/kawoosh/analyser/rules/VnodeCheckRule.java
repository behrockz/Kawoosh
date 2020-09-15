package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.Arrays;

public class VnodeCheckRule extends AggregatorRule {
    public VnodeCheckRule(DataStorage storage) {
        super(storage, "Vnode Check", Arrays.asList(new String[]{"num_tokens", "allocate_tokens_for_keyspace", "allocate_tokens_for_local_replication_factor"}));
    }
}
