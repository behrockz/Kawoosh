package com.datastax.kawoosh.parser;

import com.datastax.kawoosh.ClusterConfig;

import java.nio.file.Path;
import java.util.stream.Stream;

public class OpsCenterGeneratedDiag implements DirectoryParser {
    @Override
    public Stream<ClusterConfig> readDiag(Path path, String year, String program, String group) {
        return Stream.empty();
    }
}
