package me.lnadav.restack.impl.guis.round;

import me.lnadav.restack.Restack;
import me.lnadav.restack.api.chat.ClientChat;
import me.lnadav.restack.api.feature.AbstractFeature;
import me.lnadav.restack.api.feature.Category;
import me.lnadav.restack.api.setting.AbstractSetting;
import me.lnadav.restack.api.setting.settingTypes.BooleanSetting;
import me.lnadav.restack.api.setting.settingTypes.EnumSetting;
import me.lnadav.restack.api.setting.settingTypes.FloatSetting;
import me.lnadav.restack.api.util.render.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @Author Yoink
 * Ill write my own Gui at some point, but rn I cba to write my own*
 */

public class ClickGUIRound extends GuiScreen /*implements Client.INSTANCE*/
{

    Minecraft mc = Minecraft.getMinecraft();
    private boolean B = false;
    private boolean D = false;
    private Category draggingCategory = null;
    private int draggingSettingX;
    private int draggingSettingY;
    private int draggingSettingW;
    private int draggingSettingH;
    private FloatSetting draggingSetting = null;
    private int xB = 0;
    private int yB = 0;

    public ClickGUIRound(){
        int x = 10;
        for(Category category : Category.values()){
            category.setX(x);
            category.setY(10);
            x += 130;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawCategories(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        processClick(mouseX, mouseY, mouseButton);
    }

    public void drawCategories(int mX, int mY)
    {
        for (Category category : Category.values())
        {
            if (D && draggingCategory.equals(category))
            {
                category.setX(mX - xB);
                category.setY(mY - yB);
            }
            drawCategory(category, mX, mY);
        }
    }

    public void drawCategory(Category category, int mX, int mY)
    {

        // Draws the Category Box
        drawBox(category.getX(), category.getY(), 80, getCategoryHeight(category), 5, 30, 30, 30, 255);
        drawBox(category.getX(), category.getY(), 80, getCategoryHeight(category), 5, 30, 30, 30, 255);

        // Draws the Category Name
        FontUtil.drawString(category.toString(), (int) (category.getX() + 40 - FontUtil.getStringWidth(category.toString()) / 2f), category.getY() + 1, 0xffffffff);

        // Draws the separator
        drawBox(category.getX() + 8, category.getY() + 16, 64, 1, 1, 255, 255, 255, 255);

        drawModules(category, mX, mY);
    }

    public void drawModules(Category category, int mX, int mY)
    {
        int H = 25;
        for (AbstractFeature feature : Restack.featureManager.getFeaturesFromCategory(category))
        {
            drawModule(category, feature, H, mX, mY);
            H += 20;
        }
    }

    public void drawModule(Category category, AbstractFeature feature, int H, int mX, int mY)
    {

        int color;
        if (feature.isEnabled())
        {
            color = 0xffffffff;
        }
        else
        {
            color = 0xff999999;
        }

        // Draw module Strings
        FontUtil.drawString(feature.getName(), (int) (category.getX() + 40 - FontUtil.getStringWidth(feature.getName()) / 2f), category.getY() + H, color);

        if (feature.isExpanded())
        {
            // Draw settings for module
            drawSettings(feature, category.getX() + 80 + 10, category.getY() + H, mX, mY);
        }
    }

    public void drawSettings(AbstractFeature feature, int X, int Y, int mX, int mY)
    {

        // Setting count + 1(bind)
        int i = feature.getSettings().size() + 1;

        // Draw setting box
        drawBox(X, Y, 100, i * 14, 5, 30, 30, 30, 255);

        int H = Y + 2;

        for (AbstractSetting setting : feature.getSettings())
        {
            // Draw each setting in module
            drawSetting(setting, X, H, mX, mY);
            H += 14;
        }

        if (!feature.isBinding())
        {
            String keyName;

            try
            {
                keyName = Keyboard.getKeyName(feature.getKeybind());
            }
            catch (Exception ignored)
            {
                keyName = "NONE";
            }
            // if not listening
            FontUtil.drawString("Bind", X + 2, H, 0xffffffff);
            FontUtil.drawString(keyName, X + 100 - FontUtil.getStringWidth(keyName) - 2, H, 0xffffffff);
        }
        else
        {
            // if Listening
            FontUtil.drawString("Key...", X + 2, H, 0xffffffff);
        }
    }

    public void drawSetting(AbstractSetting setting, int X, int Y, int mX, int mY)
    {
        String settingValue = "";

        if (setting instanceof BooleanSetting) settingValue = String.valueOf(((BooleanSetting) setting).getValue());
        if (setting instanceof FloatSetting) settingValue = String.valueOf(((FloatSetting) setting).getValue());
        if (setting instanceof EnumSetting) settingValue = String.valueOf(((EnumSetting<?>) setting).getValue());

        // Draw setting and value
        FontUtil.drawString(capFirstLetter(setting.getName()), X + 2, Y, 0xffffffff);
        FontUtil.drawString(capFirstLetter(settingValue), X + 100 - FontUtil.getStringWidth(settingValue) - 2, Y, 0xffffffff);

        //Draw bar for float settings
        if(setting instanceof  FloatSetting){
            FloatSetting floatSetting = (FloatSetting) setting;
            float range = floatSetting.getMax() - floatSetting.getMin();
            float perc = ((floatSetting.getValue() - floatSetting.getMin()) / range)*100;
            drawBox(X, Y+FontUtil.getFontHeight(), (int) perc, 1, 0, 255,255,255,255);



            final double diff = Math.min(draggingSettingW, Math.max(0, mX - draggingSettingX));
            final double min = ((FloatSetting) setting).getMin();
            final double max = ((FloatSetting) setting).getMax();
            int sliderWidth = (int) ((draggingSettingW - 6) * (((FloatSetting) setting).getValue() - min) / (max - min));
            if (draggingSetting == setting)
            {
                if (diff == 0.0)
                {
                    ((FloatSetting) setting).setValue(((FloatSetting) setting).getMin());
                }
                else
                {
                    double newValue = diff / draggingSettingW * (max - min) + min;
                    ((FloatSetting) setting).setValue((float) Math.round(newValue*10)*0.1);
                }
            }
        }


    }

    public void processClick(int mX, int mY, int mB)
    {
        for (Category category : Category.values())
        {
            if (isHover(category.getX(), category.getY(), 80, 16, mX, mY))
            {
                draggingCategory = category;
                D = true;
                xB = mX - category.getX();
                yB = mY - category.getY();
            }
            int H = 25;
            for (AbstractFeature feature : Restack.featureManager.getFeaturesFromCategory(category))
            {
                if (isHover(category.getX() + 5, category.getY() + H - 5, 75, FontUtil.getFontHeight() + 5, mX, mY))
                {
                    switch (mB)
                    {
                        case 0:
                            feature.toggle();
                            break;
                        case 1:
                            for (AbstractFeature feature1 : Restack.featureManager.getFeaturesFromCategory(feature.getCategory()))
                            {
                                if (!feature1.equals(feature)) feature1.setExpanded(false);
                            }
                            feature.setExpanded(!feature.isExpanded());
                            break;
                        default:
                            break;
                    }
                }

                if (feature.isExpanded())
                {
                    int sX = category.getX() + 80 + 14;
                    int sY = category.getY() + H;
                    for (AbstractSetting setting : feature.getSettings())
                    {
                        if (isHover(sX, sY, 100, FontUtil.getFontHeight(), mX, mY))
                        {
                            processClickSetting(setting, mX, mY, sX, sY - 2, 102, FontUtil.getFontHeight() + 4, mB);
                        }
                        sY += 14;
                    }

                    if (isHover(sX, sY, 100, FontUtil.getFontHeight(), mX, mY))
                    {
                        if (!feature.isBinding()) feature.setBinding(true);
                        B = true;
                    }
                }
                H += 20;
            }
        }
    }

    public void processClickSetting(AbstractSetting setting, int mX, int mY, int X, int Y, int W, int H, int mB)
    {
        if (setting instanceof BooleanSetting)
        {
            ((BooleanSetting) setting).setValue(!((BooleanSetting) setting).getValue());
            return;
        }

        if(setting instanceof FloatSetting)
        {

            if(isHover(X,Y,W,H,mX,mY)){
                draggingSetting = (FloatSetting) setting;
                draggingSettingX = X;
                draggingSettingW = W;
                draggingSettingH = H;
                draggingSettingY = Y;
            }


        }

        if (setting instanceof EnumSetting)
        {
            processEnumClick((EnumSetting) setting, mB);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        draggingCategory = null;
        draggingSetting = null;
        D = false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (!B && keyCode == Keyboard.KEY_ESCAPE)
        {
            mc.player.closeScreen();
            return;
        }
        for (AbstractFeature feature : Restack.featureManager.getFeatures())
        {
            if (feature.isBinding())
            {
                feature.setBinding(false);
                B = false;

                if (keyCode == Keyboard.KEY_ESCAPE)
                {
                    feature.setKeybind(Keyboard.KEYBOARD_SIZE);
                }
                else
                {
                    feature.setKeybind(keyCode);
                }
            }
        }
    }

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


    public static void processEnumClick(EnumSetting setting, int mB)
    {
        if (mB == 0)
        {
            setting.setValue(setting.increment());
        }
        if (mB == 1)
        {
            setting.setValue(setting.decrement());
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }


}
