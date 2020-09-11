package com.datastax.kawoosh.parser.fileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableStatReader implements Reader {
    public Stream<Pair> read(String path){
        Stream.Builder<Pair> stream = Stream.builder();

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
                    Pair valuePair = readValue(line);
                    if(!valuePair.getKey().equals("Keyspace"))
                        continue;
                    keyspace = valuePair.getValue();
                    continue;
                }

                if(!line.isEmpty()){
                    Pair lineValue = readValue(line);
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

    Pair readValue(String line){
        line = line.trim();
        if(line.isEmpty())
            return new Pair("","");
        String key = line.substring(0, line.indexOf(':')).trim();
        String value = line.substring(line.indexOf(':') + 1).trim();

        return new Pair(key, value);
    }

}
