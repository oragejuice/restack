package me.lnadav.restack.impl.mixin;


import me.lnadav.restack.Restack;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.lnadav.restack.api.util.Globals.mc;

@Mixin(RenderLivingBase.class)
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T> {

    protected MixinRenderLivingBase(RenderManager renderManager) {
        super(renderManager);
    }

    @Shadow
    protected abstract void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor);

    @Shadow
    protected abstract void renderLayers(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn);

    @Shadow
    protected abstract void applyRotations(T entityLiving, float ageInTicks, float rotationYaw, float partialTicks);

    @Inject(method = "applyRotations(Lnet/minecraft/entity/EntityLivingBase;FFF)V", at = @At("HEAD"), cancellable = true)
    protected void applyRotations(T entityLiving, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo ci) {
        if (entityLiving instanceof EntityPlayerSP) {
            if (rotationYaw != Restack.rotationManager.getServerYaw()) {
                applyRotations(entityLiving, ageInTicks, Restack.rotationManager.getServerYaw(), partialTicks);
                ci.cancel();
            }
        }
    }


    @Inject(method = "renderModel(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    protected void renderModel(T entityLiving, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
        if (entityLiving != mc.player) return;
        if (netHeadYaw != 0) {
            renderModel(entityLiving, limbSwing, limbSwingAmount, ageInTicks, 0, Restack.rotationManager.getServerPitch(), scaleFactor);
            ci.cancel();
        }
    }

    @Inject(method = "renderLayers", at = @At("HEAD"), cancellable = true)
    protected void renderLayers(T entityLiving, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn, CallbackInfo ci) {
        if (entityLiving != mc.player) return;
        if (netHeadYaw != 0) {
            renderLayers(entityLiving, limbSwing, limbSwingAmount, partialTicks, ageInTicks, 0, Restack.rotationManager.getServerPitch(), scaleIn);
            ci.cancel();
        }
    }


}
