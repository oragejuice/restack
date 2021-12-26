package me.lnadav.restack.api.util.render.font;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.util.Globals;

import java.io.InputStream;

public class FontUtil implements Globals {

    private static FontRenderer globalFont;

    public static void load() {
        globalFont = new FontRenderer(getFont("Inconsolata", 40));
    }


    public static void drawStringWithShadow(String text, float x, float y, int color) {
        if (Restack.CUSTOM_FONT) {
            globalFont.drawStringWithShadow(text, x, y, color);
        }

        else {
            mc.fontRenderer.drawStringWithShadow(text, x, y, color);
        }
    }

    public static void drawString(String text, float x, float y, int color){
        if(Restack.CUSTOM_FONT){
            globalFont.drawString(text, x, y, color, false);
        } else {
            mc.fontRenderer.drawString(text, x, y, color, false);
        }
    }

    public static int getStringWidth(String text) {
        if (Restack.CUSTOM_FONT) {
            return globalFont.getStringWidth(text);
        }

        return mc.fontRenderer.getStringWidth(text);
    }

    public static int getFontHeight() {
        return mc.fontRenderer.FONT_HEIGHT;
    }

    public static int getFontString(String text, float x, float y, int color, boolean custom) {
        if (custom) {
            return globalFont.drawStringWithShadow(text, x, y, color);
        }

        return mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }


    private static java.awt.Font getFont(String fontName, float size) {
        try {
            InputStream inputStream = FontUtil.class.getResourceAsStream("/assets/" + fontName + ".ttf");
            assert inputStream != null;
            java.awt.Font awtClientFont = java.awt.Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(java.awt.Font.PLAIN, size);
            inputStream.close();

            return awtClientFont;
        } catch (Exception exception) {
            exception.printStackTrace();
            return new java.awt.Font("default", java.awt.Font.PLAIN, (int) size);
        }
    }
}
