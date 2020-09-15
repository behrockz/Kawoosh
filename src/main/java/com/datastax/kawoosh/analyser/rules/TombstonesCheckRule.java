package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public class TombstonesCheckRule extends TableValueInRangeRule {
    public TombstonesCheckRule(DataStorage storage) {
        super(storage, "Tombstones", "Maximum tombstones per slice (last five minutes)",0d,1000d);
    }
}
