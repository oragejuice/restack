package me.lnadav.restack.api.rotations;

import me.lnadav.restack.api.event.events.PacketEvent;
import me.lnadav.restack.api.util.Globals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.swing.text.html.parser.Entity;
import java.util.PriorityQueue;


//TODO Finish making this
public class RotationManager implements Globals {

    private static float serverPitch;
    private static float serverYaw;

    private PriorityQueue<Rotation> rotationQueue = new PriorityQueue<Rotation>();

    public void addToQueue(Rotation rotation){
        //We shouldn't add the rotation if were going to rotate to that block already
        if(rotationQueue.contains(rotation)) return;
        rotationQueue.add(rotation);
    }

    public float[] getNextRotation(){
        return RotationUtil.getRotations(rotationQueue.peek().pos.x, rotationQueue.peek().pos.y, rotationQueue.peek().pos.z);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if (mc.player == null || mc.world == null) return;

    }

    @SubscribeEvent
    public void onPacket(PacketEvent event){
        if(event.getPacket() instanceof CPacketPlayer){
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();

        }
    }


}
