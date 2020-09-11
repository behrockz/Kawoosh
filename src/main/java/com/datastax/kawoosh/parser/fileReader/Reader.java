package com.datastax.kawoosh.parser.fileReader;

import java.util.stream.Stream;

public interface Reader {
    Stream<Pair> read(String path);
}
