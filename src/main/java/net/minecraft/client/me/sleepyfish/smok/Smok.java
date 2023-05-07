package net.minecraft.client.me.sleepyfish.smok;

import net.minecraft.client.me.sleepyfish.smok.gui.GuiManager;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.RotateUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.GooberUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.rats.event.EventManager;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

// Class from SMok Client by SleepyFish
public class Smok {

    public final static Smok inst = new Smok();

    public final boolean debugMode = false;

    public double serverVersion;
    public String oldTitle;

    public Minecraft mc;

    public Rat.RatManager ratManager;
    public GuiManager guiManager;
    public ColorManager colManager;
    public EventManager eveManager;
    public RotateUtils rotManager;
    public GooberUtils gooberUtils;

    public void juue_tea_and_tha_bri_ischhh_init() {
        ClientUtils.updateClientVersion();

        if (ClientUtils.getDoubleFromUrl(Rat.Var.client_version_server) > Rat.Var.client_version_client)
            ClientUtils.browse(Rat.Var.client_github + "/blob/master/Outdatedt-Text.txt");

        mc = Minecraft.getMinecraft();

        gooberUtils = new GooberUtils();
        ratManager = new Rat.RatManager();
        guiManager = new GuiManager();
        colManager = new ColorManager();
        eveManager = new EventManager();
        rotManager = new RotateUtils();

        FontUtils.init();
    }

    // Goofy code
    public int getBind(int gui) {
        if (gui == 1)
            return Keyboard.KEY_DELETE;

        if (gui == 1 + 1)
            return Keyboard.KEY_RSHIFT;

        if (gui == 1 + 1 + 1)
            return Keyboard.KEY_BACK;

        return 0;
    }

    public void saveAndSetTitle() {
        Smok.inst.oldTitle = Display.getTitle();
        Display.setTitle(Smok.inst.oldTitle + " - " + Smok.inst.getClientName() + " Injecting...");
    }

    public String getClientName() {
        return Rat.Var.client_name;
    }

    public String getDiscord() {
        return Rat.Var.client_discord;
    }

    public double getClientVersion() {
        return Rat.Var.client_version_client;
    }

    public double getServerVersion() {
        return serverVersion;
    }

}