package com.datastax.kawoosh.parser;

import com.datastax.kawoosh.common.ClusterConfig;
import com.datastax.kawoosh.common.ClusterConfigBuilder;
import com.datastax.kawoosh.common.IpPathPair;

import java.util.stream.Stream;

public abstract class DirectoryParser {
    String rootPath;
    ClusterConfigBuilder clusterConfigBuilder;
    YamlReader yamlReader;
    TableStatReader tableStatReader;

    String nodesPath;
    String clusterName;
    Stream<IpPathPair> cassandraYamls;
    Stream<IpPathPair> dseYamls;
    Stream<IpPathPair> tableStats;
    Stream<IpPathPair> nodetoolStatuses;
    Stream<IpPathPair> cassandraEnvs;
    Stream<IpPathPair> versions;
    Stream<IpPathPair> describeClusters;
    Stream<IpPathPair> ntpStats;
    Stream<IpPathPair> osInfos;
    Stream<IpPathPair> schemas;

    public DirectoryParser(String rootPath,
                           ClusterConfigBuilder clusterConfigBuilder,
                           YamlReader yamlReader,
                           TableStatReader tableStatReader) {
        this.rootPath = rootPath;
        this.clusterConfigBuilder = clusterConfigBuilder;
        this.yamlReader = yamlReader;
        this.tableStatReader = tableStatReader;
    }

    public Stream<ClusterConfig> readDiag(){
        Stream<ClusterConfig> cassandraYamlsResults = yamlReader(cassandraYamls);
        Stream<ClusterConfig> dseYamlsResults = yamlReader(dseYamls);
        Stream<ClusterConfig> tableStatsResult = tableStatsReader(tableStats);

        return Stream.of(
                cassandraYamlsResults,
                dseYamlsResults,
                tableStatsResult)
                .flatMap(c -> c);
    }

    private Stream<ClusterConfig> yamlReader(Stream<IpPathPair> yamls) {
        return yamls.flatMap(pair -> yamlReader.read(pair.getPath()).map(entry ->
                clusterConfigBuilder.Build(clusterName,
                        pair.getIp(),
                        pair.getRelativePath(),
                        entry.getKey().toString(),
                        entry.getValue().toString())));
    }

    private Stream<ClusterConfig> tableStatsReader(Stream<IpPathPair> tableStats){
        return tableStats.flatMap( ts -> tableStatReader.read(ts.getPath()).map(entry ->
                clusterConfigBuilder.Build(clusterName,
                        ts.getIp(),
                        ts.getRelativePath(),
                        entry.getKey().toString(),
                        entry.getValue().toString())));
    }
}
