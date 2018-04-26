package com.kanbujian.annotation;

import java.util.concurrent.ExecutorService;

public @interface Job {
    String executor();
}
