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

public class PluginClassLoader extends URLClassLoader {
    public JavaPlugin plugin;
    private String name;
    private Server server;

    public PluginClassLoader(ClassLoader parent, Server serverInstance, File file) throws MalformedURLException {
        super(new URL[] { file.toURI().toURL() }, parent);
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

            serverInstance.getLogger().info("Attemping to load "+name);

            URLClassLoader child = null;
            try {
                child = new URLClassLoader(new URL[] {file.toURL()}, this.getClass().getClassLoader());
            } catch (MalformedURLException e) {
                serverInstance.getLogger().warning(e.getMessage());
                return;
            }
            Class classToLoad = null;
            try {
                classToLoad = Class.forName (mainClass, true, this);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                serverInstance.getLogger().warning(e.getMessage());
                return;
            }
            JavaPlugin pl = null;
            // As soon as a new instance is called the plugin is alive!!
            try {
                pl = (JavaPlugin) classToLoad.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            this.plugin = pl;
        } else {
            serverInstance.getLogger().warning("Missing plugin.yml in "+file.getName());
            return;
        }
    }

    public void initialize(JavaPlugin pl) {
        System.out.println("Plugin asked to be initalized!");
        pl.init(this.name,this.server);
    }
}
