package com.datastax.kawoosh;

import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;
import com.datastax.kawoosh.dataStorageAdaptor.Stargate;
import com.datastax.kawoosh.parser.DirectoryParser;
import com.datastax.kawoosh.parser.OpsCenterGeneratedDiag;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        DirectoryParser parser = new OpsCenterGeneratedDiag();
        DataStorage storage = new Stargate();

        parser.readDiag(Path.of(args[0]), args[1], args[2], args[3]).forEach(conf -> storage.write(conf));

        System.out.println("Done!");
    }
}
