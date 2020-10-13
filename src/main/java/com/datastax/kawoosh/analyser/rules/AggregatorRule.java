package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AggregatorRule extends Rule {

    protected String ruleName;
    protected List<String> configNames;

    public AggregatorRule(DataStorage storage, String ruleName, List<String> configNames) {
        super(storage);
        this.ruleName = ruleName;
        this.configNames = configNames;
    }

    @Override
    public CompletableFuture<String> check() {
        return CompletableFuture.supplyAsync(() ->  configNames
                .stream()
                .map(c -> storage.read(c))
                .map(futureConfig -> futureConfig.thenApply(configs -> getStream(configs)))
                .flatMap(completableFutureStreams -> getStream(completableFutureStreams))
                .sorted(Comparator.comparing(Config::getNodeIp)))
        .thenApply(configStream -> getResult(configStream));
    }

    private Stream<Config> getStream(CompletableFuture<Stream<Config>> configStreams) {
        try {
            return configStreams.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Stream<Config> getStream(List<Config> configs) {
        return configs == null ? Stream.empty() : configs.stream();
    }

    private String getResult(Stream<Config> sortedConfigs){
        StringBuilder result = new StringBuilder("Rule " + ruleName + " returns inconclusive result. The values are:\n\t");
        result.append(String.join("\n\t", sortedConfigs
                .map(clusterConfig -> clusterConfig.toString())
                .collect(Collectors.toList())));
        return  result.toString();
    }
}
