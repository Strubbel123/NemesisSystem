package me.strubbel.nemesis.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.API.YamlManager;

public class PlayerStats {

  public static FileConfiguration getPlayerConfig(Player p) {
    YamlManager yaml = new YamlManager("Spieler/" + p.getUniqueId());
    FileConfiguration cfg = yaml.getCustomConfig();
    return cfg;
  }

  public static void savePlayerConfig(FileConfiguration cfg, Player p) {
    YamlManager yaml = new YamlManager("Spieler/" + p.getUniqueId());
    yaml.saveCustomConfig(cfg);
  }

  public static void saveLogoutLocation(Player p) {
    FileConfiguration cfg = PlayerStats.getPlayerConfig(p);
    cfg.set("lastlogout.world", (Object) p.getWorld().getName());
    cfg.set("lastlogout.outX", (Object) p.getLocation().getX());
    cfg.set("lastlogout.outY", (Object) p.getLocation().getY());
    cfg.set("lastlogout.outZ", (Object) p.getLocation().getZ());
    cfg.set("lastlogout.outYaw", (Object) Float.valueOf(p.getLocation().getYaw()));
    PlayerStats.savePlayerConfig(cfg, p);
  }

  public static void saveLastLocation(Player p) {
    FileConfiguration cfg = PlayerStats.getPlayerConfig(p);
    cfg.set("lastloc.world", (Object) p.getWorld().getName());
    cfg.set("lastloc.outX", (Object) p.getLocation().getX());
    cfg.set("lastloc.outY", (Object) p.getLocation().getY());
    cfg.set("lastloc.outZ", (Object) p.getLocation().getZ());
    cfg.set("lastloc.outYaw", (Object) Float.valueOf(p.getLocation().getYaw()));
    PlayerStats.savePlayerConfig(cfg, p);
    if (p.hasPermission("nemesis.back")) {
      p.sendMessage(Util.getPrefix() + " \u00a77Benutze /back, um zur\u00fcck zu kommen.");
    }
  }

  public static void saveLastLocation(Player p, Location loc) {
    FileConfiguration cfg = PlayerStats.getPlayerConfig(p);
    cfg.set("lastloc.world", (Object) loc.getWorld().getName());
    cfg.set("lastloc.outX", (Object) loc.getX());
    cfg.set("lastloc.outY", (Object) loc.getY());
    cfg.set("lastloc.outZ", (Object) loc.getZ());
    cfg.set("lastloc.outYaw", (Object) Float.valueOf(loc.getYaw()));
    PlayerStats.savePlayerConfig(cfg, p);
    if (p.hasPermission("nemesis.back")) {
      p.sendMessage(Util.getPrefix() + " \u00a77Benutze /back, um zur\u00fcck zu kommen.");
    }
  }

  public static Location getLogoutLocation(Player p) {
    FileConfiguration cfg = PlayerStats.getPlayerConfig(p);
    World w = Bukkit.getServer().getWorld(cfg.getString("lastlogout.world"));
    int x = cfg.getInt("lastlogout.outX");
    int y = cfg.getInt("lastlogout.outY");
    int z = cfg.getInt("lastlogout.outZ");
    double yaw = cfg.getDouble("lastlogout.outYaw", (double) p.getLocation().getYaw());
    return new Location(w, (double) x, (double) y, (double) z, (float) yaw, 0.0f);
  }

  public static Location getLastLocation(Player p) {
    FileConfiguration cfg = PlayerStats.getPlayerConfig(p);
    World w = Bukkit.getServer().getWorld(cfg.getString("lastloc.world"));
    int x = cfg.getInt("lastloc.outX");
    int y = cfg.getInt("lastloc.outY");
    int z = cfg.getInt("lastloc.outZ");
    double yaw = cfg.getDouble("lastloc.outYaw");
    return new Location(w, (double) x, (double) y, (double) z, (float) yaw, 0.0f);
  }
}
