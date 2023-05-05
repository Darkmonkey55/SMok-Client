package net.minecraft.client.me.sleepyfish.smok.rats.impl.useless;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;

// Class from SMok Client by SleepyFish
public class Refill extends Rat {

    SlideSetting normalDelay;

    BoolSetting randomDelay;
    SlideSetting delayRandomMin;
    SlideSetting delayRandomMax;

    BoolSetting invOnly;

    public Refill() {
        super(Var.refill_name, Category.Useless, Var.refill_desc);
    }

    @Override
    public void setup() {
        this.addSetting(normalDelay = new SlideSetting(Var.refill_normalDelay, 30, 30, 1000, 10));
        this.addSetting(randomDelay = new BoolSetting(Var.refill_randomDelay, true));
        this.addSetting(delayRandomMin = new SlideSetting(Var.refill_delayRandomMin, 30, 30, 1000, 10));
        this.addSetting(delayRandomMax = new SlideSetting(Var.refill_delayRandomMax, 50, 50, 1200, 10));
        this.addSetting(invOnly = new BoolSetting(Var.refill_invOnly, true));
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (invOnly.isToggled())
            if (!Utils.inInv())
                return;

        for (Slot slot : mc.thePlayer.openContainer.inventorySlots)
            if (slot.slotNumber > 9) {
                if (slot != null)
                    if (slot.getStack() != null) {
                        boolean delay;

                        if (randomDelay.isToggled())
                            delay = Timer.hasTimeElapsed(MathUtils.randomLong(delayRandomMin.getValueToLong() * 5L, delayRandomMax.getValueToLong() * 5L), true);
                        else
                            delay = Timer.hasTimeElapsed(normalDelay.getValueToLong() * 5L, true);

                        if (delay)
                            if (slot.slotNumber > 9)
                                if (slot.getStack().getItem() instanceof ItemPotion)
                                    Utils.shiftClick(slot.slotNumber);
                    }
            }
    }

}