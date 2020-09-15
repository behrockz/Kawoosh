package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.List;
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
    public String check() {
        List<Config> clusterConfigs = storage.read(configName);
        if (clusterConfigs == null ||
                clusterConfigs.isEmpty() ||
                clusterConfigs.stream().allMatch(cc -> cc.getValue().toLowerCase().equals(expectedValue)))
            return "Rule " + ruleName + " returned success!";
        String result = "Rule " + ruleName + " failed!\n\t";
        result += String.join("\n\t", clusterConfigs.stream()
                .map(clusterConfig -> clusterConfig.toString())
                .collect(Collectors.toList()));
        return result;
    }
}
