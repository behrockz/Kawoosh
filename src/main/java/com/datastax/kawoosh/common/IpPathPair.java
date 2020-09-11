package com.datastax.kawoosh.common;

public class IpPathPair {
    String ip;
    String path;
    String relativePath;

    public IpPathPair(String ip, String path, String relativePath) {
        this.ip = ip;
        this.path = path;
        this.relativePath = relativePath;
    }

    public String getIp() {
        return ip;
    }

    public String getPath() {
        return path;
    }

    public String getRelativePath() {
        return relativePath;
    }
}
