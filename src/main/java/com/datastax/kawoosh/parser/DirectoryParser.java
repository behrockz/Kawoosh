package com.datastax.kawoosh.parser;

import com.datastax.kawoosh.ClusterConfig;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface DirectoryParser {
    Stream<ClusterConfig> readDiag(Path path, String year, String program, String group);
}
