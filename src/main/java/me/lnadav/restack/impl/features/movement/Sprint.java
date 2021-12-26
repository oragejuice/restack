package me.lnadav.restack.impl.features.movement;

import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.event.events.MoveEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Sprint extends AbstractFeature {

    public Sprint() {
        super("Sprint", "Sprints when you move", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onMoveEvent(MoveEvent event){
        if (mc.player.movementInput.moveForward == 0f && mc.player.movementInput.moveStrafe == 0f) return;
        if(!mc.player.isSprinting()){
            mc.player.setSprinting(true);
        }
    }

}
