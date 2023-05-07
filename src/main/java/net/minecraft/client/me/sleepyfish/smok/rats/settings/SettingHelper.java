package net.minecraft.client.me.sleepyfish.smok.rats.settings;

// Class from SMok Client by SleepyFish
public abstract class SettingHelper {

   public String settingName;

   public abstract String bigRatName();

   public SettingHelper(String name) {
      this.settingName = name;
   }

   public String getSettingName() {
      return this.settingName;
   }

}