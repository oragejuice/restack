package me.lnadav.restack.api.rotations;

import me.lnadav.restack.api.event.events.PacketEvent;
import me.lnadav.restack.api.event.events.PlayerUpdateEvent;
import me.lnadav.restack.api.util.Globals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.swing.text.html.parser.Entity;
import java.util.PriorityQueue;


//TODO Finish making this
public class RotationManager implements Globals {

    private static float serverPitch;
    private static float serverYaw;
    private static boolean serverOnGround;

    private PriorityQueue<Rotation> rotationQueue = new PriorityQueue<Rotation>();

    public void addToQueue(Rotation rotation){
        //We shouldn't add the rotation if were going to rotate to that block already
        if(rotationQueue.contains(rotation)) return;
        rotationQueue.add(rotation);
    }


    public float[] getNextFinalRotation(){
        return RotationUtil.getRotations(rotationQueue.peek().pos.x, rotationQueue.peek().pos.y, rotationQueue.peek().pos.z);
    }

    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event){
        /*
        *Send a packet, the onPacket event will catch it and modify it for our rotations.
        * Also don't send any extra packets (non-vanilla) if rotation queue is empty
         */
        if(rotationQueue.isEmpty()) return;
        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(
                mc.player.rotationYaw,
                mc.player.rotationPitch,
                mc.player.onGround
        ));
    }


    @SubscribeEvent
    public void onPacket(PacketEvent event){

        if(event.getPacket() instanceof CPacketPlayer){
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            serverPitch = packet.pitch;
            serverYaw = packet.yaw;
            serverOnGround = packet.isOnGround();

            //If rotation queue is empty we don't need to do anything, no need to modify packets
            if(rotationQueue.isEmpty()) return;

            Rotation rotation = rotationQueue.peek();
            float[] finalRot = RotationUtil.getRotations(rotation.pos.x, rotation.pos.y, rotation.pos.z);
            /* do a single rotation, well rotate to the next position in a packets time */
            if(rotation.strict){
                RotationUtil.getYawSteppedRotation(rotation.pos, rotation.yawstep);
            } else {
                /* just instantly rotate to that*/
                packet.yaw = finalRot[0];
                packet.pitch = finalRot[1];
            }

        }
    }

    public boolean isRotatedToPos(Vec3d pos){
        float[] rot = RotationUtil.getRotations(pos.x, pos.y, pos.z);
        if(rot[0] == serverPitch && rot[1] == serverYaw) return true;
        return false;
    }

    public static float getServerPitch() {
        return serverPitch;
    }

    public static float getServerYaw() {
        return serverYaw;
    }

}
