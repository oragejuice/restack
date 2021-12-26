package me.lnadav.restack.api.util;

import static me.lnadav.restack.api.util.Globals.mc;

public class PlayerUtil {

    public static boolean isMoving() {
        return mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f;
    }
}
