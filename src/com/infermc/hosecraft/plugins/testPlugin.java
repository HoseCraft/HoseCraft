package com.infermc.hosecraft.plugins;

public class testPlugin extends Plugin {
    @Override
    public void onEnable() {
        getLogger().info("Test Plugin Loaded!");
        getServer().getPluginManager().getPlugin("test");
    }
}
