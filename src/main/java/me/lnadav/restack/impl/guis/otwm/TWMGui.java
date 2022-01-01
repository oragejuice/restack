package me.lnadav.restack.impl.guis.otwm;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class TWMGui extends GuiScreen {

    int screenHeight;
    int screenWidth;
    Container rootNode;

    public TWMGui(){
        rootNode = new Container(0,0,100,100, null);
        rootNode.isRoot = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        ScaledResolution res = new ScaledResolution(mc);
        screenWidth = res.getScaledWidth();
        screenHeight = res.getScaledHeight();
        rootNode.updatePos(0,0,screenWidth, screenHeight);
        rootNode.draw(mouseX, mouseY);

    }

    @Override
    public void keyTyped(char typedChar, int keyCode){
        if(keyCode == Keyboard.KEY_ESCAPE){
            mc.player.closeScreen();
            return;
        }

        rootNode.keyTyped(typedChar, keyCode);

    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton){
        rootNode.mouseClicked(mouseX, mouseY, mouseButton);
    }

}
