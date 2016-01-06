package com.infermc.hosecraft.wrappers;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

// Similar to Bukkit/Spigots class of the same name. Although much more simpler.
public class YamlConfiguration {
    private Map dataMap;
    private Yaml yml = new Yaml();

    public YamlConfiguration() {
    }

    public YamlConfiguration(File file) {
        load(file);
    }

    public boolean load(File file) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(file.toString());
            this.dataMap = (Map) yml.load(stream);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean load(InputStream stream) {
        this.dataMap = (Map) yml.load(stream);
        return true;
    }

    // Getters.
    public Object get(String path) {
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
        if (dataMap.containsKey(path)) {
            Object res = dataMap.get(path);
            return res.toString();
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
