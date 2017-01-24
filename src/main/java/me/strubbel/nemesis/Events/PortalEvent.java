package me.strubbel.nemesis.Events;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.strubbel.nemesis.main.NemesisMain;
import me.strubbel.nemesis.main.PortalManager;

public class PortalEvent implements Listener {

  public NemesisMain main;
  public PortalManager manager;

  public PortalEvent(NemesisMain m) {
    this.main = m;
    this.manager = m.portalManager;
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerMove(PlayerMoveEvent e) {
    Player p = e.getPlayer();
    if (!p.hasPermission("nemesis.portal.use")) {
      p.sendMessage("Du darfst dieses Portal nicht nutzen!");
      return;
    }

    int movX = e.getFrom().getBlockX() - e.getTo().getBlockX();
    int movZ = e.getFrom().getBlockZ() - e.getTo().getBlockZ();

    if (Math.abs(movX) > 0 || Math.abs(movZ) > 0) {
      for (ConfigurationSection section : manager.getPortalList()) {
        if (p.getWorld().getName().equalsIgnoreCase(section.getString("world"))) {
          int maxX = section.getInt("maxX");
          int maxY = section.getInt("maxY");
          int maxZ = section.getInt("maxZ");

          int minX = section.getInt("minX");
          int minY = section.getInt("minY");
          int minZ = section.getInt("minZ");

          int px = p.getLocation().getBlockX();
          int py = p.getLocation().getBlockY();
          int pz = p.getLocation().getBlockZ();

          if (px <= maxX && px >= minX && py <= maxY && py >= minY && pz <= maxZ && pz >= minZ) {
            manager.portalWarp(section.getString("target"), p);
          }
        }
      }
    }
  }
}
