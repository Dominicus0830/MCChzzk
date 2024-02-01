package org.domi.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.domi.MCChzzk;
import org.domi.chat.ChatToScoreboard;

import java.util.HashMap;

public class SetScoreboard implements CommandExecutor {
    private static final HashMap<Player, Boolean> chatVisibility = new HashMap<>();
    private static final ChatToScoreboard ctcb = ChatToScoreboard.getInstance();
    private static MCChzzk plugin = MCChzzk.getInstance();

    public static boolean getChatVisibility(Player player) {
        return chatVisibility.getOrDefault(player, false);
    }

    public SetScoreboard() {
        plugin.getCommand("채팅").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            chatVisibility.put(player, !chatVisibility.getOrDefault(player, false));
            if (chatVisibility.get(player)) {
                player.setScoreboard(ctcb.getScoreboard());
            } else {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            return true;
        } else {
            sender.sendMessage("This command can only be used by a player.");
            return false;
        }
    }
}
