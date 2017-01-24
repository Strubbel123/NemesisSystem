package me.strubbel.nemesis.Events;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.strubbel.nemesis.API.Util;

public class CommandSignEvents implements Listener {

  @EventHandler(priority = EventPriority.NORMAL)
  public void onSignChange(SignChangeEvent e) {
    Player p = e.getPlayer();
    if (e.getPlayer().hasPermission("nemesis.commandsigns.create")
        && e.getLine(0).contains("[CommandSign]")) {
      String line = e.getLine(1) + " " + e.getLine(2) + " " + e.getLine(3);
      if (Bukkit.getServer().getHelpMap().getHelpTopic(line.split(" ")[0]) != null) {
        p.sendMessage(Util.getPrefix() + " \u00a77CommandSign erfolgreich erstellt.");
      } else {
        p.sendMessage(Util.getPrefix() + " \u00a7cFehler: \u00a77Command wurde nicht erkannt.");
      }
    }
  }

  @EventHandler(priority = EventPriority.NORMAL)
  public void onSignInteract(PlayerInteractEvent e) {
    if (e.getAction() == Action.RIGHT_CLICK_BLOCK
        && e.getClickedBlock().getState() instanceof Sign) {
      Sign s = (Sign) e.getClickedBlock().getState();
      String line = s.getLine(1) + " " + s.getLine(2) + " " + s.getLine(3);
      Player p = e.getPlayer();
      if (s.getLine(0).contains("[CommandSign]")) {
        if (p.hasPermission("nemesis.commandsigns.use")) {
          if (Bukkit.getServer().getHelpMap().getHelpTopic(line.split(" ")[0]) != null) {
            Bukkit.getServer().dispatchCommand((CommandSender) p, line.substring(1, line.length()));
          } else {
            p.sendMessage(
                Util.getPrefix() + " \u00a7cFehler: \u00a77Dieses CommandSign funktioniert nicht.");
          }
        } else {
          p.sendMessage(Util.getPrefix() + " \u00a77Du darfst keine CommandSigns verwenden.");
        }
      }
    }
  }
}
