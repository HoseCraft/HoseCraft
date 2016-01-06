package com.infermc.hosecraft.wrappers;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Thomas on 06/01/2016.
 */
public class ConfigSection {
    private Map dataMap;

    public ConfigSection() {
        this.dataMap = Collections.emptyMap();
    }
    public ConfigSection(Map data) {
        this.dataMap = data;
    }

    // Setter
    public void set(String path,Object obj) {
        dataMap.put(path,obj);
    }

    // Getters.
    public Object get(String path) {
        if (dataMap == null) return null;
        if (dataMap.containsKey(path)) {
            Object res = dataMap.get(path);
            return res;
        }
        return null;
    }
    public Object get(String path, Object def) {
        Object res = get(path);
        if (res == null) {
            return def;
        }
        return res;
    }

    // String
    public String getString(String path) {
        Object str = get(path);
        if (str != null) {
            if (str instanceof String) {
                return str.toString();
            }
        }
        return null;
    }
    public String getString(String path, String def) {
        String res = getString(path);
        if (res == null) {
            return def;
        }
        return res;
    }
}
