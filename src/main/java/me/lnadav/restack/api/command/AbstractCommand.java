package me.lnadav.restack.api.command;

public abstract class AbstractCommand {

    private final String name;
    private final String description;
    private final String[] aliases;
    private final int minArgs;
    private final String usage;


    public AbstractCommand(String name, String description, String usage, String[] aliases, int minArgs){
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
        this.minArgs = minArgs;
    }

    public void execute(String[] args){
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }


    public int getMinArgs() {
        return minArgs;
    }

    public String getUsage() {
        return usage;
    }
}
