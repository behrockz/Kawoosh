package com.datastax.kawoosh.analyser;

import com.datastax.kawoosh.common.ClusterConfigBuilder;
import com.datastax.kawoosh.parser.fileReader.TableStatReader;
import com.datastax.kawoosh.parser.fileReader.YamlReader;

import java.util.List;

public abstract class RuleBook {
    List<Rule> ruleList;

    public RuleBook(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }
}
