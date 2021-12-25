package me.lnadav.restack.impl.guis;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.gui.AbstractComponent;
import me.lnadav.restack.api.gui.AbstractDragable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class Window extends AbstractDragable {

    private Category category;
    private final ArrayList<AbstractFeature> features;
    public ArrayList<AbstractComponent> buttons = new ArrayList<>();


    public Window(Category category, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.category = category;
        features = Restack.featureManager.getFeaturesFromCategory(category);;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks){
        super.draw(mouseX, mouseY, partialTicks);
        Gui.drawRect(x, y, x + width, y + height, new Color(203, 203, 203, 255).getRGB());
        Minecraft.getMinecraft().fontRenderer.drawString(category.toString(), x + 4, y + 2, new Color(29, 29, 29, 232).getRGB());

        int yAdd = 0;
        for (AbstractComponent button : buttons) {
            button.x = x;
            button.y = y + height + yAdd;
            yAdd += button.height + button.height;
            button.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (AbstractComponent b : buttons) {
            b.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    public boolean insideRect(int posX, int posY, int width, int height) {
        return posX > x && posY > y && posX < x + width && posY < y + height;
    }



}
