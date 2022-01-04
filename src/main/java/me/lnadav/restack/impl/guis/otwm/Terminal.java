package me.lnadav.restack.impl.guis.otwm;

import me.lnadav.restack.api.util.Globals;
import me.lnadav.restack.api.util.MathUtil;
import me.lnadav.restack.api.util.render.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;

import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class Terminal extends AbstractProgram implements Globals {

    //public boolean focused = false;
    private boolean isCapturing = false;
    private StringBuilder currentLine = new StringBuilder();
    private ArrayList<String> display = new ArrayList<>();
    private ArrayList<StringBuilder> history = new ArrayList<>();
    private int historyPointer = -1;
    private int linePointer = 0;

    public Terminal(int x, int y, int width, int height, Container parent) {
        super(x, y, width, height, parent);
        this.focused = true;
    }

    @Override
    public void draw(int mX, int mY) {
        int termColor = new Color(51, 51, 51,255).getRGB();
        Gui.drawRect(x, y, x + width, y + height, termColor);


        Color borderColor = focused ? new Color(200,100,100) : new Color(50,70,100);
        Gui.drawRect(x,y,x+width,y+1, borderColor.getRGB()); //top
        Gui.drawRect(x,y+height-1,x+width,y+height, borderColor.getRGB()); //bottom
        Gui.drawRect(x,y,x+1,y+height, borderColor.getRGB()); //left
        Gui.drawRect(x+width,y,x+width-1,y+height, borderColor.getRGB()); //right


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
        FontUtil.drawString(getPS1() + currentLine.toString(), x+3, fY, new Color(255,255,255).getRGB());

        if((System.currentTimeMillis() / 500) % 2 == 1 && focused){
            char selectedChar = currentLine.length() < 1 ? 'A' : currentLine.charAt(MathUtil.clamp(linePointer - 1, 0, currentLine.length()));
            int charWidth = FontUtil.getStringWidth(String.valueOf(selectedChar));
            int xSpace = FontUtil.getStringWidth(getPS1() + currentLine.substring(0, MathUtil.clamp(linePointer, 0, currentLine.length()))) + charWidth;
            Gui.drawRect(x+xSpace,fY,x+xSpace + charWidth,fY+FontUtil.getFontHeight(),new Color(255,255,255).getRGB());
        }

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        focused = inside(mouseX, mouseY);
        if(focused){
            linePointer = 0;
        }
        return focused;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

        //is TWM command
        if(TWMGui.isAltKeyDown() && focused){
            if(keyCode == Keyboard.KEY_RETURN){
                getParent().addTerm(this);
                this.focused = false;
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
             else if (keyCode == Keyboard.KEY_LEFT){
                 getParent().focusLeft(this);
            }
            else if (keyCode == Keyboard.KEY_RIGHT){
                getParent().focusRight(this);
            }

        }

        //is typing into thing
        else if(focused){

            if(keyCode == Keyboard.KEY_RETURN){
                display.add(getPS1() + currentLine.toString());
                //if empty command just ignore
                if(currentLine.length() != 0) history.add(0, currentLine);
                currentLine = new StringBuilder();
                linePointer = currentLine.length();
                historyPointer = 0;
            }

            else if (keyCode == Keyboard.KEY_BACK){
                //Will give index out of range if we dont do this check
                //System.out.println(String.valueOf(linePointer) + " : linePointer - lineLength: " + String.valueOf(currentLine.length()));
                if(linePointer <= currentLine.length() && linePointer > 0) {
                    currentLine.deleteCharAt(linePointer -1);
                    linePointer--;
                }
            }
            else if (keyCode == Keyboard.KEY_UP){
                if (historyPointer < history.size() - 1) {
                    historyPointer++;
                    currentLine = new StringBuilder(history.get(historyPointer).toString());
                    linePointer =  MathUtil.clamp(linePointer, 0, currentLine.length());
                }
            }
            else if (keyCode == Keyboard.KEY_DOWN){
                if(historyPointer >= 0) {
                    historyPointer--;
                    if(historyPointer < 0) {
                        currentLine.replace(0,currentLine.length(), "");
                        linePointer = 0;
                        linePointer =  MathUtil.clamp(linePointer, 0, currentLine.length());
                        return;
                    }
                    currentLine = new StringBuilder(history.get(historyPointer).toString());
                    linePointer =  MathUtil.clamp(linePointer, 0, currentLine.length());
                } else {
                    currentLine.replace(0,currentLine.length(), "");
                    linePointer = 0;
                    linePointer =  MathUtil.clamp(linePointer, 0, currentLine.length());
                }
            }
            else if (keyCode == Keyboard.KEY_LEFT){
                if(linePointer > 0) {
                    linePointer--;
                }
            }
            else if (keyCode == Keyboard.KEY_RIGHT){
                if(linePointer < currentLine.length()) {
                    linePointer++;
                }
            }

            else {
                if(ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                    currentLine.insert(linePointer, typedChar);
                    linePointer++;
                }
            }

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
