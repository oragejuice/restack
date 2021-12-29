package me.lnadav.restack.impl.mixin;


import me.lnadav.restack.api.event.events.PushPlayerEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow
    public void move(MoverType type, double x, double y, double z){
    }



    @Shadow
    public abstract boolean removeTag(String tag);

    @Redirect(method = "applyEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void onPushPlayer(Entity entity, double x, double y, double z) {
        final PushPlayerEvent event = new PushPlayerEvent();

        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {
            entity.motionX += x;
            entity.motionY += y;
            entity.motionZ += z;
        }
    }
}
