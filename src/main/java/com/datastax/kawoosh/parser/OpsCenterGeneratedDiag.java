package com.datastax.kawoosh.parser;

import com.datastax.kawoosh.common.ClusterConfig;
import com.datastax.kawoosh.common.ClusterConfigBuilder;
import com.datastax.kawoosh.common.IpPathPair;
import com.datastax.kawoosh.parser.fileReader.ClusterInfoReader;
import com.datastax.kawoosh.parser.fileReader.Pair;
import com.datastax.kawoosh.parser.fileReader.TableStatReader;
import com.datastax.kawoosh.parser.fileReader.YamlReader;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OpsCenterGeneratedDiag extends DirectoryParser {
    ClusterInfoReader clusterInfoReader;
    Stream<IpPathPair> clusterInfo;

    public OpsCenterGeneratedDiag(String rootPath,
                                  ClusterConfigBuilder clusterConfigBuilder,
                                  YamlReader yamlReader,
                                  TableStatReader tableStatReader,
                                  ClusterInfoReader clusterInfoReader) {
        super(rootPath,
                clusterConfigBuilder,
                yamlReader,
                tableStatReader);
        this.clusterInfoReader = clusterInfoReader;

        nodesPath = rootPath + File.separator + "nodes";
        String[] nodes = new File(nodesPath).list();

        cassandraYamls = groupFiles(nodesPath, nodes, "conf/cassandra/cassandra.yaml");
        dseYamls = groupFiles(nodesPath, nodes, "conf/dse/dse.yaml");
        tableStats = groupFiles(nodesPath, nodes, "nodetool/cfstats");

        clusterInfo = groupFiles(rootPath, new String[]{ "" }, "cluster_info.json");
    }

    Stream<IpPathPair> groupFiles(String basePath, String[] nodes, String relativePath){
        return Arrays.stream(nodes).map(node -> getPair(basePath, node, relativePath)).filter(Objects::nonNull);
    }

    protected IpPathPair getPair(String basePath, String nodeIp, String relativePath){
        String path = basePath + File.separator + nodeIp + File.separator + relativePath;
        path = path.replace(File.separator.repeat(2), File.separator);
        if(new File(path).exists()){
            return new IpPathPair(nodeIp, path, path.replace(rootPath, ""));
        }

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


        Stream<ClusterConfig> clusterInfoResult = parseTheFiles(clusterInfoReader, clusterInfo);
        Stream<ClusterConfig> superResult = super.readDiag();
        return Stream.of(streamBuilder.build(),
                superResult,
                clusterInfoResult
                ).flatMap(r -> r);
    }
}
