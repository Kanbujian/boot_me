package com.kanbujian.util;

import com.sun.org.apache.regexp.internal.RE;

import java.lang.reflect.Proxy;

public class DynamicTest {
    public static void main(String[] args) {
        Book b = new Book();
        b.read();
        Readable r2 = (Readable) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { Readable.class } , new ReadableProxy(b));
        r2.read();
    }
}
