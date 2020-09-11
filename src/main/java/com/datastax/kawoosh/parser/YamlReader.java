package com.datastax.kawoosh.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;

public class YamlReader {
    Stream<Pair> read(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        InputStream resource;
        try {
            resource = new FileInputStream(path);
            Map<String, String> readValue = mapper.readValue(resource, Map.class);
            return readValue
                    .keySet()
                    .stream()
                    .map(key -> new Pair(key, readValue.get(key) == null ? "" : readValue.get(key)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
