package me.lnadav.restack.api.rotations;

import me.lnadav.restack.api.event.events.PacketEvent;
import me.lnadav.restack.api.util.Globals;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RotationHandler implements Globals {

    private float serverYaw;
    private float serverPitch;

    private float nextserverYaw;
    private float nextServerPitch;
    private float nextRotationPriority;
    private boolean isFakeRotating;

    public void rotateToNext(Rotation rotation){

        float[] req = RotationUtil.getRotations(rotation.pos.x,rotation.pos.y,rotation.pos.z);


        //only override the rotation if your priority is higher or equal
        if(rotation.priority >= nextRotationPriority){

            if(rotation.strict){
                req[0] = RotationUtil.limitAngle(serverYaw, req[0], rotation.yawstep);
            }
            nextserverYaw = req[0];
            nextServerPitch = req[1];

            nextRotationPriority = rotation.priority;
            isFakeRotating = true;
        }

    }


    public void rotateToNext(Vec3d target){
        float[] req = RotationUtil.getRotations(target.x, target.y, target.z);
        nextserverYaw = req[0];
        nextServerPitch = req[1];
        isFakeRotating = true;
    }


    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event){
        if(mc.player == null || mc.world == null) return;

        if(isFakeRotating){
            mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(
                    mc.player.posX,
                    mc.player.posY,
                    mc.player.posZ,
                    mc.player.rotationYaw,
                    mc.player.rotationPitch,
                    mc.player.onGround ));
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event){

        if(event.getPacket() instanceof CPacketPlayer.Rotation || event.getPacket() instanceof  CPacketPlayer.PositionRotation){
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();

            packet.yaw = nextserverYaw;
            packet.pitch = nextServerPitch;

            serverYaw = packet.yaw;
            serverPitch = packet.pitch;

            if(!isFakeRotating){
                nextserverYaw = mc.player.rotationYaw;
                nextServerPitch = mc.player.rotationPitch;
            }

            isFakeRotating = false;
        }
    }

    public float getServerYaw() {
        return serverYaw;
    }

    public float getServerPitch() {
        return serverPitch;
    }
}
