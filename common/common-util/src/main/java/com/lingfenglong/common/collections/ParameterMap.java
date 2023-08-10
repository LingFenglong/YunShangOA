package com.lingfenglong.common.collections;

public class ParameterMap extends StringHashMap{
    public String add(String key, String value) {
        return super.put(key, value);
    }
}
