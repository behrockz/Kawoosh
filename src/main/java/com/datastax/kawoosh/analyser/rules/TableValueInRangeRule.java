package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;
import com.datastax.kawoosh.common.ClusterConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TableValueInRangeRule extends Rule {
    protected String ruleName;
    protected String configName;
    protected Double minValue;
    protected Double maxValue;

    public TableValueInRangeRule(ClusterConfigRetriver clusterConfigRetriver,
                                 String ruleName,
                                 String configName,
                                 Double minValue,
                                 Double maxValue) {
        super(clusterConfigRetriver);
        this.ruleName = ruleName;
        this.configName = configName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public String check() {
        List<ClusterConfig> clusterConfigs = clusterConfigRetriver.queryStorage(configName);
        if(clusterConfigs == null || clusterConfigs.isEmpty())
            return "Rule " + ruleName + " is inconclusive due to lack of data!";

        List<String> results= new ArrayList<>();;
        clusterConfigs.forEach((cc) -> {if((Double.parseDouble(cc.getValue())>maxValue) || (Double.parseDouble(cc.getValue())<minValue)) {results.add("\n\t" + cc.PretyToString());};});

        if(results.isEmpty())
            return "Rule " + ruleName + " (min:" + minValue + ", max:" + maxValue + ") returns success!";

        String retVal = "Rule " + ruleName + " (min:" + minValue + ", max:" + maxValue + ") returns these as out of range:";
        retVal += String.join("\n\t", results);
        return retVal;

    }
}
