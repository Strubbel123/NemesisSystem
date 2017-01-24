package me.strubbel.nemesis.API;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.strubbel.nemesis.main.NemesisMain;

public class Util {
  public static NemesisMain getMain() {
    return NemesisMain.getMain();
  }

  public static String getPrefix() {
    return getMain().prefix;
  }

  public static boolean getWorldEdit() {
    if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") == null)
      return false;
    else
      return true;
  }

  public static File getWorldEditFile() {
    WorldEdit we =
        ((WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit")).getWorldEdit();
    LocalConfiguration config = we.getConfiguration();
    return we.getWorkingDirectoryFile(config.saveDir);
  }

  public static void disableFly(Player p) {
    p.setAllowFlight(false);
    p.setFlying(false);
  }

  public static void enableFly(Player p) {
    p.setAllowFlight(true);
    p.setFlying(true);
  }

  public static void feed(Player p) {
    p.setFoodLevel(20);
    p.setSaturation(10);
  }

  public static void heal(Player p) {
    p.setHealth(p.getMaxHealth());
    p.setFoodLevel(20);
    p.setSaturation(10);
  }

  public static void removePotionEffects(Player p) {
    for (PotionEffect effect : p.getActivePotionEffects()) {
      p.getPlayer().removePotionEffect(effect.getType());
    }
  }
}
