package me.lnadav.restack.api.rotations;

import net.minecraft.util.math.Vec3d;

public class Rotation implements Comparable<Rotation>{

    /**
     * the position to rotate to, we use this as the player might have moved a bit since the rotation was enequed
     */
    public final Vec3d pos;
    /**
     * Lower is more important, this deicded whether to overide other features packets
     */
    public final int priority;
    public final boolean strict;
    public final int yawstep;

    /**
     *
     * @param pos
     * @param priority
     * @param strict
     * @param yawstep
     */
    public Rotation(Vec3d pos, int priority, boolean strict, int yawstep){
        this.pos = pos;
        this.priority = priority;
        this.strict = strict;
        this.yawstep = yawstep;
    }

    @Override
    public int compareTo(Rotation rotation) {
        return Integer.compare(priority, rotation.priority);
    }
}
