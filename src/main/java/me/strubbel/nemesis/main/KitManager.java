package me.strubbel.nemesis.main;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import me.strubbel.nemesis.API.YamlManager;

public class KitManager extends JavaPlugin {
  public void saveKit(Player p, String name) {
    YamlManager yaml = new YamlManager("Kits/" + name);
    PlayerInventory inv = p.getInventory();
    FileConfiguration cfg = yaml.getCustomConfig();
    ConfigurationSection sectionContents = cfg.createSection("Kit.Contents");
    for (int i = 0; i < inv.getContents().length; ++i) {
      if (inv.getContents()[i] == null)
        continue;
      sectionContents.set(String.valueOf(i), (Object) inv.getContents()[i]);
    }
    ConfigurationSection sectionArmor = cfg.createSection("Kit.Armor");
    for (int i2 = 0; i2 < p.getInventory().getArmorContents().length; ++i2) {
      if (p.getInventory().getArmorContents()[i2] == null)
        continue;
      sectionArmor.set(String.valueOf(i2), (Object) p.getInventory().getArmorContents()[i2]);
    }
    yaml.saveCustomConfig(cfg);
  }

  public void loadKit(Player p, String name) {
    YamlManager yaml = new YamlManager("Kits/" + name);
    ItemStack[] contentList = new ItemStack[p.getInventory().getContents().length];
    ItemStack[] armorList = new ItemStack[p.getInventory().getArmorContents().length];
    FileConfiguration cfg = yaml.getCustomConfig();
    ConfigurationSection contentSection = cfg.getConfigurationSection("Kit.Contents");
    for (int i = 0; i < p.getInventory().getContents().length; ++i) {
      ItemStack stack;
      if (contentSection.getItemStack(String.valueOf(i)) == null)
        continue;
      contentList[i] = stack = contentSection.getItemStack(String.valueOf(i));
    }
    ConfigurationSection armorSection = cfg.getConfigurationSection("Kit.Armor");
    for (int i2 = 0; i2 < p.getInventory().getArmorContents().length; ++i2) {
      ItemStack stack;
      if (armorSection.getItemStack(String.valueOf(i2)) == null)
        continue;
      armorList[i2] = stack = armorSection.getItemStack(String.valueOf(i2));
    }
    p.getInventory().setContents(contentList);
    p.getInventory().setArmorContents(armorList);
  }

  public void deleteKit(String name) {
    YamlManager yaml = new YamlManager("Kits/" + name);
    yaml.deleteCustomConfig();
  }
}
