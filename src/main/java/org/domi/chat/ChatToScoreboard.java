package org.domi.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.domi.MCChzzk;
import org.domi.commands.SetScoreboard;
import xyz.r2turntrue.chzzk4j.chat.ChatEventListener;
import xyz.r2turntrue.chzzk4j.chat.ChatMessage;
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat;

import java.util.Random;

public class ChatToScoreboard implements ChatEventListener {
    private static final String[] COLORS = {"§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a", "§b", "§c", "§d", "§e", "§f"};
    private static MCChzzk plugin = MCChzzk.getInstance();
    private static ChatToScoreboard ctsb;
    public final Scoreboard board;
    private final Objective objective;
    ChzzkChat chat = plugin.chat;
    private final ScoreboardManager manager;
    private int messageId = 0;

    public ChatToScoreboard(MCChzzk plugin) {
        ChatToScoreboard.plugin = plugin;
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        objective = board.registerNewObjective("chat", "dummy", "chat");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public static ChatToScoreboard getInstance() {
        return ctsb;
    }

    @Override
    public void onConnect() {
        Bukkit.broadcastMessage("채팅 연결됨! 최근 채팅 요청중...");
        chat.requestRecentChat(50);
    }

    @Override
    public void onChat(ChatMessage msg) {
        Random rand = new Random();
        String color = COLORS[rand.nextInt(COLORS.length)];
        String name = msg.getProfile().getNickname();
        String chatting = msg.getContent();
        String message = chat + name + " &f: " + chatting;
        if (message.length() > 24) {
            String firstPart = message.substring(0, 22);
            String secondPart = message.substring(22);
            addMessageToScoreboard(firstPart);
            addMessageToScoreboard(secondPart);
        } else {
            addMessageToScoreboard(message);
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (SetScoreboard.getChatVisibility(onlinePlayer)) {
                onlinePlayer.setScoreboard(board);
            }
        }
    }

    private void addMessageToScoreboard(String message) {
        Score score = objective.getScore(message);
        score.setScore(messageId++);
    }

    public Scoreboard getScoreboard() {
        return this.board;
    }

}
