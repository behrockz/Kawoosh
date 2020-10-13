package com.datastax.kawoosh.analyser;

import com.datastax.kawoosh.analyser.rules.RuleBook;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Analyser {
    RuleBook ruleBook;

    public Analyser(RuleBook ruleBook) {
        this.ruleBook = ruleBook;
    }

    public Stream<CompletableFuture<String>> analyse(){
        return ruleBook.getRules().map(rule -> rule.check());
    }
}
