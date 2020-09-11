package com.datastax.kawoosh.parser;

import com.datastax.kawoosh.common.ClusterConfig;
import com.datastax.kawoosh.common.ClusterConfigBuilder;
import com.datastax.kawoosh.common.IpPathPair;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OpsCenterGeneratedDiag extends DirectoryParser {

    public OpsCenterGeneratedDiag(String rootPath,
                                  ClusterConfigBuilder clusterConfigBuilder,
                                  YamlReader yamlReader, TableStatReader tableStatReader) {
        super(rootPath,
                clusterConfigBuilder,
                yamlReader,
                tableStatReader);

        nodesPath = rootPath + File.separator + "nodes";
        String[] nodes = new File(nodesPath).list();

        cassandraYamls = groupFiles(nodes, "conf/cassandra/cassandra.yaml");
        dseYamls = groupFiles(nodes, "conf/dse/dse.yaml");
        tableStats = groupFiles(nodes, "nodetool/cfstats");
    }

    Stream<IpPathPair> groupFiles(String[] nodes, String pathFromNodes){
        return Arrays.stream(nodes).map(node -> getPair(node, pathFromNodes)).filter(Objects::nonNull);
    }

    protected IpPathPair getPair(String nodeIp, String pathFromNodes){
        String path = nodesPath + File.separator + nodeIp + File.separator + pathFromNodes;
        if(new File(path).exists())
            return new IpPathPair(nodeIp, path, path.replace(rootPath, ""));

        return null;
    }

    @Override
    public Stream<ClusterConfig> readDiag() {
        File root = new File(rootPath);
        clusterName = root.getName().substring(0, root.getName().indexOf('-'));
        List<String> files = Arrays.stream(root.listFiles())
                .map(f -> f.getName())
                .collect(Collectors.toList());
        Stream.Builder<ClusterConfig> streamBuilder = Stream.builder();

        if(files.contains("opscenterd"))
            streamBuilder.add(clusterConfigBuilder.Build(clusterName, "", root.getName(), "OpsCenter", "True"));

        Stream<ClusterConfig> result = super.readDiag();
        return Stream.concat(streamBuilder.build(), result);
    }
}
