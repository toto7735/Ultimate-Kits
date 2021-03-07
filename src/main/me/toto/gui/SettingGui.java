package main.me.toto.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import main.me.toto.UltimateKits;
import main.me.toto.files.SaveFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.print.Book;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class SettingGui implements Listener {

    public UltimateKits plugin;
    public SettingGui(UltimateKits plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void setSettingGuiInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 27, "§f[§6§lUltimate Kits§f] §eSetting Gui");
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, item);
        }
        item.setType(Material.BARRIER);
        meta.setDisplayName("§f[§6§lUltimate Kits§f] §aLook forward to the next update");
        meta.setLore(Arrays.asList("§cComming soon!"));
        item.setItemMeta(meta);
        inventory.setItem(11, item);
        item.setType(Material.CHEST);
        meta.setDisplayName("§f[§6§lUltimate Kits§f] §eSet Kits Item");
        meta.setLore(Arrays.asList("§7Click to open set kits item menu!"));
        item.setItemMeta(meta);
        inventory.setItem(13, item);
        item.setType(Material.BARRIER);
        meta.setDisplayName("§f[§6§lUltimate Kits§f] §aLook forward to the next update");
        meta.setLore(Arrays.asList("§cComming soon!"));
        item.setItemMeta(meta);
        inventory.setItem(15, item);
        player.openInventory(inventory);
        item.setType(Material.PLAYER_HEAD);
        meta.setDisplayName("§f[§6§lUltimate Kits§f] §9Discord");
        meta.setLore(Arrays.asList("§7Do you need any help? Join our discord!", "§7Click to join!"));
        item.setItemMeta(meta);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19"));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        item.setItemMeta(skullMeta);
        inventory.setItem(26, item);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equalsIgnoreCase("§f[§6§lUltimate Kits§f] §eSetting Gui")) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getType() == InventoryType.CHEST) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (event.getSlot() == 13) {
                Inventory putItemInventory = Bukkit.createInventory(player, 54, "§f[§6§lUltimate Kits§f] §ePut an item to the kit!");
                player.openInventory(putItemInventory);
            } else if (event.getSlot() == 26) {
                player.closeInventory();
                player.sendMessage("§f[§6§lUltimate Kits§f] §eJoin our discord!");
                player.sendMessage("§f[§6§lUltimate Kits§f] §b§nhttps://discord.gg/AdZyg3KcYg");
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        if (!event.getView().getTitle().equalsIgnoreCase("§f[§6§lUltimate Kits§f] §ePut an item to the kit!")) return;
        int amount = 0;
        for (int i = 0; i < 54; i++) {
            if (inventory.getItem(i) != null) {
                ItemStack item = new ItemStack(inventory.getItem(i));
                if (SaveFile.SaveFile.getItemStack("KitData.DefaultKit.Item1") == null) {
                    SaveFile.SaveFile.set("KitData.DefaultKit.Item1", item);
                    SaveFile.saveSaveFile();
                    amount++;
                    player.sendTitle("§6§lSuccess!", "§eFirst kit setup is complete", 5, 30, 10);
                    player.sendMessage("§f[§6§lUltimate Kits§f] §eKit setup is complete! You have set §6" + amount + " §eitems.");
                    return;
                }
                SaveFile.SaveFile.set("KitData.DefaultKit.Item" + (SaveFile.SaveFile.getConfigurationSection("KitData.DefaultKit").getKeys(false).size() + 1), item);
                SaveFile.saveSaveFile();
                amount++;
            }
        }
        player.sendTitle("§6§lSuccess!", "§eKit setup is complete", 5, 30, 10);
        player.sendMessage("§f[§6§lUltimate Kits§f] §eKit setup is complete! You have added §6" + amount + " §eitems.");
    }

}
