package me.strubbel.nemesis.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.API.YamlManager;
import me.strubbel.nemesis.main.PlayerStats;

public class LobbyCommands {

  @Command(name = "lobby.set", aliases = {"lobby.setspawn", "setlobby"}, usage = "/lobby set",
      permission = "nemesis.lobby.set", inGameOnly = true)
  public void setspawn(CommandArgs args) {
    Player p = args.getPlayer();
    YamlManager yaml = new YamlManager("Lobby");
    FileConfiguration cfg = yaml.getCustomConfig();

    cfg.set("Spawn.world", p.getWorld().getName());
    cfg.set("Spawn.outX", p.getLocation().getX());
    cfg.set("Spawn.outY", p.getLocation().getY());
    cfg.set("Spawn.outZ", p.getLocation().getZ());
    cfg.set("Spawn.outYaw", p.getLocation().getYaw());

    yaml.saveCustomConfig(cfg);
    p.sendMessage(Util.getPrefix() + " §7Lobby Spawnpunkt gesetzt.");
  }

  @Command(name = "lobby", usage = "/lobby", permission = "nemesis.lobby", inGameOnly = true)
  public void lobby(CommandArgs args) {
    Player p = args.getPlayer();
    YamlManager yaml = new YamlManager("Lobby");
    FileConfiguration cfg = yaml.getCustomConfig();

    if (!cfg.getString("Spawn.world").equalsIgnoreCase(p.getLocation().getWorld().getName()))
      PlayerStats.saveLogoutLocation(p);

    if (cfg.contains("Spawn")) {
      World w = Bukkit.getWorld(cfg.getString("Spawn.world"));
      int x = cfg.getInt("Spawn.outX");
      int y = cfg.getInt("Spawn.outY");
      int z = cfg.getInt("Spawn.outZ");
      double yaw = cfg.getDouble("Spawn.outYaw");
      Location loc = new Location(w, x, y, z, (float) yaw, (float) 0.0);

      p.teleport(loc);
      p.sendMessage(Util.getPrefix() + " §7Du wurdest zur Lobby teleportiert.");
    } else
      p.sendMessage(Util.getPrefix() + " §7Es wurde noch keine Lobby festgelegt.");;
  }
}
