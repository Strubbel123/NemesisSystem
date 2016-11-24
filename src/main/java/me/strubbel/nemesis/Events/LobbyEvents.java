package me.strubbel.nemesis.Events;

import me.strubbel.nemesis.API.YamlManager;
import me.strubbel.nemesis.NemesisMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyEvents implements Listener {
    private NemesisMain main;
    private YamlManager yaml;
    private FileConfiguration cfg;
    private World world;

    public LobbyEvents(NemesisMain m){
        this.main = m;
        yaml = new YamlManager("Lobby");
        cfg = yaml.getCustomConfig();
        world = Bukkit.getServer().getWorld(cfg.getString("Spawn.world"));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (main.getConfig().getBoolean("lobbyspawn")) {
            World w = Bukkit.getWorld(cfg.getString("Spawn.world"));
            int x = cfg.getInt("Spawn.outX");
            int y = cfg.getInt("Spawn.outY");
            int z = cfg.getInt("Spawn.outZ");
            double yaw = cfg.getDouble("Spawn.outYaw");
            Location loc = new Location(w, x, y, z, (float) yaw, (float) 0.0);

            p.teleport(loc);
        }
    }
}
