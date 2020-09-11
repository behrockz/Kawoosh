package com.datastax.kawoosh.analyser;

import com.datastax.kawoosh.analyser.rules.RuleBook;

import java.util.stream.Stream;

public class Analyser {
    RuleBook ruleBook;

    public Analyser(RuleBook ruleBook) {
        this.ruleBook = ruleBook;
    }

    public Stream<String> analyse(){
        return ruleBook.getRules().map(rule -> rule.check());
    }
}
