package main.me.toto.files;

import main.me.toto.UltimateKits;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SettingsFile {

    private UltimateKits plugin;
    public SettingsFile (UltimateKits plugin) {
        this.plugin = plugin;
        plugin.saveResource("config.yml", false);
    }



    
}
