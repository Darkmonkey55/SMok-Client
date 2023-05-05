package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.RotateUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;

import java.io.File;
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

    public static void createDir(File file) {
        if (!file.exists())
            file.mkdir();
    }

    public static void createFile(File file) {
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (Exception ignored) { }
    }

}