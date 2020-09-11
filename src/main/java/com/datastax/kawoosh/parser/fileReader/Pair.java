package com.datastax.kawoosh.parser.fileReader;

public class Pair {
    Object key;
    Object value;

    public Pair(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key.toString();
    }

    public String getValue() {
        return value.toString();
    }
}
