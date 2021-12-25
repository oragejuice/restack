package me.lnadav.restack.api.feature;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    WORLD("World"),
    CLIENT("Client"),
    RENDER("Render"),
    MISC("Misc");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
