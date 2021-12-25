package me.lnadav.restack.api.setting.settingTypes;


import me.lnadav.restack.api.setting.AbstractSetting;

public class EnumSetting<T extends Enum<T>> extends AbstractSetting {

    private final T[] values;
    private T value;

    public EnumSetting(String name, T value){
        super(name);
        this.value = value;
        this.values = (T[]) value.getClass().getEnumConstants();
    }

    public T getValue(){
        return value;
    }

    public void setValue(T t){
        value = t;
    }

    public T[] getValues(){
        return values;
    }

    public T increment() {
        if (value.ordinal() == values.length - 1) {
            value = values[0];
        } else {
            value = values[value.ordinal() + 1];
        }

        return value;
    }

    public T decrement() {
        if (value.ordinal() == 0) {
            value = values[values.length - 1];
        } else {
            value = values[value.ordinal() - 1];
        }
        return value;
    }


    public boolean setValue(String value){
        for(T t: values){
            if(t.toString().equalsIgnoreCase(value)){
                this.value = t;
                return true;
            }
        }
        System.out.println(value + " is not found in enum " + value.getClass().toString());
        return false;
    }


}
