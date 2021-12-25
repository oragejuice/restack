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

    private int X;
    private int Y;
    private boolean expanded;

    Category(String name, int X)
    {
        this.name = name;
        this.X = X;
        this.Y = 3;
        this.expanded = true;
    }

    public int getX()
    {
        return X;
    }

    public void setX(int x)
    {
        X = x;
    }

    public int getY()
    {
        return Y;
    }

    public void setY(int y)
    {
        Y = y;
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }
}
