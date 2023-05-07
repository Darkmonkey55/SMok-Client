package net.minecraft.client.club.maxstats.weaveyoutube.event;

import net.minecraft.client.me.sleepyfish.smok.rats.event.Event;
import net.minecraft.network.Packet;

// Class from SMok Client by SleepyFish
public class EventSendPacket extends Event {

    private Packet<?> packet;

    public EventSendPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

}