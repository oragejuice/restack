package me.lnadav.restack.api.command;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.impl.commands.*;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

public class CommandManager {



    private String prefix = "-";

    private ArrayList<AbstractCommand> commands = new ArrayList<>();

    public CommandManager(){
        //Add commands here
        commands.add(new Toggle());
        commands.add(new Bind());
        commands.add(new Settings());
        commands.add(new Set());
        commands.add(new Prefix());
        commands.add(new Font());

    }

    public void execute(String cmd){
        String[] s = cmd.substring(1).split(" ");
        System.out.println(s);
        for (AbstractCommand command : commands){
            for(String alias : command.getAliases()){
                if(s[0].equalsIgnoreCase(alias)){
                    if (s.length >= command.getMinArgs() + 1){
                        command.execute(s);
                        return;
                    } else {
                        ClientChat.sendClientMessage("not enough arguments provided. " + command.getUsage());
                        return;
                    }
                }
            }
        }

        /*
        this just makes using commands a bit nicer
         */

        for (AbstractFeature feature : Restack.featureManager.getFeatures()){
            if(s[0].equalsIgnoreCase(feature.getName())){
                if(s.length == 1){
                    new Toggle().execute(ArrayUtils.add(s,0,"toggle"));
                    return;
                }
                if(s.length == 3){
                    new Set().execute(ArrayUtils.add(s,0,"set"));
                    return;
                }
            }
        }
        ClientChat.sendClientMessage("Command not found");
    }

    public ArrayList<AbstractCommand> getCommands() {
        return commands;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
