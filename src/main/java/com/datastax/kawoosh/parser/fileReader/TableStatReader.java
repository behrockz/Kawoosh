package com.datastax.kawoosh.parser.fileReader;

import com.datastax.kawoosh.common.Tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableStatReader implements Reader {
    public Stream<Tuple> read(String path){
        Stream.Builder<Tuple> stream = Stream.builder();

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
                    Tuple valueTuple = readValue(line);
                    if(!valueTuple.getValue1().equals("Keyspace"))
                        continue;
                    keyspace = valueTuple.getValue2();
                    continue;
                }

                if(!line.isEmpty()){
                    Tuple lineValue = readValue(line);
                    if(lineValue.getValue1().equals("Table")){
                        table = lineValue.getValue2();
                        continue;
                    }

                    String value1 =  lineValue.getValue1();
                    String value2 = lineValue.getValue2();
                    String value3 = keyspace + (table != null ? "." + table : "");
                    stream.add(new Tuple(value1, value2, value3));
                } else {
                    table = null;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.build();
    }

    Tuple readValue(String line){
        line = line.trim();
        if(line.isEmpty())
            return new Tuple("","");
        String key = line.substring(0, line.indexOf(':')).trim();
        String value = line.substring(line.indexOf(':') + 1).trim();

        return new Tuple(key, value);
    }

}
