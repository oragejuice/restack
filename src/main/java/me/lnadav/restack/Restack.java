package me.lnadav.restack;

import me.lnadav.restack.api.command.CommandManager;
import me.lnadav.restack.api.config.ConfigHelper;
import me.lnadav.restack.api.displayComponent.DisplayComponentManager;
import me.lnadav.restack.api.event.EventHandler;
import me.lnadav.restack.api.feature.FeatureManager;
import me.lnadav.restack.api.rotations.RotationHandler;
import me.lnadav.restack.api.util.render.font.FontUtil;
import me.lnadav.restack.impl.guis.HUDEditor;
import me.lnadav.restack.impl.guis.round.ClickGUIRound;
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
    public static ClickGUIRound clickGuiScreen;
    public static HUDEditor hudEditor;
    public static boolean CUSTOM_FONT;
    public static RotationHandler rotationManager;
    public static final DisplayComponentManager displayComponentManager = new DisplayComponentManager();

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        FontUtil.load();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        clickGuiScreen = new ClickGUIRound();
        hudEditor = new HUDEditor();
        rotationManager = new RotationHandler();
        MinecraftForge.EVENT_BUS.register(rotationManager);

    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event){

        System.out.println("Re:stack loaded");
        configHelper.loadConfig();
        configHelper.saveConfig();
    }
}
