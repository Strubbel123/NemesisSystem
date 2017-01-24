package me.strubbel.nemesis.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.main.NemesisMain;
import me.strubbel.nemesis.main.PortalManager;

public class PortalCommands {
  NemesisMain main;
  PortalManager portalManager;

  public PortalCommands(NemesisMain m) {
    this.main = m;
    portalManager = m.portalManager;
  }


  @Command(name = "portal.create", aliases = {"portal.add", "p.add", "p.create"},
      usage = "/portal create <portal name> <target name>", permission = "nemesis.portal.manage",
      inGameOnly = true)
  public void create(CommandArgs args) {
    Player p = (Player) args.getSender();
    portalManager.newPortal(args.getArgs(0), args.getArgs(1), p);
  }


  @Command(name = "portal.delete", aliases = {"portal.remove", "p.delete", "p.remove"},
      usage = "/portal delete <portal name>", permission = "nemesis.portal.manage")
  public void delete(CommandArgs args) {
    portalManager.deletePortal(args.getArgs(0), args.getSender());
  }


  @Command(name = "portal.warp", aliases = {"portal.tp", "p.warp", "p.tp"},
      usage = "/portal warp <target name>", permission = "nemesis.portal.warp", inGameOnly = true)
  public void warp(CommandArgs args) {
    Player p = (Player) args.getSender();
    if (args.length() == 0) {
      p.sendMessage("§7Bitte gib ein Portal an.");
      return;
    }
    if (args.length() > 1) {
      p = Bukkit.getPlayerExact(args.getArgs(1));
    }
    portalManager.portalWarp(args.getArgs(0), p);
  }


  @Command(name = "portal.list", aliases = "p.list", usage = "/portal list",
      permission = "nemesis.portal.manage")
  public void list(CommandArgs args) {
    CommandSender sender = args.getSender();
    sender.sendMessage("\n" + Util.getPrefix() + " §8Liste aller Portale:");
    int i = 1;
    for (ConfigurationSection cfg : portalManager.getPortalList()) {
      sender.sendMessage("§6" + i + ". §7" + cfg.getString("name"));
      i++;
    }
  }


  @Command(name = "portal.info", aliases = "p.info", usage = "/portal info <name>",
      permission = "nemesis.portal.manage")
  public void info(CommandArgs args) {
    CommandSender sender = args.getSender();
    sender.sendMessage("\n" + Util.getPrefix() + " §8Portal Infos:");
    sender.sendMessage("§8Name:§7 " + portalManager.getPortalName(args.getArgs(0)));
    sender.sendMessage("§8Ziel:§7 " + portalManager.getPortalTarget(args.getArgs(0)));
    sender.sendMessage("§8Welt:§7 " + portalManager.getPortalWorld(args.getArgs(0)));
  }
}
