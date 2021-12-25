package me.lnadav.restack.api.feature;

import me.lnadav.restack.api.setting.AbstractSetting;
import me.lnadav.restack.api.setting.AnnotationReflectionSearcher;
import me.lnadav.restack.impl.features.client.ClickGUI;
import me.lnadav.restack.impl.features.misc.TestFeature;
import me.lnadav.restack.impl.features.movement.Sprint;

import java.util.ArrayList;

public class FeatureManager {

    ArrayList<AbstractFeature> features = new ArrayList<>();

    public FeatureManager(){
        //Add the features here like a good boy :D
        features.add(new TestFeature());
        features.add(new Sprint());
        features.add(new ClickGUI());

        /*
         * Loads the Settings by using reflection, basically im super smart :D
         */
        for (AbstractFeature feature : features){
            ArrayList<AbstractSetting> settings = AnnotationReflectionSearcher.getSettingsForFeature(feature);
            for (AbstractSetting setting : settings){
                feature.addSetting(setting);
            }
        }
    }

    public ArrayList<AbstractFeature> getFeatures(){
        return features;
    }

    public AbstractFeature getFeatureByName(String featureName){
        for (AbstractFeature f : features){
            if(f.getName().equalsIgnoreCase(featureName)){
                return f;
            }
        }
        return null;
    }

    public ArrayList<AbstractFeature> getFeaturesFromCategory(Category category){
        ArrayList<AbstractFeature> ret = new ArrayList<>();
        for (AbstractFeature f : features){
            if(f.getCategory() == category){
                ret.add(f);
            }
        }
        return ret;
    }
}
