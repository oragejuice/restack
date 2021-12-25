package me.lnadav.restack.api.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class ClientChat {

    static Minecraft mc = Minecraft.getMinecraft();

    public static String getPrefix(){
        return ChatFormatting.RED + "[re" + ChatFormatting.GRAY + ":" + ChatFormatting.WHITE + "stack]  ";
    }

    public static void sendClientMessage(String message){
        if (mc.ingameGUI == null) return;
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(getPrefix() + message));
    }

    public static void addToSentMessages(String message){
        if (mc.ingameGUI == null) return;
        mc.ingameGUI.getChatGUI().addToSentMessages(message);
    }
}
