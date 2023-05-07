package net.minecraft.client.me.sleepyfish.smok.rats.settings;

// Class from SMok Client by SleepyFish
public class ModeSetting<T extends Enum<?>> extends SettingHelper {

    private T currentOption;
    private T[] options;

    public ModeSetting(String name, T _default) {
        super(name);

        this.currentOption = _default;
        try {
            this.options = (T[]) _default.getClass().getMethod("values").invoke(null);
        } catch (Exception ignored) {
        }
    }

    public String bigRatName() {
        return "mode";
    }

    public T getMode() {
        return this.currentOption;
    }

    public void setMode(T smok) {
        this.currentOption = smok;
    }

    public void nextMode() {
        if (options == null) return;

        for (int i = 0; i < options.length; i++)
            if (options[i] == currentOption)
                if (i != (options.length - 1)) {
                    currentOption = options[(i + 1) % (options.length)];
                    return;
                }
    }

    public void prevMode() {
        if (options == null) return;

        for (int i = 0; i < options.length; i++)
            if (options[i] == currentOption)
                if (i != -0) {
                    currentOption = options[(i - 1) % (options.length)];
                    return;
                }

    }

}