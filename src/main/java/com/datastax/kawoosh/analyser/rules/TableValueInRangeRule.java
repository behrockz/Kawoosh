package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriever;
import com.datastax.kawoosh.common.Config;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class TableValueInRangeRule extends Rule {
    protected String ruleName;
    protected String configName;
    protected Double minValue;
    protected Double maxValue;

    public TableValueInRangeRule(ClusterConfigRetriever clusterConfigRetriver,
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
        List<Config> configs = clusterConfigRetriver.queryStorage(configName);
        if(configs == null || configs.isEmpty())
            return "Rule " + ruleName + " is inconclusive due to lack of data!";

        List<String> results = configs.stream()
                .filter(Objects::isNull)
                .filter(c -> (Double.parseDouble(c.getValue())>maxValue)
                || (Double.parseDouble(c.getValue())<minValue))
                .map(c -> c.toString())
                .collect(Collectors.toList());

        if(results.isEmpty())
            return "Rule " + ruleName + " (min:" + minValue + ", max:" + maxValue + ") returns success!";

        String retVal = "Rule " + ruleName + " (min:" + minValue + ", max:" + maxValue + ") returns these as out of range:";
        retVal += String.join("\n\t", results);
        return retVal;

    }
}
