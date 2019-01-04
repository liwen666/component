package com.temp.common.base.classload;

import com.temp.common.base.classload.base.DiskClassLoader;

public class DiskClassLoadExt extends DiskClassLoader {
    public DiskClassLoadExt(String path) {
        super(path);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
