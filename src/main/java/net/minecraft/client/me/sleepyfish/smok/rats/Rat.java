package net.minecraft.client.me.sleepyfish.smok.rats;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.SoundUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.legit.*;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.other.*;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.*;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant.*;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.useless.*;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SettingHelper;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.Notification;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.NotificationManager;
import net.minecraft.client.Minecraft;
import java.util.List;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class Rat {

    private final String skkmo;
    private final Category smmko;
    private final String skmok;
    private int bind;

    private boolean toggled;
    private boolean hiding;

    private final ArrayList<SettingHelper> ksmo;

    public Minecraft mc;

    public Rat(String skmok, Category skkmo, String smmko) {
        this.skkmo = smmko;
        this.smmko = skkmo;
        this.skmok = skmok;

        this.ksmo = new ArrayList<>();

        this.bind = 0;
        this.hiding = false;
        this.toggled = false;

        this.mc = Minecraft.getMinecraft();
        this.setup();
    }

    public void onEnable() {
        if (!this.isHidden())
            if (this.getCategory() == Category.Blatant)
                if (!Gui.blatantMode.isToggled()) {
                    ClientUtils.notify("Error!", "Blatant Mode is not enabled", Notification.Icon.Warning, 3);
                    return;
                }

        this.toggled = true;
        Smok.inst.eveManager.register(this);
        
        if (!this.isHidden()) {
            if (!this.getName().toLowerCase().endsWith("gui")) {
                if (Gui.moduleSounds.isToggled())
                    SoundUtils.playSound(SoundUtils.click, 1F, 0.8F);

                if (Gui.toggleNotify.isToggled())
                    ClientUtils.notify(getName(), "Enabled", Notification.Icon.Check, 1);
            }
        }

        this.onEnableEvent();
    }

    public void onDisable() {
        this.toggled = false;
        Smok.inst.eveManager.unregister(this);

        if (!this.isHidden()) {
            if (!this.getName().toLowerCase().endsWith("gui")) {
                if (Gui.moduleSounds.isToggled())
                    SoundUtils.playSound(SoundUtils.click, 1F, 0.7F);

                if (Gui.toggleNotify.isToggled())
                    if (!this.isHidden())
                        if (NotificationManager.pending.isEmpty())
                            ClientUtils.notify(getName(), "Disabled", Notification.Icon.No, 1);
            }
        }

        this.onDisableEvent();
    }

    public void setup() {
        // addSettings here
    }

    public void onEnableEvent() {
        // module utilities
    }

    public void onDisableEvent() {
        // module utilities
    }

    public void toggle() {
        if (this.toggled)
            this.onDisable();
        else
            this.onEnable();
    }

    public String getName() {
        return skmok;
    }

    public Category getCategory() {
        return this.smmko;
    }

    public String getDescription() {
        return this.skkmo;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setKeycode(int keycode) {
        this.bind = keycode;
    }

    public int getKeycode() {
        return bind;
    }

    public boolean isHidden() {
        return hiding;
    }

    public void setHidden(boolean hidden) {
        this.hiding = hidden;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public ArrayList<SettingHelper> getSettings() {
        return this.ksmo;
    }

    public void addSetting(SettingHelper Setting) {
        getSettings().add(Setting);
    }

    public enum Category {
        Legit("Legit"), Blatant("Blatant"), Visual("Visual"), Other("Other"), Useless("Useless");

        public final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class RatManager {

        private final ArrayList<Rat> moks = new ArrayList<>();
        private int smok;

        public RatManager() {
            this.addBigRat(new Seed_Structure());
            this.addBigRat(new No_Background());
            this.addBigRat(new Add_Friends());
            this.addBigRat(new Head_Hitter());
            this.addBigRat(new Friends_Gui());
            this.addBigRat(new Target_Hud());
            this.addBigRat(new Animations());
            this.addBigRat(new Legit_Mode());
            this.addBigRat(new Bunny_Hop());
            this.addBigRat(new Aimassist());
            this.addBigRat(new Hit_Delay());
            this.addBigRat(new Auto_Chat());
            this.addBigRat(new FPS_Boost());
            this.addBigRat(new Scaffold());
            this.addBigRat(new Detector());
            //this.addBigRat(new Nametags());
            this.addBigRat(new Text_Gui());
            this.addBigRat(new Parkour());
            this.addBigRat(new Clicker());
            this.addBigRat(new No_Slow());
            this.addBigRat(new Refill());
            this.addBigRat(new Chams());
            this.addBigRat(new Eagle());
            this.addBigRat(new Blink());
            this.addBigRat(new Spin());
            this.addBigRat(new Aura());
            this.addBigRat(new Esp());
            this.addBigRat(new Gui());
        }

        private void addBigRat(Rat smok) {
            this.moks.add(smok);
            this.smok++;
        }

        public ArrayList<Rat> getBigRats() {
            return this.moks;
        }

        public Rat getBigRatByClass(Class<?> rat) {
            return rat != null ? this.moks.stream().filter((mod) -> {
                return mod.getClass().equals(rat);
            }).findFirst().orElse(null) : null;
        }

        public List<Rat> getBigRatsInCategory(Category smok) {
            if (Smok.inst.ratManager != null) {
                ArrayList<Rat> rats = new ArrayList<>();

                for (Rat rat : this.moks)
                    if (rat.getCategory().equals(smok))
                        rats.add(rat);

                return rats;
            } else
                return null;
        }

    }

    public static class Var {

        /*  ---------------------------------------- *\
        /
        /   Info from Sleepy:
        /   this code is made with much goofy ness
        /   and i know its useless but i did it
        /   anyway to waste my time and urs :)
        /
        /   ----------------------------------------  |
        /
        /   i also made this whole goofy code
        /   to see if someone makes fun of it
        /   because its not that stupid to do
        /   it like this if u have to much time
        /   i just wanna spend my useless lifetime
        /   on this cause this makes more fun then
        /   talking to random pples (tho im almost
        /   everytime online on discord to stream in
        /   my discord channel to talk to random
        /   people because else its to boring,
        /   anyways here are some infos about me
        /   that no one wants to know about:
        /   i am almost 16 not 13 like my youtube
        /   sais because youtube is to retarded
        /   to update a youtube profile
        /   proof here: https://youtu.be/qD_mW4zYI7Y
        /   as u can clearly see for some pples it
        /   updated.
        /
        /   ----------------------------------------  |
        /
        /   what i took inspiration of / modified :
        /   - this client is not skidded from vape and i dont even have the vape source and i dont want it dont send it to me
        /   - visual functions from soar v4.1 (fixed some gl errors)
        /   - clickgui code from raven n+ lite (i modified it to make it better)
        /   - ip grabber method from a random website (auto chat)
        /   - mixin entityplayersp from minecraft source code (to modify server rotations)
        /   - weave esp from ? who thought it: weave
        /   - aimassist from raven n+ lite
        /   - bhop code from raven n+ lite
        /   - RotateUtils.smoothRotation from Smellon
        /   - RotateUtils.getSensitivity from Smellon
        /   -
        /   -
        /
        /*  ---------------------------------------- */

        // Client
        public final static double client_version_client = 1.3;
        public final static String client_version_server = "https://raw.githubusercontent.com/SleepyFishYT/SMok-Client/master/Newest-Version.txt";

        public final static String client_name = "Smok";
        public final static String client_author = "SleepyFish";
        public final static String client_discord = "https://discord.gg/qpbTZKwG2s";
        public final static String client_github = "https://github.com/SleepyFishYT/SMok-Client";

        // Version Check
        public final static String missing_string = "missing_string";

        // Normal strings
        public final static String rat = "rat";
        public final static String nothing = "";
        public final static String value = "Value";
        public final static String checks = "Checks";

        // Add Friends
        public final static String friends_add_ver = "1.2";
        public final static String friends_add_name = "Add Friends";
        public final static String friends_add_desc = "Add and Remove friends with Middle Click";

        // Head Hitter
        public final static String head_hitter_ver = "1.2";
        public final static String head_hitter_name = "Head Hitter";
        public final static String head_hitter_desc = "Jumps when a block is above u";
        public final static String head_hitter_move_only = "Only Moving";
        public final static String head_hitter_delay = "Delay";

        // Target Hud
        public final static String target_hud_ver = "1.5";
        public final static String target_hud_name = "Target Hud";
        public final static String target_hud_desc = "Draws info about your Target";

        // Bunny hop
        public final static String bunny_hop_ver = "1.3";
        public final static String bunny_hop_name = "Bunny Hop";
        public final static String bunny_hop_desc = "Jump like a autistic retard";
        public final static String bunny_hop_speed = "Speed";
        public final static String bunny_hop_mode = "Mode";

        // Auto Chat
        public final static String auto_chat_ver = "1.6";
        public final static String auto_chat_name = "Auto Chat";
        public final static String auto_chat_desc = "Spam chat";
        public final static String auto_chat_mode = "Mode";
        public final static String auto_chat_bypass = "Bypass";
        public final static String auto_chat_dumb = "Dumb Bypass text";
        public final static String auto_chat_random = "Random";
        public final static String auto_chat_random_block = "Block get IP from Random";
        public final static String auto_chat_url = "http://checkip.amazonaws.com";

        // Aimassist
        public final static String aimassist_ver = "1.1";
        public final static String aimassist_name = "Aimassist";
        public final static String aimassist_desc = "Aims at players";
        public final static String aimassist_speed1 = "Speed 1";
        public final static String aimassist_speed2 = "Speed 2";
        public final static String aimassist_fov = "Fov";
        public final static String aimassist_range = "Range";
        public final static String aimassist_weapon_only = "Weapon Only";
        public final static String aimassist_aimlock = "Aimlock";
        public final static String aimassist_aimlock_silent = "Aimlock Silent";
        public final static String aimassist_ignore_friends = "Ignore Friends";

        // Hit Delay
        public final static String hit_delay_ver = "1.0";
        public final static String hit_delay_name = "Hit Delay";
        public final static String hit_delay_desc = "Fix the hit delay when not hitting";

        // Arraylist
        public final static String arraylist_ver = "1.3";
        public final static String arraylist_name = "Text Gui";
        public final static String arraylist_desc = "A list of enabled modules";
        public final static String arraylist_randomColor = "Random Color";
        public final static String arraylist_visualModules = "Visual Modules";

        // Parkour
        public final static String parkour_ver = "1.1";
        public final static String parkour_name = "Parkour";
        public final static String parkour_desc = "Jump at the end of a block";
        public final static String parkour_move_only = "Only Moving";

        // Clicker
        public final static String clicker_ver = "1.8";
        public final static String clicker_name = "Clicker";
        public final static String clicker_desc = "Clicks for you";
        public final static String clicker_right = "Right";
        public final static String clicker_rightCpsMin = "Right Cps Min";
        public final static String clicker_rightCpsMax = "Right Cps Max";
        public final static String clicker_onlyWhileLooking = "Only while Looking";
        public final static String clicker_onlyWhileHoldingBlock = "Only Block in hand";
        public final static String clicker_left = "Left";
        public final static String clicker_leftCpsMax = "Left Cps Min";
        public final static String clicker_leftCpsMin = "Left Cps Max";
        public final static String clicker_onlyWhileTargeting = "Only while Targeting";
        public final static String clicker_breakBlocks = "Break Blocks";
        public final static String clicker_onlyWeapon = "Only Weapon";
        public final static String clicker_hitselect = "Hit Select";

        // Eagle
        public final static String eagle_ver = "1.8";
        public final static String eagle_name = "Eagle";
        public final static String eagle_desc = "Sneaks on the end of blocks";
        public final static String eagle_blocksOnly = "Blocks Only";
        public final static String eagle_backwardsOnly = "Backwards Only";
        public final static String eagle_fastMode = "Fast Mode";
        public final static String eagle_fixForward = "Fix Forward";
        public final static String eagle_onlyFixWithoutBlocks = "Only Fix Without Blocks";
        public final static String eagle_fixWalkingTime = "Fix Walking Time";
        public final static String eagle_blockChecks = "Block " + Var.checks;

        // Blink
        public final static String blink_ver = "1.9";
        public final static String blink_name = "Blink";
        public final static String blink_desc = "Makes you lag server sided";
        public final static String blink_cancelAllPackets = "Cancel all packets";
        public final static String blink_cancelBlockPackets = "Cancel Block packets";
        public final static String blink_sendPacketsOnDisable = "Send Packets on disable";
        public final static String blink_spawnNpc = "Spawn Blink entity";
        public final static String blink_pulse = "Pulse";
        public final static String blink_pulseDelay = "Pulse Delay (MS)";

        // Aura
        public final static String aura_ver = "2.7";
        public final static String aura_name = "Aura";
        public final static String aura_desc = "Attacks Targets in a range";
        public final static String aura_attack_range = "Attack Range";
        public final static String aura_rotating_range = "Rotation Range";
        public final static String aura_cpsMin = "CPS Min";
        public final static String aura_cpsMax = "CPS Max";
        public final static String aura_onlyWeapon = "Only Weapon";
        public final static String aura_fov = "Fov";
        public final static String aura_fovRange = "Fov Range";
        public final static String aura_ignoreFriends = "Ignore Friends";
        public final static String aura_thruWalls = "Thru Walls";
        public final static String aura_blockBlocking = "Block Blocking";
        public final static String aura_esp = "Show Target";
        public final static String aura_mode = "Rotation Mode";
        public final static String aura_yawMin = "Random Yaw Min";
        public final static String aura_yawMax = "Random Yaw Max";

        // Refill
        public final static String refill_ver = "1.3";
        public final static String refill_name = "Refill";
        public final static String refill_desc = "Refills pots from Inventory";
        public final static String refill_normalDelay = "Normal Delay";
        public final static String refill_randomDelay = "Random Delay";
        public final static String refill_delayRandomMin = "Random Delay Min";
        public final static String refill_delayRandomMax = "Random Delay Max";
        public final static String refill_invOnly = "Inventory Only";

        // Scaffold
        public final static String scaffold_ver = "2.9";
        public final static String scaffold_name = "Scaffold";
        public final static String scaffold_desc = "Place Blocks under you";
        public final static String scaffold_delay = "Delay";
        public final static String scaffold_randomize = "Randomize Rotation";
        public final static String scaffold_tower = "Tower";
        public final static String scaffold_blockSprint = "Change Item";
        public final static String scaffold_changeItem = "Block Sprint";

        // Esp
        public final static String esp_ver = "1.5";
        public final static String esp_name = "Esp";
        public final static String esp_desc = "Entity Spy Proof";
        public final static String esp_mode = "Mode";
        public final static String esp_health = "Show Health";

        // Gui
        public final static String gui_ver = "1.7";
        public final static String gui_name = "Gui";
        public final static String gui_desc = "Change ur Gui Appearance";
        public final static String gui_blatant_mode = "Blatant Mode";
        public final static String gui_darkmode = "Dark mode";
        public final static String gui_toggle_notifications = "Toggle Notifications";
        public final static String gui_module_notifications = "Module Notifications";
        public final static String gui_module_sounds = "Module Sounds";
        public final static String gui_color_mode = "Color Mode";

        // Spin
        public final static String spin_ver = "1.4";
        public final static String spin_name = "Spin";
        public final static String spin_desc = "Most useless Module that ever exists";
        public final static String spin_speed = "Speed";
        public final static String spin_client = "Server Sided";
        public final static String spin_head = "Reversed Head";

        // NoSlow
        public final static String noslow_ver = "1.1";
        public final static String noslow_name = "No Slow";
        public final static String noslow_desc = "Slow on sword ? L then cuz this not gonna baipai";

        // Chams
        public final static String chams_ver = "1.0";
        public final static String chams_name = "Chams";
        public final static String chams_desc = "Render players thru walls";

        // Seed Structures
        public final static String seed_structure_ver = "1.2";
        public final static String seed_structure_name = "Seed Structure";
        public final static String seed_structure_desc = "Opens a URL that shows where are structures";

        // No Background
        public final static String no_background_ver = "1.0";
        public final static String no_background_name = "No Background";
        public final static String no_background_desc = "Removes the gui background";

        // Auto Bucket
        public final static String auto_bucket_ver = "1.0";
        public final static String auto_bucket_name = "Auto Bucket";
        public final static String auto_bucket_desc = "Auto MLG's when you have a water bucket";
        public final static String auto_bucket_rotate = "Rotate";
        public final static String auto_bucket_leave = "Auto Leave";

        // FPS Boost
        public final static String fpsBoost_ver = "1.0";
        public final static String fpsBoost_name = "Fps Boost";
        public final static String fpsBoost_Desc = "Boosts your FPS";

        // Friends Gui
        public final static String friendsgui_ver = "1.2";
        public final static String friendsgui_name = "Friengs Gui";
        public final static String friendsgui_desc = "Draws infos about ur friends";

        // Detector
        public final static String detector_ver = "1.7";
        public final static String detector_name = "Detector";
        public final static String detector_desc = "Client sided AC that is made by " + Var.client_author;
        public final static String detector_fly = "Fly " + Var.checks;
        public final static String detector_speed = "Speed " + Var.checks;
        public final static String detector_speed_value = "Speed "+ Var.value;
        public final static String detector_velocity = "Velocity " + Var.checks;
        public final static String detector_velocity_value = "Velocity "+ Var.value;
        public final static String detector_reach = "Reach " + Var.checks;
        public final static String detector_reach_value = "Reach "+ Var.value;
        public final static String detector_goober = "Goober Checks";
        public final static String detector_mode = "Message Mode";

        // Nametags
        public final static String nametags_ver = "1.2";
        public final static String nametags_name = "Nametags";
        public final static String nametags_desc = "Renders the players names";

        // Animations
        public final static String animations_ver = "1.3";
        public final static String animations_name = "Animations";
        public final static String animations_desc = "Modify ur First person Hand";
        public final static String animations_blockingAnimation = "Block Animations " + Var.value;
        public final static String animations_removeHand = "Remove Hand";

        // Legit Screen
        public final static String legit_screen_ver = "1.0";
        public final static String legit_screen_name = "Legit Screen";
        public final static String legit_screen_desc = "Makes your screen look legit";
    }

}