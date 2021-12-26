package me.lnadav.restack.impl.guis;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.displayComponent.AbstractDisplayComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

public class HUDEditor extends GuiScreen {

    ArrayList<AbstractDisplayComponent> displayComponents = new ArrayList<>();
    AbstractDisplayComponent dragComponent = null;
    int dragComponentX = 0;
    int dragComponentY = 0;

    int screenWidth;
    int screenHeight;


    public HUDEditor(){
        displayComponents = Restack.displayComponentManager.getDisplayComponents();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){

        ScaledResolution res = new ScaledResolution(mc);
        screenWidth = res.getScaledWidth();
        screenHeight = res.getScaledHeight();

        if(dragComponent != null){
            dragComponent.setX(Math.max( Math.min(mouseX - dragComponentX , screenWidth - dragComponent.getWidth()), 0));
            dragComponent.setY(Math.max( Math.min( mouseY - dragComponentY, screenHeight - dragComponent.getHeight()), 0));
        }

        for(AbstractDisplayComponent component : displayComponents){
            int alpha = component.isEnabled() ? 100 : 50;
            Color color = new Color(50,50,50,alpha);
            drawRect(component.getX(),component.getY(),component.getX() + component.getWidth(),component.getY() + component.getHeight(),color.getRGB());
            component.draw();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton){
        if(mouseButton == 0){
            for(AbstractDisplayComponent component : displayComponents){
                if(isHover(component.getX(), component.getY(), component.getWidth(), component.getHeight(), mouseX, mouseY)){
                    dragComponentX = mouseX - component.getX();
                    dragComponentY = mouseY - component.getY();
                    dragComponent = component;
                    return;
                }
            }
        }
        if(mouseButton == 1){
            for(AbstractDisplayComponent component : displayComponents){
                if(isHover(component.getX(), component.getY(), component.getWidth(), component.getHeight(), mouseX, mouseY)){
                    component.setEnabled(!component.isEnabled());
                }
            }
        }

    }

    @Override
    public void mouseReleased(int mX, int mY, int state){

        //Do snap to corner of screen
        if(dragComponent != null){
            double Xscaled = ((double) (dragComponent.getX() + dragComponent.getWidth() ) )/ (double) screenWidth;
            if(Xscaled > 0.95){
                dragComponent.setX(screenWidth - dragComponent.getWidth() - 2);
            } else if (Xscaled < 0.05){
                dragComponent.setX(2);
            }
            double Yscaled = ((double) (dragComponent.getY() + dragComponent.getHeight() ) )/ (double) screenHeight;
            if(Xscaled > 0.95){
                dragComponent.setX(screenHeight - dragComponent.getHeight() - 2);
            } else if (Xscaled < 0.05){
                dragComponent.setY(2);
            }
        }



        dragComponent = null;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static boolean isHover(int X, int Y, int W, int H, int mX, int mY)
    {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }


}
