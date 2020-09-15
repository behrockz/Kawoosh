package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public abstract class Rule {
    protected DataStorage storage;

    public Rule(DataStorage storage) {
        this.storage = storage;
    }

    public abstract String check();
}