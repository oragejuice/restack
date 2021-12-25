package me.lnadav.restack.impl.guis;

import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.gui.AbstractComponent;
import me.lnadav.restack.api.setting.AbstractSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class FeatureButton extends AbstractComponent {

    private final AbstractFeature feature;
    public boolean isOpen = false;

    public FeatureButton(int x, int y, int width, int height, AbstractFeature feature){
        super(x, y, width, height);
        this.feature = feature;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, new Color(203, 203, 203, 255).getRGB());
        Minecraft.getMinecraft().fontRenderer.drawString(feature.getName(), x + 4, y + 2, new Color(29, 29, 29, 232).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(inside(mouseX, mouseY)){
            if(mouseButton == 0){
                feature.toggle();
            }
            if(mouseButton == 1){
                ClientChat.sendClientMessage("Settings are not supported with your mouse, config using commands like a normal person");
                String settingList = feature.getName() + " {";
                for (AbstractSetting setting : feature.getSettings()){
                    settingList += setting.getName() + " ";
                }
                settingList += "}";
                ClientChat.sendClientMessage(settingList);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(int keyCode) {

    }
}
