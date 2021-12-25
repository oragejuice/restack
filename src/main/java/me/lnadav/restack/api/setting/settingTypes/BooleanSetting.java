package me.lnadav.restack.api.setting.settingTypes;

import me.lnadav.restack.api.setting.AbstractSetting;

public class BooleanSetting extends AbstractSetting {

    private boolean value;

    /**
     *
     * @param name the name of the setting
     * @param value value of the setting
     */
    public BooleanSetting(String name, boolean value) {
        super(name);
        this.value = value;
    }

    public void setValue(boolean value){
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void toggle() {
        value = !value;
    }
}