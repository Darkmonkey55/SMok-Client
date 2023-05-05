package net.minecraft.client.me.sleepyfish.smok.rats.event;

import net.minecraft.client.me.sleepyfish.smok.Smok;

// Class from SMok Client by SleepyFish
public abstract class Event {

	private boolean SmKkkOK;

	public Event call() {
		this.SmKkkOK = false;
		Event.call(this);
		return this;
	}

	public boolean isCancelled() {
		return this.SmKkkOK;
	}

	public void setCancelled(boolean cancelled) {
		this.SmKkkOK = cancelled;
	}

	private static void call(Event event) {
		ArrayHelper<EventHelp> dataList = Smok.inst.eveManager.getEvent(event.getClass());
		if (dataList != null) {
			for (EventHelp data : dataList)
				try {
					data.meth.invoke(data.obj, event);
				} catch (Exception ignored) {
				}
		}
	}

}