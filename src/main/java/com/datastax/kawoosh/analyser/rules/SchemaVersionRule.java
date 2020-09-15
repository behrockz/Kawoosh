package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public class SchemaVersionRule extends AllEqualRule {
    public SchemaVersionRule(DataStorage storage) {
        super(storage, "Same Schema Version ", "Schema versions");
    }
}
