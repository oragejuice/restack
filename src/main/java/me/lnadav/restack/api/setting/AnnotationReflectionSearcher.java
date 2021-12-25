package me.lnadav.restack.api.setting;

import me.lnadav.restack.api.feature.AbstractFeature;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Idea from charlie, written by me :D
 * @Credits L-Nadav
 */

public class AnnotationReflectionSearcher {

    public static ArrayList<AbstractSetting> getSettingsForFeature(AbstractFeature f){
        Class<?> mod = f.getClass();
        ArrayList<AbstractSetting> settings = new ArrayList<>();
        Field[] fields = mod.getDeclaredFields();

        for (Field s : fields){
            s.setAccessible(true);
            if(s.isAnnotationPresent(Register.class)){
                if(AbstractSetting.class.isAssignableFrom(s.getType())){
                    try {
                        AbstractSetting setting = (AbstractSetting) s.get(f);
                        settings.add(setting);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return settings;
    }
}
