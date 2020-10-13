package com.datastax.kawoosh.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IpPathPair {
    String ip;
    String path;
    String relativePath;
}
