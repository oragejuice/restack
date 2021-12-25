package me.lnadav.restack;

import me.lnadav.restack.api.command.CommandManager;
import me.lnadav.restack.api.config.ConfigHelper;
import me.lnadav.restack.api.event.EventHandler;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.FeatureManager;
import me.lnadav.restack.api.setting.AbstractSetting;
import me.lnadav.restack.api.setting.settingTypes.BooleanSetting;
import me.lnadav.restack.api.setting.settingTypes.EnumSetting;
import me.lnadav.restack.api.setting.settingTypes.FloatSetting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;

@Mod(modid = Restack.MOD_ID)
public class Restack {
    public static final String MOD_ID = "restack";

    public static final FeatureManager featureManager = new FeatureManager();
    public static final ConfigHelper configHelper = new ConfigHelper();
    public static final CommandManager commandManager = new CommandManager();

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event){

        System.out.println("Re:stack loaded");

        configHelper.loadConfig();


        configHelper.saveConfig();
    }
}
