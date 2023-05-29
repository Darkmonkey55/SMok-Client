package net.minecraft.client.me.sleepyfish.smok.gui.comp.impl;

import net.minecraft.client.me.sleepyfish.smok.gui.comp.IComp;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FastEditUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.SlideSetting;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

// Class from SMok Client by SleepyFish
public class SliderComp implements IComp {

   private final ModuleComp module;
   private final SlideSetting slider;
   public boolean sliding = false;
   private int y, x, offset;
   private double w;
   int guiSize;

   public SliderComp(SlideSetting slider, ModuleComp modComp, int o) {
      this.slider = slider;
      this.module = modComp;
      this.x = modComp.getCategory().getX() + modComp.getCategory().getWidth();
      this.y = modComp.getCategory().getY() + modComp.getOff();
      this.offset = o;
   }

   public void setComponentStartAt(int n) {
      this.offset = n;
   }

   public int getHeight() {
      return 0;
   }

   public void mouseReleased(int x, int y, int m) {
      this.sliding = false;
   }

   public void keyTyped(char chara, int key) {
      if (key == 1) {
         this.sliding = false;
      }
   }

   public int getY() {
      return y;
   }

   public void draw() {
      int l = this.module.getCategory().getX() + 4;

      //Gui.drawRect(l, this.smkokk.smmksko.getY() + this.msokks + 11, l + this.smkokk.smmksko.getWidth() - 8, this.smkokk.smmksko.getY() + this.msokks + 13, x);
      RoundedUtils.drawRound(l, this.module.getCategory().getY() + this.offset + 11, this.module.getCategory().getWidth() - 8, 2, 1, ColorUtils.getBackgroundColor(5).brighter());

      //Gui.drawRect(l, this.smkokk.smmksko.getY() + this.msokks + 11, r, this.smkokk.smmksko.getY() + this.msokks + 13, c);
      RoundedUtils.drawRound(l, this.module.getCategory().getY() + this.offset + 11, (int) this.w, 2, 1, ColorUtils.getClientColor(6969));

      GL11.glPushMatrix();
      GL11.glScaled(0.5D, 0.5D, 0.5D);

      FontUtils.r24.drawStringWithShadow(this.slider.getSettingName(), (float) ((int) ((float) l * 2F)),
              (float) ((int) ((float) (this.module.getCategory().getY() + this.offset + 3) * 2F)), ColorUtils.getFontColor(1).darker().darker());

      String val = new DecimalFormat("#.##").format(this.slider.getValue());
      double valr = FontUtils.r24.getStringWidth(val) / 2;

      FontUtils.r24.drawStringWithShadow(val, (int) ((float) (this.module.getCategory().getX() + 78 - valr) * 2F),
              (int) ((float) (this.module.getCategory().getY() + this.offset + 3) * 2F), ColorUtils.getFontColor(1).darker());

      GL11.glPopMatrix();
   }

   public void update(int x, int y) {
      if (isOverSlider(x, y) || overSliderStart(x, y))
         guiSize = 5;
      else guiSize = 4;

      this.y = this.module.getCategory().getY() + this.offset;
      this.x = this.module.getCategory().getX();
      double d = Math.min(this.module.getCategory().getWidth() - 8, Math.max(0, x - this.x));
      this.w = (double) (this.module.getCategory().getWidth() - 8) * (this.slider.getValue() - this.slider.getMin()) / (this.slider.getMax() - this.slider.getMin());

      if (this.sliding) {
         if (d == 0.0D)
            this.slider.setValue(this.slider.getMin());
         else
            this.slider.setValue(round(d / (double) (this.module.getCategory().getWidth() - 8) * (this.slider.getMax() - this.slider.getMin()) + this.slider.getMin()));
      }
   }

   private static double round(double v) {
      BigDecimal bd = new BigDecimal(v);
      bd = bd.setScale(2, RoundingMode.HALF_UP);
      return bd.doubleValue();
   }

   public void mouseDown(int x, int y, int b) {
      if (this.overSliderStart(x, y) && b == 0 && this.module.isExpanded())
         this.sliding = true;

      if (this.isOverSlider(x, y) && b == 0 && this.module.isExpanded())
         this.sliding = true;
   }

   public boolean overSliderStart(int x, int y) {
      return x > this.x && x < this.x + this.module.getCategory().getWidth() / 2 + 1 && y > this.y && y < this.y + FastEditUtils.ratGap;
   }

   public boolean isOverSlider(int x, int y) {
      return x > this.x + this.module.getCategory().getWidth() / 2 && x < this.x + this.module.getCategory().getWidth() && y > this.y && y < this.y + FastEditUtils.ratGap;
   }

}