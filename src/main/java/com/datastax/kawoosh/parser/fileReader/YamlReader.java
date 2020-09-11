package com.datastax.kawoosh.parser.fileReader;

import com.datastax.kawoosh.common.Tuple;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;

public class YamlReader implements Reader {
    public Stream<Tuple> read(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        InputStream resource;
        try {
            resource = new FileInputStream(path);
            Map<String, String> readValue = mapper.readValue(resource, Map.class);
            return readValue
                    .keySet()
                    .stream()
                    .map(key -> new Tuple(key, readValue.get(key) == null ? "" : readValue.get(key)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
