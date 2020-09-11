package com.datastax.kawoosh.analyser.rules;

import com.datastax.kawoosh.analyser.ClusterConfigRetriver;
import com.datastax.kawoosh.common.ClusterConfig;

import java.util.List;
import java.util.stream.Collectors;

public class AutoSnapshotCheckRule extends Rule {

    private String configName = "auto_snapshot";

    public AutoSnapshotCheckRule(ClusterConfigRetriver clusterConfigRetriver) {
        super(clusterConfigRetriver);
    }

    @Override
    public String check() {
        List<ClusterConfig> clusterConfigs = clusterConfigRetriver.queryStorage(configName);
        if(clusterConfigs == null ||
                clusterConfigs.isEmpty() ||
                clusterConfigs.stream().allMatch(cc -> cc.getValue().toLowerCase().equals("true")))
            return "Rule auto_snapshot returned success!";
        String result = "Rule auto_snapshot failed!\n\t";
        result += String.join("\n\t", clusterConfigs.stream()
                .map(clusterConfig -> clusterConfig.toString())
                .collect(Collectors.toList()));
        return result;
    }
}
