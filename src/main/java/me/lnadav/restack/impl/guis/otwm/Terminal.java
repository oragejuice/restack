package me.lnadav.restack.impl.guis.otwm;

import me.lnadav.restack.api.chat.ClientChat;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Terminal extends AbstractProgram {

    private boolean focused = false;


    public Terminal(int x, int y, int width, int height, Container parent) {
        super(x, y, width, height, parent);
    }

    @Override
    public void draw(int mX, int mY) {
        int color = focused ? new Color(128,128,128,200).getRGB() : new Color(128,128,200,200).getRGB();
        Gui.drawRect(x, y, x + width, y + height, color);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        focused = inside(mouseX, mouseY);
        return focused;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if(TWMGui.isAltKeyDown() && focused){
            if(keyCode == Keyboard.KEY_RETURN){
                getParent().addTerm(this);
            }

            else if (keyCode == Keyboard.KEY_O){
                getParent().addContainer(this);
            }

            else if (keyCode == Keyboard.KEY_LSHIFT){
                getParent().verticalAlign = !getParent().verticalAlign;
                ClientChat.sendClientMessage("vertical align: " + String.valueOf(getParent().verticalAlign));
            }

            else if (keyCode == Keyboard.KEY_Q){
                getParent().killProcess(this);
            }



        }
    }

    public void setSize(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
