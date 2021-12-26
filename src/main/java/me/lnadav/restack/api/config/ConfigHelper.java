package me.lnadav.restack.api.config;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import me.lnadav.restack.Restack;
import me.lnadav.restack.api.displayComponent.AbstractDisplayComponent;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.setting.AbstractSetting;
import me.lnadav.restack.api.setting.settingTypes.BooleanSetting;
import me.lnadav.restack.api.setting.settingTypes.EnumSetting;
import me.lnadav.restack.api.setting.settingTypes.FloatSetting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author L-Nadav
 *  Should Help with config shit :D
 */

public class ConfigHelper {

    Path directory = Paths.get("Restack/");

    public void saveConfig(){
        File dir = new File("Restack/");
        if(!dir.exists()){
            try {
                Files.createDirectories(directory);
                System.out.println("Directory didn't exist, created it");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(AbstractFeature feature : Restack.featureManager.getFeatures()){

            File featureFile = new File("Restack/" + feature.getName() + ".toml");
            if(!featureFile.exists()){
                try {
                    featureFile.createNewFile();
                    System.out.println(featureFile.toString() + " didn't exist, creating");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            saveFeature(feature);
        }

        TomlWriter tomlWriter = new TomlWriter();
        File mainFile = new File("Restack/main.toml");
        Map<String, Object> mainSettings = new HashMap<>();
        if(!mainFile.exists()){
            try {
                mainFile.createNewFile();
                System.out.println(mainFile.toString() + " didn't exist, creating");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mainSettings.put("prefix",Restack.commandManager.getPrefix());


        try {
            tomlWriter.write(mainSettings, mainFile);
        } catch (IOException e) {
            System.out.println("Unable to create main config file");
            e.printStackTrace();
        }
    }

    public void saveHUD(){

        TomlWriter tomlWriter = new TomlWriter();

        for(AbstractDisplayComponent component : Restack.displayComponentManager.getDisplayComponents()){
            File componentFile = new File("Restack/" + component.getName() + ".toml");

            if(!componentFile.exists()){
                try {
                    componentFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            HashMap<String, Object> settings = new HashMap<>();
            settings.put("X",component.getX());
            settings.put("Y",component.getY());
            settings.put("enabled", component.isEnabled());
            try {
                tomlWriter.write(settings, componentFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFeature(AbstractFeature feature){
        TomlWriter tomlWriter = new TomlWriter();

        Map<String, Object> settings = new HashMap<String, Object>();

        settings.put("Feature", feature.getName());

        settings.put("enabled",feature.isEnabled());

        /* TODO make this save as a char and not an int */
        settings.put("bind", feature.getKeybind());

        for (AbstractSetting setting : feature.getSettings()){
            if (setting instanceof BooleanSetting){
                settings.put(setting.getName(), ((BooleanSetting) setting).getValue());
            } else if (setting instanceof FloatSetting){
                settings.put(setting.getName(), ((FloatSetting) setting).getValue());
            } else if (setting instanceof EnumSetting){
                settings.put(setting.getName(), ((EnumSetting<?>) setting).getValue());
            }
        }

        try {
            tomlWriter.write(settings, new File("Restack/" + feature.getName() + ".toml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){

        for (AbstractFeature feature : Restack.featureManager.getFeatures()){
            File featureFile = new File("Restack/" + feature.getName() + ".toml");

            /*
            This should only happen when first loading the client
             or when the user has deleted the config folder for some reason
             */

            if(!featureFile.exists()){

                System.out.println(featureFile.toString() + " Does not exist, creating now");

                File dir = new File("Restack/");
                if(!dir.exists()){
                    try {
                        Files.createDirectories(directory);
                        System.out.println("Directory didn't exist either, created it");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                saveFeature(feature);
            }

            Toml featureTomlFile = new Toml().read(featureFile);
            System.out.println(featureTomlFile.toString());

            Integer bind = Math.toIntExact(featureTomlFile.getLong("bind"));
            if(bind != null) feature.setKeybind(bind);

            Boolean enabled = featureTomlFile.getBoolean("enabled");
            feature.setEnabled(enabled);

            for(AbstractSetting setting : feature.getSettings()){

                if (setting instanceof BooleanSetting){
                    Boolean value = featureTomlFile.getBoolean(setting.getName());
                    System.out.println(value);
                    if(value != null){
                        ((BooleanSetting) setting).setValue(value);
                    }
                } else if (setting instanceof FloatSetting){
                    Double value = featureTomlFile.getDouble(setting.getName());
                    System.out.println(value);
                    if (value != null) {
                        ((FloatSetting) setting).setValue(value);
                    }
                } else if (setting instanceof EnumSetting){
                    String value = featureTomlFile.getString(setting.getName());
                    System.out.println(value);
                    if (value != null){
                        ((EnumSetting<?>) setting).setValue(value);
                    }
                }

            }
        }

        for(AbstractDisplayComponent component : Restack.displayComponentManager.getDisplayComponents()){

            File componentFile = new File("Restack/" + component.getName() + ".toml");
            if(!componentFile.exists()){
                saveHUD();
            }
            Toml componentTomlFile = new Toml().read(componentFile);
            component.setX(componentTomlFile.getLong("X").intValue());
            component.setY(componentTomlFile.getLong("Y").intValue());
            component.setEnabled(componentTomlFile.getBoolean("enabled"));
        }

        File mainConfigTomlFile = new File("Restack/main.toml");
        if(!mainConfigTomlFile.exists()){
            //Im lazy
            saveConfig();
        }
        Toml mainTomlFile = new Toml().read(mainConfigTomlFile);
        Restack.commandManager.setPrefix(mainTomlFile.getString("prefix"));
    }
}
