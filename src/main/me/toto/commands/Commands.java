package main.me.toto.commands;

import main.me.toto.UltimateKits;
import main.me.toto.files.SaveFile;
import main.me.toto.gui.SettingGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {

    SettingGui gui = new SettingGui(UltimateKits.getInstance());

    public UltimateKits plugin;
    public Commands(UltimateKits plugin) {
        this.plugin = plugin;
        plugin.getCommand("kit").setExecutor(this);
        plugin.getCommand("editkit").setExecutor(this);
    }


    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        if (command.getName().equals("kit")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("§f[§6§lUltimate Kits§f] §eOnly players can execute this command.");
                return false;
            }
            Player player = (Player) commandSender;
            if (SaveFile.SaveFile.getLong("PlayerData." + player.getUniqueId()) > System.currentTimeMillis()) {
                long timeLeft = (SaveFile.SaveFile.getLong("PlayerData." + player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                String moreTimeMessage = plugin.getConfig().getString("Insufficient Time Message").replace("&", "§");
                player.sendMessage(moreTimeMessage.replace("%more-time%", Long.toString(timeLeft)));
                return false;
            }
            for (int i = 1; i < SaveFile.SaveFile.getConfigurationSection("KitData.DefaultKit").getKeys(false).size() + 1; i++) {
                player.getInventory().addItem(SaveFile.SaveFile.getItemStack("KitData.DefaultKit.Item" + i));
            }
            String message = plugin.getConfig().getString("Recived message").replace("&", "§");
            player.sendMessage(message);
            long coolTime = plugin.getConfig().getInt("Cycle of can recive");
            SaveFile.SaveFile.set("PlayerData." + player.getUniqueId(), System.currentTimeMillis() + (coolTime * 1000));
            SaveFile.saveSaveFile();
        } else if (command.getName().equals("editkit")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("§f[§6§lUltimate Kits§f] §eOnly players can execute this command.");
                return false;
            }
            Player player = (Player) commandSender;
            if (!commandSender.hasPermission("ultimatekits.settingsmenu")) {
                String noPermissionMessage = plugin.getConfig().getString("Insufficient Time Message").replace("&", "§");
                player.sendMessage(noPermissionMessage);
                return false;
            }
            gui.setSettingGuiInventory((Player) commandSender);
        }
        return false;
    }
}
