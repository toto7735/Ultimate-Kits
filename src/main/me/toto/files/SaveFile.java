package main.me.toto.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SaveFile {

    public static File file;
    public static FileConfiguration SaveFile;

    public static void setupSaveFile() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("UltimateKits").getDataFolder(), "saves.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
        SaveFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void saveSaveFile() {
        try {
            SaveFile.save(file);
        } catch (IOException e) {
            System.out.println("cannot save save file.");
        }
    }

    public static void reloadSaveFile() {
        SaveFile = YamlConfiguration.loadConfiguration(file);
    }
}
