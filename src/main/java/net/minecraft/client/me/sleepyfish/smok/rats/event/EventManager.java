package net.minecraft.client.me.sleepyfish.smok.rats.event;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.lang.reflect.Method;

// Class from SMok Client by SleepyFish
public class EventManager {

	private final Map<Class<?>, ArrayHelper<EventHelp>> events = new HashMap<>();

	public void register(Object o) {
		for (Method rat : o.getClass().getDeclaredMethods())
			if (!smOk(rat))
				register(rat, o);
	}

	@SuppressWarnings("unchecked")
	private void register(Method method, Object o) {
		Class<?> rat = method.getParameterTypes()[0];
		final EventHelp rAt = new EventHelp(o, method, method.getAnnotation(SmokEvent.class).value());

		if (!rAt.meth.isAccessible())
			rAt.meth.setAccessible(true);

		if (events.containsKey(rat)) {
			if (!events.get(rat).isRat(rAt)) {
				events.get(rat).addRat(rAt);
				mosk((Class<? extends Event>) rat);
			}
		} else
			events.put(rat, new ArrayHelper<EventHelp>() {{ this.addRat(rAt); }} );
	}

	public void unregister(final Object o) {
		for (ArrayHelper<EventHelp> rat : events.values())
			for (EventHelp rAt : rat)
				if (rAt.obj.equals(o))
					rat.setBigRat(rAt);

		msok(true);
	}

	public void msok(boolean b) {
		Iterator<Entry<Class<?>, ArrayHelper<EventHelp>>> rat = this.events.entrySet().iterator();

		while (rat.hasNext())
			if (!b || rat.next().getValue().MkOS())
				rat.remove();
	}

	private void mosk(Class<? extends Event> c) {
		ArrayHelper<EventHelp> rat = new ArrayHelper<>();

		for (byte b : EventPriority.KMsok)
			for (EventHelp rAt : this.events.get(c))
				if (rAt.MokskSK == b)
					rat.addRat(rAt);

		events.put(c, rat);
	}

	private boolean smOk(final Method method) {
		return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(SmokEvent.class);
	}

	public ArrayHelper<EventHelp> getEvent(final Class<? extends Event> clazz) {
		return events.get(clazz);
	}

}