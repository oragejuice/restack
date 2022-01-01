package me.lnadav.restack.impl.guis.otwm;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Container extends AbstractProgram {

    boolean isRoot = false;
    private CopyOnWriteArrayList<AbstractProgram> children = new CopyOnWriteArrayList<>();
    final int PADDING = 3;
    boolean verticalAlign = false;

    public Container(int x, int y, int width, int height, Container parent) {
        super(x, y, width, height, parent);
        children.add(new Terminal(x, y, width, height, this));
    }

    public void draw(int mX, int mY) {
        drawRect(this.x , this.y, this.x + this.width , this.y + this.height, new Color(255,100,100));

        int programX = x + PADDING ;
        int programY = y + PADDING ;
        int programWidth = width - PADDING ;
        int programHeight = height - PADDING ;
        int xOffset = 0;
        int yOffset = 0;

        //weird bug, this seems to fix it
        if(children.size() == 0) return;

        if(verticalAlign){
            programHeight = ((height - (PADDING*2)) / children.size());
            yOffset = programHeight + PADDING;
        } else {
            programWidth = ((width - (PADDING*2)) / children.size());
            System.out.println(String.valueOf(programWidth) + " : " + String.valueOf(width - PADDING));
            xOffset = programWidth + PADDING;

        }

        for(AbstractProgram program : children){
            program.updatePos(programX, programY, programWidth, programHeight);
            program.draw(mX, mY);
            programX += xOffset;
            programY += yOffset;
        }

    }

    public void addTerm(AbstractProgram program){
        int i = (children.indexOf(program) + 1) % children.size();
        children.add(i ,new Terminal(x, y, width, height,this));
    }

    public void addContainer(AbstractProgram program){
        int i = (children.indexOf(program) + 1) % children.size();
        children.add(i, new Container(x, y, width, height, this));
    }

    public void killProcess(AbstractProgram program){
        children.remove(program);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean inGutters = true;
        for (AbstractProgram program : children){
            boolean isInTerm = program.mouseClicked(mouseX, mouseY, mouseButton);

            //if any of the terms register a focused click, the click was not in a gutter.
            if(isInTerm) inGutters = false;

        }

        focused = inGutters && inside(mouseX, mouseY);
        return focused;
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (TWMGui.isAltKeyDown() && focused) {
            if (keyCode == Keyboard.KEY_Q && !isRoot) {
                getParent().killProcess(this);
            }


        }

        for (AbstractProgram program : children) {
            program.keyTyped(typedChar, keyCode);
        }
    }


    public static void drawRect(float x1, float y1, float x2, float y2, Color color) {
        applyColor(color);
        drawRect(x1, y1, x2, y2);
    }

    private static void drawRect(float x1, float y1, float x2, float y2) {
        enabledGL2D();
        drawLine(x1, y1, x2, y1);
        drawLine(x2, y1, x2, y2);
        drawLine(x2, y2, x1, y2);
        drawLine(x1, y2, x1, y1);
        disabledGL2D();
    }

    private static void applyColor(Color color) {
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    public static void drawLine(float x1, float y1, float x2, float y2, Color color) {
        applyColor(color);
        drawLine(x1, y1, x2, y2);
    }

    private static void drawLine(float x1, float y1, float x2, float y2) {
        enabledGL2D();
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        disabledGL2D();
    }

    public static void enabledGL2D() {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
    }

    public static void disabledGL2D() {
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        GlStateManager.color(1f, 1f, 1f,1f);
    }


}
