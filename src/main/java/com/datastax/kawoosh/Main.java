package com.datastax.kawoosh;

import com.datastax.kawoosh.analyser.Analyser;
import com.datastax.kawoosh.analyser.rules.*;
import com.datastax.kawoosh.common.Cluster;
import com.datastax.kawoosh.dataStorageAdaptor.MapStorage;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.datastax.kawoosh.parser.DirectoryParser;
import com.datastax.kawoosh.parser.OpsCenterGeneratedDiag;
import com.datastax.kawoosh.parser.fileReader.ClusterInfoReader;
import com.datastax.kawoosh.parser.fileReader.DescribeClusterReader;
import com.datastax.kawoosh.parser.fileReader.TableStatReader;
import com.datastax.kawoosh.parser.fileReader.YamlReader;

public class Main {
    public static void main(String[] args) {
        YamlReader yamlReader = new YamlReader();
        TableStatReader tableStatReader = new TableStatReader();
        ClusterInfoReader clusterInfoReader = new ClusterInfoReader();
        DescribeClusterReader describeClusterReader = new DescribeClusterReader();
        Cluster cluster = new Cluster(args[2], args[3], args[4], args[5]);
        DataStorage storage = new MapStorage(cluster);

        switch (args[0]) {
            case "Upload": {
                DirectoryParser parser = new OpsCenterGeneratedDiag(args[1], yamlReader, tableStatReader, clusterInfoReader, describeClusterReader);
                parser.readDiag().forEach(storage::write);
                break;
            }
            case "Report": {
                RuleBook ruleBook = new RuleBook(storage);
                Analyser analyser = new Analyser(ruleBook);
                analyser.analyse().forEach(s -> System.out.println("***********\n" + s));
                break;
            }
            case "Test": {
                DirectoryParser parser = new OpsCenterGeneratedDiag(args[1], yamlReader, tableStatReader, clusterInfoReader, describeClusterReader);
                parser.readDiag().forEach(storage::write);
                RuleBook ruleBook = new RuleBook(storage);
                Analyser analyser = new Analyser(ruleBook);
                analyser.analyse().forEach(s -> System.out.println("***********\n" + s));
                break;
            }
            default:
                System.out.printf("The task {0} was not recognised, please refer to the readme!%n", args[0]);
                break;
        }

        System.out.println("Done!");
        System.exit(0);
    }
}
