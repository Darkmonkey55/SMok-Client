package net.minecraft.client.me.sleepyfish.smok.rats.impl.visual;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;

// Class from SMok Client by SleepyFish
public class Friends_Gui extends Rat {

    public Friends_Gui() {
        super(Var.friendsgui_name, Category.Visual, Var.friendsgui_desc);
    }

    @Override
    public void setup() {
        this.setBind(ClientUtils.getBind(3));
    }

    @Override
    public void onEnableEvent() {
        mc.displayGuiScreen(Smok.inst.guiManager.getFriendsGui());
        this.toggle();
    }

}