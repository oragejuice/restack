package me.lnadav.restack.impl.commands;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.command.AbstractCommand;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.setting.AbstractSetting;
import me.lnadav.restack.api.setting.settingTypes.BooleanSetting;
import me.lnadav.restack.api.setting.settingTypes.EnumSetting;
import me.lnadav.restack.api.setting.settingTypes.FloatSetting;

public class Set extends AbstractCommand {
    public Set() {
        super("Set", "Sets settings in features","Set <feature> <setting> <value>", new String[]{"Set","s"}, 2);
    }

    @Override
    public void execute(String[] args){
        String featurename = args[1];

        AbstractFeature feature =  Restack.featureManager.getFeatureByName(featurename);
        if(feature != null){
            for (AbstractSetting setting : feature.getSettings()){
                if(setting.getName().equalsIgnoreCase(args[2])){
                    try {
                        if (setting instanceof BooleanSetting) {
                            ((BooleanSetting) setting).setValue(Boolean.valueOf(args[3]));
                            ClientChat.sendClientMessage("Set " + args[2] + " to " + ((BooleanSetting) setting).getValue());
                            return;
                        } else if (setting instanceof FloatSetting) {
                            ((FloatSetting) setting).setValue(Float.valueOf(args[3]));
                            ClientChat.sendClientMessage("Set " + args[2] + " to " + args[3]);
                            return;
                        } else if (setting instanceof EnumSetting) {
                            ((EnumSetting<?>) setting).setValue(args[3]);
                            ClientChat.sendClientMessage("Set " + args[2] + " to " + args[3]);
                            return;
                        }
                    } catch (Exception e){
                        ClientChat.sendClientMessage("incorrect setting input (wrong type?) " + e.getMessage() + ". expected" + setting.getClass());
                        return;
                    }
                }

            }
            ClientChat.sendClientMessage(args[2] + "is not a setting");
        } else {
            ClientChat.sendClientMessage( featurename + " is not a feature");
        }
    }
}
