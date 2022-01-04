package me.lnadav.restack.api.Discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class Discord {

    public final DiscordRichPresence presence = new DiscordRichPresence();
    private final DiscordRPC rpc = DiscordRPC.INSTANCE;


    public void start() {

        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        String clientID = "927869703005823006";
        rpc.Discord_Initialize(clientID, handlers, true, "");
        presence.startTimestamp = System.currentTimeMillis() / 1000L;

        rpc.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    rpc.Discord_RunCallbacks();
                    rpc.Discord_UpdatePresence(presence);
                }
                catch (Exception ignored) {
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException ignored) {
                }
            }
        }, "Discord-RPC-Callback-Handler").start();


    }

    public void stop(){
        rpc.Discord_Shutdown();
    }
}
