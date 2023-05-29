package net.minecraft.client.me.sleepyfish.smok.gui.guis;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.MouseUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.SoundUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FriendUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Animation;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Direction;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.impl.EaseBackIn;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.GlUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class FriendsGui extends GuiScreen {

    private final int guiFriendsWidth;
    private float guiFriendsX;
    private float guiFriendsY;
    private boolean guiAllowDraggable;

    private boolean close;
    private Animation introAnimation;

    public FriendsGui() {
        this.guiFriendsWidth = 170;
        this.guiFriendsX = 130;
        this.guiFriendsY = 80;

        this.guiAllowDraggable = false;
    }

    @Override
    public void initGui() {
        introAnimation = new EaseBackIn(450, 1, 0.5F);
        close = false;
    }

    @Override
    public void drawScreen(int x, int y, float b) {
        RenderUtils.drawAuthor(this.width, this.height);

        FontUtils.r20.drawString("To dumb to open the ClickGui?, well u clicked Backspace and not Delete", 5, 5, ColorUtils.getFontColor(1));

        int count = 0;

        if (guiAllowDraggable) {
            guiFriendsX = x - (guiFriendsWidth / 2F);
            guiFriendsY = y;
        }

        if (close) {
            introAnimation.setDirection(Direction.BACKWARDS);
            if (introAnimation.isDone(Direction.BACKWARDS))
                mc.displayGuiScreen(null);
        }

        if (FriendUtils.getFriends() != null) {
            GlUtils.startScale(((guiFriendsX) + (guiFriendsX + guiFriendsWidth)) / 2, ((guiFriendsY) + (guiFriendsY + 145)) / 2, (float) introAnimation.getValue());

            ColorUtils.clearColor();
            RoundedUtils.drawGradientRoundLR(guiFriendsX, guiFriendsY, guiFriendsWidth, 145, 4, ColorUtils.getBackgroundColor(5), ColorUtils.getBackgroundColor(5).darker());

            ColorUtils.clearColor();
            RoundedUtils.drawGradientRoundLR(guiFriendsX + 2, guiFriendsY + 2, guiFriendsWidth - 4, 2, 2, ColorUtils.getClientColor(1), ColorUtils.getClientColor(19999));

            RoundedUtils.drawRound(guiFriendsX + 2, guiFriendsY + 133, 62, 10, 2, ColorUtils.getBackgroundColor(3));
            ColorUtils.clearColor();
            FontUtils.r20.drawStringWithShadow("Delete Friend", guiFriendsX + 3, guiFriendsY + 135, ColorUtils.getFontColor(2));

            if (FriendUtils.getFriends().size() == 0)
                FontUtils.r16.drawString("You dont have Friends", guiFriendsX + guiFriendsWidth / 2F - FontUtils.r16.getStringWidth("You dont have Friends") / 2F, guiFriendsY + (145 / 2F) - 3, ColorUtils.getBackgroundColor(3).brighter().brighter());

            ColorUtils.clearColor();
            for (Entity friend : FriendUtils.getFriends()) {
                if (mc.theWorld == null)
                    if (FriendUtils.isFriend(friend))
                        FriendUtils.removeFriend(friend);

                if (count > 2)
                    return;

                ResourceLocation skin = ((AbstractClientPlayer) friend).getLocationSkin();

                float off = guiFriendsY + (count * 40F) + 8F;

                if (skin != null) {
                    ColorUtils.clearColor();
                    mc.getTextureManager().bindTexture(skin);
                    RenderUtils.drawScaledCustomSizeModalRect(guiFriendsX + 5, off + 3, 3, 3, 3, 3, 30, 30, 24, 24.5F);
                    ColorUtils.clearColor();
                }

                if (mc.thePlayer.getTeam() != null)
                    if (mc.thePlayer.isOnSameTeam((EntityLivingBase) friend))
                        if (!FriendUtils.isFriend(friend))
                            FriendUtils.addFriend(friend);

                FontUtils.r20.drawStringWithShadow(friend.getName(), guiFriendsX + 37, off + 9, ColorUtils.getFontColor(1));
                FontUtils.r20.drawStringWithShadow("Lvl: " + ((AbstractClientPlayer) friend).experienceLevel, guiFriendsX + 37, off + 19, ColorUtils.getFontColor(1));

                count++;

                if (Smok.inst.debugMode)
                    Gui.drawRect((int) guiFriendsX + 2, (int) off + 1, (int) guiFriendsX + guiFriendsWidth - 2, (int) off + 1 + 35, 0xCCAA0000);
                else if (MouseUtils.isInside(x, y, guiFriendsX + 2, off + 1, guiFriendsWidth - 4, 35)) {
                    RoundedUtils.drawRound(guiFriendsX + 2, off + 1, guiFriendsWidth - 4, 35, 2, ColorUtils.getBackgroundColor(4, 80));

                    if (friend.getPosition() != null) {
                        if (mc.thePlayer.getEntityWorld() != friend.getEntityWorld())
                            FriendUtils.removeFriend(friend);

                        FontUtils.r16.drawString("X: " + (int) friend.posX, guiFriendsX + 127, off + 6, ColorUtils.getFontColor(1));
                        FontUtils.r16.drawString("Y: " + (int) friend.posY, guiFriendsX + 127, off + 16, ColorUtils.getFontColor(1));
                        FontUtils.r16.drawString("Z: " + (int) friend.posZ, guiFriendsX + 127, off + 26, ColorUtils.getFontColor(1));
                    }
                }
            }
        }


        GL11.glPopMatrix();
    }

    @Override
    public void keyTyped(char c, int i) {
        if (i == 1)
            close = true;
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        if (MouseUtils.isInside(x, y, guiFriendsX + 2, guiFriendsY + 133, 62, 10) && b == MouseUtils.MOUSE_LEFT) {
            SoundUtils.playSound(SoundUtils.click, 1F, 0.8F);

            if (FriendUtils.getFriends() != null) {
                for (Entity friend : FriendUtils.getFriends()) {
                    FriendUtils.removeFriend(friend);
                    return;
                }
            }
        }

        if (MouseUtils.isInside(x, y, guiFriendsX + 2, guiFriendsY + 2, guiFriendsWidth, 10)) {
            if (b == MouseUtils.MOUSE_LEFT)
                guiAllowDraggable = true;
        }
    }

    @Override
    public void mouseReleased(int x, int y, int b) {
        if (guiAllowDraggable)
            guiAllowDraggable = false;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}