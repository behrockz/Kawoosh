package com.datastax.kawoosh.parser.fileReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

public class ClusterInfoReader implements Reader {
    @Override
    public Stream<Pair> read(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map info = mapper.readValue(new File(path), Map.class);
            return Stream.empty();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }
}
