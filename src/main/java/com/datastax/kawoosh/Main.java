package com.datastax.kawoosh;

import com.datastax.kawoosh.analyser.Analyser;
import com.datastax.kawoosh.analyser.ClusterConfigRetriever;
import com.datastax.kawoosh.analyser.rules.*;
import com.datastax.kawoosh.common.ClusterConfigBuilder;
import com.datastax.kawoosh.common.ClusterConfigImpl;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.datastax.kawoosh.dataStorageAdaptor.MapStorage;
import com.datastax.kawoosh.parser.DirectoryParser;
import com.datastax.kawoosh.parser.OpsCenterGeneratedDiag;
import com.datastax.kawoosh.parser.fileReader.ClusterInfoReader;
import com.datastax.kawoosh.parser.fileReader.TableStatReader;
import com.datastax.kawoosh.parser.fileReader.YamlReader;


public class Main {
    public static void main(String[] args) {

        YamlReader yamlReader = new YamlReader();
        TableStatReader tableStatReader = new TableStatReader();
        ClusterInfoReader clusterInfoReader = new ClusterInfoReader();
        DataStorage storage = new MapStorage();

        if(args[0].equals("Upload")){
            ClusterConfigBuilder builder = new ClusterConfigImpl(args[2], args[3], args[4], args[5]);
            DirectoryParser parser = new OpsCenterGeneratedDiag(args[1], builder, yamlReader, tableStatReader, clusterInfoReader);
            parser.readDiag().forEach(conf -> storage.write(conf));
        } else if(args[0].equals("Report")){
            ClusterConfigRetriever clusterConfigRetriver = new ClusterConfigRetriever(storage, args[1], args[2], args[3], args[4], args[5]);
            RuleBook ruleBook = new RuleBook(clusterConfigRetriver);
            Analyser analyser = new Analyser(ruleBook);
            analyser.analyse().forEach(s -> System.out.println("***********\n" + s));
        } else if(args[0].equals("Test")) {
            ClusterConfigBuilder builder = new ClusterConfigImpl(args[2], args[3], args[4], args[5]);
            DirectoryParser parser = new OpsCenterGeneratedDiag(args[1], builder, yamlReader, tableStatReader, clusterInfoReader);
            parser.readDiag().forEach(conf -> storage.write(conf));
            ClusterConfigRetriever clusterConfigRetriver = new ClusterConfigRetriever(storage, args[2], args[3], args[4], args[5], parser.getClusterName());
            RuleBook ruleBook = new RuleBook(clusterConfigRetriver);
            Analyser analyser = new Analyser(ruleBook);
            analyser.analyse().forEach(s -> System.out.println("***********\n" + s));
        } else {
            System.out.println(String.format("The task {0} was not recognised, please refer to the readme!", args[0]));
        }

        System.out.println("Done!");
    }
}
