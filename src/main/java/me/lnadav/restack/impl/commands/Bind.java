package me.lnadav.restack.impl.commands;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.command.AbstractCommand;
import me.lnadav.restack.api.feature.AbstractFeature;
import org.lwjgl.input.Keyboard;

public class Bind extends AbstractCommand {
    public Bind() {
        super("Bind", "Binds a feature to a key","bind <feature> <key>", new String[]{"bind","b"}, 2);
    }

    @Override
    public void execute(String[] args){

        AbstractFeature feature = Restack.featureManager.getFeatureByName(args[1]);
        if(feature == null){
            ClientChat.sendClientMessage(args[1] + " isnt a feature");
            return;
        }

        int keybind = Keyboard.getKeyIndex(args[2].toUpperCase());
        feature.setKeybind(keybind);
        ClientChat.sendClientMessage(feature.getName() + " bound to " + Keyboard.getKeyName(feature.getKeybind()));
    }
}
