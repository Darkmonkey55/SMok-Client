package net.minecraft.client.me.sleepyfish.smok.rats.impl.other;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant.Aura;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant.Blink;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant.Bunny_Hop;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant.Scaffold;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.useless.Auto_Chat;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.useless.Spin;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.*;

// Class from SMok Client by SleepyFish
public class Legit_Screen extends Rat {

    private boolean[] enabled = {};

    public Legit_Screen() {
        super(Var.legit_screen_name, Category.Other, Var.legit_screen_desc);
    }

    @Override
    public void onEnableEvent() {
        this.enabled = new boolean[] {
                Smok.inst.ratManager.getBigRatByClass(Aura.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Aura.class).isEnabled() && Aura.esp.isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Chams.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Target_Hud.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Esp.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(No_Background.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Text_Gui.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Auto_Chat.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Spin.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Add_Friends.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Detector.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Blink.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Bunny_Hop.class).isEnabled(),
                Smok.inst.ratManager.getBigRatByClass(Scaffold.class).isEnabled(),
                Gui.blatantMode.isEnabled()
        };

        if (enabled[0])
            Smok.inst.ratManager.getBigRatByClass(Aura.class).toggle();

        if (enabled[1])
            Aura.esp.toggle();

        if (enabled[2])
            Smok.inst.ratManager.getBigRatByClass(Chams.class).toggle();

        if (enabled[3])
            Smok.inst.ratManager.getBigRatByClass(Target_Hud.class).toggle();

        if (enabled[4])
            Smok.inst.ratManager.getBigRatByClass(Esp.class).toggle();

        if (enabled[5])
            Smok.inst.ratManager.getBigRatByClass(No_Background.class).toggle();

        if (enabled[6])
            Smok.inst.ratManager.getBigRatByClass(Text_Gui.class).toggle();

        if (enabled[7])
            Smok.inst.ratManager.getBigRatByClass(Auto_Chat.class).toggle();

        if (enabled[8])
            Smok.inst.ratManager.getBigRatByClass(Spin.class).toggle();

        if (enabled[9])
            Smok.inst.ratManager.getBigRatByClass(Add_Friends.class).toggle();

        if (enabled[10])
            Smok.inst.ratManager.getBigRatByClass(Detector.class).toggle();

        if (enabled[11])
            Smok.inst.ratManager.getBigRatByClass(Blink.class).toggle();

        if (enabled[12])
            Smok.inst.ratManager.getBigRatByClass(Bunny_Hop.class).toggle();

        if (enabled[13])
            Smok.inst.ratManager.getBigRatByClass(Scaffold.class).toggle();

        if (enabled[14])
            Gui.blatantMode.toggle();
    }

    @Override
    public void onDisableEvent() {
        if (enabled[0])
            Smok.inst.ratManager.getBigRatByClass(Aura.class).toggle();

        if (enabled[1])
            Aura.esp.toggle();

        if (enabled[2])
            Smok.inst.ratManager.getBigRatByClass(Chams.class).toggle();

        if (enabled[3])
            Smok.inst.ratManager.getBigRatByClass(Target_Hud.class).toggle();

        if (enabled[4])
            Smok.inst.ratManager.getBigRatByClass(Esp.class).toggle();

        if (enabled[5])
            Smok.inst.ratManager.getBigRatByClass(No_Background.class).toggle();

        if (enabled[6])
            Smok.inst.ratManager.getBigRatByClass(Text_Gui.class).toggle();

        if (enabled[7])
            Smok.inst.ratManager.getBigRatByClass(Auto_Chat.class).toggle();

        if (enabled[8])
            Smok.inst.ratManager.getBigRatByClass(Spin.class).toggle();

        if (enabled[9])
            Smok.inst.ratManager.getBigRatByClass(Add_Friends.class).toggle();

        if (enabled[10])
            Smok.inst.ratManager.getBigRatByClass(Detector.class).toggle();

        if (enabled[11])
            Smok.inst.ratManager.getBigRatByClass(Blink.class).toggle();

        if (enabled[12])
            Smok.inst.ratManager.getBigRatByClass(Bunny_Hop.class).toggle();

        if (enabled[13])
            Smok.inst.ratManager.getBigRatByClass(Scaffold.class).toggle();

        if (enabled[14])
            Gui.blatantMode.toggle();
    }

}