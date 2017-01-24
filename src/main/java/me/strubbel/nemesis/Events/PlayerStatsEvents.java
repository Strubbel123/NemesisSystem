package me.strubbel.nemesis.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.main.PlayerStats;

public class PlayerStatsEvents implements Listener {

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerQuit(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    if (!Util.isPlayerInLobby(p)) {
      PlayerStats.saveLogoutLocation(p);
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerKick(PlayerKickEvent e) {
    Player p = e.getPlayer();
    if (!Util.isPlayerInLobby(p)) {
      PlayerStats.saveLogoutLocation(p);
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerDeath(PlayerDeathEvent e) {
    Player p = e.getEntity();
    if (!Util.isPlayerInLobby(p)) {
      PlayerStats.saveLastLocation(p);
    }
  }
}
