package com.temp.common.common;

public interface RedisCacheInterface <T>{
    String findIdKey(T t);
}
