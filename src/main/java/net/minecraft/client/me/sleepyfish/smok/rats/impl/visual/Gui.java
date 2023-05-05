package net.minecraft.client.me.sleepyfish.smok.rats.impl.visual;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;

// Class from SMok Client by SleepyFish
public class Gui extends Rat {

    public static BoolSetting darkMode;
    public static BoolSetting blatantMode;
    public static BoolSetting toggleNotify;
    public static BoolSetting moduleNotify;
    public static BoolSetting moduleSounds;

    public static ModeSetting<Enum<?>> mode;

    public Gui() {
        super(Var.gui_name, Category.Visual, Var.gui_desc);
    }

    @Override
    public void setup() {
        this.addSetting(blatantMode = new BoolSetting(Var.gui_blatant_mode, Smok.inst.debugMode));
        this.addSetting(darkMode = new BoolSetting(Var.gui_darkmode, true));
        this.addSetting(toggleNotify = new BoolSetting(Var.gui_toggle_notifications, true));
        this.addSetting(moduleNotify = new BoolSetting(Var.gui_module_notifications, true));
        this.addSetting(moduleSounds = new BoolSetting(Var.gui_module_sounds, true));
        this.addSetting(mode = new ModeSetting<>(Var.gui_color_mode, modes.PurpleBlue));
    }

    @Override
    public void onEnableEvent() {
        this.toggle();
    }

    public enum modes {
        Windows, Fruit, SpeaMint, GreenSpirit, RosyPink, Magenta, Amethyst,
        SunsetPink, BlazeOrange, Lemon, PinkBlood, NeonRed, DeepOcean, ChambrayBlue,
        MintBlue, PacificBlue, TropicalBlue, PurpleBlue, Melon, Orange, Pink, MintYellow,
        March7th, LightOrange, Tenacity, Purple, Love, Gray, Candy, Amin, Bmin, Metapolis,
        KyeMeh, Magic, Subline, RelaxingRed, Salphur, CinnaMint, BlackWhite, WaterMelon, Discord
    }

}