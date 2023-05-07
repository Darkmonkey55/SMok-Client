package net.minecraft.client.me.sleepyfish.smok.rats.event;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

// Class from SMok Client by SleepyFish

/**
 * @author SMok Client by SleepyFish
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SmokEvent {
	byte value() default 2;
}