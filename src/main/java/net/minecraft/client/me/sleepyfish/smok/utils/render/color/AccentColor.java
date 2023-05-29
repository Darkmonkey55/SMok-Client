package net.minecraft.client.me.sleepyfish.smok.utils.render.color;

import java.awt.Color;

// Class from SMok Client by SleepyFish
public class AccentColor {

	private final int id;
	private final Color color1, color2;

	public AccentColor(int id, Color color1, Color color2) {
		this.id = id;
		this.color1 = color1;
		this.color2 = color2;
	}
	
	public int getID() {
		return id;
	}

	public Color getColor1() {
		return color1;
	}

	public Color getColor2() {
		return color2;
	}
}
