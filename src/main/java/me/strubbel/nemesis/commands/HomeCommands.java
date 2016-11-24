package me.strubbel.nemesis.commands;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.API.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HomeCommands {

    @Command(name = "sethome", usage = "/sethome <name>", permission="nemesis.home", inGameOnly=true)
    public void setHome(CommandArgs args) {
        Player p = args.getPlayer();

        if(args.length() > 0){
            String name = args.getArgs(0);

            YamlManager yaml = new YamlManager("Spieler/" + p.getUniqueId());
            FileConfiguration cfg = yaml.getCustomConfig();

            cfg.set("homes." + name.toLowerCase() + ".name", name);
            cfg.set("homes." + name.toLowerCase() + ".world", p.getLocation().getWorld().getName());
            cfg.set("homes." + name.toLowerCase() + ".x", p.getLocation().getX());
            cfg.set("homes." + name.toLowerCase() + ".y", p.getLocation().getY());
            cfg.set("homes." + name.toLowerCase() + ".z", p.getLocation().getZ());
            yaml.saveCustomConfig(cfg);

            p.sendMessage(Util.getPrefix() + " §7Dein Home wurde gespeichert.");
        } else p.sendMessage(Util.getPrefix() + " §7Gib einen Namen an.");
    }


    @Command(name = "delhome", usage = "/delhome <name>", permission="nemesis.home", inGameOnly=true)
    public void delHome(CommandArgs args) {
        Player p = args.getPlayer();

        if(args.length() > 0){
            String name = args.getArgs(0);

            YamlManager yaml = new YamlManager("Spieler/" + p.getUniqueId());
            FileConfiguration cfg = yaml.getCustomConfig();

            cfg.set("homes." + name.toLowerCase(), null);
            yaml.saveCustomConfig(cfg);

            p.sendMessage(Util.getPrefix() + " §7Dein Home wurde gelöscht.");
        } else p.sendMessage(Util.getPrefix() + " §7Gib einen Namen an.");
    }


    @Command(name = "home", usage = "/home <name>", permission="nemesis.home", inGameOnly=true)
    public void home(CommandArgs args) {
        Player p = args.getPlayer();

        if(args.length() > 0){
            String name = args.getArgs(0);

            YamlManager yaml = new YamlManager("Spieler/" + p.getUniqueId());
            FileConfiguration cfg = yaml.getCustomConfig();
            if(cfg.contains("homes." + name.toLowerCase())){
                World w = Bukkit.getServer().getWorld(cfg.getString("homes." + name.toLowerCase() + ".world"));
                int x = cfg.getInt("homes." + name.toLowerCase() + ".x");
                int y = cfg.getInt("homes." + name.toLowerCase() + ".y");
                int z = cfg.getInt("homes." + name.toLowerCase() + ".z");

                p.teleport(new Location(w, x, y, z));
                p.sendMessage(Util.getPrefix() + " §7Du wurdest zum Home §6" + cfg.get("homes." + name.toLowerCase() + ".name") + " §7teleportiert.");
            } else p.sendMessage(Util.getPrefix() + " §7Dieses Home existiert nicht.");
        } else p.sendMessage(Util.getPrefix() + " §7Gib einen Namen an.");
    }


    @Command(name = "homelist", usage = "/homelist", permission="nemesis.home", inGameOnly=true)
    public void homeList(CommandArgs args) {
        Player p = args.getPlayer();
        YamlManager yaml = new YamlManager("Spieler/" + p.getUniqueId());
        FileConfiguration cfg = yaml.getCustomConfig();

        p.sendMessage("\n" + Util.getPrefix() + " §7Liste deiner Homes:");
        int i = 1;
        ConfigurationSection section;

        for(String s : cfg.getConfigurationSection("homes").getKeys(false)){
            section = cfg.getConfigurationSection("homes." + s.toLowerCase());
            p.sendMessage("§8" + i + ". §7" + section.getName());
            i++;
        }
    }
}
