package me.lnadav.restack.api.util.render;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.feature.Category;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class GuiUtils {

    public static void drawBox(int X, int Y, int W, int H, int R, int r, int g, int b, int a)
    {
        drawRegularPolygon(X, Y, R, 30, convertIntToFloat(r), convertIntToFloat(g), convertIntToFloat(b), convertIntToFloat(a));
        drawRegularPolygon(X + W, Y, R, 30, convertIntToFloat(r), convertIntToFloat(g), convertIntToFloat(b), convertIntToFloat(a));
        drawRegularPolygon(X, Y + H, R, 30, convertIntToFloat(r), convertIntToFloat(g), convertIntToFloat(b), convertIntToFloat(a));
        drawRegularPolygon(X + W, Y + H, R, 30, convertIntToFloat(r), convertIntToFloat(g), convertIntToFloat(b), convertIntToFloat(a));
        Gui.drawRect(X - R, Y, X + W + R, Y + H, getIntFromColor(r, g, b));
        Gui.drawRect(X, Y - R, X + W, Y + H + R, getIntFromColor(r, g, b));
    }


    public static void drawRegularPolygon(double x, double y, int radius, int sides, float r, float g, float b, float a)
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(r, g, b, a);
        tessellator.getBuffer().begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        tessellator.getBuffer().pos(x, y, 0).endVertex();
        for (int i = 0; i <= sides; i++)
        {
            double angle = (Math.PI * 2 * i / sides) + Math.toRadians(180);
            tessellator.getBuffer().pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static Tessellator tessellator = Tessellator.getInstance();

    public static float convertIntToFloat(int color)
    {
        return color / 255f;
    }

    public static int getIntFromColor(int red, int green, int blue)
    {
        int r = (red << 16) & 0x00FF0000;
        int g = (green << 8) & 0x0000FF00;
        int b = blue & 0x000000FF;

        return 0xFF000000 | r | g | b;
    }

    public static int getCategoryHeight(Category category)
    {
        int H = 17;
        H += Restack.featureManager.getFeaturesFromCategory(category).size() * 20;
        return H;
    }

    public static String capFirstLetter(String string)
    {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static boolean isHover(int X, int Y, int W, int H, int mX, int mY)
    {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }
}
