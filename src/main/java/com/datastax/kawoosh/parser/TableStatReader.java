package com.datastax.kawoosh.parser;

import javafx.util.Pair;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableStatReader {
    Stream<Pair<String, String>> read(String path){
        Stream.Builder<Pair<String, String>> stream = Stream.builder();

        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            String keyspace = null;
            String table = null;
            for (String line: lines.collect(Collectors.toList())) {
                if(line.contains("----------------")) {
                    keyspace = null;
                    table = null;
                    continue;
                }
                if(keyspace == null){
                    Pair<String, String> valuePair = readValue(line);
                    if(!valuePair.getKey().equals("Keyspace"))
                        continue;
                    keyspace = valuePair.getValue();
                    continue;
                }

                if(!line.isEmpty()){
                    Pair<String, String> lineValue = readValue(line);
                    if(lineValue.getKey().equals("Table")){
                        table = lineValue.getValue();
                        continue;
                    }

                    String value = lineValue.getValue();
                    String key = keyspace + (table != null ? "." + table : "") + "." + lineValue.getKey();

                    stream.add(new Pair(key, value));
                } else {
                    table = null;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.build();
    }

    Pair<String, String> readValue(String line){
        line = line.trim();
        String key = line.substring(0, line.indexOf(':') );
        String value = line.substring(line.indexOf(':') + 1).trim();

        return new Pair<>(key, value);
    }

}
