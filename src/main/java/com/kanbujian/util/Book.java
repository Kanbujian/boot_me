package com.kanbujian.util;

public class Book implements Readable {
    @Override
    public void read() {
        System.out.println("I'm a book");
    }
}
