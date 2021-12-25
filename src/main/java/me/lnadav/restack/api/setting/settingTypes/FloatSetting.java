package me.lnadav.restack.api.setting.settingTypes;

import me.lnadav.restack.api.setting.AbstractSetting;

public class FloatSetting extends AbstractSetting {

    private float value;
    private final float max;
    private final float min;

    /**
     *
     * @param name
     * @param value
     * @param min
     * @param max
     */
    public FloatSetting(String name, float value, float min, float max) {
        super(name);
        this.value = value;
        this.max = max;
        this.min = min;
    }

    public float getValue(){
        return value;
    }

    public void setValue(float value){
        this.value = value;
    }

    public void setValue(double value){
        this.value = (float) value;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }
}
