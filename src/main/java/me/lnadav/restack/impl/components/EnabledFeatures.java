package me.lnadav.restack.impl.components;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.displayComponent.AbstractDisplayComponent;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.util.render.GuiUtils;
import me.lnadav.restack.api.util.render.font.FontUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class EnabledFeatures extends AbstractDisplayComponent {

    public EnabledFeatures() {
        super("ArrayList", 2, 50, 50, 14);
    }

    ArrayList<AbstractFeature> enabled = new ArrayList<>();
    ArrayList<AbstractFeature> sortedLength = new ArrayList<>();


    @Override
    public void draw(){

        enabled = Restack.featureManager.getEnabledFeatures();
        ArrayList<String> list = new ArrayList<String>();
        for(AbstractFeature feature : enabled){
            list.add(feature.getName());
        }
        list.sort(Comparator.comparingInt(String::length));



        boolean bottom = GuiUtils.isOnBottomHalf(this.y);
        if(!bottom){
            Collections.reverse(list);
        }
        int iY = this.y;
        boolean right = GuiUtils.isOnRightHalf(this.x);

        int newWidth = 50;
        for(String name : list){
            int drawX = right ? this.x - FontUtil.getStringWidth(name) + width: this.x;
            FontUtil.drawString(name, drawX,iY, new Color(255,255,255,255).getRGB());
            iY += FontUtil.getFontHeight()+3;
            newWidth = Math.max(FontUtil.getStringWidth(name), newWidth);
         }

         this.height = list.size() * (FontUtil.getFontHeight() +3);
         this.width = newWidth;
    }
}
