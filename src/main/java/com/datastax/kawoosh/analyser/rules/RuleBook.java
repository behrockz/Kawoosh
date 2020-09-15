package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.List;
import java.util.stream.Stream;

public class RuleBook {
    List<Rule> rules;

    public RuleBook(DataStorage storage) {
        this.rules = List.of(
                new ClusterIdRule(storage),
                new AutoBootStrapCheckRule(storage),
                new AutoSnapshotCheckRule(storage),
                new VnodeCheckRule(storage),
                new ConcurrencyCheckRule(storage),
                new CompactionCheckRule(storage),
                new SeedListRule(storage),
                new LargePartitionCheckRule(storage),
                new NbOfSSTablesCheckRule(storage),
                new TombstonesCheckRule(storage),
                new DroppedMutationsCheckRule(storage),
                new SchemaVersionRule(storage));
    }

    public Stream<Rule> getRules() {
        return rules.stream();
    }
}
