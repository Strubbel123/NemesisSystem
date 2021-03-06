package me.strubbel.nemesis.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.strubbel.nemesis.API.YamlManager;
import me.strubbel.nemesis.main.NemesisMain;
import me.strubbel.nemesis.main.PlayerStats;

public class PlayerStatsEvents implements Listener {
  @SuppressWarnings("unused")
  private NemesisMain main;

  public PlayerStatsEvents(NemesisMain m) {
    this.main = m;
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();

    YamlManager yaml = new YamlManager("Spieler/" + p.getUniqueId());
    FileConfiguration cfg = yaml.getCustomConfig();

    cfg.set("lastname", p.getName());
    cfg.set("ipAdress", p.getAddress().getHostName());

    yaml.saveCustomConfig(cfg);
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerQuit(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    YamlManager yaml = new YamlManager("Lobby");
    FileConfiguration cfg = yaml.getCustomConfig();
    if (!cfg.getString("Spawn.world").equalsIgnoreCase(p.getLocation().getWorld().getName()))
      PlayerStats.saveLogoutLocation(p);
  }
}
