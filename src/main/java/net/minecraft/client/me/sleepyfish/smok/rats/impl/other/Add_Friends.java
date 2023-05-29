package net.minecraft.client.me.sleepyfish.smok.rats.impl.other;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.*;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.Notification;
import net.minecraft.entity.Entity;

// Class from SMok Client by SleepyFish
public class Add_Friends extends Rat {

    Timer.Better timer = new Timer.Better();

    public Add_Friends() {
        super(Var.friends_add_name, Category.Other, Var.friends_add_desc);
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork())
            if (MouseUtils.isButtonDown(MouseUtils.MOUSE_MIDDLE)) {
                if (timer.delay(1000)) {
                    Entity target = mc.objectMouseOver.entityHit;

                    if (target != null) {
                        if (!FriendUtils.isFriend(target)) {
                            ClientUtils.notify(Var.friends_add_name, "Added Friend: " + target.getName(), Notification.Icon.Check, 1);
                            FriendUtils.addFriend(target);
                            timer.reset();
                        } else {
                            ClientUtils.notify(Var.friends_add_name, "Removed Friend: " + target.getName(), Notification.Icon.Bell, 1);
                            FriendUtils.removeFriend(target);
                            timer.reset();
                        }
                    } else {
                        ClientUtils.notify(Var.friends_add_name, "No Entity found", Notification.Icon.No, 1);
                        timer.reset();
                    }
                }
            }
    }

}