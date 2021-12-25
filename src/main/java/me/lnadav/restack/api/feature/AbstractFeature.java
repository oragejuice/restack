package me.lnadav.restack.api.feature;

import ca.weblite.objc.Client;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.setting.AbstractSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public abstract class AbstractFeature {

    private ArrayList<AbstractSetting> settings = new ArrayList<>();

    protected Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private String description;
    private Category category;
    private int keybind;

    private boolean enabled;

    private boolean expanded;
    private boolean binding;

    /**
     *
     * @param name
     * @param description
     * @param category
     */
    public AbstractFeature(String name, String description, Category category){
        this.name = name;
        this.description = description;
        this.category = category;
        this.expanded = false;
        this.binding = false;
    }

    public void enable(){
        if (enabled) return;
        enabled = true;
        MinecraftForge.EVENT_BUS.register(this);
        onEnable();
    }

    public void disable(){
        if (!enabled) return;
        enabled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
        onDisable();
    }

    public void toggle(){
        //Why can't I use a ternary operator
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }

    public ArrayList<AbstractSetting> getSettings(){
        return settings;
    }

    public void setEnabled(boolean enabled){
        if(enabled){
            enable();
        } else {
            disable();
        }
    }

    public void addSetting(AbstractSetting setting){
        settings.add(setting);
    }

    public AbstractSetting getSettingByName(String name){
        for (AbstractSetting setting : settings){
            if(setting.getName() == name){
                return setting;
            }
        }
        return null;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void onEnable(){
        ClientChat.sendClientMessage(this.getName() + ChatFormatting.GREEN + " enabled");
    }

    public void onDisable(){
        ClientChat.sendClientMessage(this.getName() + ChatFormatting.RED + " disabled");

    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public Category getCategory(){
        return category;
    }


    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isBinding() {
        return binding;
    }

    public void setBinding(boolean value){
        this.binding = value;
    }
}
