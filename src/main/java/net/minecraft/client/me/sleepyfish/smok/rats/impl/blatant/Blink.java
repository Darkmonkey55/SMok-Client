package net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventSendPacketPre;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.event.SmokEvent;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SpaceSetting;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class Blink extends Rat {

    BoolSetting cancelAllPackets;
    BoolSetting cancelBlockPackets;
    BoolSetting sendPacketsOnDisable;
    BoolSetting spawnNpc;

    BoolSetting pulse;
    SlideSetting pulseDelay;

    private boolean allowSpawn;
    private boolean allowListen;
    private ArrayList<Packet<?>> packets;

    private Timer.Better pulseTimer;

    public Blink() {
        super(Var.blink_name, Category.Blatant, Var.blink_desc);
    }

    @Override
    public void setup() {
        this.addSetting(cancelAllPackets = new BoolSetting(Var.blink_cancelAllPackets, false));
        this.addSetting(cancelBlockPackets = new BoolSetting(Var.blink_cancelBlockPackets, false));
        this.addSetting(sendPacketsOnDisable = new BoolSetting(Var.blink_sendPacketsOnDisable, true));
        this.addSetting(spawnNpc = new BoolSetting(Var.blink_spawnNpc, true));
        this.addSetting(new SpaceSetting());
        this.addSetting(pulse = new BoolSetting(Var.blink_pulse, false));
        this.addSetting(pulseDelay = new SlideSetting(Var.blink_pulseDelay, 650, 50, 2500, 50));

        this.allowSpawn = false;
        this.allowListen = false;
        this.packets = new ArrayList<>();

        this.pulseTimer = new Timer.Better();
    }

    @Override
    public void onEnableEvent() {
        this.packets.clear();
        this.allowSpawn = true;
        this.allowListen = true;

        if (this.spawnNpc.isEnabled())
            Utils.Npc.spawn();
    }

    @Override
    public void onDisableEvent() {
        this.allowSpawn = true;
        this.allowListen = false;

        if (this.spawnNpc.isEnabled())
            Utils.Npc.kill();

        if (this.sendPacketsOnDisable.isEnabled())
            if (!this.pulse.isEnabled()) {
                for (Packet<?> p : this.packets)
                    mc.thePlayer.sendQueue.addToSendQueue(p);

                this.packets.clear();
            }
    }

    @SmokEvent
    public void onPacket(EventSendPacketPre e) {
        if (Utils.canLegitWork()) {
            if (!spawnNpc.isEnabled()) {
                if (Utils.Npc.getNpc() != null) {
                    Utils.Npc.kill();
                }
            }

            Packet<?> packet = e.getPacket();

            if (!cancelAllPackets.isEnabled()) {
                if (cancelBlockPackets.isEnabled()) {
                    if (this.allowListen && packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C07PacketPlayerDigging) {
                        this.packets.add(packet);
                        e.setCancelled(true);

                        ClientUtils.addDebug("blink: added block and digging packet");
                    }
                }

                if (this.allowListen && packet instanceof C03PacketPlayer) {
                    this.packets.add(packet);
                    e.setCancelled(true);

                    ClientUtils.addDebug("blink: added player packet");
                }

                if (this.allowListen && packet instanceof S08PacketPlayerPosLook) {
                    this.packets.add(packet);
                    e.setCancelled(true);

                    ClientUtils.addDebug("blink: added server player pos look packet");
                }

            } else if (e.getPacket() != null) {
                if (this.allowListen) {
                    this.packets.add(packet);
                    e.setCancelled(true);

                    ClientUtils.addDebug("blink: added " + packet.toString() + " packet");
                }
            }

            if (pulse.isEnabled()) {
                if (this.allowListen) {
                    if (packet != null)
                        e.setCancelled(true);
                }

                if (pulseTimer.delay(pulseDelay.getValueToLong())) {
                    if (allowSpawn) {
                        this.onEnableEvent();
                        this.allowSpawn = false;
                    } else {
                        this.onDisableEvent();
                        this.allowSpawn = true;
                        this.pulseTimer.reset();
                    }
                }
            }
        }
    }

}