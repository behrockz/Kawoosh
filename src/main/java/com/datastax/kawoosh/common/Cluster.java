package com.datastax.kawoosh.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cluster {
    private String year;
    private String quarter;
    private String environmentType;
    private String clusterName;
}
