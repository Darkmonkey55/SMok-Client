package net.minecraft.client.me.sleepyfish.smok.gui.comp.impl;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.gui.comp.IComp;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FastEditUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import java.util.Iterator;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class CategoryComp {

   public ArrayList<IComp> ratInCategory = new ArrayList<>();
   private boolean categoryOpened;
   public boolean mouseRelocate;
   private final double marginX;
   public Rat.Category category;
   private int categoryHeight;
   public boolean n4m = false;
   private final int height;
   private int catBGOff = 2;
   private int offset;
   private int width;
   public int xx;
   public int yy;
   private int y;
   private int x;

   public CategoryComp(Rat.Category smok) {
      this.category = smok;
      this.width = 92;
      this.height = 10;
      this.x = 5;
      this.y = 5;
      this.xx = 0;

      this.categoryOpened = false;
      this.mouseRelocate = false;

      int tY = getHeight() + 3;
      this.marginX = 55;

      for (Iterator<Rat> iter = Smok.inst.ratManager.getBigRatsInCategory(this.category).iterator(); iter.hasNext(); tY += FastEditUtils.ratGap) {
         Rat rat = iter.next();
         this.ratInCategory.add(new ModuleComp(rat, this, tY));
      }
   }

   public void drawCategory() {
      this.width = 92;

      if (this.categoryOpened) {
         this.categoryHeight = 0;
         IComp comp;

         for (Iterator<IComp> iter = this.ratInCategory.iterator(); iter.hasNext(); this.categoryHeight += comp.getHeight())
            comp = iter.next();

         RoundedUtils.drawRoundOutline(getX(), getY(), getWidth(), getHeight() + getCategoryHeight() + catBGOff - 2, 1F, 2.62F, ColorUtils.getBackgroundColor(5), ColorUtils.getBackgroundColor(5).darker());
      } else
         RoundedUtils.drawRoundOutline(getX(), getY(), getWidth(), getHeight() + catBGOff, 1F, 2.62F, ColorUtils.getBackgroundColor(5), ColorUtils.getBackgroundColor(5).darker());

      if (category == Rat.Category.Other)
         FontUtils.i20.drawString("r", getX() + 2, getY() + 3, ColorUtils.getFontColor(1));

      if (category == Rat.Category.Visual)
         FontUtils.i20.drawString("i", getX() + 2, getY() + 3, ColorUtils.getFontColor(1));

      if (category == Rat.Category.Legit)
         FontUtils.i20.drawString(";", getX() + 2, getY() + 3, ColorUtils.getFontColor(1));

      if (category == Rat.Category.Blatant)
         FontUtils.i20.drawString("A", getX() + 2, getY() + 3, ColorUtils.getFontColor(1));

      if (category == Rat.Category.Useless)
         FontUtils.i20.drawString("6", getX() + 2, getY() + 3, ColorUtils.getFontColor(1));

      // category name
      FontUtils.r20.drawStringWithClientColor(this.category.getName(), getX() + catBGOff + 14, getY() + catBGOff, true);

      if (!this.n4m) {
         FontUtils.i28.drawString(this.categoryOpened ? "J" : "K", (int) (getX() + marginX + 14), getY() + 1.3F, ColorUtils.getFontColor(1));

         if (this.categoryOpened && !this.ratInCategory.isEmpty())
            for (IComp c : this.ratInCategory)
               c.draw();
      }
   }

   public void render() {
      int off = getHeight() + 3;
      IComp comp;

      for (Iterator<IComp> iter = this.ratInCategory.iterator(); iter.hasNext(); off += comp.getHeight()) {
         comp = iter.next();
         comp.setComponentStartAt(off);
      }
   }

   public int getWidth() {
      return this.width - 10;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y + this.getOffset();
   }

   public String getName() {
      return String.valueOf(ratInCategory);
   }

   public void scroll(float smok) {
      if (ClientUtils.inClickGui())
         this.offset += smok;
   }

   public int getCategoryHeight() {
      return this.categoryHeight;
   }

   public void mousePressed(boolean d) {
      if (!ClientUtils.inClickGui())
         return;

      this.mouseRelocate = d;
   }

   public boolean isOpened() {
      return this.categoryOpened;
   }

   private int getOffset() {
      return this.offset;
   }

   public int getHeight() {
      return this.height;
   }

   public ArrayList<IComp> getRats() {
      return this.ratInCategory;
   }

   public void up(int x, int y) {
      if (this.mouseRelocate && ClientUtils.inClickGui()) {
         this.setX(x - this.xx);
         this.setY(y - this.yy);
      }
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y - this.getOffset();
   }

   public void setOpened(boolean smok) {
      this.categoryOpened = smok;
   }

   public boolean isHoveringOverCategoryCollapseIcon(int x, int y) {
      if (!ClientUtils.inClickGui())
         return false;

      return x >= getX() + width - 13 && x <= getX() + getWidth() && (float) y >= (float) getY() + 2F && y <= getY() + getHeight() + 1;
   }

   public boolean mousePressed(int x, int y) {
      if (!ClientUtils.inClickGui())
         return false;

      return x >= getX() + 77 && x <= getX() + getWidth() - 6 && (float) y >= (float) getY() + 2F && y <= getY() + getHeight() + 1;
   }

   public boolean insideArea(int x, int y) {
      if (!ClientUtils.inClickGui())
         return false;

      return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
   }

}