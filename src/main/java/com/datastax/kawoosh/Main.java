package com.datastax.kawoosh;

import com.datastax.kawoosh.analyser.Analyser;
import com.datastax.kawoosh.analyser.ClusterConfigRetriver;
import com.datastax.kawoosh.analyser.rules.AutoBootStrapCheckRule;
import com.datastax.kawoosh.analyser.rules.AutoSnapshotCheckRule;
import com.datastax.kawoosh.analyser.rules.LargePartitionCheckRule;
import com.datastax.kawoosh.analyser.rules.Rule;
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

import java.net.ServerSocket;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        ClusterConfigBuilder builder = new ClusterConfigImpl(args[1], args[2], args[3], args[4]);
        YamlReader yamlReader = new YamlReader();
        TableStatReader tableStatReader = new TableStatReader();
        ClusterInfoReader clusterInfoReader = new ClusterInfoReader();
        DirectoryParser parser = new OpsCenterGeneratedDiag(args[0], builder, yamlReader, tableStatReader, clusterInfoReader);
        DataStorage storage = new MapStorage();
        parser.readDiag().forEach(conf -> storage.write(conf));

        ClusterConfigRetriver clusterConfigRetriver = new ClusterConfigRetriver(storage, args[1], args[2], args[3], args[4], parser.getClusterName());
        ArrayList<Rule> ruleList = new ArrayList<Rule> ();
        ruleList.add(new AutoBootStrapCheckRule(clusterConfigRetriver));
        ruleList.add(new AutoSnapshotCheckRule(clusterConfigRetriver));
        ruleList.add(new VnodeCheckRule(clusterConfigRetriver));
        ruleList.add(new ConcurrencyCheckRule(clusterConfigRetriver));
        ruleList.add(new CompactionCheckRule(clusterConfigRetriver));
        ruleList.add(new SeedListRule(clusterConfigRetriver));
        ruleList.add(new ClusterIdRule(clusterConfigRetriver));
        ruleList.add(new LargePartitionCheckRule(clusterConfigRetriver));
        ruleList.add(new NbOfSSTablesCheckRule(clusterConfigRetriver));
        Analyser analyser = new Analyser(ruleList);
        analyser.analyse().forEach(s -> System.out.println(s));
        System.out.println("Done!");
    }
}
