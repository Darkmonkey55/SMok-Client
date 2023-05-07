package net.minecraft.client.me.sleepyfish.smok.gui.guis;

import net.minecraft.client.me.sleepyfish.smok.Smok;
import net.minecraft.client.me.sleepyfish.smok.utils.MouseUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.SoundUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.FriendUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Animation;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.Direction;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.normal.impl.EaseBackIn;
import net.minecraft.client.me.sleepyfish.smok.utils.animation.simple.SimpleAnimation;
import net.minecraft.client.me.sleepyfish.smok.utils.font.FontUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.GlUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RenderUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.RoundedUtils;
import net.minecraft.client.me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class FriendsGui extends GuiScreen {

    private int guiFriends;
    private float guiFriendsX;
    private float guiFriendsY;
    private boolean guiAllowDraggable;

    private boolean close;
    private Animation introAnimation;

    public FriendsGui() {
        this.guiFriends = 170;
        this.guiFriendsX = 130;
        this.guiFriendsY = 80;

        this.guiAllowDraggable = false;
    }

    @Override
    public void initGui() {
        introAnimation = new EaseBackIn(450, 1, 2);
        close = false;
    }

    @Override
    public void drawScreen(int x, int y, float b) {
        RenderUtils.drawAuthor(this.width, this.height);

        int count = 0;

        if (guiAllowDraggable) {
            this.guiFriendsX = x - (guiFriends / 2F);
            this.guiFriendsY = y;
        }

        if (close) {
            introAnimation.setDirection(Direction.BACKWARDS);
            if (introAnimation.isDone(Direction.BACKWARDS))
                mc.displayGuiScreen(null);
        }

        GlUtils.startScale(((guiFriendsX) + (guiFriendsX + guiFriends)) / 2, ((guiFriendsY) + (guiFriendsY + 145)) / 2, (float) introAnimation.getValue());

        ColorUtils.clearColor();
        RoundedUtils.drawGradientRoundLR(this.guiFriendsX, this.guiFriendsY, guiFriends, 145, 4, ColorUtils.getBackgroundColor(5), ColorUtils.getBackgroundColor(5).darker());

        ColorUtils.clearColor();
        RoundedUtils.drawGradientRoundLR(this.guiFriendsX + 2, this.guiFriendsY + 2, guiFriends - 4, 2, 2, ColorUtils.getClientColor(1), ColorUtils.getClientColor(19999));

        ColorUtils.clearColor();
        RoundedUtils.drawRound(this.guiFriendsX + guiFriends - 12, this.guiFriendsY + 133, 10, 10, 1, ColorUtils.getBackgroundColor(3));

        if (FriendUtils.getFriends() != null) {
            ColorUtils.clearColor();
            RoundedUtils.drawRound(this.guiFriendsX + 2, this.guiFriendsY + 133, 62, 10, 2, ColorUtils.getBackgroundColor(3));
            ColorUtils.clearColor();
            FontUtils.r20.drawStringWithShadow("Delete Friend", this.guiFriendsX + 3, this.guiFriendsY + 135, ColorUtils.getFontColor(1).getRGB());

            ColorUtils.clearColor();
            for (Entity friend : FriendUtils.getFriends()) {
                if (count > 2)
                    return;

                ResourceLocation skin = ((AbstractClientPlayer) friend).getLocationSkin();

                float off = this.guiFriendsY + (count * 40F) + 8F;

                if (skin != null) {
                    ColorUtils.clearColor();
                    mc.getTextureManager().bindTexture(skin);
                    RenderUtils.drawScaledCustomSizeModalRect(this.guiFriendsX + 5, off + 3, 3, 3, 3, 3, 30, 30, 24, 24.5F);
                    ColorUtils.clearColor();
                }

                if (mc.thePlayer.getTeam() != null)
                    if (mc.thePlayer.isOnSameTeam((EntityLivingBase) friend))
                        FriendUtils.addFriend(friend);

                FontUtils.r20.drawStringWithShadow(friend.getName(), this.guiFriendsX + 37, off + 9, ColorUtils.getFontColor(1).getRGB());
                FontUtils.r20.drawStringWithShadow("Lvl: " + ((AbstractClientPlayer) friend).experienceLevel, this.guiFriendsX + 37, off + 19, ColorUtils.getFontColor(1).getRGB());

                count++;

                if (Smok.inst.debugMode)
                    Gui.drawRect((int) this.guiFriendsX + 2, (int) off + 1, (int) this.guiFriendsX + guiFriends - 2, (int) off + 1 + 35, 0xCCAA0000);
                else if (MouseUtils.isInside(x, y, this.guiFriendsX + 2, off + 1, guiFriends - 4, 35)) {
                    RoundedUtils.drawRound(this.guiFriendsX + 2, off + 1, guiFriends - 4, 35, 2, ColorUtils.getBackgroundColor(4, 80));

                    if (friend.getPosition() != null) {
                        if (mc.thePlayer.getEntityWorld() != friend.getEntityWorld())
                            FriendUtils.removeFriend(friend);

                        FontUtils.r16.drawString("X: " + (int) friend.posX, this.guiFriendsX + 127, off + 5, ColorUtils.getFontColor(1).getRGB());
                        FontUtils.r16.drawString("Y: " + (int) friend.posY, this.guiFriendsX + 127, off + 15, ColorUtils.getFontColor(1).getRGB());
                        FontUtils.r16.drawString("Z: " + (int) friend.posZ, this.guiFriendsX + 127, off + 25, ColorUtils.getFontColor(1).getRGB());
                    } else {
                        if (((AbstractClientPlayer) friend).getGameProfile().getProperties() != null)
                            FontUtils.r16.drawString("Session: " + ((AbstractClientPlayer) friend).getGameProfile().getProperties().get("session.id"), this.guiFriendsX + 77, off + 24, ColorUtils.getFontColor(1).getRGB());
                        else
                            FontUtils.r16.drawString("session.id", this.guiFriendsX + 127, off + 25, ColorUtils.getFontColor(1).getRGB());
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
        if (MouseUtils.isInside(x, y, this.guiFriendsX + 2, this.guiFriendsY + 133, 62, 10) && b == MouseUtils.MOUSE_LEFT) {
            SoundUtils.playSound(SoundUtils.click, 1F, 0.8F);

            if (FriendUtils.getFriends() != null)
                for (Entity friend : FriendUtils.getFriends()) {
                    FriendUtils.removeFriend(friend);
                    return;
                }
        }

        if (MouseUtils.isInside(x, y, this.guiFriendsX + 2, this.guiFriendsY + 2, guiFriends, 10)) {
            if (b == MouseUtils.MOUSE_LEFT)
                this.guiAllowDraggable = true;
        }
    }

    @Override
    public void mouseReleased(int x, int y, int b) {
        if (guiAllowDraggable)
            this.guiAllowDraggable = false;
    }

}