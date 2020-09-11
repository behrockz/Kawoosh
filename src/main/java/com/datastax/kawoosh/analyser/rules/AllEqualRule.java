package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;
import com.datastax.kawoosh.common.ClusterConfig;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AllEqualRule extends Rule {

    protected String ruleName;
    protected String configName;

    public AllEqualRule(ClusterConfigRetriver clusterConfigRetriver, String ruleName, String configName) {
        super(clusterConfigRetriver);
        this.ruleName = ruleName;
        this.configName = configName;
    }

    @Override
    public String check() {
        List<ClusterConfig> clusterConfigs = clusterConfigRetriver.queryStorage(configName);
        long count = clusterConfigs.stream().map(c -> c.getValue()).distinct().count();
        if(count == 1)
            return "Rule " + ruleName + " returned success!";

        String result = "Rule " + ruleName + " returns failure. The values are:\n\t";
        result += String.join("\n\t", clusterConfigs.stream()
                .map(clusterConfig -> clusterConfig.PretyToString())
                .collect(Collectors.toList()));
        return result;
    }
}
