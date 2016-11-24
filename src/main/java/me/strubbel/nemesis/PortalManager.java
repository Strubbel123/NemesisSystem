package me.strubbel.nemesis;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import me.strubbel.nemesis.NemesisMain;
import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.API.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PortalManager {

    private WorldEditPlugin worldEdit;
    private YamlManager yaml;
    private FileConfiguration cfg;
    private NemesisMain main;

    public PortalManager(NemesisMain m){
        this.main = m;
        yaml = new YamlManager("Portals");
        cfg = yaml.getCustomConfig();

        if(Util.getWorldEdit() == true) worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    }


    public void newPortal(String name, String target, Player p){
        if(worldEdit == null){
            p.sendMessage(Util.getPrefix() + " §7Du benötigst WorldEdit um diese Funktion zu nutzen.");
            return;
        }

        cfg = yaml.getCustomConfig();
        Selection selection = worldEdit.getSelection(p);

        if (selection != null) {
            if(!cfg.contains(name.toLowerCase()) &&  !target.equalsIgnoreCase("lastlogout")){
                Location min = selection.getMinimumPoint();
                Location max = selection.getMaximumPoint();

                cfg.set(name.toLowerCase() + ".name", name);
                p.sendMessage("§7Das Portal " + name + " wurde erfolgreich erstellt");
                name = name.toLowerCase();
                cfg.set(name + ".target", target);
                cfg.set(name + ".world", p.getLocation().getWorld().getName());
                cfg.set(name + ".maxX", max.getX());
                cfg.set(name + ".maxY", max.getY());
                cfg.set(name + ".maxZ", max.getZ());
                cfg.set(name + ".minX", min.getX());
                cfg.set(name + ".minY", min.getY());
                cfg.set(name + ".minZ", min.getZ());
                cfg.set(name + ".outX", p.getLocation().getX());
                cfg.set(name + ".outY", p.getLocation().getY());
                cfg.set(name + ".outZ", p.getLocation().getZ());
                cfg.set(name + ".outYaw", p.getLocation().getYaw());
                yaml.saveCustomConfig(cfg);
            } else {
                p.sendMessage(Util.getPrefix() + " §7Es existiert bereits ein Portal mit diesem Namen.");
            }
        } else {
            p.sendMessage(Util.getPrefix() + " §7Bitte wähle einen Bereich aus.");
            return;
        }
        yaml.saveCustomConfig(cfg);
    }


    public void deletePortal(String name, CommandSender sender){
        if(cfg.contains(name.toLowerCase())) {
            cfg.set(name.toLowerCase(), null);
            sender.sendMessage(Util.getPrefix() + " §7Das Portal §6" + name + " §7 wurde gelöscht.");
        } else {
            sender.sendMessage(Util.getPrefix() + " §7Dieses Portal existiert nicht.");
        }
    }


    public void portalWarp(String target, Player p){
        target = target.toLowerCase();
        if (target.equalsIgnoreCase("lastlogout")){
            YamlManager player = new YamlManager("Spieler/" + p.getUniqueId());
            FileConfiguration playerCfg = player.getCustomConfig();

            World w = Bukkit.getWorld(playerCfg.getString("lastlogout.world"));
            int x = playerCfg.getInt("lastlogout.outX");
            int y = playerCfg.getInt("lastlogout.outY");
            int z = playerCfg.getInt("lastlogout.outZ");
            double yaw = playerCfg.getDouble("lastlogout.outYaw");

            Location out = new Location(w, x, y, z, (float)yaw, (float)0.0);
            p.teleport(out);
            p.sendMessage(Util.getPrefix() + " §7Du wurdest zu deinem letzten Logout-Punkt teleportiert.");

        } else if(cfg.contains(target)){
            World w = Bukkit.getWorld(cfg.getString(target + ".world"));
            int x = cfg.getInt(target + ".outX");
            int y = cfg.getInt(target + ".outY");
            int z = cfg.getInt(target + ".outZ");
            double yaw = cfg.getDouble(target + ".outYaw");

            Location out = new Location(w, x, y, z, (float)yaw, (float)0.0);
            p.teleport(out);
            p.sendMessage(Util.getPrefix() + " §7Du wurdest zum Portal §6" + cfg.getString(target + ".name") + " §7teleportiert.");

        } else {
            p.sendMessage(Util.getPrefix() + " §7Dieses Zielportal existiert nicht.");
        }
    }


    public ArrayList<ConfigurationSection> getPortalList(){
        ArrayList<ConfigurationSection> portals = new ArrayList<ConfigurationSection>();
        for(String sectionName : cfg.getKeys(false)){
            portals.add(cfg.getConfigurationSection(sectionName));
        }
        return portals;
    }

    public String getPortalName(String portal){
         return cfg.getString(portal.toLowerCase() + ".name");
    }

    public String getPortalTarget(String portal){
        return cfg.getString(portal.toLowerCase() + ".target");
    }

    public String getPortalWorld(String portal){
        return cfg.getString(portal.toLowerCase() + ".world");
    }
}