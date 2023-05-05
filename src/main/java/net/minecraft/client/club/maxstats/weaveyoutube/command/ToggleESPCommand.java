package net.minecraft.client.club.maxstats.weaveyoutube.command;

import club.maxstats.weave.loader.api.command.Command;
import net.minecraft.client.club.maxstats.weaveyoutube.listener.RenderListener;

// Class from SMok Client by SleepyFish
public class ToggleESPCommand extends Command {

    private final RenderListener var1;

    public ToggleESPCommand(RenderListener var1) {
        super("sfjoifjwe09fj3e09d09jd0mkewf0omdpoivkjefk3pofkdpodjojdfoi jsf oijcd ofijfoiwjeof  ");
        this.var1 = var1;
    }

    @Override
    public void handle(String[] args) {
        this.var1.render = !this.var1.render;
    }

}