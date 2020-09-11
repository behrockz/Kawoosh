package com.datastax.kawoosh;

import com.datastax.kawoosh.common.ClusterConfigBuilder;
import com.datastax.kawoosh.common.ClusterConfigImpl;
import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.datastax.kawoosh.dataStorageAdaptor.Dummy;
import com.datastax.kawoosh.parser.DirectoryParser;
import com.datastax.kawoosh.parser.OpsCenterGeneratedDiag;
import com.datastax.kawoosh.parser.YamlReader;


public class Main {
    public static void main(String[] args) {

        ClusterConfigBuilder builder = new ClusterConfigImpl(args[1], args[2], args[3], args[4]);
        YamlReader yamlReader = new YamlReader();
        DirectoryParser parser = new OpsCenterGeneratedDiag(args[0], builder, yamlReader);
        DataStorage storage = new Dummy();
        parser.readDiag().forEach(conf -> storage.write(conf));

        System.out.println("Done!");
    }
}
