package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.entity.Entity;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class FriendUtils {

    private static final ArrayList<Entity> friends = new ArrayList<>();

    public static ArrayList<Entity> getFriends() {
        return friends;
    }

    public static boolean isFriend(Entity friend) {
        return getFriends().contains(friend);
    }

    public static void addFriend(Entity friend) {
        getFriends().add(friend);
    }

    public static void removeFriend(Entity friend) {
        if (isFriend(friend))
            getFriends().remove(friend);
    }

}