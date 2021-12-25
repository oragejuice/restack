package me.lnadav.restack.impl.guis;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.impl.features.client.ClickGUI;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class ClickGuiScreen extends GuiScreen {

    ArrayList<Window> windows = new ArrayList<>();

    public ClickGuiScreen(){

        int xOffset = 3;
        int yOffset = 0;
        for(Category category : Category.values()){
            Window window = new Window(category, xOffset, 3, 80,10);
            for(AbstractFeature f : Restack.featureManager.getFeaturesFromCategory(category)){
                window.buttons.add(new FeatureButton(window.x, window.y + window.height + yOffset, window.width, window.height, f));
                yOffset += window.height;
            }
            windows.add(window);
            xOffset += 100;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //doScroll();
        for (Window window : windows) {
            window.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Window window : windows) {
            window.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Window window : windows) {
            window.mouseReleased(mouseX, mouseY, state);
        }
    }
}
