package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.concurrent.CompletableFuture;

public abstract class Rule {
    protected DataStorage storage;

    public Rule(DataStorage storage) {
        this.storage = storage;
    }

    public abstract CompletableFuture<String> check();
}