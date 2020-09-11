package com.datastax.kawoosh.analyser;

import com.datastax.kawoosh.analyser.rules.Rule;

import java.util.List;
import java.util.stream.Stream;

public class Analyser {
    List<Rule> ruleBook;

    public Analyser(List<Rule> ruleBook) {
        this.ruleBook = ruleBook;
    }

    public Stream<String> analyse(){
        return ruleBook.stream().map(rule -> rule.check());
    }
}
