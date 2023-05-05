package net.minecraft.client.me.sleepyfish.smok.rats.impl.useless;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Gui;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.Notification;

// Class from SMok Client by SleepyFish
public class Spin extends Rat {

    SlideSetting speed;

    BoolSetting serverSided;
    BoolSetting reversedHead;

    private float yaw;

    public Spin() {
        super(Var.spin_name, Category.Useless, Var.spin_desc);
    }

    @Override
    public void setup() {
        this.addSetting(speed = new SlideSetting(Var.spin_speed, 5, 1, 100, 1));
        this.addSetting(serverSided = new BoolSetting(Var.spin_client, false));
        this.addSetting(reversedHead = new BoolSetting(Var.spin_head, true));
        this.yaw = 0;
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (serverSided.isToggled()) {
            if (!Gui.blatantMode.isToggled()) {
                serverSided.toggle();

                if (Gui.moduleNotify.isToggled())
                    ClientUtils.notify(Var.spin_name, "U cant enable this setting without blatant mode", Notification.Icon.Info, 3);
            }

            Smok.inst.rotManager.setRotations(this.yaw, mc.thePlayer.rotationPitch);
            Smok.inst.rotManager.setRotating(true);
        } else {
            Smok.inst.rotManager.setRotating(false);

            if (reversedHead.isToggled())
                mc.thePlayer.rotationYawHead = -this.yaw;
            else
                mc.thePlayer.rotationYawHead = this.yaw;

            mc.thePlayer.renderYawOffset = this.yaw;
        }

        this.yaw += speed.getValueToFloat();
    }

    @Override
    public void onEnableEvent() {
        this.yaw = 0;

        if (!serverSided.isToggled())
            Smok.inst.rotManager.setRotating(false);
    }

    @Override
    public void onDisableEvent() {
        this.yaw = 0;

        if (!serverSided.isToggled())
            Smok.inst.rotManager.setRotating(false);
    }

}