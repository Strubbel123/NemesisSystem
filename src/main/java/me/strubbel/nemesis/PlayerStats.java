package me.strubbel.nemesis;

import me.strubbel.nemesis.API.YamlManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerStats {

    public static void saveLogoutLocation(Player p){
        YamlManager yaml = new YamlManager("Spieler/" + p.getUniqueId());
        FileConfiguration cfg = yaml.getCustomConfig();

        cfg.set("lastlogout.world", p.getWorld().getName());
        cfg.set("lastlogout.outX", p.getLocation().getX());
        cfg.set("lastlogout.outY", p.getLocation().getY());
        cfg.set("lastlogout.outZ", p.getLocation().getZ());
        cfg.set("lastlogout.outYaw", p.getLocation().getYaw());

        yaml.saveCustomConfig(cfg);
    }
}
