package com.kvart;

import java.util.Map;

public class JSON {

    private Map<String, String> form;
    private Map<String, String> headers;

    @Override
    public String toString() {
        return "JSON{" +
                "form=" + form +
                ", headers=" + headers +
                '}';
    }
}
