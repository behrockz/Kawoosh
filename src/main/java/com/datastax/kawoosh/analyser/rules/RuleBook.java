package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;

import java.util.List;
import java.util.stream.Stream;

public class RuleBook {
    List<Rule> rules;

    public RuleBook(ClusterConfigRetriever clusterConfigRetriver) {
        this.rules = List.of(
                 new ClusterIdRule(clusterConfigRetriver),
                 new AutoBootStrapCheckRule(clusterConfigRetriver),
                 new AutoSnapshotCheckRule(clusterConfigRetriver),
                 new VnodeCheckRule(clusterConfigRetriver),
                 new ConcurrencyCheckRule(clusterConfigRetriver),
                 new CompactionCheckRule(clusterConfigRetriver),
                 new SeedListRule(clusterConfigRetriver),
                 new LargePartitionCheckRule(clusterConfigRetriver),
                 new NbOfSSTablesCheckRule(clusterConfigRetriver),
                 new TombstonesCheckRule(clusterConfigRetriver),
                 new DroppedMutationsCheckRule(clusterConfigRetriver));
    }

    public Stream<Rule> getRules() {
        return rules.stream();
    }
}
