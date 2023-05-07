package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// Class from SMok Client by SleepyFish
public class FileUtils {

    public static String path = Smok.inst.getClientName().toLowerCase();

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');
        } catch (Exception ignored) { }

        return stringBuilder.toString();
    }

}