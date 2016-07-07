package com.isuwang.dapeng.bootstrap.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * App Class Loader
 *
 * @author craneding
 * @date 16/1/28
 */
public class AppClassLoader extends URLClassLoader {

    public AppClassLoader(URL[] urls) {
        super(urls, ClassLoader.getSystemClassLoader());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        try {
            return ClassLoaderManager.shareClassLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
            return super.loadClass(name, resolve);
        }
//        if(name.startsWith("com.isuwang.dapeng.core") || name.startsWith("com.isuwang.org.apache.thrift") || name.startsWith("com.isuwang.dapeng.transaction.api")
//                || name.startsWith("com.google.gson"))
//            return ClassLoaderManager.shareClassLoader.loadClass(name);
//        return super.loadClass(name, resolve);
    }
}
