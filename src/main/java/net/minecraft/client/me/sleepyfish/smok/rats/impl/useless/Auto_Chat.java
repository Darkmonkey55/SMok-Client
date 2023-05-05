package net.minecraft.client.me.sleepyfish.smok.rats.impl.useless;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventTick;
import net.minecraft.client.club.maxstats.weaveyoutube.event.EventSendPacket;
import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.ModeSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SpaceSetting;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// Class from SMok Client by SleepyFish
public class Auto_Chat extends Rat {

    BoolSetting bypass;
    BoolSetting bypassText;
    BoolSetting randomMessage;
    BoolSetting blockIP;

    ModeSetting<Enum<?>> mode;

    String message = Var.missing_string;
    String ipMessage = Var.missing_string;

    public Auto_Chat() {
        super(Var.auto_chat_name, Category.Useless, Var.auto_chat_desc);
    }

    @Override
    public void setup() {
        this.addSetting(mode = new ModeSetting<>(Var.auto_chat_mode, modes.BestSong));
        this.addSetting(new SpaceSetting());
        this.addSetting(bypass = new BoolSetting(Var.auto_chat_bypass, true));
        this.addSetting(bypassText = new BoolSetting(Var.auto_chat_dumb, false));
        this.addSetting(randomMessage = new BoolSetting(Var.auto_chat_random, true));
        this.addSetting(blockIP = new BoolSetting(Var.auto_chat_random_block, true));
    }

