package net.minecraft.client.me.sleepyfish.smok.rats.settings;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

// Class from SMok Client by SleepyFish
public class SlideSetting extends SettingHelper {

   private final double interval;
   private double max, min, value;
   private final String defName;
   private String name;

   public SlideSetting(String name, double def, double min, double max, double add) {
      super(name);
      this.name = name;
      this.defName = name;
      this.value = def;
      this.min = min;
      this.max = max;
      this.interval = add;
   }

   public double getMin() {
      return this.min;
   }

   public String bigRatName() {
      return "slider";
   }

   public double getValue() {
      return round(this.value, 2);
   }

   public float getValueToFloat() {
      return (float) getValue();
   }

   public int getValueToInt() {
      return (int) getValue();
   }

   public long getValueToLong() {
      return (long) getValue();
   }

   public double getMax() {
      return this.max;
   }

   public void setMax(double max) {
      this.max = max;
   }

   public String getSettingName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setValue(double val) {
      val = check(val, this.min, this.max);
      val = (double) Math.round(val * (1D / this.interval)) / (1D / this.interval);
      this.value = val;
   }

   public static double check(double def, double max, double min) {
      def = Math.max(max, def);
      def = Math.min(min, def);
      return def;
   }

   public static double round(double v, int p) {
      if (p < 0)
         return 0D;
      else {
         BigDecimal bd = new BigDecimal(v);
         bd = bd.setScale(p, RoundingMode.HALF_UP);

         return bd.doubleValue();
      }
   }

}