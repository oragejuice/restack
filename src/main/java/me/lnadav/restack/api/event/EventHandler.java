package me.lnadav.restack.api.event;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.feature.AbstractFeature;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;


public class EventHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if (!Keyboard.getEventKeyState() || Keyboard.isRepeatEvent() || Keyboard.getEventKey() == 0) return;
        for (AbstractFeature feature : Restack.featureManager.getFeatures()){
            if (feature.getKeybind() == Keyboard.getEventKey()) feature.toggle();
        }

    }

    @SubscribeEvent
    public void onChatSend(ClientChatEvent event){

        if(event.getMessage().startsWith(Restack.commandManager.getPrefix())){
            Restack.commandManager.execute(event.getMessage());
            event.setCanceled(true);
            ClientChat.addToSentMessages(event.getMessage());
        }
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event){
        Restack.configHelper.saveConfig();
    }
}
