package net.minecraft.client.me.sleepyfish.smok.utils.render.color;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.MathUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.impl.visual.Gui;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.simple.SimpleAnimation;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import java.time.Month;
import java.time.LocalDate;

// Class from SMok Client by SleepyFish
public class ColorUtils {

	public static Color lightRed = new Color(240, 0, 20);

	public static SimpleAnimation[] animation = {
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F)
	};

    public static Color getBackgroundColor(int id, int alpha) {
		int speed = 12;
		Color rawColor = getRawBackgroundColor(id);

		if (!isGooberDate()) {
			if (id == 1) {
				animation[0].setAnimation(rawColor.getRed(), speed);
				animation[1].setAnimation(rawColor.getGreen(), speed);
				animation[2].setAnimation(rawColor.getBlue(), speed);
				return new Color((int) animation[0].getValue(), (int) animation[1].getValue(), (int) animation[2].getValue(), alpha);
			}

			if (id == 2) {
				animation[3].setAnimation(rawColor.getRed(), speed);
				animation[4].setAnimation(rawColor.getGreen(), speed);
				animation[5].setAnimation(rawColor.getBlue(), speed);
				return new Color((int) animation[3].getValue(), (int) animation[4].getValue(), (int) animation[5].getValue(), alpha);
			}

			if (id == 3) {
				animation[6].setAnimation(rawColor.getRed(), speed);
				animation[7].setAnimation(rawColor.getGreen(), speed);
				animation[8].setAnimation(rawColor.getBlue(), speed);
				return new Color((int) animation[6].getValue(), (int) animation[7].getValue(), (int) animation[8].getValue(), alpha);
			}

			if (id == 4) {
				animation[9].setAnimation(rawColor.getRed(), speed);
				animation[10].setAnimation(rawColor.getGreen(), speed);
				animation[11].setAnimation(rawColor.getBlue(), speed);
				return new Color((int) animation[9].getValue(), (int) animation[10].getValue(), (int) animation[11].getValue(), alpha);
			}
		} else
			return Color.green;

		return rawColor;
	}

	public static Color getBackgroundColor(int id) {
		return getBackgroundColor(id, 255);
	}

	private static Color getRawBackgroundColor(int id) {
		Color color = new Color(255, 0, 0);
		boolean dark = Gui.darkMode.isEnabled();

		if (!isGooberDate()) {
			switch (id) {
				case 1:
					if (dark)
						color = new Color(26, 33, 42);
					else
						color = new Color(232, 234, 240);
					break;
				case 2:
					if (dark)
						color = new Color(35, 40, 46);
					else
						color = new Color(239, 244, 249);
					break;
				case 3:
					if (dark)
						color = new Color(46, 51, 57);
					else
						color = new Color(247, 250, 252);
					break;
				case 4:
					if (dark)
						color = new Color(57, 61, 67);
					else
						color = new Color(253, 254, 254);
					break;
				case 5:
					if (dark)
						color = new Color(0x1A191A);
					else
						color = new Color(253, 254, 254);
			}
		} else
			return Color.ORANGE;

		return color;
	}

	public static Color getFontColor(int id, int alpha) {
		Color rawColor = getRawFontColor(id);
		int speed = 12;

		if (!isGooberDate()) {
			if (id == 1) {
				animation[12].setAnimation(rawColor.getRed(), speed);
				animation[13].setAnimation(rawColor.getGreen(), speed);
				animation[14].setAnimation(rawColor.getBlue(), speed);
				return new Color((int) animation[12].getValue(), (int) animation[13].getValue(), (int) animation[14].getValue(), alpha);
			}

			if (id == 2) {
				animation[15].setAnimation(rawColor.getRed(), speed);
				animation[16].setAnimation(rawColor.getGreen(), speed);
				animation[17].setAnimation(rawColor.getBlue(), speed);
				return new Color((int) animation[15].getValue(), (int) animation[16].getValue(), (int) animation[17].getValue(), alpha);
			}
		} else
			return Color.YELLOW;

		return rawColor;
	}

	private static Color getRawFontColor(int id) {
		Color color = new Color(0, 0, 255);
		boolean dark = Gui.darkMode.isEnabled();

		if (!isGooberDate()) {
			switch (id) {
				case 1:
					if (dark)
						color = new Color(255, 255, 255);
					else
						color = new Color(27, 27, 27);
					break;
				case 2:
					if (dark)
						color = new Color(207, 209, 210);
					else
						color = new Color(96, 97, 97);
					break;
			}
		} else
			return Color.PINK;

		return color;
	}

	public static Color getFontColor(int id) {
		return getFontColor(id, 255);
	}

	public static Color getClientColor(int index, int alpha) {
		for (AccentColor c : Smok.inst.colManager.getColorModes())
			if (c.equals(Smok.inst.colManager.getColorMode()))
				return ColorUtils.interpolateColorsBackAndForth(15, index, new Color(c.getColor1().getRed(), c.getColor1().getGreen(), c.getColor1().getBlue(), alpha), new Color(c.getColor2().getRed(), c.getColor2().getGreen(), c.getColor2().getBlue(), alpha), false);

		return ColorUtils.interpolateColorsBackAndForth(15, index, new Color(234, 107, 149, alpha), new Color(238, 164, 123, alpha), false);
	}

	public static Color getClientColor(int index) {
		return ColorUtils.getClientColor(index, 255);
	}

	public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
		int angle = (int) (((System.currentTimeMillis()) / speed + index) % 360);
		angle = (angle >= 180 ? 360 - angle : angle) * 2;
		return trueColor ? ColorUtils.interpolateColorHue(start, end, angle / 360f) : ColorUtils.getInterpolateColor(start, end, angle / 360f);
	}

	private static Color getInterpolateColor(Color color1, Color color2, float amount) {
		amount = Math.min(1, Math.max(0, amount));
		return new Color(MathUtils.interpolateInt(color1.getRed(), color2.getRed(), amount), MathUtils.interpolateInt(color1.getGreen(), color2.getGreen(), amount), MathUtils.interpolateInt(color1.getBlue(), color2.getBlue(), amount), MathUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
	}

	private static Color interpolateColorHue(Color color1, Color color2, float amount) {
		amount = Math.min(1, Math.max(0, amount));
		float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
		float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);

		Color resultColor = Color.getHSBColor(MathUtils.interpolateFloat(color1HSB[0], color2HSB[0], amount), MathUtils.interpolateFloat(color1HSB[1], color2HSB[1], amount), MathUtils.interpolateFloat(color1HSB[2], color2HSB[2], amount));
		return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), MathUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
	}

	public static void setColor(Color color, float alpha) {
		float r = (float) (color.getRGB() >> 16 & 255) / 255.0F;
		float g = (float) (color.getRGB() >> 8 & 255) / 255.0F;
		float b = (float) (color.getRGB() & 255) / 255.0F;
		GlStateManager.color(r, g, b, alpha);
	}

	public static void setColor(Color color) {
		setColor(color, (float) (color.getRGB() >> 24 & 255) / 255.0F);
	}

	public static void clearColor() {
		GlStateManager.color(1, 1, 1, 1);
	}

	public static Color rainbow(int index, double speed, int opacity) {
		int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
		Color c = new Color(Color.HSBtoRGB(angle / 360f, 1F, 1));
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity);
	}

	public static Color niceRainbow() {
		return ColorUtils.rainbow(0, 25, 255);
	}

	public static boolean isGooberDate() {
		LocalDate d = LocalDate.now();

		return (d.getDayOfMonth() == 1 && d.getMonth() == Month.JULY) || (d.getDayOfMonth() == 24 && d.getMonth() == Month.DECEMBER) || (d.getDayOfMonth() == 1 && d.getMonth() == Month.MAY);
	}

}