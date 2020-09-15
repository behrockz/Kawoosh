package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public class SeedListRule extends AllEqualRule {
    public SeedListRule(DataStorage storage) {
        super(storage, "Similar Seed list", "seed_provider");
    }
}
