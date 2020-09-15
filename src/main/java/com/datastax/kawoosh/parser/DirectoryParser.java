package com.datastax.kawoosh.parser;

import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.common.IpPathPair;
import com.datastax.kawoosh.parser.fileReader.Reader;
import com.datastax.kawoosh.parser.fileReader.TableStatReader;
import com.datastax.kawoosh.parser.fileReader.YamlReader;

import java.util.stream.Stream;

public abstract class DirectoryParser {
    String rootPath;
    YamlReader yamlReader;
    TableStatReader tableStatReader;

    String nodesPath;
    String clusterName;
    Stream<IpPathPair> cassandraYamls;
    Stream<IpPathPair> dseYamls;
    Stream<IpPathPair> tableStats;
    Stream<IpPathPair> versions;
    Stream<IpPathPair> nodetoolStatuses;
    Stream<IpPathPair> cassandraEnvs;
    Stream<IpPathPair> describeClusters;
    Stream<IpPathPair> ntpStats;
    Stream<IpPathPair> osInfos;
    Stream<IpPathPair> schemas;

    public String getClusterName() {
        return clusterName;
    }

    public DirectoryParser(String rootPath,
                           YamlReader yamlReader,
                           TableStatReader tableStatReader) {
        this.rootPath = rootPath;
        this.yamlReader = yamlReader;
        this.tableStatReader = tableStatReader;
    }

    public Stream<Config> readDiag(){
        Stream<Config> cassandraYamlsResults = parseTheFiles(yamlReader, cassandraYamls);
        Stream<Config> dseYamlsResults = parseTheFiles(yamlReader, dseYamls);
        Stream<Config> tableStatsResult = parseTheFiles(tableStatReader, tableStats);

        return Stream.of(
                cassandraYamlsResults,
                dseYamlsResults,
                tableStatsResult)
                .flatMap(c -> c);
    }

    protected Stream<Config> parseTheFiles(Reader reader, Stream<IpPathPair> files){
        return files.flatMap( f -> reader.read(f.getPath()).map(entry ->
                new Config(
                        f.getIp() + ": " + entry.getValue3(),
                        f.getRelativePath(),
                        entry.getValue1(),
                        entry.getValue2())));
    }
}
