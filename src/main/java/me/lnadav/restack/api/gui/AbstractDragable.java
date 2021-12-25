package me.lnadav.restack.api.gui;

public abstract class AbstractDragable extends AbstractComponent{

    private int dragX, dragY;
    private boolean dragging;

    public AbstractDragable(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (inside(mouseX, mouseY) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && dragging)
            dragging = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }
    }

    @Override
    public void keyTyped(int keyCode) { }
}
