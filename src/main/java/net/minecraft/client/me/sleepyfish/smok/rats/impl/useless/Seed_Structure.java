package net.minecraft.client.me.sleepyfish.smok.rats.impl.useless;

import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;

// Class from SMok Client by SleepyFish
public class Seed_Structure extends Rat {

    ModeSetting<Enum<?>> mode;

    public Seed_Structure() {
        super(Var.seed_structure_name, Category.Useless, Var.seed_structure_desc);
    }

    @Override
    public void setup() {
        this.addSetting(mode = new ModeSetting<>("Mode", modes.Mineshaft));
    }

    @Override
    public void onEnableEvent() {
        String middleUrl = "?";

        if (mode.getMode() == modes.NetherFortress)
            middleUrl = "nether-fortress";

        if (mode.getMode() == modes.JungleTemple)
            middleUrl = "jungle-temple";

        if (mode.getMode() == modes.DesertTemple)
            middleUrl = "desert-temple";

        if (mode.getMode() == modes.Slime)
            middleUrl = "slime";

        if (mode.getMode() == modes.Village)
            middleUrl = "village";

        if (mode.getMode() == modes.Igloo)
            middleUrl = "igloo";

        if (mode.getMode() == modes.Mineshaft)
            middleUrl = "mineshaft";

        if (middleUrl == "?")
            return;

        String txt;

        try {
            String url = "https://www.chunkbase.com/apps/" + middleUrl + "-finder#";
            txt = url + ClientUtils.getSeed();
            ClientUtils.addMessage("Registered Seed.");
        } catch (Exception e) {
            txt = "Couldn't find seed.";
            ClientUtils.addMessage("Failed getting Seed.");
        }

        if (txt != "Couldn't find seed.") {
            ClientUtils.browse(txt);
            ClientUtils.addMessage("Opening URL that belongs to the Seed.");
        }

        this.toggle();
    }

    public enum modes {
        Mineshaft, NetherFortress, JungleTemple, DesertTemple, Slime, Village, Igloo;
    }

}