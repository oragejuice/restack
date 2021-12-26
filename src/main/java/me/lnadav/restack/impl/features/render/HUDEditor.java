package me.lnadav.restack.impl.features.render;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;

public class HUDEditor extends AbstractFeature {

    public HUDEditor() {
        super("HUDeditor", "Edits the HUD", Category.RENDER);
    }

    @Override
    public void onEnable(){
        mc.displayGuiScreen(Restack.hudEditor);
        this.setEnabled(false);
    }
}
