package com.gamingutils.telefruit.telefruit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Telefruit extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().log(Level.INFO, "TeleFruit Enabled");
        TelefruitGenerator teleFruitGenerator = new TelefruitGenerator();
        this.getCommand("boundtelefruit").setExecutor(teleFruitGenerator);
        this.getCommand("boundtelefruit").setTabCompleter(teleFruitGenerator);
        this.getServer().getPluginManager().registerEvents(new TelefruitInterceptor(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().log(Level.INFO, "TeleFruit Disabled");
    }
}
