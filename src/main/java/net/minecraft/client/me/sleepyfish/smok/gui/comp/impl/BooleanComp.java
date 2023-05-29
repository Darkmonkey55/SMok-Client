package net.minecraft.client.me.sleepyfish.smok.gui.comp.impl;

import net.minecraft.client.me.sleepyfish.smok.utils.SoundUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.gui.comp.IComp;
import net.minecraft.client.me.sleepyfish.smok.utils.FastEditUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.BoolSetting;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class BooleanComp implements IComp {

   private final ModuleComp module;
   private final BoolSetting bool;
   private int off;
   private int x;
   private int y;

   public BooleanComp(BoolSetting bool, ModuleComp modComp, int off) {
      this.bool = bool;
      this.module = modComp;
      this.x = modComp.getCategory().getX() + modComp.getCategory().getWidth();
      this.y = modComp.getCategory().getY() + modComp.getOff();
      this.off = off;
   }

   public int getY() {
      return y;
   }

   public void setComponentStartAt(int sk) {
      this.off = sk;
   }

   public void keyTyped(char chara, int key) {
   }

   public void mouseReleased(int x, int y, int b) {
   }

   public int getHeight() {
      return 0;
   }

   public void draw() {
      GL11.glPushMatrix();
      GL11.glScaled(0.5D, 0.5D, 0.5D);

      if (this.bool.isEnabled())
         RoundedUtils.drawRound((this.module.getCategory().getX() + 72.5F) * 2, (float) ((this.module.getCategory().getY() + this.off + 3) * 2), 10, 10, 10, ColorUtils.getClientColor(1));
      else
         RoundedUtils.drawRound((this.module.getCategory().getX() + 72.5F) * 2, (float) ((this.module.getCategory().getY() + this.off + 3) * 2), 10, 10, 10, ColorUtils.getBackgroundColor(5).darker().darker());

      FontUtils.r24.drawString(this.bool.getSettingName(), (float) ((this.module.getCategory().getX() + 4) * 2), (float) ((this.module.getCategory().getY() + this.off + 3) * 2), ColorUtils.getFontColor(1).darker().darker());

      GL11.glPopMatrix();
   }

   public void update(int x, int y) {
      this.y = this.module.getCategory().getY() + this.off;
      this.x = this.module.getCategory().getX();
   }

   public void mouseDown(int x, int y, int smok) {
      if (!ClientUtils.inClickGui())
         return;

      if (this.isHovering(x, y) && smok == 0 && this.module.isExpanded()) {
         if (this.bool.isEnabled())
            SoundUtils.playSound(SoundUtils.click, 1F, 0.7F);
         else
            SoundUtils.playSound(SoundUtils.click, 1F, 0.8F);

         this.bool.toggle();
      }
   }

   public boolean isHovering(int x, int y) {
      return x > this.x && x < this.x + this.module.getCategory().getWidth() && y > this.y && y < this.y + FastEditUtils.settingGap;
   }

}