package net.minecraft.client.me.sleepyfish.smok.utils;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.rats.Rat;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.Notification;
import net.minecraft.client.me.sleepyfish.smok.utils.render.notifications.NotificationManager;
import net.minecraft.util.ChatComponentText;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Desktop;

// Class from SMok Client by SleepyFish
public class ClientUtils {

    public static void notify(String title, String message, Notification.Icon category, long seconds) {
        NotificationManager.show(new Notification(title, message, category, seconds * 1000L));
    }

    public static String getSeed() {
        return "" + Smok.inst.mc.getIntegratedServer().worldServerForDimension(0).getSeed();
    }

    public static void browse(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateClientVersion() {
        Smok.inst.serverVersion = ClientUtils.getDoubleFromUrl(Rat.Var.client_version_server);
        ClientUtils.addDebug("updated server double");
    }

    public static double getDoubleFromUrl(String url) {
        try {
            URL web = new URL(url);
            URLConnection connection = web.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String valueString = reader.readLine();

            return Double.parseDouble(valueString);
        } catch (Exception e) {
            return 9.9D;
        }
    }

    public static String getStringFromUrl(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();

                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                in.close();

                return response.toString();
            } else
                return "failed to get string from url";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "get some wifi before launching";
    }

    public static boolean isHypickle() {
        if (Smok.inst.mc.isSingleplayer())
            return false;
        else
            return Smok.inst.mc.getCurrentServerData().serverIP.toLowerCase().startsWith("hypixel");
    }

    public static boolean inClickGui() {
        return Smok.inst.mc.currentScreen == Smok.inst.guiManager.getClickGui();
    }

    public static List<String> StringListToList(String[] strings) {
        List<String> smok = new ArrayList<>();
        Collections.addAll(smok, strings);
        return smok;
    }

    /**
     * @Info: Dont forget the ChatFormatting !
     */
    public static void addMessage(String message) {
        Smok.inst.mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static void addDebug(String message) {
        if (Smok.inst.debugMode && Smok.inst.mc.thePlayer != null)
            Smok.inst.mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }

}