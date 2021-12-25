package me.lnadav.restack.impl.commands;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.command.AbstractCommand;

public class Prefix extends AbstractCommand {

    public Prefix() {
        super("Prefix", "Sets the prefix for the client", "Prefix <char>", new String[]{"prefix","pre"}, 1);
    }

    @Override
    public void execute(String[] args){
        Restack.commandManager.setPrefix(args[1]);
    }
}
