package com.datastax.kawoosh.common;

public class Config {
    private String nodeIp;
    private String filename;
    private String confName;
    private String value;

    public Config() {
    }

    public Config(String nodeIp, String filename, String confName, String value) {
        this.nodeIp = nodeIp;
        this.filename = filename;
        this.confName = confName;
        this.value = value;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public String getFilename() {
        return filename;
    }

    public String getConfName() {
        return confName;
    }

    public String getValue() {
        return value;
    }


    public String toString(){
        return getNodeIp() + " -> " +
                getConfName() + ": " +
                getValue();
    }
}
