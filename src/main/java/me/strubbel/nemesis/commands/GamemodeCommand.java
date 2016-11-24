package me.strubbel.nemesis.commands;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCommand {
    @Command(name = "gamemode", aliases = "gm", usage = "/gamemode [player]", permission="nemesis.gamemode", inGameOnly=true)
    public void enableFly(CommandArgs args) {
        Player p = args.getPlayer();
        switch(args.length()){
            case 0:
                switch(p.getGameMode()){

                    case SURVIVAL:
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(Util.getPrefix() + " §7Dein Spielmodus wurde auf §6Kreativ §7gesetzt.");
                        break;
                    case ADVENTURE:
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(Util.getPrefix() + " §7Dein Spielmodus wurde auf §6Kreativ §7gesetzt.");
                        break;
                    case CREATIVE:
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(Util.getPrefix() + " §7Dein Spielmodus wurde auf §6Überleben §7gesetzt.");
                        break;
                    case SPECTATOR:
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(Util.getPrefix() + " §7Dein Spielmodus wurde auf §6Kreativ §7gesetzt.");
                        break;
                }
                break;


            case 1:
                if(getGameMode(p, args.getArgs(0)) != null) {
                    p.setGameMode(getGameMode(p, args.getArgs(0)));
                    p.sendMessage(Util.getPrefix() + " §7Dein Spielmodus wurde geändert.");
                }
                break;


            case 2:
                if(Bukkit.getPlayerExact(args.getArgs(1))!=null) {
                    Player target = Bukkit.getPlayerExact(args.getArgs(1));
                    if(getGameMode(p, args.getArgs(0)) != null) {
                        target.setGameMode(getGameMode(target, args.getArgs(0)));
                        target.sendMessage(Util.getPrefix() + " §7Dein Spielmodus wurde von §6" + p.getDisplayName() + " §7geändert.");
                        p.sendMessage(Util.getPrefix() + " §7Der Spielmodus von §6" + target.getDisplayName() + " §7wurde geändert.");
                    }
                } else p.sendMessage(Util.getPrefix() + " §7Dieser Spieler existiert nicht.");
                break;


            default:
                p.sendMessage(Util.getPrefix() + " §7/gamemode [player]");
                break;
        }
    }


    private GameMode getGameMode(Player p, String mode) {
        GameMode gameMode = null;
        try {
            int gamemodeInt = Integer.parseInt(mode);
            switch (gamemodeInt) {
                case 0:
                    gameMode = GameMode.SURVIVAL;
                    break;
                case 1:
                    gameMode = GameMode.CREATIVE;
                    break;
                case 2:
                    gameMode = GameMode.ADVENTURE;
                    break;
                case 3:
                    gameMode = GameMode.SPECTATOR;
                    break;
                default:
            }
            if (gameMode != null) return gameMode;

        } catch (Exception ex) {
            try {
                gameMode = GameMode.valueOf(mode.toUpperCase());
                return gameMode;
            } catch (Exception ex2) {
                p.sendMessage(Util.getPrefix() + " §7Gib einen Spielmodus an.");
            }
        }
        return null;
    }
}