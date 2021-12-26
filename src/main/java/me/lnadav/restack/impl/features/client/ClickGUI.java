package me.lnadav.restack.impl.features.client;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends AbstractFeature {


    public ClickGUI() {
        super("ClickGui", "draws the clickgui", Category.CLIENT);
        setKeybind(Keyboard.KEY_SEMICOLON);
    }

    @Override
    public void onEnable(){
        mc.displayGuiScreen(Restack.clickGuiScreen);
        this.setEnabled(false);
    }
}
