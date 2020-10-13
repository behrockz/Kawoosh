package com.datastax.kawoosh;

import com.datastax.kawoosh.analyser.Analyser;
import com.datastax.kawoosh.analyser.rules.*;
import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.common.Config;
import com.datastax.kawoosh.common.Tuple;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.datastax.kawoosh.dataStorageAdaptor.astra.AstraStorage;
import com.datastax.kawoosh.dataStorageAdaptor.stargate.NewStargateStorage;
import com.datastax.kawoosh.dataStorageAdaptor.stargate.RestClient;
import com.datastax.kawoosh.parser.DirectoryParser;
import com.datastax.kawoosh.parser.OpsCenterGeneratedDiag;
import com.datastax.kawoosh.parser.fileReader.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        YamlReader yamlReader = new YamlReader();
        TableStatReader tableStatReader = new TableStatReader();
        ClusterInfoReader clusterInfoReader = new ClusterInfoReader();
        DescribeClusterReader describeClusterReader = new DescribeClusterReader();
        DotShReader dotShReader = new DotShReader();
        Cluster cluster = new Cluster(args[2], args[3], args[4], args[5]);
        ObjectMapper mapper = new ObjectMapper();
        RestClient restClient = new RestClient(mapper, cluster.getClusterName());
        DataStorage storage = new NewStargateStorage(cluster, restClient, mapper);
        //DataStorage storage = new AstraStorage(cluster);

        switch (args[0]) {
            case "Upload": {
                DirectoryParser parser = new OpsCenterGeneratedDiag(args[1],
                        yamlReader,
                        tableStatReader,
                        clusterInfoReader,
                        describeClusterReader,
                        dotShReader);
                parser.readDiag().forEach(storage::write);
                break;
            }
            case "Report": {
                RuleBook ruleBook = new RuleBook(storage);
                Analyser analyser = new Analyser(ruleBook);
                analyser
                        .analyse()
                        .map(s -> s.thenAccept(r -> System.out.println("***********\n" + r)))
                        .forEach(ss-> getaVoid(ss));
                break;
            }
            case "Test": {
                DirectoryParser parser = new OpsCenterGeneratedDiag(args[1],
                        yamlReader,
                        tableStatReader,
                        clusterInfoReader,
                        describeClusterReader,
                        dotShReader);
                List<Config> configStream = parser.readDiag().collect(Collectors.toList());
                System.out.println("Calculated!");
                configStream.parallelStream()
                        .map(c -> storage.write(c).thenAccept( r -> System.out.println(c.toString() + (r ? " Success!" : " Failure" ))))
                        .forEach(ss -> getaVoid(ss));

                RuleBook ruleBook = new RuleBook(storage);
                Analyser analyser = new Analyser(ruleBook);
                analyser
                        .analyse()
                        .map(s -> s.thenAccept(r -> System.out.println("***********\n" + r)))
                        .forEach(ss-> getaVoid(ss));
                break;
            }
            default:
                System.out.printf("The task {0} was not recognised, please refer to the readme!%n", args[0]);
                break;
        }

        System.out.println("Done!");
        System.exit(0);
    }

    private static void getaVoid(CompletableFuture<Void> ss) {
        try {
            ss.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
