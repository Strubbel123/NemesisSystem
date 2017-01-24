package me.strubbel.nemesis.commands;

import org.bukkit.entity.Player;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import net.md_5.bungee.api.ChatColor;

public class SchematicCommands {
  @Command(name = "/schematic.load", aliases = {"/schem.load"}, inGameOnly = true)
  public void schemLoad(CommandArgs args) {
    if (!args.getArgs(0).toLowerCase().startsWith("pr/")) {
      Player p = args.getPlayer();
      if ((!(args.getPlayer().hasPermission("nemesis.schematic.member")
          && args.getPlayer().hasPermission("nemesis.schematic.pr")))
          || (!args.getPlayer().isOp())) {
        if (args.length() > 0) {
          p.performCommand(args.getCommand().toString() + " pr/" + args.getArgs());
        }
      } else {
        p.sendMessage("Darfst du nicht.");
        return;
      }
    }
  }

  @Command(name = "/schematic.save", aliases = {"schem.save"}, inGameOnly = true)
  public void schemSave(CommandArgs args) {
    Player p = args.getPlayer();
    if (!args.getArgs(0).toLowerCase().startsWith("pr/")) {
      if ((!(args.getPlayer().hasPermission("nemesis.schematic.member")
          && args.getPlayer().hasPermission("nemesis.schematic.pr")))
          || (!args.getPlayer().isOp())) {
        if (args.length() > 0) {
          if ((!(args.getPlayer().hasPermission("nemesis.schematic.member")
              && args.getPlayer().hasPermission("nemesis.schematic.pr")))
              || (!args.getPlayer().isOp())) {
            if (args.length() > 0) {
              p.performCommand(args.getCommand().toString() + " pr/" + args.getArgs());
            }
          }
        }
      } else {
        p.sendMessage("Darfst du nicht.");
        return;
      }
    }
  }

  @Command(name = "/schematic.list", aliases = {"/schem.list"}, inGameOnly = true)
  public void schemList(CommandArgs args) {
    Player p = args.getPlayer();
    if (!args.getArgs(0).toLowerCase().startsWith("pr/")) {
      if ((!(args.getPlayer().hasPermission("nemesis.schematic.member")
          && args.getPlayer().hasPermission("nemesis.schematic.pr")))
          || (!args.getPlayer().isOp())) {
        if (args.length() > 0) {
          if ((!(args.getPlayer().hasPermission("nemesis.schematic.member")
              && args.getPlayer().hasPermission("nemesis.schematic.pr")))
              || (!args.getPlayer().isOp())) {
            if (args.length() > 0) {
              p.sendMessage(ChatColor.RED + "Funktion bald verfügbar!");
              return;
            }
          }
        }
      } else {
        p.sendMessage("Darfst du nicht.");
        return;
      }
    }
  }

  @Command(name = "/schematic.delete", aliases = {"schem.delete"}, inGameOnly = true)
  public void schemDelete(CommandArgs args) {
    Player p = args.getPlayer();
    if (!args.getArgs(0).toLowerCase().startsWith("pr/")) {
      if ((!(args.getPlayer().hasPermission("nemesis.schematic.member")
          && args.getPlayer().hasPermission("nemesis.schematic.pr")))
          || (!args.getPlayer().isOp())) {
        if (args.length() > 0) {
          if ((!(args.getPlayer().hasPermission("nemesis.schematic.member")
              && args.getPlayer().hasPermission("nemesis.schematic.pr")))
              || (!args.getPlayer().isOp())) {
            if (args.length() > 0) {
              p.performCommand(args.getCommand().toString() + " pr/" + args.getArgs());
              return;
            }
          }
        }
      } else {
        p.sendMessage("Darfst du nicht.");
        return;
      }
    }
  }
}
