package me.lnadav.restack.impl.features.combat;

import me.lnadav.restack.api.event.events.PlayerUpdateEvent;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.rotations.Rotation;
import me.lnadav.restack.api.setting.Register;
import me.lnadav.restack.api.setting.settingTypes.BooleanSetting;
import me.lnadav.restack.api.setting.settingTypes.FloatSetting;
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

    @Register BooleanSetting rotate = new BooleanSetting("rotate", true);
    @Register BooleanSetting strictRotate = new BooleanSetting("strictRotate", true);
    @Register FloatSetting range = new FloatSetting("range", 5.5F, 0,7);

    Comparator<Entity> compByDistance = Comparator.comparingDouble(e -> mc.player.getDistance(e));


    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        Optional<Entity> closet = mc.world.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityOtherPlayerMP).min(compByDistance);
        if (closet.isPresent()) {
            if (closet.get().getDistance(mc.player) < range.getValue()) {
                EntityLivingBase target = (EntityLivingBase) closet.get();
                if (rotate.getValue()) rotationManager.rotateToNext(new Rotation(target.getPositionVector(), 32,strictRotate.getValue(), 10));
                if (mc.player.getCooledAttackStrength(0F) == 1F
                        && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword
                        && target.hurtResistantTime < (float) target.maxHurtResistantTime / 2.0F) {
                    mc.playerController.attackEntity(mc.player, target);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }

}
