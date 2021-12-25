package me.lnadav.restack.impl.features.misc;

import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.setting.settingTypes.BooleanSetting;
import me.lnadav.restack.api.setting.Register;
import me.lnadav.restack.api.setting.settingTypes.EnumSetting;
import me.lnadav.restack.api.setting.settingTypes.FloatSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TestFeature extends AbstractFeature {

    @Register BooleanSetting testBooleanSetting = new BooleanSetting("bool1", false);
    @Register FloatSetting testFloatSetting = new FloatSetting("TestFloatSetting", 1, 0, 10);
    @Register FloatSetting testfloatSettingtwo = new FloatSetting("foo2", 3,-10, 100);
    @Register EnumSetting<testEnum> EnumSetting = new EnumSetting<>("EnumSetting", testEnum.ONE);


    public TestFeature(){
        super("TestFeature", "A feature to test shit", Category.MISC);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        System.out.println(testfloatSettingtwo.getValue());
        ClientChat.sendClientMessage("HELLO!!! " + String.valueOf(testBooleanSetting.getValue()));
    }

    private enum testEnum{
        ONE,
        TWO,
        THREE,
        FOUR
    }
}