    @Override
    public void onEnableEvent() {
        try {
            InputStream is = new URL(Var.auto_chat_url).openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            ipMessage = "Bro heres my ip: " + br.readLine() + "  dox me alr !";
            br.close();
        } catch (Exception ignored) {
            ipMessage = "failed to get the string... :(";
        }
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork()) {
            if (randomMessage.isToggled()) {
                // Goofy ahh code :)
                int smok = MathUtils.randomInt(1, modes.values().length); // 31

                if (smok == 1)
                    mode.setMode(modes.Advertising);

                if (smok == 2)
                    mode.setMode(modes.AmariOnTop);

                if (smok == 3)
                    mode.setMode(modes.FDPSkidded);

                if (smok == 4)
                    mode.setMode(modes.FDPFatDefaultPaste);

                if (smok == 5)
                    mode.setMode(modes.FDPIsARat);

                if (smok == 6)
                    mode.setMode(modes.StfuRyu);

                if (smok == 7)
                    mode.setMode(modes.RiseOnTop);

                if (smok == 8)
                    mode.setMode(modes.RiseSkidded);

                if (smok == 9)
                    mode.setMode(modes.VerusInstul1);

                if (smok == 10)
                    mode.setMode(modes.VerusInstul2);

                if (smok == 11)
                    mode.setMode(modes.VerusOnTop);

                if (smok == 12)
                    mode.setMode(modes.SmokOnTop);

                if (smok == 13)
                    mode.setMode(modes.SmokIsACat);

                if (smok == 14)
                    mode.setMode(modes.SmokIsARat);

                if (smok == 15)
                    mode.setMode(modes.BlowsyInstult1);

                if (smok == 16)
                    mode.setMode(modes.BlowsyInsult2);

                if (smok == 17)
                    mode.setMode(modes.DreamLooksLikeShit);

                if (smok == 18)
                    mode.setMode(modes.NWord);

                if (smok == 19)
                    mode.setMode(modes.WannaTakeAPic);

                if (smok == 20)
                    mode.setMode(modes.SkidWareOnTop);

                if (smok == 21)
                    mode.setMode(modes.WurstBestBaipai);

                if (smok == 22)
                    mode.setMode(modes.FuckADog);

                if (smok == 23)
                    mode.setMode(modes.DuckSenseSkidded);

                if (smok == 24)
                    mode.setMode(modes.BroDidntGotUpdate);

                if (smok == 25)
                    mode.setMode(modes.RandomInt);

                if (smok == 26)
                    mode.setMode(modes.SessionID);

                if (smok == 27)
                    mode.setMode(modes.ProbSkiddedFromTena);

                if (smok == 28)
                    mode.setMode(modes.BestSong);

                if (smok == 29)
                    if (!blockIP.isToggled())
                        mode.setMode(modes.IPLeak);

                if (smok == 30)
                    mode.setMode(modes.Modules);
            } else {
                if (mode.getMode() == modes.Advertising)
                    message = "Amari: discord/U764DR6rsY";

                if (mode.getMode() == modes.AmariOnTop)
                    message = "Amari On Top";

                if (mode.getMode() == modes.FDPSkidded)
                    message = "FDP is skidded...";

                if (mode.getMode() == modes.FDPFatDefaultPaste)
                    message = "FDP == Fat Default Paste";

                if (mode.getMode() == modes.FDPIsARat)
                    message = "FDP is a Rat";

                if (mode.getMode() == modes.StfuRyu)
                    message = "Stfu Ryu user";

                if (mode.getMode() == modes.RiseOnTop)
                    message = "Rise on Top";

                if (mode.getMode() == modes.RiseSkidded)
                    message = "Rise is skidded by Wurst";

                if (mode.getMode() == modes.VerusInstul1)
                    message = "Verus is good. its just not working";

                if (mode.getMode() == modes.VerusInstul2)
                    message = "Verus is good. it just doesnt have checks";

                if (mode.getMode() == modes.VerusOnTop)
                    message = "Verus is the best anticheat";

                if (mode.getMode() == modes.SmokOnTop)
                    message = "Smok On Top";

                if (mode.getMode() == modes.SmokIsACat)
                    message = "Smok is a cat";

                if (mode.getMode() == modes.SmokIsARat)
                    message = "Smok is a big rat";

                if (mode.getMode() == modes.BlowsyInstult1)
                    message = "Blowsy ? More like blow me";

                if (mode.getMode() == modes.BlowsyInsult2)
                    message = "Blowsy a clown";

                if (mode.getMode() == modes.DreamLooksLikeShit)
                    message = "Dream looks like shit";

                if (mode.getMode() == modes.NWord)
                    message = "I hate nibbas (imagine not hating em)";

                if (mode.getMode() == modes.WannaTakeAPic)
                    message = "I wanna take a pic with cardi b inside my sharigan (goofy ah)";

                if (mode.getMode() == modes.SkidWareOnTop)
                    message = "Skidware on top";

                if (mode.getMode() == modes.WurstBestBaipai)
                    message = "Wurst has the best baipai (like insane)";

                if (mode.getMode() == modes.FuckADog)
                    message = "Go ahead and fack a dog (makes fun trust me i tried it)";

                if (mode.getMode() == modes.DuckSenseSkidded)
                    message = "Ducksense is skidded (best client(not)) :)";

                if (mode.getMode() == modes.BroDidntGotUpdate)
                    message = "Bro didnt got the nibba update";

                if (mode.getMode() == modes.RandomInt)
                    message = "Here is a random integer: [" + MathUtils.randomInt(-999, 99999) + "]";

                if (mode.getMode() == modes.SessionID)
                    message = "my session id: " + mc.getSession().getSessionID() + " - Please heck me 11!!11!!1!";

                if (mode.getMode() == modes.ProbSkiddedFromTena)
                    message = "Ur client prob skidded Tenacity visuals";

                if (mode.getMode() == modes.BestSong)
                    message = "I saw ur code and just lol, u haded skidded nofall, u coded nothing at all, but now ur client is free ?";

                if (mode.getMode() == modes.IPLeak)
                    message = ipMessage;

                if (mode.getMode() == modes.Modules) {
                    message = "Enabled Modules: ";

                    for (Rat m : Smok.inst.ratManager.getBigRats())
                        if (m.isToggled() && !m.isHidden())
                            message += m.getName() + ", ";
                }
            }

            if (bypass.isToggled()) {
                if (!bypassText.isToggled())
                    message += " - [" + MathUtils.randomInt(-999, 99999) + "]";
                else
                    message += " - Easy baipai: [" + MathUtils.randomInt(-999, 99999) + "]";
            }

            if (Timer.hasTimeElapsed(3000L, true))
                mc.thePlayer.sendChatMessage(message);
        }
    }

    @SmokEvent
    public void onPacket(EventSendPacket e) {
        if (this.isToggled())
            if (Utils.canLegitWork())
                if (Timer.hasTimeElapsed(2800L, true))
                    if (mode.getMode() == modes.Packets)
                        message = "Packet sended: " + e.getPacket().toString().toLowerCase();
    }

    public enum modes {
        Advertising, AmariOnTop, FDPSkidded, FDPFatDefaultPaste,
        FDPIsARat, StfuRyu, RiseOnTop, RiseSkidded, VerusInstul1,
        VerusInstul2, VerusOnTop, SmokOnTop, SmokIsACat, SmokIsARat,
        BlowsyInstult1, BlowsyInsult2, DreamLooksLikeShit, NWord,
        WannaTakeAPic, SkidWareOnTop, WurstBestBaipai, FuckADog,
        DuckSenseSkidded, BroDidntGotUpdate, RandomInt, SessionID,
        ProbSkiddedFromTena, BestSong, IPLeak, Packets, Modules;
    }

}