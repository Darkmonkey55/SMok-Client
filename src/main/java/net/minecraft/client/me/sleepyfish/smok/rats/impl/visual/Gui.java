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

    public static void setColorMode() {
        if (Gui.mode.getMode() == Gui.modes.BlueBerry)
            Smok.inst.colManager.setColorMode(1);

        if (Gui.mode.getMode() == Gui.modes.Fruit)
            Smok.inst.colManager.setColorMode(2);

        if (Gui.mode.getMode() == Gui.modes.Mint)
            Smok.inst.colManager.setColorMode(3);

        if (Gui.mode.getMode() == Gui.modes.Pink)
            Smok.inst.colManager.setColorMode(4);

        if (Gui.mode.getMode() == Gui.modes.Lavender)
            Smok.inst.colManager.setColorMode(5);

        if (Gui.mode.getMode() == Gui.modes.Grapefruit)
            Smok.inst.colManager.setColorMode(6);

        if (Gui.mode.getMode() == Gui.modes.Devil)
            Smok.inst.colManager.setColorMode(7);

        if (Gui.mode.getMode() == Gui.modes.Nightsky)
            Smok.inst.colManager.setColorMode(8);

        if (Gui.mode.getMode() == Gui.modes.Lemon)
            Smok.inst.colManager.setColorMode(9);

        if (Gui.mode.getMode() == Gui.modes.PurpleBlue)
            Smok.inst.colManager.setColorMode(10);

        if (Gui.mode.getMode() == Gui.modes.Orange)
            Smok.inst.colManager.setColorMode(11);

        if (Gui.mode.getMode() == Gui.modes.Tenacity)
            Smok.inst.colManager.setColorMode(12);

        if (Gui.mode.getMode() == Gui.modes.AMin)
            Smok.inst.colManager.setColorMode(13);

        if (Gui.mode.getMode() == Gui.modes.BlackWhite)
            Smok.inst.colManager.setColorMode(14);

        if (Gui.mode.getMode() == Gui.modes.Discord)
            Smok.inst.colManager.setColorMode(15);
    }

    public enum modes {
        BlueBerry, Fruit, Mint, Pink, Lavender, Grapefruit, Devil, Nightsky,
        Lemon, PurpleBlue, Orange, Tenacity, AMin, BlackWhite, Discord;
    }

}