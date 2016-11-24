package me.strubbel.nemesis.Events;

import me.strubbel.nemesis.API.YamlManager;
import me.strubbel.nemesis.NemesisMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class StandartEvents implements Listener {
    private NemesisMain main;

    public StandartEvents(NemesisMain m){
        this.main = m;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if(e.getPlayer().hasPermission("nemesis.color")){
            for(int i = 0; i < 3; i++) {
                System.out.println(e.getLine(i));
                e.setLine(i, ChatColor.translateAlternateColorCodes('&', e.getLine(i)));
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        if(!main.getConfig().getBoolean("weather")) {
            if(e.toWeatherState()) {
                e.setCancelled(true);
            }
        }
    }
}
