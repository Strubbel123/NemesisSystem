package me.strubbel.nemesis.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.API.YamlManager;
import me.strubbel.nemesis.main.NemesisMain;
import mkremins.fanciful.FancyMessage;

public class LobbyEvents implements Listener {
  private NemesisMain main;
  private YamlManager yaml;
  private FileConfiguration cfg;

  public LobbyEvents(NemesisMain m) {
    this.main = m;
    this.yaml = new YamlManager("Lobby");
    this.cfg = this.yaml.getCustomConfig();
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (this.main.getConfig().getBoolean("lobbyspawn")) {
      World w = Bukkit.getWorld((String) this.cfg.getString("Spawn.world"));
      int x = this.cfg.getInt("Spawn.outX");
      int y = this.cfg.getInt("Spawn.outY");
      int z = this.cfg.getInt("Spawn.outZ");
      double yaw = this.cfg.getDouble("Spawn.outYaw");
      Location loc = new Location(w, (double) x, (double) y, (double) z, (float) yaw, 0.0f);
      p.teleport(loc);
    }
    p.sendMessage("\n\u00a78I---------------------------------------------------I");
    p.sendMessage(Util.getPrefix() + ChatColor.translateAlternateColorCodes((char) '&',
        (String) this.main.getConfig().getString("motd")));
    p.sendMessage("\u00a78I---------------------------------------------------I");
    if (p.hasPermission("nemesis.lastlogout")) {
      new FancyMessage(
          "Wenn du zur\u00fcck zu deinem letzten Logout-Punkt gelangen m\u00f6chtest, ")
              .color(ChatColor.GRAY).then("klick Hier").color(ChatColor.GOLD).style(ChatColor.BOLD)
              .command("/lastlogout").then(".").color(ChatColor.GRAY).send(p);
      p.sendMessage("\u00a78I---------------------------------------------------I");
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onItemDrop(PlayerDropItemEvent e) {
    Player p = e.getPlayer();
    if (Util.isPlayerInLobby(p)) {
      e.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onDamage(EntityDamageEvent e) {
    Player p;
    if (e.getEntity() instanceof Player && Util.isPlayerInLobby(p = (Player) e.getEntity())) {
      e.setCancelled(true);
    }
  }
}
