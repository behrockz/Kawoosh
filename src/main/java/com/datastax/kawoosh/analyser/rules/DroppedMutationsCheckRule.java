package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public class DroppedMutationsCheckRule extends TableValueInRangeRule {
    public DroppedMutationsCheckRule(DataStorage storage) {
        super(storage, "Dropped Mutations", "Dropped Mutations", 0d,0d);
    }
}
