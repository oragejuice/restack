package me.lnadav.restack.impl.commands;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.command.AbstractCommand;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.setting.AbstractSetting;

public class Settings extends AbstractCommand {

    public Settings() {
        super("Settings", "returns the settings of a given feature","setting <feature>" ,new String[]{"setting", "settings"},1);
    }

    @Override
    public void execute(String[] args){
        String featurename = args[1];

        AbstractFeature feature =  Restack.featureManager.getFeatureByName(featurename);
        if(feature != null){
            String settingList = feature.getName() + " {";
            for (AbstractSetting setting : feature.getSettings()){
                settingList += setting.getName() + " ";
            }

            ClientChat.sendClientMessage(settingList);
        } else {
            ClientChat.sendClientMessage( featurename + " is not a feature");
        }
    }
}
