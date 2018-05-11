package com.kanbujian.payment;

import java.util.Map;

public interface Converter {
    public Map convertNotifyParams(byte[] data);
}
