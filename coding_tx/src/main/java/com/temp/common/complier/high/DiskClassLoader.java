package com.temp.common.complier.high;

import java.util.Map;


public class DiskClassLoader extends ClassLoader {

    private Map<String, byte[]> clazz;

    public DiskClassLoader(Map<String, byte[]> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected Class<?> findClass(String name) {
        return defineClass(name, clazz.get(name), 0, clazz.get(name).length);
    }
}