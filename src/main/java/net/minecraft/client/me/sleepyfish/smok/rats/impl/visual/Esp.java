package net.minecraft.client.me.sleepyfish.smok.rats.impl.visual;

import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;

// Class from SMok Client by SleepyFish
public class Esp extends Rat {

    public static BoolSetting showHealth;
    public static ModeSetting<Enum<?>> mode;

    // Mixin module
    public Esp() {
        super(Var.esp_name, Category.Visual, Var.esp_desc);
    }

    @Override
    public void setup() {
        this.addSetting(mode = new ModeSetting<>(Var.esp_mode, modes.Vape));
        this.addSetting(showHealth = new BoolSetting(Var.esp_health, true));
    }

    public enum modes {
        Weave, Capsule, Vape, Kioshii, Smok, FischKock;
    }

}