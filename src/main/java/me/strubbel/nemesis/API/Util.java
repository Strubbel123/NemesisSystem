package me.strubbel.nemesis.API;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
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
    return Util.getMain().prefix;
  }

  public static boolean getWorldEdit() {
    if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") == null) {
      return false;
    }
    return true;
  }

  public static File getWorldEditFile() {
    WorldEdit we =
        ((WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit")).getWorldEdit();
    LocalConfiguration config = we.getConfiguration();
    return we.getWorkingDirectoryFile(config.saveDir);
  }

  public static GameMode getGameMode(String mode) {
    GameMode gameMode = null;
    try {
      int gamemodeInt = Integer.parseInt(mode);
      switch (gamemodeInt) {
        case 0: {
          gameMode = GameMode.SURVIVAL;
          break;
        }
        case 1: {
          gameMode = GameMode.CREATIVE;
          break;
        }
        case 2: {
          gameMode = GameMode.ADVENTURE;
          break;
        }
        // case 3: {
        // gameMode = GameMode.SPECTATOR;
        // break;
        // }
      }
      if (gameMode != null) {
        return gameMode;
      }
    } catch (Exception ex) {
      try {
        gameMode = GameMode.valueOf((String) mode.toUpperCase());
        return gameMode;
      } catch (Exception ex2) {
        return null;
      }
    }
    return null;
  }

  public static boolean isPlayerInLobby(Player p) {
    YamlManager yaml = new YamlManager("Lobby");
    FileConfiguration cfg = yaml.getCustomConfig();
    World w = Bukkit.getServer().getWorld(cfg.getString("Spawn.world"));
    if (p.getWorld().equals((Object) w)) {
      return true;
    }
    return false;
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
    p.setSaturation(10.0f);
  }

  public static void heal(Player p) {
    p.setHealth(p.getMaxHealth());
    p.setFoodLevel(20);
    p.setSaturation(10.0f);
  }

  public static void removePotionEffects(Player p) {
    for (PotionEffect effect : p.getActivePotionEffects()) {
      p.getPlayer().removePotionEffect(effect.getType());
    }
  }
}
