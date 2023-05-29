package net.minecraft.client.me.sleepyfish.smok.rats.impl.legit;

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
        super(Var.refill_name, Category.Legit, Var.refill_desc);
    }

    @Override
    public void setup() {
        this.addSetting(normalDelay = new SlideSetting(Var.refill_normalDelay, 30, 30, 300, 10));
        this.addSetting(randomDelay = new BoolSetting(Var.refill_randomDelay, true));
        this.addSetting(delayRandomMin = new SlideSetting(Var.refill_delayRandomMin, 30, 30, 300, 10));
        this.addSetting(delayRandomMax = new SlideSetting(Var.refill_delayRandomMax, 50, 50, 350, 10));
        this.addSetting(invOnly = new BoolSetting(Var.refill_invOnly, true));
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (invOnly.isEnabled())
            if (!Utils.inInv())
                return;

        for (Slot slot : mc.thePlayer.openContainer.inventorySlots)
            if (slot != null)
                if (slot.getStack() != null) {
                    long delay;

                    if (randomDelay.isEnabled())
                        delay = MathUtils.randomLong(delayRandomMin.getValueToLong() * 5L, delayRandomMax.getValueToLong() * 5L);
                    else
                        delay = normalDelay.getValueToLong() * 5L;

                    if (Timer.hasTimeElapsed(delay, true))
                        if (slot.slotNumber < 36)
                            if (slot.getStack().getItem() instanceof ItemPotion)
                                Utils.shiftClick(slot.slotNumber);
                }
    }

}