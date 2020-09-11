package com.datastax.kawoosh.parser.fileReader;

import com.datastax.kawoosh.common.Tuple;

import java.util.stream.Stream;

public interface Reader {
    Stream<Tuple> read(String path);
}
