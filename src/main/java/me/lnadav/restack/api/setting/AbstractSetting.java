package me.lnadav.restack.api.setting;

public class AbstractSetting {

    private final String name;

    public AbstractSetting(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
