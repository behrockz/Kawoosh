package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;
import com.datastax.kawoosh.common.ClusterConfig;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TableValueInRangeRule extends Rule {
    protected String ruleName;
    protected String configName;
    protected String minValue;
    protected String maxValue;

    public TableValueInRangeRule(ClusterConfigRetriver clusterConfigRetriver,
                                 String ruleName,
                                 String configName,
                                 String minValue,
                                 String maxValue) {
        super(clusterConfigRetriver);
        this.ruleName = ruleName;
        this.configName = configName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public String check() {
        List<ClusterConfig> clusterConfigs = clusterConfigRetriver.queryStorage(configName);
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
