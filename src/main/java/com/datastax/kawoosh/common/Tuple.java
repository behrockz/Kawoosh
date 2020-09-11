package com.datastax.kawoosh.common;

public class Tuple {
    Object value1;
    Object value2;
    Object value3;

    public Tuple(Object value1, Object value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public Tuple(Object value1, Object value2, Object value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public String getValue1() {
        return value1.toString();
    }

    public String getValue2() {
        return value2.toString();
    }

    public String getValue3() {
        return value3 == null ? "" : value3.toString();
    }
}
