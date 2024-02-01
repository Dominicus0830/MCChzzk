package org.domi;

import org.bukkit.plugin.java.JavaPlugin;
import org.domi.chat.ChatToScoreboard;
import org.domi.commands.SetScoreboard;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.ChzzkBuilder;
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannel;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelRules;

import java.io.IOException;

public class MCChzzk extends JavaPlugin {
    private static MCChzzk plugin;
    public Chzzk chzzk = new ChzzkBuilder().build();
    public ChzzkChat chat = chzzk.chat();

    public static MCChzzk getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        chzzk = new ChzzkBuilder().build();
        new SetScoreboard();
        String CHANNEL_ID = getConfig().getString("channelId");
        ChzzkChannel channel;
        try {
            channel = chzzk.getChannel(CHANNEL_ID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("채널 " +'"'+ channel.getChannelName() +'"'+ " 로 연결중...");
        chat.addListener(new ChatToScoreboard(this));
        try {
            chat.connectFromChannelId(CHANNEL_ID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}