package me.lnadav.restack.impl.features.client;

import me.lnadav.restack.api.Discord.Discord;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;

public class RPC extends AbstractFeature {

    private final Discord discord = new Discord();

    public RPC() {
        super("RPC", "Discord RPC", Category.CLIENT);
    }

    @Override
    public void onEnable(){
        super.onEnable();
        discord.start();
    }

    @Override
    public void onDisable(){
        super.onDisable();
        discord.stop();
    }
}
