package com.infermc.hosecraft.plugins;

import com.infermc.hosecraft.server.Server;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class PluginManager {
    private ArrayList<Plugin> plugins = new ArrayList<Plugin>();
    private Server serverInstance;

    public PluginManager(Server si) {
        this.serverInstance = si;
    }

    public Server getServer() {
        return this.serverInstance;
    }

    public Plugin getPlugin(String name) {
        for (Plugin pl : plugins) {
            if (pl.getName().equalsIgnoreCase(name)) {
                return pl;
            }
        }
        return null;
    }
    public void unloadPlugin(String name) {
        Plugin pl = getPlugin(name);
        if (pl != null) unloadPlugin(pl);
    }
    public void unloadPlugin(Plugin plClass) {

    }

    public Plugin[] loadPlugins(File directory) {
        if (directory.isDirectory()) {
            ArrayList<Plugin> list = new ArrayList<Plugin>();
            for (File f : directory.listFiles()) {
                Plugin pl = loadPlugin(f);
                if (pl != null) list.add(pl);
            }
            //return (Plugin[]) list.toArray();
            return null;
        }
        return null;
    }

    public Plugin loadPlugin(File file) {
        JarFile jar;
        try {
            jar = new JarFile(file);
        } catch (IOException e) {
            serverInstance.getLogger().warning(e.getMessage());
            return null;
        }
        JarEntry pluginFile = jar.getJarEntry("plugin.yml");
        if (pluginFile != null) {
            InputStream pluginstream;

            try {
                pluginstream = jar.getInputStream(pluginFile);
            } catch (IOException e) {
                serverInstance.getLogger().warning(e.getMessage());
                return null;
            }

            Yaml pluginyaml = new Yaml();
            Map pluginyml = (Map) pluginyaml.load(pluginstream);

            String name = (String) pluginyml.get("name");
            String mainClass = (String) pluginyml.get("main");
            String desc = (String) pluginyml.get("description");
            String author = (String) pluginyml.get("author");
            Double version = (Double) pluginyml.get("version");

            serverInstance.getLogger().info("Attemping to load "+name);

            URLClassLoader child = null;
            try {
                child = new URLClassLoader(new URL[] {file.toURL()}, this.getClass().getClassLoader());
            } catch (MalformedURLException e) {
                serverInstance.getLogger().warning(e.getMessage());
                return null;
            }
            Class classToLoad = null;
            try {
                classToLoad = Class.forName (mainClass, true, child);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                serverInstance.getLogger().warning(e.getMessage());
                return null;
            }

            Plugin main = new Plugin(serverInstance,name);
            main.getClass().cast(classToLoad);
            main.onEnable();
            return main;
        } else {
            getServer().getLogger().warning("Missing plugin.yml in "+file.getName());
            return null;
        }
    }
}
