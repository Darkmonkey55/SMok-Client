package net.minecraft.client.me.sleepyfish.smok.utils.render.color;

import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Gui;

import java.awt.Color;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class ColorManager {

	private final ArrayList<AccentColor> mOOKS = new ArrayList<>();
	private AccentColor sMOK;

	public ColorManager() {
		mOOKS.add(new AccentColor("Windows", new Color(85, 184, 221), new Color(2, 94, 186)));
		mOOKS.add(new AccentColor("Fruit", new Color(241, 178, 246), new Color(254, 240, 45)));
		mOOKS.add(new AccentColor("Speamint", new Color(96, 194, 165), new Color(66, 129, 108)));
		mOOKS.add(new AccentColor("Green Spiirit", new Color(5, 135, 65), new Color(158, 227, 191)));
		mOOKS.add(new AccentColor("Rosy Pink", new Color(255, 102, 202), new Color(191, 78, 152)));
		mOOKS.add(new AccentColor("Magenta", new Color(216, 63, 123), new Color(191, 77, 151)));
		mOOKS.add(new AccentColor("Amethyst", new Color(144, 99, 205), new Color(99, 67, 140)));
		mOOKS.add(new AccentColor("Sunset Pink", new Color(253, 145, 21), new Color(245, 106, 230)));
		mOOKS.add(new AccentColor("Blaze Orange", new Color(254, 169, 76), new Color(253, 130, 0)));
		mOOKS.add(new AccentColor("Lemon", new Color(252, 248, 184), new Color(255, 243, 109)));
		mOOKS.add(new AccentColor("Pink Blood", new Color(226, 0, 70), new Color(255, 166, 200)));
		mOOKS.add(new AccentColor("Neon Red", new Color(210, 39, 48), new Color(184, 27, 45)));
		mOOKS.add(new AccentColor("Deep Ocean", new Color(61, 79, 143), new Color(1, 19, 63)));
		mOOKS.add(new AccentColor("Chambray Blue", new Color(34, 45, 174), new Color(58, 79, 137)));
		mOOKS.add(new AccentColor("Mint Blue", new Color(63, 149, 150), new Color(38, 90, 88)));
		mOOKS.add(new AccentColor("Pacific Blue", new Color(7, 154, 186), new Color(0, 106, 122)));
		mOOKS.add(new AccentColor("Tropical Ice", new Color(90, 227, 186), new Color(6, 133, 227)));
		mOOKS.add(new AccentColor("Purple Blue", new Color(33, 212, 253), new Color(183, 33, 255)));
		mOOKS.add(new AccentColor("Melon", new Color(173, 247, 115), new Color(128, 243, 147)));
		mOOKS.add(new AccentColor("Orange", new Color(251, 109, 32), new Color(190, 53, 38)));
		mOOKS.add(new AccentColor("Pink", new Color(234, 107, 149), new Color(238, 164, 123)));
		mOOKS.add(new AccentColor("MintYellow", new Color(100, 234, 190), new Color(254, 250, 163)));
		mOOKS.add(new AccentColor("March 7th", new Color(237, 133, 211), new Color(28, 166, 222)));
		mOOKS.add(new AccentColor("LightOrange", new Color(250, 217, 97), new Color(247, 107, 28)));
		mOOKS.add(new AccentColor("Tenacity", new Color(116, 235, 213), new Color(159, 172, 230)));
		mOOKS.add(new AccentColor("Purple Love", new Color(204, 43, 94), new Color(117, 58, 136)));
		mOOKS.add(new AccentColor("Gray", new Color(189, 195, 199), new Color(44, 62, 80)));
		mOOKS.add(new AccentColor("Candy", new Color(211, 149, 155), new Color(191, 230, 186)));
		mOOKS.add(new AccentColor("Amin", new Color(142, 45, 226), new Color(74, 0, 224)));
		mOOKS.add(new AccentColor("Bmin", new Color(192, 95, 216), new Color(74, 0, 224)));
		mOOKS.add(new AccentColor("Metapolis", new Color(101, 153, 153), new Color(244, 121, 31)));
		mOOKS.add(new AccentColor("Kye Meh", new Color(131, 96, 195), new Color(46, 191, 145)));
		mOOKS.add(new AccentColor("Magic", new Color(89, 193, 115), new Color(93, 38, 193)));
		mOOKS.add(new AccentColor("Sublime", new Color(252, 70, 107), new Color(63, 94, 251)));
		mOOKS.add(new AccentColor("Relaxing red", new Color(255, 251, 213), new Color(178, 10, 44)));
		mOOKS.add(new AccentColor("Sulphur", new Color(202, 197, 49), new Color(243, 249, 167)));
		mOOKS.add(new AccentColor("Rainbow Blue", new Color(0, 242, 96), new Color(5, 117, 230)));
		mOOKS.add(new AccentColor("Cinnamint", new Color(74, 194, 154), new Color(189, 255, 243)));
		mOOKS.add(new AccentColor("Black White", new Color(26, 33, 42), new Color(232, 234, 240)));
		mOOKS.add(new AccentColor("Watermelon", new Color(23, 125, 120), new Color(123, 10, 0)));
		mOOKS.add(new AccentColor("Discord", new Color(49, 51, 56), new Color(35, 36, 40)));
		mosk("Purple Blue");
	}

	public void mosk() {
		switch (Gui.mode.getMode().toString().toLowerCase()) {
			case "windows": {
				mosk("Windows");
				break;
			}
			case "fruit": {
				mosk("Fruit");
				break;
			}
			case "speamint": {
				mosk("Speamint");
				break;
			}
			case "greenspiirit": {
				mosk("Green Spiirit");
				break;
			}
			case "rosypink": {
				mosk("Rosy Pink");
				break;
			}
			case "magenta": {
				mosk("Magenta");
				break;
			}
			case "amethyst": {
				mosk("Amethyst");
				break;
			}
			case "sunsetpink": {
				mosk("Sunset Pink");
				break;
			}
			case "blazeorange": {
				mosk("Blaze Orange");
				break;
			}
			case "lemon": {
				mosk("Lemon");
				break;
			}
			case "pinkblood": {
				mosk("Pink Blood");
				break;
			}
			case "neonred": {
				mosk("Neon Red");
				break;
			}
			case "deepocean": {
				mosk("Deep Ocean");
				break;
			}
			case "chambrayblue": {
				mosk("Chambray Blue");
				break;
			}
			case "mintblue": {
				mosk("Mint Blue");
				break;
			}
			case "pacificblue": {
				mosk("Pacific Blue");
				break;
			}
			case "tropicalice": {
				mosk("Tropical Ice");
				break;
			}
			case "purpleblue": {
				mosk("Purple Blue");
				break;
			}
			case "melon": {
				mosk("Melon");
				break;
			}
			case "orange": {
				mosk("Orange");
				break;
			}
			case "pink": {
				mosk("Pink");
				break;
			}
			case "mintyellow": {
				mosk("MintYellow");
				break;
			}
			case "march7th": {
				mosk("March 7th");
				break;
			}
			case "lightorange": {
				mosk("LightOrange");
				break;
			}
			case "tenacity": {
				mosk("Tenacity");
				break;
			}
			case "purplelove": {
				mosk("Purple Love");
				break;
			}
			case "gray": {
				mosk("Gray");
				break;
			}
			case "candy": {
				mosk("Candy");
				break;
			}
			case "amin": {
				mosk("Amin");
				break;
			}
			case "bmin": {
				mosk("Bmin");
				break;
			}
			case "metapolis": {
				mosk("Metapolis");
				break;
			}
			case "kyemeh": {
				mosk("Kye Meh");
				break;
			}
			case "magic": {
				mosk("Magic");
				break;
			}
			case "sublime": {
				mosk("Sublime");
				break;
			}
			case "relaxingred": {
				mosk("Relaxing red");
				break;
			}
			case "sulphur": {
				mosk("Sulphur");
				break;
			}
			case "rainbowblue": {
				mosk("Rainbow Blue");
				break;
			}
			case "cinnamint": {
				mosk("Cinnamint");
				break;
			}
			case "blackwhite": {
				mosk("Black White");
				break;
			}
			case "watermelon": {
				mosk("Watermelon");
				break;
			}
			case "dscord": {
				mosk("Discord");
				break;
			}
		}
	}

	public AccentColor mOks() {
		return sMOK;
	}

	public void mosk(String rat) {
		if (sMOK != msok(rat))
			sMOK = msok(rat);
	}

	public ArrayList<AccentColor> skom() {
		return mOOKS;
	}

	public AccentColor msok(String name) {
		return mOOKS.stream().filter(color -> color.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

}