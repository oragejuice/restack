package me.lnadav.restack.impl.features.movement;

import me.lnadav.restack.api.event.events.MoveEvent;
import me.lnadav.restack.api.event.events.PacketEvent;
import me.lnadav.restack.api.event.events.PushPlayerEvent;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.setting.Register;
import me.lnadav.restack.api.setting.settingTypes.BooleanSetting;
import me.lnadav.restack.api.setting.settingTypes.FloatSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends AbstractFeature {

    @Register FloatSetting VelocityH = new FloatSetting("H%",0,0,100);
    @Register FloatSetting VelocityV = new FloatSetting("V%",0,0,100);
    @Register BooleanSetting noPush = new BooleanSetting("noPush", true);


    public Velocity() {
        super("Velocity", "Cancels knockback", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event){
        if(event.getPacket() instanceof SPacketEntityVelocity){
            SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();
            packet.motionX *= (VelocityH.getValue()/100);
            packet.motionY *= (VelocityV.getValue()/100);
            packet.motionZ *= (VelocityH.getValue()/100);
        }
        if(event.getPacket() instanceof SPacketExplosion){
            SPacketExplosion packet = (SPacketExplosion) event.getPacket();
            packet.motionX *= (VelocityH.getValue()/100);
            packet.motionY *= (VelocityV.getValue()/100);
            packet.motionZ *= (VelocityH.getValue()/100);
        }
    }

    @SubscribeEvent
    public void onPush(PushPlayerEvent event){
        event.setCanceled(noPush.getValue());
    }
}
