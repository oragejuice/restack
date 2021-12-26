package me.lnadav.restack.api.displayComponent;

import me.lnadav.restack.api.gui.AbstractDragable;
import me.lnadav.restack.api.util.Globals;

public class AbstractDisplayComponent  implements Globals {

    protected final String name;
    protected boolean enabled;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public AbstractDisplayComponent(String name, int x, int y, int width, int height){
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw() {

    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }
}
