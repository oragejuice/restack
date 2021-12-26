package me.lnadav.restack.api.displayComponent;

import me.lnadav.restack.impl.components.EnabledFeatures;
import me.lnadav.restack.impl.components.Watermark;

import java.util.ArrayList;

public class DisplayComponentManager {

    ArrayList<AbstractDisplayComponent> displayComponents = new ArrayList<>();

    public DisplayComponentManager(){

        displayComponents.add(new Watermark());
        displayComponents.add(new EnabledFeatures());

    }

    public ArrayList<AbstractDisplayComponent> getDisplayComponents() {
        return displayComponents;
    }

    public ArrayList<AbstractDisplayComponent> getEnabledDisplayComponents(){
        ArrayList<AbstractDisplayComponent> ret = new ArrayList<>();
        for(AbstractDisplayComponent component : displayComponents){
            if(component.isEnabled()){
                ret.add(component);
            }
        }
        return ret;
    }
}
