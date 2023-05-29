package net.minecraft.client.me.sleepyfish.smok.utils.render.color;

import java.awt.Color;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class ColorManager {

	private final ArrayList<AccentColor> colorModes = new ArrayList<>();
	private AccentColor colorMode;

	public ColorManager() {
		getColorModes().add(new AccentColor(1, new Color(78, 139, 255), new Color(0, 48, 98)));
		getColorModes().add(new AccentColor(2, new Color(241, 178, 246), new Color(254, 240, 45)));
		getColorModes().add(new AccentColor(3, new Color(5, 135, 65), new Color(158, 227, 191)));
		getColorModes().add(new AccentColor(4, new Color(216, 63, 123), new Color(191, 77, 151)));
		getColorModes().add(new AccentColor(5, new Color(152, 114, 206), new Color(90, 47, 141)));
		getColorModes().add(new AccentColor(6, new Color(253, 145, 21), new Color(245, 106, 230)));
		getColorModes().add(new AccentColor(7, new Color(210, 39, 48), new Color(79, 13, 26)));
		getColorModes().add(new AccentColor(8, new Color(61, 79, 143), new Color(1, 19, 63)));
		getColorModes().add(new AccentColor(9, new Color(90, 227, 186), new Color(6, 133, 227)));
		getColorModes().add(new AccentColor(10, new Color(33, 212, 253), new Color(183, 33, 255)));
		getColorModes().add(new AccentColor(11, new Color(251, 109, 32), new Color(190, 53, 38)));
		getColorModes().add(new AccentColor(12, new Color(237, 133, 211), new Color(28, 166, 222)));
		getColorModes().add(new AccentColor(13, new Color(192, 95, 216), new Color(74, 0, 224)));
		getColorModes().add(new AccentColor(14, new Color(26, 33, 42), new Color(232, 234, 240)));
		getColorModes().add(new AccentColor(15, new Color(30, 31, 34), new Color(63, 63, 66)));
		this.setColorMode(12);
	}

	public AccentColor getColorMode() {
		return this.colorMode;
	}

	public void setColorMode(int id) {
		this.colorMode = this.getColorModeByName(id);
	}

	public ArrayList<AccentColor> getColorModes() {
		return this.colorModes;
	}

	public AccentColor getColorModeByName(int id) {
		return colorModes.stream().filter(color -> color.getID() == id).findFirst().orElse(null);
	}

}