package com.leprofi.joinmsgrl;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener, CommandExecutor {


    public void onEnable() {
        ConfigManager();
        Bukkit.getConsoleSender().sendMessage("§8-----------------------------");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§cJoinMessage §6Reloaded §2v1.0");
        Bukkit.getConsoleSender().sendMessage("§9made by §6LeNinjaHD");
        Bukkit.getConsoleSender().sendMessage("§9The Join Message is: §6" + getConfig().getString("JoinMessage").replace("%playername%", "PLAYERNAME"));
        if(getConfig().getBoolean("SendPrivateMessage")) {
            Bukkit.getConsoleSender().sendMessage("§9Send private Message is: §6" + getConfig().getBoolean("SendPrivateMessage"));
            Bukkit.getConsoleSender().sendMessage("§9The Private Join Message is: §6" + getConfig().getString("JoinPrivate").replace("%playername%", "PLAYERNAME"));
        } else {
            Bukkit.getConsoleSender().sendMessage("§9Send private Message is: §6" + getConfig().getBoolean("SendPrivateMessage"));
        }
        Bukkit.getConsoleSender().sendMessage("§9The Quit Message is: §6" + getConfig().getString("QuitMessage").replace("%playername%", "PLAYERNAME"));
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§8-----------------------------");

        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {

    }

    public void ConfigManager() {
        FileConfiguration config = getConfig();
        config.addDefault("Prefix", "[JoinMSG-RL]");
        config.addDefault("JoinMessage", "%playername% joined the Server.");
        config.addDefault("SendPrivateMessage", true);
        config.addDefault("JoinPrivate", "Hello, %playername%! Welcome on YourServer.net");
        config.addDefault("QuitMessage", "%playername% left the server.");
        config.options().copyDefaults(true);
        saveConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(getConfig().getString("JoinMessage").replace("%playername%", player.getDisplayName()));
        if(getConfig().getBoolean("SendPrivateMessage")) {
            player.sendMessage(getConfig().getString("JoinPrivate").replace("%playername%", player.getDisplayName()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.setQuitMessage(getConfig().getString("QuitMessage").replace("%playername%", player.getDisplayName()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("setjoinmsg")) {
            if(args.length == 0) {
                sender.sendMessage(getConfig().getString("Prefix") + " §cError: args.length equals zero. args' minimum is 1");
            } else {
                getConfig().set("JoinMessage", args[0]);
                sender.sendMessage(getConfig().getString("Prefix") + " §6The Join Message is now: §5 " + getConfig().getString("JoinMessage"));
            }
        } else if(cmd.getName().equalsIgnoreCase("setquitmsg")) {
            if(args.length == 0) {
                sender.sendMessage(getConfig().getString("Prefix") + " §cError: args.length equals zero. args' minimum is 1");
            } else {
                getConfig().set("QuitMessage", args[0]);
                sender.sendMessage(getConfig().getString("Prefix") + " §6The Quit Message is now: §5 " + getConfig().getString("QuitMessage"));
            }
        }
        return true;
    }
}
