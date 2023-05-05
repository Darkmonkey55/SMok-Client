package net.minecraft.client.me.sleepyfish.smok.gui.comp;

// Class from SMok Client by SleepyFish
public interface IComp {

    void draw();

    void update(int x, int y);

    void mouseDown(int x, int y, int b);

    void mouseReleased(int x, int y, int b);

    void setComponentStartAt(int n);

    void keyTyped(char chara, int key);

    int getHeight();

    int getY();

}