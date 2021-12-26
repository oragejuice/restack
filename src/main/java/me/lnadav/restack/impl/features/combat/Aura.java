package me.lnadav.restack.impl.features.combat;

import me.lnadav.restack.api.event.events.PlayerUpdateEvent;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.rotations.Rotation;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;
import java.util.Optional;

import static me.lnadav.restack.Restack.rotationManager;

public class Aura extends AbstractFeature {

    public Aura() {
        super("Aura", "Attacks Entities", Category.COMBAT);
    }

    Comparator<Entity> compByDistance = Comparator.comparingDouble(e -> mc.player.getDistance(e));


    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        Optional<Entity> closet = mc.world.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityOtherPlayerMP).min(compByDistance);
        if (closet.isPresent()) {
            EntityLivingBase target = (EntityLivingBase) closet.get();
            if (mc.player.getCooledAttackStrength(0F) == 1F
                    && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword
                    && target.hurtResistantTime < (float) target.maxHurtResistantTime / 2.0F) {
                rotationManager.addToQueue(new Rotation(target.getPositionVector(), 0, false, 360));
                mc.playerController.attackEntity(mc.player, target);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }

}
