package net.minecraft.client.me.sleepyfish.smok.rats.event;

import java.lang.reflect.Method;

// Class from SMok Client by SleepyFish
public class EventHelp {

	public final Object obj;
	public final Method meth;
	public final byte MokskSK;

	public EventHelp(Object obj, Method meth, byte byt) {
		this.obj = obj;
		this.meth = meth;
		this.MokskSK = byt;
	}

}