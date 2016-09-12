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
        for (Plugin pl : plugins) {
            if (pl.getName().equalsIgnoreCase(name)) {
                return pl;
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

    }

    public List<Plugin> loadPlugins(File directory) throws FileNotFoundException {
        ArrayList<Plugin> list = new ArrayList<Plugin>();
        if (directory.exists()) {
            if (directory.isDirectory()) {
                for (File f : directory.listFiles()) {
                    Plugin pl = loadPlugin(f);
                    if (pl != null) {
                        list.add(pl);
                        plugins.add(pl);
                    }
                }
                return list;
            }
        } else {
            throw new FileNotFoundException();
        }
        return list;
    }

    public Plugin loadPlugin(File file) {
        PluginClassLoader loader = null;
        try {
            loader = new PluginClassLoader(this.getClass().getClassLoader(), serverInstance, file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return loader.plugin;
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