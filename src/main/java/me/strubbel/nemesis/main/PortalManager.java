package me.strubbel.nemesis.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.API.YamlManager;

public class PortalManager {
  private WorldEditPlugin worldEdit;
  private YamlManager yaml;
  private FileConfiguration cfg;

  public PortalManager() {
    this.yaml = new YamlManager("Portals");
    this.cfg = this.yaml.getCustomConfig();
    if (Util.getWorldEdit()) {
      this.worldEdit =
          (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    }
  }

  public void createPortal(String name, String target, String gamemode, Player p) {
    if (this.worldEdit == null) {
      p.sendMessage(
          Util.getPrefix() + " \u00a77Du ben\u00f6tigst WorldEdit um diese Funktion zu nutzen.");
      return;
    }
    this.cfg = this.yaml.getCustomConfig();
    Selection selection = this.worldEdit.getSelection(p);
    if (selection != null) {
      if (!this.cfg.contains(name.toLowerCase()) && !target.equalsIgnoreCase("lastlogout")) {
        Location min = selection.getMinimumPoint();
        Location max = selection.getMaximumPoint();
        this.cfg.set(name.toLowerCase() + ".name", (Object) name);
        p.sendMessage("\u00a77Das Portal " + name + " wurde erfolgreich erstellt");
        name = name.toLowerCase();
        this.cfg.set(name + ".target", (Object) target);
        this.cfg.set(name + ".gamemode", (Object) gamemode);
        this.cfg.set(name + ".world", (Object) p.getLocation().getWorld().getName());
        this.cfg.set(name + ".maxX", (Object) max.getX());
        this.cfg.set(name + ".maxY", (Object) max.getY());
        this.cfg.set(name + ".maxZ", (Object) max.getZ());
        this.cfg.set(name + ".minX", (Object) min.getX());
        this.cfg.set(name + ".minY", (Object) min.getY());
        this.cfg.set(name + ".minZ", (Object) min.getZ());
        this.cfg.set(name + ".outX", (Object) p.getLocation().getX());
        this.cfg.set(name + ".outY", (Object) p.getLocation().getY());
        this.cfg.set(name + ".outZ", (Object) p.getLocation().getZ());
        this.cfg.set(name + ".outYaw", (Object) Float.valueOf(p.getLocation().getYaw()));
        this.yaml.saveCustomConfig(this.cfg);
      } else {
        p.sendMessage(
            Util.getPrefix() + " \u00a77Es existiert bereits ein Portal mit diesem Namen.");
      }
    } else {
      p.sendMessage(Util.getPrefix() + " \u00a77Bitte w\u00e4hle einen Bereich aus.");
      return;
    }
    this.yaml.saveCustomConfig(this.cfg);
  }

  public void deletePortal(String name, CommandSender sender) {
    if (this.cfg.contains(name.toLowerCase())) {
      sender.sendMessage(Util.getPrefix() + " \u00a77Das Portal \u00a76" + this.getPortalName(name)
          + " \u00a77 wurde gel\u00f6scht.");
      this.cfg.set(name.toLowerCase(), (Object) null);
    } else {
      sender.sendMessage(Util.getPrefix() + " \u00a77Dieses Portal existiert nicht.");
    }
  }

  public void portalWarp(String target, Player p) {
    if ((target = target.toLowerCase()).equalsIgnoreCase("lastlogout")) {
      YamlManager player = new YamlManager("Spieler/" + p.getUniqueId());
      FileConfiguration playerCfg = player.getCustomConfig();
      if (playerCfg.contains("lastlogout")) {
        Location out = PlayerStats.getLogoutLocation(p);
        p.teleport(out);
        p.sendMessage(
            Util.getPrefix() + " \u00a77Du wurdest zu deinem letzten Logout-Punkt teleportiert.");
      } else {
        p.sendMessage(Util.getPrefix()
            + " \u00a7cFehler: \u00a77Du hast dich auf diesem Server noch nie ausgeloggt.");
      }
    } else if (this.cfg.contains(target)) {
      World w = Bukkit.getWorld((String) this.cfg.getString(target + ".world"));
      int x = this.cfg.getInt(target + ".outX");
      int y = this.cfg.getInt(target + ".outY");
      int z = this.cfg.getInt(target + ".outZ");
      double yaw = this.cfg.getDouble(target + ".outYaw");
      Location out = new Location(w, (double) x, (double) y, (double) z, (float) yaw, 0.0f);
      p.setGameMode(Util.getGameMode(this.getPortalGamemode(target)));
      p.teleport(out);
      p.sendMessage(Util.getPrefix() + " \u00a77Du wurdest zum Portal \u00a76"
          + this.getPortalName(target) + " \u00a77teleportiert.");
    } else {
      p.sendMessage(Util.getPrefix() + " \u00a7cFehler: \u00a77Dieses Zielportal existiert nicht.");
    }
  }

  public ArrayList<ConfigurationSection> getPortalList() {
    ArrayList<ConfigurationSection> portals = new ArrayList<ConfigurationSection>();
    for (String sectionName : this.cfg.getKeys(false)) {
      portals.add(this.cfg.getConfigurationSection(sectionName));
    }
    return portals;
  }

  public String getPortalName(String portal) {
    return ChatColor.translateAlternateColorCodes((char) '&',
        (String) this.cfg.getString(portal.toLowerCase() + ".name"));
  }

  public String getPortalTarget(String portal) {
    return this.cfg.getString(portal.toLowerCase() + ".target");
  }

  public String getPortalWorld(String portal) {
    return this.cfg.getString(portal.toLowerCase() + ".world");
  }

  public String getPortalGamemode(String portal) {
    return this.cfg.getString(portal.toLowerCase() + ".gamemode");
  }
}
