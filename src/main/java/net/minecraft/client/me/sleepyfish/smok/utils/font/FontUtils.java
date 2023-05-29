package net.minecraft.client.me.sleepyfish.smok.utils.font;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.Display;

import java.awt.Font;
import java.util.Map;
import java.awt.Desktop;
import java.util.HashMap;
import java.io.InputStream;

// Class from SMok Client by SleepyFish
public class FontUtils {

    public static MinecraftFontRenderer r12, r16, r20, r22, r24, rBold18, rBold20, rBold22, rBold26, rBold14, i18, i20, i24, i28;
    private static Font r12_, r16_, r20_, r24_, r22_, rBold18_, rBold20_, rBold22_, rBold26_, rBold14_, i18_, i20_, i24_, i28_;

    private static int prevScale;

    public static void init() {
        Map<String, Font> locationMap = new HashMap<>();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        int scale = sr.getScaleFactor();

        if (scale != prevScale) {
            prevScale = scale;

            FontUtils.r12_ = FontUtils.getFont(locationMap, "regular.ttf", 12);
            FontUtils.r12 = new MinecraftFontRenderer(FontUtils.r12_);
            FontUtils.r16_ = FontUtils.getFont(locationMap, "regular.ttf", 16);
            FontUtils.r16 = new MinecraftFontRenderer(FontUtils.r16_);
            FontUtils.r20_ = FontUtils.getFont(locationMap, "regular.ttf", 20);
            FontUtils.r20 = new MinecraftFontRenderer(FontUtils.r20_);
            FontUtils.r22_ = FontUtils.getFont(locationMap, "regular.ttf", 22);
            FontUtils.r22 = new MinecraftFontRenderer(FontUtils.r22_);
            FontUtils.r24_ = FontUtils.getFont(locationMap, "regular.ttf", 24);
            FontUtils.r24 = new MinecraftFontRenderer(FontUtils.r24_);
            FontUtils.rBold18_ = FontUtils.getFont(locationMap, "regular_bold.ttf", 18);
            FontUtils.rBold18 = new MinecraftFontRenderer(FontUtils.rBold18_);
            FontUtils.rBold20_ = FontUtils.getFont(locationMap, "regular_bold.ttf", 20);
            FontUtils.rBold20 = new MinecraftFontRenderer(FontUtils.rBold20_);
            FontUtils.rBold22_ = FontUtils.getFont(locationMap, "regular_bold.ttf", 22);
            FontUtils.rBold22 = new MinecraftFontRenderer(FontUtils.rBold22_);
            FontUtils.rBold26_ = FontUtils.getFont(locationMap, "regular_bold.ttf", 26);
            FontUtils.rBold26 = new MinecraftFontRenderer(FontUtils.rBold26_);
            FontUtils.rBold14_ = FontUtils.getFont(locationMap, "regular_bold.ttf", 14);
            FontUtils.rBold14 = new MinecraftFontRenderer(FontUtils.rBold14_);

            FontUtils.i18_ = FontUtils.getFont(locationMap, "icon.ttf", 18);
            FontUtils.i18 = new MinecraftFontRenderer(FontUtils.i18_);
            FontUtils.i20_ = FontUtils.getFont(locationMap, "icon.ttf", 20);
            FontUtils.i20 = new MinecraftFontRenderer(FontUtils.i20_);
            FontUtils.i24_ = FontUtils.getFont(locationMap, "icon.ttf", 24);
            FontUtils.i24 = new MinecraftFontRenderer(FontUtils.i24_);
            FontUtils.i28_ = FontUtils.getFont(locationMap, "icon.ttf", 28);
            FontUtils.i28 = new MinecraftFontRenderer(FontUtils.i28_);

            if (!Smok.inst.debugMode)
                Display.setTitle(Smok.inst.oldTitle);
            else
                Display.setTitle(Smok.inst.oldTitle + " - [Smok]: Debug Mode");
        }
    }

    public static Font getFont(Map<String, Font> locationMap, String location, float size) {
        Font font;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        size = size * ((float) sr.getScaleFactor() / 2);

        try {
            if (locationMap.containsKey(location))
                font = locationMap.get(location).deriveFont(Font.PLAIN, size);
            else {
                InputStream is = Minecraft.getMinecraft().getResourceManager()
                        .getResource(new ResourceLocation(FileUtils.path + "/fonts/" + location)).getInputStream();
                locationMap.put(location, font = Font.createFont(0, is));
                font = font.deriveFont(Font.PLAIN, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
            font = new Font("default", Font.PLAIN, +10);
        }

        return font;
    }

}