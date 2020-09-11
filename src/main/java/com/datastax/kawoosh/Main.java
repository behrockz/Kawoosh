package com.datastax.kawoosh;

import com.datastax.kawoosh.analyser.Analyser;
import com.datastax.kawoosh.analyser.ClusterConfigRetriver;
import com.datastax.kawoosh.analyser.rules.AutoBootStrapCheckRule;
import com.datastax.kawoosh.analyser.rules.AutoSnapshotCheckRule;
import com.datastax.kawoosh.analyser.rules.Rule;
import com.datastax.kawoosh.common.ClusterConfigBuilder;
import com.datastax.kawoosh.common.ClusterConfigImpl;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.datastax.kawoosh.dataStorageAdaptor.Dummy;
import com.datastax.kawoosh.dataStorageAdaptor.MapStorage;
import com.datastax.kawoosh.parser.DirectoryParser;
import com.datastax.kawoosh.parser.OpsCenterGeneratedDiag;
import com.datastax.kawoosh.parser.fileReader.ClusterInfoReader;
import com.datastax.kawoosh.parser.fileReader.TableStatReader;
import com.datastax.kawoosh.parser.fileReader.YamlReader;

import java.util.List;


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
        Rule r1 = new AutoBootStrapCheckRule(clusterConfigRetriver);
        Rule r2 = new AutoSnapshotCheckRule(clusterConfigRetriver);
        Analyser analyser = new Analyser(List.of(r1, r2));
        analyser.analyse().forEach(s -> System.out.println(s));
        System.out.println("Done!");
    }
}
