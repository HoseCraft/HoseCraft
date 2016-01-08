package com.infermc.hosecraft.wrappers;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Similar to Bukkit/Spigots class of the same name. Although much more simpler.
public class YamlConfiguration {
    private HashMap<Object, Object> dataMap;
    private Yaml yml = new Yaml();

    public YamlConfiguration() {
        this.dataMap = new HashMap<Object, Object>();
    }

    public YamlConfiguration(File file) {
        load(file);
    }

    public boolean load(File file) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(file.toString());
            this.dataMap = new HashMap<Object,Object>((Map) yml.load(stream));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean load(InputStream stream) {
        this.dataMap = new HashMap<Object, Object>((Map) yml.load(stream));
        return true;
    }

    // Getters.
    public ConfigSection getSection(String path) {
        if (dataMap.containsKey(path)) {
            Object section = dataMap.get(path);
            if (section instanceof Map) {
                return new ConfigSection(new HashMap((Map) section));
            } else {

            }
        }
        return null;
    }

    public ConfigSection getRoot() {
        return new ConfigSection(dataMap);
    }

    public void setSection(String path, ConfigSection section) {
        dataMap.put(path, section);
    }

    public void save(File file) throws IOException {
        Writer writer = new FileWriter(file);
        yml.dump(dataMap, writer);
        writer.close();
    }

    public void save(OutputStream input) throws IOException {
        Writer writer = new OutputStreamWriter(input);
        yml.dump(dataMap, writer);
        writer.close();
    }
}
