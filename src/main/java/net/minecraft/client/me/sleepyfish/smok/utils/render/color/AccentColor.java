package net.minecraft.client.me.sleepyfish.smok.utils.render.color;

import java.awt.Color;

// Class from SMok Client by SleepyFish
public class AccentColor {

	private final String name;
	private final Color color1, color2;

	public AccentColor(String name, Color color1, Color color2) {
		this.name = name;
		this.color1 = color1;
		this.color2 = color2;
	}
	
	public String getName() {
		return name;
	}

	public Color getColor1() {
		return color1;
	}

	public Color getColor2() {
		return color2;
	}
}
