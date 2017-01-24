package me.strubbel.nemesis.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.main.NemesisMain;

public class StandartEvents implements Listener {
  private NemesisMain main;

  public StandartEvents(NemesisMain m) {
    this.main = m;
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onSignChange(SignChangeEvent e) {
    if (e.getPlayer().hasPermission("nemesis.color")) {
      for (int i = 0; i < 3; ++i) {
        e.setLine(i, ChatColor.translateAlternateColorCodes((char) '&', (String) e.getLine(i)));
      }
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onWeatherChange(WeatherChangeEvent e) {
    if (!this.main.getConfig().getBoolean("weather") && e.toWeatherState()) {
      e.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onFoodLoose(FoodLevelChangeEvent e) {
    Player p = (Player) e.getEntity();
    if (!this.main.getConfig().getBoolean("looseHunger")) {
      Util.feed(p);
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    e.setJoinMessage("\u00a76" + p.getDisplayName() + " \u00a77ist dem Server beigetreten.");
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    e.setQuitMessage("\u00a76" + p.getDisplayName() + " \u00a77hat den Server verlassen.");
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerKick(PlayerKickEvent e) {
    Player p = e.getPlayer();
    e.setLeaveMessage("\u00a76" + p.getDisplayName()
        + " \u00a77wurde aus folgendem Grund gekickt: \n\u00a76" + e.getReason());
  }
}
