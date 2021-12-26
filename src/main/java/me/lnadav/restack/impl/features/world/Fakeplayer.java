package me.lnadav.restack.impl.features.world;

import com.mojang.authlib.GameProfile;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class Fakeplayer extends AbstractFeature {

    public Fakeplayer() {
        super("Fakeplayer", "Spawns a fake player", Category.WORLD);
    }

    private EntityOtherPlayerMP fakePlayer;

    @Override
    public void onEnable() {

        if(mc.player == null || mc.world == null) return;

        GameProfile profile = new GameProfile(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5"), "Notch");
        fakePlayer = new EntityOtherPlayerMP(mc.world, profile);
        fakePlayer.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ);
        mc.world.addEntityToWorld(-420, fakePlayer);
    }

    @Override
    public void onDisable() {
        if (mc.world.getLoadedEntityList().contains(fakePlayer)) {
            mc.world.removeEntity(fakePlayer);
        }
    }
}
