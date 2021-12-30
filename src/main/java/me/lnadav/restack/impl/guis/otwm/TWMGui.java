package me.lnadav.restack.impl.guis.otwm;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class TWMGui extends GuiScreen {

    TerminalNode masterNode;
    int screenHeight;
    int screenWidth;

    public TWMGui(){
        masterNode = new TerminalNode(2,2, 100, 100, null);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        ScaledResolution res = new ScaledResolution(mc);
        screenWidth = res.getScaledWidth();
        screenHeight = res.getScaledHeight();
        masterNode.setWidth(screenWidth - 4);
        masterNode.setHeight(screenHeight - 4);
        masterNode.draw(mouseX,mouseY);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode){
        if(keyCode == Keyboard.KEY_ESCAPE){
            mc.player.closeScreen();
            return;
        }
        masterNode.keyTyped(typedChar, keyCode);
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton){
        masterNode.mouseClicked(mouseX, mouseY, mouseButton);
    }



}
