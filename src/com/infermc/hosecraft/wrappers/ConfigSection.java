package com.infermc.hosecraft.wrappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigSection {
    private HashMap<Object,Object> dataMap;

    public ConfigSection() {
        this.dataMap = new HashMap<Object, Object>();
    }

    public ConfigSection(HashMap data) {
        this.dataMap = data;
    }

    // Setter
    public void set(String path, Object obj) {
        dataMap.put(path, obj);
    }

    // Getters.
    public HashMap<Object,Object> get() {
        return this.dataMap;
    }
    //public HashMap<String path, HashMap> getSection(String path) { return new ConfigSection(this.dataMap.get(path));  }

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

    // Double
    public Double getDouble(String path) {
        String str =  get(path).toString();
        if (str != null) return Double.parseDouble(str);
        return null;
    }

    public Double getString(String path, Double def) {
        Double res = getDouble(path);
        if (res == null) {
            return def;
        }
        return res;
    }
}
