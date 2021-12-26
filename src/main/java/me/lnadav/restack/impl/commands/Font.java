package me.lnadav.restack.impl.commands;

import me.lnadav.restack.api.command.AbstractCommand;
import me.lnadav.restack.api.util.render.font.FontUtil;

public class Font extends AbstractCommand {
    public Font() {
        super("Font", "Sets the font", "font <font name>", new String[]{"font"}, 1);
    }

    @Override
    public void execute(String[] args){
        FontUtil.load(args[1]);
    }
}
