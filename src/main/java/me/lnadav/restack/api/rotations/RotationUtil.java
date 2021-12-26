package me.lnadav.restack.api.rotations;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.util.Globals;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil implements Globals {

    public static float[] getRotations(double x, double y, double z) {
        double xSize = x - mc.player.posX;
        double ySize = y - (mc.player.posY + mc.player.getEyeHeight());
        double zSize = z - mc.player.posZ;
        double theta = MathHelper.sqrt(xSize * xSize + zSize * zSize);
        float yaw = (float) (Math.atan2(zSize, xSize) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(ySize, theta) * 180.0D / Math.PI));
        return new float[]{
                (mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw)) % 360F,
                (mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)) % 360F,
        };
    }

    public static float[] getYawSteppedRotation(Vec3d target, int yawStep){
        float serverYaw = RotationManager.getServerYaw();
        float[] req = getRotations(target.x, target.y, target.z);
        req[0] = Math.min(yawStep, req[0]);
        return req;
    }

    public static float[] getRotations(EntityLivingBase entity) {
        if (entity == null)
            return null;
        return getRotations(entity.posX, entity.posY + ((double) entity.getEyeHeight() / 2F), entity.posZ);
    }
}
