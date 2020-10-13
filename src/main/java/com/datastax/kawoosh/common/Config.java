package com.datastax.kawoosh.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Config {
    private String nodeIp;
    private String filename;
    private String confName;
    private String value;

    public String toString(){
        return getNodeIp() + " -> " +
                getConfName() + ": " +
                getValue();
    }
}
