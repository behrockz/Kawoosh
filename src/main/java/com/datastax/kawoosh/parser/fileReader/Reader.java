package com.datastax.kawoosh.parser.fileReader;

import javafx.util.Pair;

import java.util.stream.Stream;

public interface Reader {
    Stream<Pair> read(String path);
}
