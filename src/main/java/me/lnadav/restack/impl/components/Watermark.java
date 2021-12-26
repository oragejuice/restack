package me.lnadav.restack.impl.components;

import me.lnadav.restack.api.displayComponent.AbstractDisplayComponent;
import me.lnadav.restack.api.util.render.font.FontUtil;

import java.awt.*;

public class Watermark extends AbstractDisplayComponent {

    public Watermark() {
        super("WaterMark", 100, 100, 52, 14);
    }

    @Override
    public void draw(){
        this.height = FontUtil.getFontHeight() + 2;
        this.width = FontUtil.getStringWidth("[re:stack]" + 2);
        FontUtil.drawString("[re:stack]",this.x+1, this.y+1, new Color(255,255,255,255).getRGB());
    }
}
