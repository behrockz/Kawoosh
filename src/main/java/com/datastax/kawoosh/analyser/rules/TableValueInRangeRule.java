package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class TableValueInRangeRule extends Rule {
    protected String ruleName;
    protected String configName;
    protected Double minValue;
    protected Double maxValue;

    public TableValueInRangeRule(DataStorage storage,
                                 String ruleName,
                                 String configName,
                                 Double minValue,
                                 Double maxValue) {
        super(storage);
        this.ruleName = ruleName;
        this.configName = configName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public CompletableFuture<String> check() {
        return storage
            .read(configName)
            .thenApply(configs -> getResult(configs));
    }

    private String getResult(List<Config> configs) {
        if(configs == null || configs.isEmpty())
            return "Rule " + ruleName + " is inconclusive due to lack of data!";

        List<String> results = configs.stream()
                .filter(Objects::isNull)
                .filter(c -> (Double.parseDouble(c.getValue())>maxValue)
                || (Double.parseDouble(c.getValue())<minValue))
                .map(c -> c.toString())
                .collect(Collectors.toList());

        String result = String.format("Rule %s (min: %s, max: %s) returns ", ruleName, minValue, maxValue);
        StringBuilder retVal = new StringBuilder(result);
        if(results.isEmpty()) {
            retVal.append("success!");
        } else {
            retVal.append("these as out of range: ");
            retVal.append(String.join("\n\t", results));
        }
        return retVal.toString();
    }
}
