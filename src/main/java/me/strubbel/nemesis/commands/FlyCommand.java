package me.strubbel.nemesis.commands;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by TOBIAS on 23.11.2016.
 */
public class FlyCommand {

  @Command(name = "fly", usage = "/fly [player]", permission = "nemesis.fly", inGameOnly = true)
  public void enableFly(CommandArgs args) {

    Player p = args.getPlayer();
    if (args.length() > 0) {
      if (Bukkit.getPlayerExact(args.getArgs(0)) != null) {
        Player target = Bukkit.getPlayerExact(args.getArgs(0));

        if (!target.getAllowFlight()) {
          Util.enableFly(target);
          target.sendMessage(Util.getPrefix() + " §7Du kannst nun fliegen.");
          p.sendMessage(
              Util.getPrefix() + " §6" + target.getDisplayName() + " §7kann nun fliegen.");

        } else {
          Util.disableFly(target);
          target.sendMessage(Util.getPrefix() + " §7Du kannst nun nicht mehr fliegen.");
          p.sendMessage(Util.getPrefix() + " §6" + target.getDisplayName()
              + " §7kann nun nicht mehr fliegen.");
        }
      } else
        p.sendMessage(Util.getPrefix() + " §7Dieser Spieler existiert nicht.");


    } else {
      if (!p.getAllowFlight()) {
        Util.enableFly(p);
        p.sendMessage(Util.getPrefix() + " §7Du kannst nun fliegen.");

      } else {
        Util.disableFly(p);
        p.sendMessage(Util.getPrefix() + " §7Du kannst nun nicht mehr fliegen.");
      }
    }
  }
}
