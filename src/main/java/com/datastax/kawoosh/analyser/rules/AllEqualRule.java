package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AllEqualRule extends Rule {

    protected String ruleName;
    protected String configName;

    public AllEqualRule(DataStorage storage, String ruleName, String configName) {
        super(storage);
        this.ruleName = ruleName;
        this.configName = configName;
    }

    @Override
    public String check() {
        List<Config> configs = storage.read(configName);
        long count = configs.stream().map(Config::getValue).distinct().count();
        if(count == 1)
            return "Rule " + ruleName + " returned success!";

        String result = "Rule " + ruleName + " returns failure. The values are:\n\t";
        result += configs.stream()
                .map(Config::toString)
                .collect(Collectors.joining("\n\t"));
        return result;
    }
}
