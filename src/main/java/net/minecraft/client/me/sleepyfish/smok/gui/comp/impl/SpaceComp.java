package net.minecraft.client.me.sleepyfish.smok.gui.comp.impl;

import net.minecraft.client.me.sleepyfish.smok.gui.comp.IComp;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;

// Class from SMok Client by SleepyFish
public class SpaceComp implements IComp {

   private final ModuleComp p;
   private int o;

   public SpaceComp(ModuleComp b, int o) {
      this.p = b;
      this.o = o;
   }

   public void update(int x, int y) {
   }

   public void mouseDown(int x, int y, int b) {
   }

   public void mouseReleased(int x, int y, int m) {
   }

   public void keyTyped(char chara, int key) {
   }

   public void setComponentStartAt(int n) {
      this.o = n;
   }

   public int getHeight() {
      return 0;
   }

   public int getY() {
      return (this.p.getCategory().getY() + this.o);
   }

   public void draw() {
      RoundedUtils.drawRound(this.p.getCategory().getX() + 8F, getY() + 5F, 65, 1, 2F, ColorUtils.getBackgroundColor(5).brighter());
   }

}