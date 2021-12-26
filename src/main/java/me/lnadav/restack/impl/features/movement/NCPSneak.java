package me.lnadav.restack.impl.features.movement;

import me.lnadav.restack.api.event.events.PacketEvent;
import me.lnadav.restack.api.event.events.PlayerUpdateEvent;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.util.Globals;
import me.lnadav.restack.api.util.PlayerUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class NCPSneak extends AbstractFeature {

    boolean isSneaking = false;

    public NCPSneak(){
        super("NCPSneak","Sneaks on NCP", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
      if (PlayerUtil.isMoving()) {
          mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
          mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
      } else {
          mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
      }
    }


    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof CPacketEntityAction) {
            CPacketEntityAction packet = (CPacketEntityAction) event.getPacket();
            if (packet.getAction() == CPacketEntityAction.Action.START_SNEAKING) isSneaking = true;
            if (packet.getAction() == CPacketEntityAction.Action.STOP_SNEAKING) isSneaking = false;
        }
    }


}