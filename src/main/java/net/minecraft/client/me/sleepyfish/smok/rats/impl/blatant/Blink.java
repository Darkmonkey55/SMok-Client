package net.minecraft.client.me.sleepyfish.smok.rats.impl.blatant;

import net.minecraft.client.club.maxstats.weaveyoutube.event.EventSendPacket;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Utils;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
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
        this.addSetting(pulseDelay = new SlideSetting(Var.blink_pulseDelay, 300, 50, 2500, 50));

        this.allowSpawn = false;
        this.allowListen = true;
        this.packets = new ArrayList<>();
    }

    @Override
    public void onEnableEvent() {
        this.packets.clear();

        if (spawnNpc.isToggled())
            Utils.Npc.spawn();
    }

    @Override
    public void onDisableEvent() {
        if (spawnNpc.isToggled())
            Utils.Npc.kill();

        if (sendPacketsOnDisable.isToggled())
            if (!pulse.isToggled()) {
                for (Packet<?> p : this.packets)
                    mc.thePlayer.sendQueue.addToSendQueue(p);

                this.packets.clear();
            }
    }

    @SmokEvent
    public void onPacket(EventSendPacket e) {
        if (Utils.canLegitWork()) {
            Packet<?> packet = e.getPacket();

            if (!cancelAllPackets.isToggled()) {
                if (cancelBlockPackets.isToggled()) {
                    if (allowListen && packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C07PacketPlayerDigging) {
                        this.packets.add(packet);
                        e.setCancelled(true);

                        ClientUtils.addDebug("blink: added block and digging packet");
                    }
                }

                if (allowListen && packet instanceof C03PacketPlayer) {
                    this.packets.add(packet);
                    e.setCancelled(true);

                    ClientUtils.addDebug("blink: added player packet");
                }

                if (allowListen && packet instanceof S08PacketPlayerPosLook) {
                    this.packets.add(packet);
                    e.setCancelled(true);

                    ClientUtils.addDebug("blink: added server player pos look packet");
                }

            } else if (e.getPacket() != null) {
                if (allowListen) {
                    this.packets.add(packet);
                    e.setCancelled(true);

                    ClientUtils.addDebug("blink: added " + packet.toString() + " packet");
                }
            }

            if (pulse.isToggled()) {
                if (Timer.hasTimeElapsed(pulseDelay.getValueToLong(), true)) {
                    this.packetSendLoop();

                    if (!allowSpawn) {
                        allowListen = true;
                        ClientUtils.addDebug("blink: clear packets");
                        this.packets.clear();
                        this.killNPC();
                    }
                }

                this.spawnNPC();
            }
        }
    }

    private void spawnNPC() {
        allowListen = false;

        if (spawnNpc.isToggled()) {
            ClientUtils.addDebug("blink: spawned pulse entity");
            Utils.Npc.spawn();
        }
    }

    private void killNPC() {
        if (spawnNpc.isToggled()) {
            ClientUtils.addDebug("blink: killed pulse entity");
            Utils.Npc.kill();
        }

        allowSpawn = true;
    }

    private void packetSendLoop() {
        ClientUtils.addDebug("blink: sended packets loop");

        if (allowListen)
            for (Packet<?> p : this.packets)
                mc.thePlayer.sendQueue.addToSendQueue(p);
    }

}