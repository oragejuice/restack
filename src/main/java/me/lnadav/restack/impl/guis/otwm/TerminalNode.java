package me.lnadav.restack.impl.guis.otwm;

import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TerminalNode {

    TerminalNode parent;
    private int x;
    private int y;
    private int width;
    private int height;
    private CopyOnWriteArrayList<TerminalNode> children = new CopyOnWriteArrayList<>();
    private listDirection direction = listDirection.HORIZONTAL;
    private boolean focused = false;



    public TerminalNode(int x, int y, int height, int width, TerminalNode parent){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.parent = parent;
    }

    public void draw(int mX, int mY){
        if(isTerminal()){
            int color = focused ? new Color(128,128,128,200).getRGB() : new Color(128,128,200,200).getRGB();
            Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, color);
        } else {
            updateChildren();
            for (TerminalNode term : children){
                term.draw(mX, mY);
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode){

        if(!focused) return;
        if (TWMGui.isAltKeyDown() && focused){

            //Adding and splitting terms
            if (keyCode == Keyboard.KEY_RETURN){
                if (TWMGui.isShiftKeyDown()){
                    this.split();
                } else {
                    //Handle the master node
                    if (parent == null) {
                        this.split();
                    } else {
                        parent.addChild();
                    }
                }
            }

            if (keyCode == Keyboard.KEY_SPACE){
                if(parent != null) {
                    parent.changeDirection();
                }
            }


        }

        for (TerminalNode child : children){
            child.keyTyped(typedChar, keyCode);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0){
            focused = inside(mouseX, mouseY);
        }
        for (TerminalNode child : children){
            child.mouseClicked(mouseX,mouseY,mouseButton);
        }
    }

    public void addChild(){
        children.add(new TerminalNode(x,y,width,height, this));
    }

    private void updateChildren(){
        //is a container for other terminals
        if(direction == listDirection.HORIZONTAL){
            int termWidth = (width / children.size()) - 3;
            int xOffset = 0;
            for (TerminalNode term : children){
                term.setX(x + xOffset);
                term.setWidth(termWidth);
                term.setY(y);
                term.setHeight(height);
                xOffset += termWidth + 3;
            }
        }
        //direction is horizontal
        else {
            int termHeight = (height / children.size()) - 3;
            int yOffset = 0;
            for (TerminalNode term : children){
                term.setX(x);
                term.setWidth(width);
                term.setY(y + yOffset);
                term.setHeight(termHeight);
                yOffset += termHeight + 3;
            }
        }
    }


    public boolean isTerminal(){
        return children.isEmpty();
    }

    public void split(){
        if(isTerminal()){
            //TerminalNode self = this;
            //self.setParent(this);
            //self.focused = false;
            //children.add(self);
            children.add(new TerminalNode(this.x, this.y, this.width/2, this.height/2, this));
            children.add(new TerminalNode(this.width/2, this.height/2, this.width, this.height, this));
        }
    }

    public boolean inside(int posX, int posY) {
        return posX > x && posY > y && posX < x + width && posY < y + height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TerminalNode getParent() {
        return parent;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setParent(TerminalNode parent) {
        this.parent = parent;
    }

    public listDirection getDirection() {
        return direction;
    }

    public void setDirection(listDirection direction) {
        this.direction = direction;
    }

    public void changeDirection(){
        if(direction == listDirection.HORIZONTAL){
            direction = listDirection.VERTICAL;
        } else {
            direction = listDirection.HORIZONTAL;
        }
    }





    public enum listDirection {
        VERTICAL,
        HORIZONTAL

    }

}
