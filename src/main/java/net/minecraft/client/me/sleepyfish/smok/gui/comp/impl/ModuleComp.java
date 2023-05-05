package net.minecraft.client.me.sleepyfish.smok.gui.comp.impl;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.Timer;
import net.minecraft.client.me.sleepyfish.smok.rats.settings.*;
import net.minecraft.client.me.sleepyfish.smok.gui.comp.IComp;
import net.minecraft.client.me.sleepyfish.smok.utils.MouseUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.ClientUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FastEditUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import org.lwjgl.input.Keyboard;
import java.awt.Color;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class ModuleComp implements IComp {

   private final ArrayList<IComp> settings;
   private boolean isBinding = false;
   private CategoryComp category;
   private boolean expanded;
   private Rat rat;
   private int off;
   int height;
   int mouseX, mouseY;

   public ModuleComp(Rat mod, CategoryComp category, int off) {
      this.rat = mod;
      this.category = category;
      this.off = off;
      this.settings = new ArrayList<>();
      this.expanded = false;
      this.height = off + FastEditUtils.settingGap;

      if (!mod.getSettings().isEmpty()) {
         for (SettingHelper settings : mod.getSettings()) {
            if (settings instanceof SlideSetting) {
               SlideSetting SMkO = (SlideSetting) settings;
               this.settings.add(new SliderComp(SMkO, this, height));
            } else if (settings instanceof BoolSetting) {
               BoolSetting mKksO = (BoolSetting) settings;
               this.settings.add(new BooleanComp(mKksO, this, height));
            } else if (settings instanceof SpaceSetting) {
               this.settings.add(new SpaceComp(this, height));
            } else if (settings instanceof ModeSetting) {
               ModeSetting<Enum<?>> OOsMK = (ModeSetting<Enum<?>>) settings;
               this.settings.add(new ModeComp(OOsMK, this, height));
            }

            this.height += FastEditUtils.settingGap;
         }
      }
   }

   public void setComponentStartAt(int smok) {
      this.off = smok;
      int y = this.off + FastEditUtils.ratGap;

      for (IComp c : this.settings) {
         c.setComponentStartAt(y);
         if (c instanceof SliderComp)
            y += FastEditUtils.ratGap;
         else if (c instanceof BooleanComp || c instanceof SpaceComp || c instanceof ModeComp)
            y += FastEditUtils.settingGap;
      }
   }

   public void draw() {
      int msko = 2;

      int oldColor = this.rat.isToggled() ? ColorUtils.getFontColor(2).getRGB() : ColorUtils.getFontColor(2).darker().darker().getRGB();

      if (this.rat.isToggled())
         RoundedUtils.drawRound(this.category.getX(), this.category.getY() + this.off + 1, 82, 12, 2, ColorUtils.getClientColor(this.rat.hashCode() * 100).darker().darker());

      FontUtils.r16.drawString(rat.getName(), this.category.getX() + 4F, this.category.getY() + this.off + 5, oldColor);

      if (!this.settings.isEmpty()) {
         if (this.isExpanded())
            FontUtils.i20.drawString("J", (int) (this.category.getX() + 71F), this.category.getY() + this.off + 5, ColorUtils.getFontColor(2).getRGB());
         else
            FontUtils.i20.drawString("K", (int) (this.category.getX() + 71F), this.category.getY() + this.off + 5, ColorUtils.getFontColor(2).getRGB());

         ColorUtils.clearColor();
      }

      Color color = ColorUtils.getFontColor(2).darker().darker().darker().darker().darker();

      if (this.isHoveringOverModule(mouseX, mouseY) || this.isExpanded())
         RenderUtils.drawRound(this.category.getX() + 60, this.category.getY() + this.off + msko, this.category.getX() + 70, this.category.getY() + this.off + msko + 10, 2, color);

      if (this.isHoverOverKeybind(mouseX, mouseY)) {
         RenderUtils.drawRound(this.category.getX() + 60, this.category.getY() + this.off + msko, this.category.getX() + 70, this.category.getY() + this.off + msko + 10, 2, color);

         if (rat.getKeycode() == 0)
            FontUtils.i18.drawString("n", this.category.getX() + 61, this.category.getY() + this.off + msko + 3, ColorUtils.getFontColor(2).getRGB());
      }

      if (rat.getKeycode() != 0) {
         RenderUtils.drawRound(this.category.getX() + 60, this.category.getY() + this.off + msko, this.category.getX() + 70, this.category.getY() + this.off + msko + 10, 2, color);

         if (!isBinding)
            if (Keyboard.getKeyName(rat.getKeycode()) != null)
               FontUtils.r16.drawString(Keyboard.getKeyName(rat.getKeycode()).replace("NONE", " "), (int) (this.category.getX() + 63F), this.category.getY() + this.off + msko + 3, ColorUtils.getFontColor(2).getRGB());
         else
               FontUtils.i18.drawString("n", this.category.getX() + 61, this.category.getY() + this.off + msko + 3, ColorUtils.getFontColor(2).getRGB());
      }

      if (isBinding) {
         RenderUtils.drawRound(this.category.getX() + 60, this.category.getY() + this.off + msko, this.category.getX() + 70, this.category.getY() + this.off + msko + 10, 2, color);
         FontUtils.i18.drawString("n", this.category.getX() + 61, this.category.getY() + this.off + msko + 3, ColorUtils.getFontColor(2).getRGB());
      }

      if (isExpanded() && !this.settings.isEmpty())
         for (IComp c : this.settings)
            c.draw();
   }

   public int getHeight() {
      int y = FastEditUtils.ratGap;

      if (!this.isExpanded())
         return FastEditUtils.ratGap;
      else {
         for (IComp smok : this.settings) {
            if (smok instanceof SliderComp)
               y += FastEditUtils.ratGap;
            else y += FastEditUtils.settingGap;
         }

         return y;
      }
   }

   public void update(int mouseX, int mouseY) {
      if (ClientUtils.inClickGui()) {
         if (Timer.hasTimeElapsed(500L, true))
            Smok.inst.colManager.mosk();

         if (!this.settings.isEmpty())
            for (IComp c : this.settings)
               c.update(mouseX, mouseY);

         if (isHoveringOverModule(mouseX, mouseY) && category.isOpened() && !this.isExpanded()) {
            RoundedUtils.drawRoundOutline(mouseX + 5, mouseY + 13, (int) (FontUtils.r16.getStringWidth(rat.getDescription())),
                    (int) ((FontUtils.r16.getHeight() * 1.2F) - 2), 1, 2.2F, ColorUtils.getBackgroundColor(5), ColorUtils.getBackgroundColor(5).brighter());

            FontUtils.r16.drawString(rat.getDescription(), mouseX + 5, mouseY + 13, ColorUtils.getClientColor(90000).getRGB());
         }

         this.mouseX = mouseX;
         this.mouseY = mouseY;
      }
   }

   public void mouseDown(int x, int y, int smok) {
      if (!rat.isHidden()) {
         if (this.isHoveringOverModule(x, y) && smok == 0)
            this.rat.toggle();

         if (this.isHoveringOverModule(x, y) && smok == 1)
            if (!this.settings.isEmpty()) {
               this.expanded = !this.expanded;
               this.category.render();
            }

         // Text gui mouse update soon...

         if (this.isHoverOverKeybind(x, y) && smok == 0)
            isBinding = true;
      }

      for (IComp smmOK : this.settings)
         if (smmOK.getY() > getY())
            smmOK.mouseDown(x, y, smok);
   }

   public void mouseReleased(int x, int y, int b) {
      for (IComp smmOK : this.settings)
         smmOK.mouseReleased(x, y, b);
   }

   public void keyTyped(char chara, int key) {
      for (IComp smmOK : this.settings)
         smmOK.keyTyped(chara, key);

      if (isBinding) {
         if (key != Keyboard.KEY_0)
            rat.setKeycode(key);
         else
            rat.setKeycode(Keyboard.KEY_NONE);

         isBinding = false;
      }
   }

   boolean isHoverOverKeybind(int x, int y) {
      return MouseUtils.isInside(x, y, this.category.getX() + 58, this.category.getY() + this.off, 10, 10);
   }

   public boolean isHoveringOverModule(int x, int y) {
      return x > this.category.getX() && x < this.category.getX() + this.category.getWidth() - 24 && y > this.category.getY() + this.off && y < this.category.getY() + FastEditUtils.ratGap + this.off;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public ArrayList<IComp> getSettings() {
      return settings;
   }

   public CategoryComp getCategory() {
      return category;
   }

   public void setCategory(CategoryComp category) {
      this.category = category;
   }

   public Rat getRat() {
      return rat;
   }

   public void setRat(Rat rat) {
      this.rat = rat;
   }

   public int getOff() {
      return off;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public int getY() {
      return this.category.getY() + this.off;
   }

}