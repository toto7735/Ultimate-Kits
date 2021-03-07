package main.me.toto;

import main.me.toto.commands.Commands;
import main.me.toto.files.SaveFile;
import main.me.toto.files.SettingsFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UltimateKits extends JavaPlugin {

    private static UltimateKits instance;
    public static UltimateKits getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Bukkit.getLogger().info("§e§l-----------------------");
        Bukkit.getLogger().info("§6§lUltimate Kits §e0.1 §6by toto7735");
        Bukkit.getLogger().info("§eBeen §aEnabled!");
        Bukkit.getLogger().info("§e§l-----------------------");
        new SettingsFile(this);
        new Commands(this);
        SaveFile.setupSaveFile();
    }

    public void onDisable() {
        Bukkit.getLogger().info("§e§l-----------------------");
        Bukkit.getLogger().info("§6§lUltimate Kits §e0.1 §6by toto7735");
        Bukkit.getLogger().info("§eBeen §cDisabled!");
        Bukkit.getLogger().info("§e§l-----------------------");
    }
}
