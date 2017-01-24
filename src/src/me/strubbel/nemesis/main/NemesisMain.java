package me.strubbel.nemesis.main;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.pro_crafting.commandframework.CommandFramework;
import me.strubbel.nemesis.API.Util;
import me.strubbel.nemesis.Events.PlayerStatsEvents;
import me.strubbel.nemesis.Events.PortalEvent;
import me.strubbel.nemesis.Events.StandartEvents;
import me.strubbel.nemesis.commands.FlyCommand;
import me.strubbel.nemesis.commands.GamemodeCommand;
import me.strubbel.nemesis.commands.HealCommands;
import me.strubbel.nemesis.commands.HomeCommands;
import me.strubbel.nemesis.commands.LobbyCommands;
import me.strubbel.nemesis.commands.PortalCommands;
import me.strubbel.nemesis.commands.SchematicCommands;
import me.strubbel.nemesis.commands.SchematicSearchCommand;
import me.strubbel.nemesis.commands.TeleportCommands;

public class NemesisMain extends JavaPlugin implements Listener {

  private static NemesisMain main;

  public PortalManager portalManager;
  public CommandFramework framework;

  public String prefix;

  @Override
  public void onEnable() {
    NemesisMain.main = this;

    getConfig().options().copyDefaults(true);
    saveDefaultConfig();

    this.portalManager = new PortalManager(this);
    this.prefix = this.getConfig().getString("prefix");

    this.setupEvents();
    this.setupCommands();

    this.getLogger().info("[Nemesis] Plugin erfolgreich geladen!");
  }

  public void setupEvents() {
    this.getServer().getPluginManager().registerEvents(new StandartEvents(this), this);
    this.getServer().getPluginManager().registerEvents(new PortalEvent(this), this);
    this.getServer().getPluginManager().registerEvents(new PlayerStatsEvents(this), this);
  }

  public void setupCommands() {
    this.framework = new CommandFramework(this);
    this.framework.registerCommands(new PortalCommands(this));
    this.framework.registerCommands(new LobbyCommands());
    this.framework.registerCommands(new FlyCommand());
    this.framework.registerCommands(new HealCommands());
    this.framework.registerCommands(new TeleportCommands());
    this.framework.registerCommands(new SchematicSearchCommand());
    this.framework.registerCommands(new GamemodeCommand());
    this.framework.registerCommands(new HomeCommands());
    this.framework.registerCommands(new SchematicCommands());
    this.framework.setInGameOnlyMessage(
        Util.getPrefix() + " §7Der Befehl muss von einem Spieler ausgeführt werden.");
  }

  public static NemesisMain getMain() {
    return main;
  }
}
