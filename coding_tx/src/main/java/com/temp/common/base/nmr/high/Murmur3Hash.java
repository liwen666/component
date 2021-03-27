package com.temp.common.base.nmr.high;

import java.nio.charset.StandardCharsets;

public class Murmur3Hash {

    public static long hash(String key) {
        return Murmur3HashAlgorithm.hash32(key.getBytes(StandardCharsets.UTF_8));
    }
    public static long[] hash128(String key) {
        return Murmur3HashAlgorithm.hash128(key.getBytes(StandardCharsets.UTF_8));
    }
}