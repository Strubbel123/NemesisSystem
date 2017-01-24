package me.strubbel.nemesis.commands;

import de.pro_crafting.commandframework.Command;
import de.pro_crafting.commandframework.CommandArgs;
import me.strubbel.nemesis.API.Util;
import org.bukkit.command.CommandSender;

import java.io.File;

public class SchematicSearchCommand {
  @Command(name = "search", aliases = {"/schematic.search", "/schem search"},
      usage = "/search <name> [directory]", permission = "nemesis.search")
  public void delete(CommandArgs args) {
    CommandSender sender = args.getSender();

    if (!Util.getWorldEdit()) {
      sender
          .sendMessage(Util.getPrefix() + " §7Du benötigst WorldEdit um diese Funktion zu nutzen.");
      return;
    }

    String name = args.getArgs(0);
    String directory = "";
    if (args.length() > 1)
      directory = args.getArgs(1) + "/";

    File Pfad = new File(Util.getWorldEditFile(), directory);
    File[] list = Pfad.listFiles();

    sender.sendMessage(Util.getPrefix() + " §7Sucherrgebnisse:");

    if (list != null) {
      int i = 1;
      for (File file : list) {
        if (file.getName().toLowerCase().contains(name.toLowerCase())) {
          sender.sendMessage("§8" + i + ". §7" + file.getName());
          i++;
        }
      }
    }
  }
}
