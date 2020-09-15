package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public class AutoBootStrapCheckRule extends SpecificValueForAconfigRule {
    public AutoBootStrapCheckRule(DataStorage storage) {
        super(storage, "Auto Bootstrap", "auto_bootstrap", "true");
    }
}
