package com.datastax.kawoosh.parser.fileReader;

import com.datastax.kawoosh.common.Tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DescribeClusterReader implements Reader {
    @Override
    public Stream<Tuple> read(String path) {
        Stream.Builder<Tuple> stream = Stream.builder();

        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            boolean areVersions = false;
            for (String line: lines.collect(Collectors.toList())) {
                if(line.isEmpty())
                    continue;
                if (line.contains("Schema versions")) {
                    areVersions = true;
                    continue;
                }
                if(areVersions){
                    line = line.trim();
                    String[] split = line.split(":");
                    String version = split[0];
                    String ips = split[1].trim();
                    ips = ips.substring(1, ips.length()-1);
                    ips = Arrays.stream(ips.split(",")).sorted().collect(Collectors.joining(", "));
                    ips = String.format("%s, %s", version, ips);
                    stream.add(new Tuple("Schema versions", ips));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.build();
    }
}
