package me.lnadav.restack.impl.guis.otwm;

import me.lnadav.restack.api.gui.Rectangle;

public abstract class AbstractProgram extends Rectangle {

    public boolean focused;
    private Container parent;

    public AbstractProgram(int x, int y, int width, int height, Container parent) {
        super(x, y, width, height);
        this.parent = parent;
    }

    public void updatePos(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return  false;
    }

    public void draw(int mX, int mY) {

    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public Container getParent() {
        return parent;
    }
}
