package net.minecraft.client.me.sleepyfish.smok.rats.settings;

// Class from SMok Client by SleepyFish
public class BoolSetting extends SettingHelper {

   private boolean isEnabled;
   private final String name;

   public BoolSetting(String name, boolean _default) {
      super(name);
      this.name = name;
      this.isEnabled = _default;
   }

   public String bigRatName() {
      return "tick";
   }

   public boolean isToggled() {
      return this.isEnabled;
   }

   public void toggle() {
      this.isEnabled = !this.isEnabled;
   }

   public String getSettingName() {
      return this.name;
   }

}