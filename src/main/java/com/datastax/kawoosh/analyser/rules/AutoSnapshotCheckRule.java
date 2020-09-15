package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public class AutoSnapshotCheckRule extends SpecificValueForAconfigRule {
    public AutoSnapshotCheckRule(DataStorage storage) {
        super(storage, "Auto Snapshot", "auto_snapshot", "true");
    }
}
