package com.infermc.hosecraft.plugins;

import com.infermc.hosecraft.events.*;
import com.infermc.hosecraft.server.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PluginManager {
    private ArrayList<Plugin> plugins = new ArrayList<Plugin>();
    private Server serverInstance;

    public HashMap<Class<?>, HandlerList> listeners = new HashMap<Class<?>, HandlerList>();

    public PluginManager(Server si) {
        this.serverInstance = si;
    }

    public Server getServer() {
        return this.serverInstance;
    }

    public Plugin getPlugin(String name) {
        if (plugins.size() > 0) {
            for (Plugin pl : plugins) {
                if (pl == null) {
                    serverInstance.getLogger().warning("A null plugin got into the list?");
                    return null;
                }
                if (pl.getName() != null) {
                    if (pl.getName().equalsIgnoreCase(name)) return pl;
                } else {
                    serverInstance.getLogger().warning(pl.toString() + " is null?");
                }
            }
        }
        return null;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void unloadPlugin(String name) {
        Plugin pl = getPlugin(name);
        if (pl != null) unloadPlugin(pl);
    }

    public void unloadPlugin(Plugin plClass) {
        // This isn't a thing yes :(
    }

    public List<Plugin> loadPlugins(File directory) throws FileNotFoundException {
        ArrayList<Plugin> list = new ArrayList<Plugin>();

        if (directory.exists()) {
            if (directory.isDirectory()) {
                for (File f : directory.listFiles()) {
                    PluginLoader pLoader = loadPlugin(f);
                    if (pLoader != null) {
                        // Does a plugin with the same name exist?
                        if (getPlugin(pLoader.name) == null) {
                            try {
                                pLoader.loadClass();
                                if (pLoader.plugin != null) {
                                    serverInstance.getLogger().info("Loaded "+pLoader.name);
                                    list.add(pLoader.plugin);
                                    plugins.add(pLoader.plugin);
                                } else {
                                    // This should be unreachable?
                                    serverInstance.getLogger().warning("Unable to load plugin");
                                }
                            } catch (Throwable throwable) {
                                serverInstance.getLogger().warning("Error loading plugin '"+pLoader.name+"' main class!");
                                throwable.printStackTrace();
                            }
                        } else {
                            serverInstance.getLogger().warning("A plugin already exists with the name '"+pLoader.name+"', skipping.");
                        }
                    }
                }
                return list;
            }
        } else {
            throw new FileNotFoundException();
        }
        return list;
    }

    public PluginLoader loadPlugin(File file) {
        PluginLoader loader = null;
        try {
            loader = new PluginLoader(this.getClass().getClassLoader(), serverInstance, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loader;
    }

    public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> klass = type;
        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {
                    Annotation annotInstance = method.getAnnotation(annotation);
                    // TODO process annotInstance
                    methods.add(method);
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return methods;
    }

    public void registerEvents(Listener listener, Plugin plugin) {
        // Does nothing!
        List<Method> methods = getMethodsAnnotatedWith(listener.getClass(), EventHandler.class);
        for (Method m : methods) {
            Class<?>[] types = m.getParameterTypes();
            for (Class<?> c : types) {
                // Where c is the event the method acts upon.
                if (Event.class.isAssignableFrom(c)) {
                    HandlerList handlers;
                    if (listeners.containsKey(c)) {
                        handlers = listeners.get(c);
                    } else {
                        handlers = new HandlerList();
                        listeners.put(c, handlers);
                    }
                    EventListener el = new EventListener(plugin, m, listener);
                    handlers.addListener(el);
                }
            }
        }
    }

    public void callEvent(Event ev) {
        if (listeners.containsKey(ev.getClass())) {
            HandlerList handlers = listeners.get(ev.getClass());
            for (EventListener l : handlers.getListeners()) {
                try {
                    l.getMethod().invoke(l.getListener(), ev);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
