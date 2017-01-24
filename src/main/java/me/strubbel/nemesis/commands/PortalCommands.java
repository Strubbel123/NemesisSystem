package me.strubbel.nemesis.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.main.NemesisMain;
import me.strubbel.nemesis.main.PlayerStats;
import me.strubbel.nemesis.main.PortalManager;
import mkremins.fanciful.FancyMessage;

public class PortalCommands {
  NemesisMain main;
  PortalManager portalManager;

  public PortalCommands(NemesisMain m) {
    this.main = m;
    this.portalManager = m.portalManager;
  }

  @Command(name = "lastlogout", usage = "/lastlogout", permission = "nemesis.lastlogout",
      inGameOnly = true, description = "Teleportiert dich zu deinem letzten Logout-Punkt.")
  public void lastLogout(CommandArgs args) {
    Player p = (Player) args.getSender();
    this.portalManager.portalWarp("lastlogout", p);
  }

  @Command(name = "portal", usage = "/portal", permission = "nemesis.portal.warp",
      description = "Listet aller /portal Befehle.")
  public void portal(CommandArgs args) {
    CommandSender sender = args.getSender();
    sender.sendMessage(Util.getPrefix() + " \u00a77Liste aller Portal-Befehle:");
    sender.sendMessage("\u00a77/portal warp <name>");
    if (sender.hasPermission("nemesis.portal.manage")) {
      sender.sendMessage("\u00a77/portal create <name> <target> <gamemode>");
      sender.sendMessage("\u00a77/portal delete <name>");
      sender.sendMessage("\u00a77/portal list");
      sender.sendMessage("\u00a77/portal info <name>");
    }
  }

  @Command(name = "portal.create", aliases = {"portal.add", "pcreate"},
      usage = "/portal create <name> <target> <gamemode>", permission = "nemesis.portal.warp",
      inGameOnly = true, description = "Teleportiert dich zu einem Portal.")
  public void create(CommandArgs args) {
    Player p = (Player) args.getSender();
    if (args.length() < 3) {
      p.sendMessage("\u00a77/portal create <name> <target> <gamemode>");
      return;
    }
    if (args.length() > 2) {
      String gamemode = args.getArgs(2);
      if (Util.getGameMode(gamemode) != null) {
        this.portalManager.createPortal(args.getArgs(0), args.getArgs(1), gamemode, p);
      } else {
        p.sendMessage(Util.getPrefix() + " \u00a77Gib einen Gamemode an!");
      }
    }
  }

  @Command(name = "portal.delete", aliases = {"portal.remove", "pdelete"},
      usage = "/portal delete <portal name>", permission = "nemesis.portal.manage",
      description = "L\u00f6scht ein Portal.")
  public void delete(CommandArgs args) {
    this.portalManager.deletePortal(args.getArgs(0), args.getSender());
  }

  @Command(name = "portal.warp", aliases = {"portal.tp", "pwarp"},
      usage = "/portal warp <portal name>", permission = "nemesis.portal.warp", inGameOnly = true,
      description = "Teleportiert dich zu einem Portal.")
  public void warp(CommandArgs args) {
    Player p = (Player) args.getSender();
    if (args.length() == 0) {
      p.sendMessage("\u00a77Bitte gib ein Portal an.");
      return;
    }
    if (args.length() > 1) {
      p = Bukkit.getPlayerExact((String) args.getArgs(1));
    }
    PlayerStats.saveLastLocation(p);
    this.portalManager.portalWarp(args.getArgs(0), p);
  }

  @Command(name = "portal.list", aliases = {"plist"}, usage = "/portal list",
      permission = "nemesis.portal.manage", description = "Gibt dir eine Liste aller Portale.")
  public void list(CommandArgs args) {
    CommandSender sender = args.getSender();
    sender.sendMessage("\n\u00a7l" + Util.getPrefix() + " \u00a78Liste aller Portale:");
    int i = 1;
    for (ConfigurationSection cfg : this.portalManager.getPortalList()) {
      new FancyMessage("" + i + ". ").color(ChatColor.DARK_GRAY).then(cfg.getString("name"))
          .color(ChatColor.GRAY).tooltip((Object) ChatColor.GOLD + "Klicken, um Info anzuzeigen.")
          .command("/portal info " + cfg.getString("name").toLowerCase()).send(sender);
      ++i;
    }
  }

  @Command(name = "portal.info", aliases = {"pinfo"}, usage = "/portal info <portal name>",
      permission = "nemesis.portal.manage",
      description = "Zeigt dir die Infos \u00fcber ein Portal an.")
  public void info(CommandArgs args) {
    CommandSender sender = args.getSender();
    if (args.length() < 1) {
      sender.sendMessage(Util.getPrefix() + " \u00a77Gib einen Portalnahmen an");
    }
    sender.sendMessage("\n\u00a7l" + Util.getPrefix() + " \u00a78Portal Infos:");
    sender.sendMessage(
        "\u00a78Name:\u00a77 " + this.portalManager.getPortalName(args.getArgs(0).toLowerCase()));
    sender.sendMessage(
        "\u00a78Ziel:\u00a77 " + this.portalManager.getPortalTarget(args.getArgs(0).toLowerCase()));
    sender.sendMessage(
        "\u00a78Welt:\u00a77 " + this.portalManager.getPortalWorld(args.getArgs(0).toLowerCase()));
    sender.sendMessage("\u00a78Spielmodus:\u00a77 "
        + this.portalManager.getPortalGamemode(args.getArgs(0).toLowerCase()));
  }
}
