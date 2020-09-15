package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;
import com.datastax.kawoosh.common.Config;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AggregatorRule extends Rule {

    protected String ruleName;
    protected List<String> configNames;

    public AggregatorRule(ClusterConfigRetriever clusterConfigRetriver, String ruleName, List<String> configNames) {
        super(clusterConfigRetriver);
        this.ruleName = ruleName;
        this.configNames = configNames;
    }

    @Override
    public String check() {
        Stream<Config> sorted = configNames.stream().map(c -> clusterConfigRetriver.queryStorage(c))
                .flatMap(c -> c == null? Stream.empty() : c.stream())
                .sorted(Comparator.comparing(Config::getNodeIp));
        String result = "Rule " + ruleName + " returns inconclusive result. The values are:\n\t";
        result += String.join("\n\t", sorted
                .map(clusterConfig -> clusterConfig.toString())
                .collect(Collectors.toList()));
        return result;
    }
}
