package me.lnadav.restack.impl.features.render;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;

public class CustomFont extends AbstractFeature {

    public CustomFont() {
        super("CustomFont", "Renders everything using a customFont", Category.RENDER);
    }

    @Override
    public void onEnable(){
        Restack.CUSTOM_FONT = true;
    }

    @Override
    public void onDisable(){
        Restack.CUSTOM_FONT = false;
    }
}
