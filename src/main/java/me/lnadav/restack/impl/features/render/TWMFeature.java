package me.lnadav.restack.impl.features.render;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.impl.guis.otwm.TWMGui;

public class TWMFeature extends AbstractFeature {


    public TWMFeature() {
        super("twm", "opens the TWM", Category.RENDER);
    }

    @Override
    public void onEnable(){
        super.onEnable();
        mc.displayGuiScreen(Restack.twmGui);
        this.setEnabled(false);
    }

    @Override
    public void onDisable(){
        super.onDisable();
    }
}
