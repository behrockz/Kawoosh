package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;
import com.datastax.kawoosh.common.Config;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AllEqualRule extends Rule {

    protected String ruleName;
    protected String configName;

    public AllEqualRule(ClusterConfigRetriever clusterConfigRetriver, String ruleName, String configName) {
        super(clusterConfigRetriver);
        this.ruleName = ruleName;
        this.configName = configName;
    }

    @Override
    public String check() {
        List<Config> clusterConfigs = clusterConfigRetriver.queryStorage(configName);
        long count = clusterConfigs.stream().map(c -> c.getValue()).distinct().count();
        if(count == 1)
            return "Rule " + ruleName + " returned success!";

        String result = "Rule " + ruleName + " returns failure. The values are:\n\t";
        result += String.join("\n\t", clusterConfigs.stream()
                .map(clusterConfig -> clusterConfig.toString())
                .collect(Collectors.toList()));
        return result;
    }
}
