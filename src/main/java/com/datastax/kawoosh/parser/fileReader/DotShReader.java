package com.datastax.kawoosh.parser.fileReader;

import com.datastax.kawoosh.common.Tuple;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DotShReader implements Reader{
    @Override
    public Stream<Tuple> read(String path) {
        ProcessBuilder pb = new ProcessBuilder(path);
        List<String> command = pb.command();
        command.stream().collect(Collectors.joining("\n"));
        return Stream.<Tuple>builder().build();
    }
}
