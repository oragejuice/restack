package me.lnadav.restack.impl.features.client;

import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.util.render.font.FontUtil;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class Watermark extends AbstractFeature {

    public Watermark() {
        super("Watermark", "Draws the client name", Category.CLIENT);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event){
        FontUtil.drawStringWithShadow("[re:stack]",10,10,new Color(255,255,255).getRGB());
    }

}
