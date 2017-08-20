package com.imyvm.onlinemarket;

import com.imyvm.onlinemarket.market.MarketCommands;
import cat.nyaa.nyaacore.CommandReceiver;
import cat.nyaa.nyaacore.LanguageRepository;
import cat.nyaa.nyaacore.Message;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler extends CommandReceiver<Main> {

    private final Main plugin;
    @SubCommand("market")
    public MarketCommands marketCommands;

    public CommandHandler(Main plugin, LanguageRepository i18n) {
        super(plugin, i18n);
        this.plugin = plugin;
    }

    public String getHelpPrefix() {
        return "";
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            String cmd = args[0].toLowerCase();
            String subCommand = "";
            if (cmd.equals("view") || cmd.equals("sell")) {
                subCommand = "market";
            }
            if (subCommand.length() > 0) {
                String[] tmp = new String[args.length + 1];
                tmp[0] = subCommand;
                for (int i = 0; i < args.length; i++) {
                    tmp[i + 1] = args[i];
                }
                return super.onCommand(sender, command, label, tmp);
            }
        }
        return super.onCommand(sender, command, label, args);
    }

    @SubCommand(value = "save", permission = "heh.admin")
    public void forceSave(CommandSender sender, Arguments args) {
        plugin.config.save();
        msg(sender, "admin.info.save_done");
    }


    @SubCommand(value = "debug", permission = "heh.debug")
    public void debug(CommandSender sender, Arguments args) {
        String sub = args.next();
        if ("showitem".equals(sub) && sender instanceof Player) {
            Player player = (Player) sender;
            new Message("Player has item: ").append(player.getInventory().getItemInMainHand()).send(player);
        } else if ("dbi".equals(sub) && sender instanceof Player) {
            plugin.database.addTemporaryStorage((Player) sender, new ItemStack(Material.DIAMOND, 64));
        } else if ("ymllist".equals(sub)) {
            List<ItemStack> t = new ArrayList<ItemStack>() {{
                add(new ItemStack(Material.DIAMOND));
                add(new ItemStack(Material.ACACIA_DOOR));
            }};
            YamlConfiguration yml = new YamlConfiguration();
            yml.addDefault("abc", t);
            yml.set("abc", t);
            sender.sendMessage("\n" + yml.saveToString());
        }
    }

    @SubCommand(value = "force-load", permission = "heh.admin")
    public void forceLoad(CommandSender sender, Arguments args) {
        plugin.reload();
        msg(sender, "admin.info.load_done");
    }

    @SubCommand(value = "version")
    public void version(CommandSender sender, Arguments args) {
        String ver = plugin.getDescription().getVersion();
        List<String> authors = plugin.getDescription().getAuthors();
        String au = authors.get(0);
        for (int i = 1; i < authors.size(); i++) {
            au += " " + authors.get(i);
        }
        msg(sender, "manual.license", ver, au);
    }


    @SubCommand(value = "retrieve", permission = "heh.retrieve")
    public void userRetrieve(CommandSender sender, Arguments args) {
        Player p = asPlayer(sender);
        if (args.length() == 1) {
            msg(sender, "user.retrieve.need_confirm");
            return;
        }
        List<ItemStack> items = plugin.database.getTemporaryStorage(p);
        if (items.size() == 0) {
            msg(sender, "user.retrieve.no_item");
            return;
        }
        for (ItemStack s : items) {
            p.getWorld().dropItem(p.getEyeLocation(), s);
        }
        plugin.database.clearTemporaryStorage(p);
    }
}
