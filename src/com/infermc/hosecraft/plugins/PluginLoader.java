package com.infermc.hosecraft.plugins;

import com.infermc.hosecraft.server.Server;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader extends URLClassLoader {
    public JavaPlugin plugin;
    public String name;
    private Server server;
    private Class main;

    public PluginLoader(ClassLoader parent, Server serverInstance, File file) throws Exception {
        super(new URL[]{file.toURI().toURL()}, parent);
        JarFile jar;
        try {
            jar = new JarFile(file);
        } catch (IOException e) {
            serverInstance.getLogger().warning(e.getMessage());
            return;
        }
        JarEntry pluginFile = jar.getJarEntry("plugin.yml");
        if (pluginFile != null) {
            InputStream pluginstream;

            try {
                pluginstream = jar.getInputStream(pluginFile);
            } catch (IOException e) {
                serverInstance.getLogger().warning(e.getMessage());
                return;
            }

            Yaml pluginyaml = new Yaml();
            Map pluginyml = (Map) pluginyaml.load(pluginstream);

            String name = (String) pluginyml.get("name");
            String mainClass = (String) pluginyml.get("main");
            String desc = (String) pluginyml.get("description");
            String author = (String) pluginyml.get("author");
            Double version = (Double) pluginyml.get("version");

            this.name = name;
            this.server = serverInstance;

            serverInstance.getLogger().info("Attemping to load " + name);

            URLClassLoader child = null;
            try {
                child = new URLClassLoader(new URL[]{file.toURL()}, this.getClass().getClassLoader());
            } catch (MalformedURLException e) {
                serverInstance.getLogger().warning(e.getMessage());
                return;
            }

            try {
                main = Class.forName(mainClass, true, this);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                serverInstance.getLogger().warning("Plugin main class not found: "+e.getMessage());
                return;
            }
        } else {
            serverInstance.getLogger().warning("Missing plugin.yml in " + file.getName());
            return;
        }
    }

    public void initialize(JavaPlugin pl) {
        pl.init(this.name, this.server);
    }

    public void loadClass() throws Throwable {
        // As soon as a new instance is called the plugin is alive!!
        if (main != null) {
            try {
                this.plugin = (JavaPlugin) main.newInstance();
            } catch (InstantiationException e) {
                throw e;
            } catch (IllegalAccessException e) {
                throw e;
            }
        }
    }
}
