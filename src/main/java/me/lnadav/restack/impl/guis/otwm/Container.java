package me.lnadav.restack.impl.guis.otwm;

import me.lnadav.restack.api.util.render.GuiUtils;
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

        Color color = focused ? new Color(255,100,100) : new Color(255,255,255);
        GuiUtils.drawRect(this.x , this.y, this.x + this.width , this.y + this.height, color);

        int programX = x + PADDING ;
        int programY = y + PADDING ;
        int programWidth = width - PADDING*2 ;
        int programHeight = height - PADDING*2 ;
        int xOffset = 0;
        int yOffset = 0;

        //weird bug, this seems to fix it
        if(children.size() == 0) return;

        if(verticalAlign){
            programHeight = ((height - (PADDING)) / children.size()) - PADDING;
            yOffset = programHeight + PADDING;
        } else {
            programWidth = ((width - (PADDING)) / children.size()) - PADDING;
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
        int i = (children.indexOf(program) + 1) % (children.size() + 1);
        children.add(i ,new Terminal(x, y, width, height,this));
    }

    public void focusFirstTerm(){
        children.get(0).focused = true;
    }

    public void focusRight(AbstractProgram program){
        System.out.println("moved right");
        program.focused = false;
        if (isRoot){
            int j = (children.indexOf(program) + 1) % children.size();
            if(children.get(j) instanceof Container) ((Container) children.get(j)).focusFirstTerm();
            else children.get(j).focused = true;
        } else{
            int i = children.indexOf(program) + 1;
            if (i > children.size()){
                getParent().focusRight(this);
            } else {
                if(children.get(i) instanceof Container) ((Container) children.get(i)).focusFirstTerm();
                else children.get(i).focused = true;
            }

        }
    }

    public void focusLeft(AbstractProgram program){
        System.out.println("moved left");
        program.focused = false;
        if (isRoot){
            int j = Math.floorMod((children.indexOf(program) - 1), children.size());
            if(children.get(j) instanceof Container) ((Container) children.get(j)).focusFirstTerm();
            else children.get(j).focused = true;
        } else{
            int i = children.indexOf(program) - 1;
            if (i < 0){
                getParent().focusLeft(this);
            } else {
                if(children.get(i) instanceof Container) ((Container) children.get(i)).focusFirstTerm();
                else children.get(i).focused = true;
            }

        }
    }

    public void addContainer(AbstractProgram program){
        program.focused = false;
        int i = (children.indexOf(program) + 1) % children.size();
        children.add(i, new Container(x, y, width, height, this));
    }

    public void killProcess(AbstractProgram program){
        children.remove(program);
        if(children.isEmpty() && !isRoot){
            getParent().killProcess(this);
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {

        /*
        *WHY IS EVERY SECOND ONE CONTAINER GETTING SELECTED ???
         */
        boolean inGutters = true;

        for (AbstractProgram program : children){
            boolean isInTerm = program.mouseClicked(mouseX, mouseY, mouseButton);

            //if any of the terms register a focused click, the click was not in a gutter.
            if(isInTerm) inGutters = false;

        }

        focused = inGutters && inside(mouseX, mouseY);
        //other programs dont care if its the container or its terms that are focused, as long as one of them are
        return inside(mouseX, mouseY);
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (TWMGui.isAltKeyDown() && focused) {
            if (keyCode == Keyboard.KEY_Q && !isRoot) {
                getParent().killProcess(this);
            }
            else if( keyCode == Keyboard.KEY_RETURN){
                children.add(new Terminal(x,y,width,height,this));
            }


        }

        for (AbstractProgram program : children) {
            program.keyTyped(typedChar, keyCode);
        }
    }





}
