package me.lnadav.restack.api.gui;

public class Rectangle {

    public int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean inside(int posX, int posY) {
        return posX > x && posY > y && posX < x + width && posY < y + height;
    }
}
