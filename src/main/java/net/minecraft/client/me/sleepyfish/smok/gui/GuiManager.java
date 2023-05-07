package net.minecraft.client.me.sleepyfish.smok.gui;

import net.minecraft.client.me.sleepyfish.smok.gui.guis.ClickGui;
import net.minecraft.client.me.sleepyfish.smok.gui.guis.FriendsGui;

// Class from SMok Client by SleepyFish
public class GuiManager {

    private final ClickGui gui;
    private final FriendsGui friends;

    public GuiManager() {
        gui = new ClickGui();
        friends = new FriendsGui();
    }

    public ClickGui getClickGui() {
        return gui;
    }

    public FriendsGui getFriendsGui() {
        return friends;
    }

}