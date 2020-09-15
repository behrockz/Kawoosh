package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public class NbOfSSTablesCheckRule extends TableValueInRangeRule {
    public NbOfSSTablesCheckRule(DataStorage storage) {
        super(storage, "SSTable Count", "SSTable count",  0d, 40d);
    }
}
