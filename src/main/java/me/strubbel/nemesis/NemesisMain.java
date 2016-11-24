package me.strubbel.nemesis;

import de.pro_crafting.commandframework.CommandFramework;
import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.Events.PlayerStatsEvents;
import me.strubbel.nemesis.Events.StandartEvents;
import me.strubbel.nemesis.commands.*;
import me.strubbel.nemesis.Events.PortalEvent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NemesisMain extends JavaPlugin implements Listener {

    private static NemesisMain main;

    public PortalManager portalManager;
    public CommandFramework framework;

    public String prefix;

    @Override
    public void onEnable(){
        this.main = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        this.portalManager = new PortalManager(this);
        this.prefix = this.getConfig().getString("prefix");

        this.setupEvents();
        this.setupCommands();

        this.getLogger().info("[Nemesis] Plugin erfolgreich geladen!");
    }

    public void setupEvents(){
        this.getServer().getPluginManager().registerEvents(new StandartEvents(this), this);
        this.getServer().getPluginManager().registerEvents(new PortalEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerStatsEvents(this), this);
    }

    public void setupCommands(){
        this.framework = new CommandFramework(this);
        this.framework.registerCommands(new PortalCommands(this));
        this.framework.registerCommands(new LobbyCommands());
        this.framework.registerCommands(new FlyCommand());
        this.framework.registerCommands(new HealCommands());
        this.framework.registerCommands(new TeleportCommands());
        this.framework.registerCommands(new SchematicSearchCommand());
        this.framework.registerCommands(new GamemodeCommand());
        this.framework.registerCommands(new HomeCommands());
        this.framework.setInGameOnlyMessage(Util.getPrefix() + " §7Der Befehl muss von einem Spieler ausgeführt werden.");
    }

    public static NemesisMain getMain(){
        return main;
    }
}