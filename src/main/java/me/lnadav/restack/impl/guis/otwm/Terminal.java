package me.lnadav.restack.impl.guis.otwm;

import me.lnadav.restack.api.util.Globals;
import me.lnadav.restack.api.util.render.font.FontUtil;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Terminal extends AbstractProgram implements Globals {

    private boolean focused = false;
    private boolean isCapturing = false;
    private String currentLine = getPS1();
    private ArrayList<String> display = new ArrayList<>();

    public Terminal(int x, int y, int width, int height, Container parent) {
        super(x, y, width, height, parent);
    }

    @Override
    public void draw(int mX, int mY) {
        int termColor = new Color(79,79,79,255).getRGB();
        Gui.drawRect(x, y, x + width, y + height, termColor);


        Color borderColor = focused ? new Color(200,100,100) : new Color(50,70,100);
        Gui.drawRect(x,y,x+width,y+1, borderColor.getRGB()); //top
        Gui.drawRect(x,y+height-1,x+width,y+height, borderColor.getRGB()); //bottom
        Gui.drawRect(x,y,x+1,y+height, borderColor.getRGB()); //left
        Gui.drawRect(x+width,y,x+width-1,y+height, borderColor.getRGB()); //right

        /*
        GL11.glPushMatrix();
        GL11.glScissor(x+1, y+1, width-1, height-1); //SCISSOR
        GL11.glEnable(GL11.GL_SCISSOR_TEST); //ENABLE SCISSOR

         */

        //ArrayList<String> nDisp = new ArrayList();


        int nOfLines = height / (FontUtil.getFontHeight() + 1);
        ArrayList<String> rDisplay = display;
        if(display.size() > nOfLines - 1){
            rDisplay =  new ArrayList<String>( display.subList((display.size()-nOfLines+1), display.size()));
        }

        int fY = y + 1;
        for (String line : rDisplay){
            FontUtil.drawString(line, x+3, fY,new Color(255,255,255).getRGB());
            fY += FontUtil.getFontHeight() + 1;
        }
        FontUtil.drawString(currentLine, x+3, fY, new Color(255,255,255).getRGB());


        /*
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST); //DISABLE SCISSOR
        GL11.glPopMatrix();

         */
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        focused = inside(mouseX, mouseY);
        return focused;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        //is TWM command
        if(TWMGui.isAltKeyDown() && focused){
            if(keyCode == Keyboard.KEY_RETURN){
                getParent().addTerm(this);
            }

            else if (keyCode == Keyboard.KEY_O){
                getParent().addContainer(this);
            }

            else if (keyCode == Keyboard.KEY_LSHIFT){
                getParent().verticalAlign = !getParent().verticalAlign;
            }

            else if (keyCode == Keyboard.KEY_Q){
                getParent().killProcess(this);
            }



        }

        //is typing into thing
        else if(focused){

            if(keyCode == Keyboard.KEY_RETURN){
                display.add(currentLine);
                currentLine = getPS1();
            }

            currentLine += typedChar;
        }

    }

    public void setSize(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private static String getPS1(){
        if(mc.player == null){
            return "[player@Restack]$ ";
        }
        StringBuilder PS1 = new StringBuilder();
        PS1.append("[").append(mc.player.getName()).append("@").append("restack").append("]$ ");
        return PS1.toString();
    }
}
