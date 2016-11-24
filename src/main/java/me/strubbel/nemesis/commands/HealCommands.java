package me.strubbel.nemesis.commands;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HealCommands {

    @Command(name = "heal", usage = "/heal [player]", permission="nemesis.heal", inGameOnly=true)
    public void heal(CommandArgs args) {
        Player p = args.getPlayer();
        switch(args.length()){
            case 0:
                if(Bukkit.getPlayerExact(args.getArgs(0))!=null) {
                    Player target = Bukkit.getPlayerExact(args.getArgs(0));

                    Util.heal(target);
                    Util.removePotionEffects(target);
                    target.sendMessage(Util.getPrefix() + " §7Du wurdest geheilt.");

                    p.sendMessage(Util.getPrefix() + " §7Du hast §6" + target.getDisplayName() + " §7geheilt.");
                } else p.sendMessage(Util.getPrefix() + " §7Dieser Spieler existiert nicht.");
                break;

            case 1:
                Util.heal(p);
                Util.removePotionEffects(p);
                p.sendMessage(Util.getPrefix() + " §7Du wurdest geheilt.");
                break;

            default:
                p.sendMessage(Util.getPrefix() + " §7/heal [player]");
                break;
        }
    }


    @Command(name = "feed", usage = "/feed [player]", permission="nemesis.heal", inGameOnly=true)
    public void feed(CommandArgs args) {
        Player p = args.getPlayer();
        switch(args.length()){
            case 0:
                if(Bukkit.getPlayerExact(args.getArgs(0))!=null) {
                    Player target = Bukkit.getPlayerExact(args.getArgs(0));

                    Util.feed(target);
                    target.sendMessage(Util.getPrefix() + " §7Dein Hunger wurde aufgefüllt.");

                    p.sendMessage(Util.getPrefix() + " §7Du hast §6" + target.getDisplayName() + " §7geheilt.");
                } else p.sendMessage(Util.getPrefix() + " §7Dieser Spieler existiert nicht.");
                break;

            case 1:
                Util.feed(p);
                p.sendMessage(Util.getPrefix() + " §7Deubt.");
                break;

            default:
                p.sendMessage(Util.getPrefix() + " §7/feed [player]");
                break;
        }
    }
}
