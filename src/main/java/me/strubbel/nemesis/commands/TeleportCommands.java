package me.strubbel.nemesis.commands;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeleportCommands {

    @Command(name = "tphere", aliases = "s", usage = "/tphere <player>", permission="nemesis.tp", inGameOnly=true)
    public void tphere(CommandArgs args) {
        Player p = args.getPlayer();

        if(args.length() > 0){
            if(Bukkit.getPlayerExact(args.getArgs(0))!=null) {
                Player target = Bukkit.getPlayerExact(args.getArgs(0));
                target.teleport(p);
                target.sendMessage(Util.getPrefix() + " §7Du wurdest zu §6" + p.getDisplayName() + " §7teleportiert.");

                p.sendMessage(Util.getPrefix() + " §6" + target.getDisplayName() + " §7wurde zu dir teleportiert.");
            } else p.sendMessage(Util.getPrefix() + " §7Dieser Spieler existiert nicht.");
        } else p.sendMessage(Util.getPrefix() + " §7Gib einen Spieler an.");
    }


    @Command(name = "tpall", usage = "/tpall", permission="nemesis.tp", inGameOnly=true)
    public void tpall(CommandArgs args) {
        Player p = args.getPlayer();

        for(Player all : Bukkit.getOnlinePlayers()){
            all.teleport(p);
        }
        Bukkit.broadcastMessage(Util.getPrefix() + " §7Alle Spieler wurden zu §6" + p.getDisplayName() + " §7teleportiert.");
    }
}
