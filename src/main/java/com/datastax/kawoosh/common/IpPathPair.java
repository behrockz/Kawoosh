package com.datastax.kawoosh.common;

import java.io.File;

public class IpPathPair {
    String ip;
    String path;
    String relativePath;

    public IpPathPair(String ip, String path, String relativePath) {
        this.ip = ip;
        this.path = path;
        this.relativePath = relativePath.trim();
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
