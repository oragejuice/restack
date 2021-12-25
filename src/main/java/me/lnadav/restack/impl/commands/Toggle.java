package me.lnadav.restack.impl.commands;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.command.AbstractCommand;
import me.lnadav.restack.api.feature.AbstractFeature;

public class Toggle extends AbstractCommand {

    public Toggle() {
        super("Toggle", "Toggles Modules on and off", "toggle <feature>", new String[]{"t","toggle"}, 1);
    }

    @Override
    public void execute(String[] args){
        String featurename = args[1];

       AbstractFeature feature =  Restack.featureManager.getFeatureByName(featurename);
       if(feature != null){
           feature.toggle();
       } else {
           ClientChat.sendClientMessage( featurename + " is not a feature");
       }
    }
}
