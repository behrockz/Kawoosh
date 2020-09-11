package com.datastax.kawoosh.parser;

import com.datastax.kawoosh.common.ClusterConfig;
import com.datastax.kawoosh.common.ClusterConfigBuilder;
import com.datastax.kawoosh.common.IpPathPair;
import com.datastax.kawoosh.parser.fileReader.Reader;
import com.datastax.kawoosh.parser.fileReader.TableStatReader;
import com.datastax.kawoosh.parser.fileReader.YamlReader;

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
        Stream<ClusterConfig> cassandraYamlsResults = parseTheFiles(yamlReader, cassandraYamls);
        Stream<ClusterConfig> dseYamlsResults = parseTheFiles(yamlReader, dseYamls);
        Stream<ClusterConfig> tableStatsResult = parseTheFiles(tableStatReader, tableStats);

        return Stream.of(
                cassandraYamlsResults,
                dseYamlsResults,
                tableStatsResult)
                .flatMap(c -> c);
    }

    private Stream<ClusterConfig> parseTheFiles(Reader reader, Stream<IpPathPair> files){
        return files.flatMap( f -> reader.read(f.getPath()).map(entry ->
                clusterConfigBuilder.Build(clusterName,
                        f.getIp(),
                        f.getRelativePath(),
                        entry.getKey(),
                        entry.getValue())));
    }
}
