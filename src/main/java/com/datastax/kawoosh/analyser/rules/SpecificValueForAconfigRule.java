package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class SpecificValueForAconfigRule extends Rule {
    protected String ruleName;
    protected String configName;
    protected String expectedValue;

    public SpecificValueForAconfigRule(DataStorage storage,
                                       String ruleName,
                                       String configName,
                                       String expectedValue) {
        super(storage);
        this.ruleName = ruleName;
        this.configName = configName;
        this.expectedValue = expectedValue;
    }

    @Override
    public CompletableFuture<String> check() {
        return storage
                .read(configName)
                .thenApply(clusterConfigs -> getResult(clusterConfigs));
    }

    private String getResult(List<Config> clusterConfigs) {
        if (clusterConfigs == null ||
                clusterConfigs.isEmpty() ||
                clusterConfigs.stream().allMatch(cc -> cc.getValue().toLowerCase().equals(expectedValue)))
            return "Rule " + ruleName + " returned success!";
        StringBuilder result = new StringBuilder("Rule " + ruleName + " failed!\n\t");
        result.append(clusterConfigs.stream()
                .map(Config::toString)
                .collect(Collectors.joining("\n\t")));
        return result.toString();
    }
}
