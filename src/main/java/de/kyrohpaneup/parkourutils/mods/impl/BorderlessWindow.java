package de.kyrohpaneup.parkourutils.mods.impl;

import de.kyrohpaneup.parkourutils.mods.Category;
import de.kyrohpaneup.parkourutils.mods.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class BorderlessWindow extends Module {

    public BorderlessWindow() {
        super("BorderlessWindow", true, Category.GENERAL, "borderlesswindow");
    }

    boolean lastFullscreen = false;

    // boolean fullScreenNow = Minecraft.func_71410_x().func_71372_G();
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            boolean fullScreenNow = Minecraft.getMinecraft().isFullScreen();
            if (this.lastFullscreen != fullScreenNow) {
                this.fix(fullScreenNow);
                this.lastFullscreen = fullScreenNow;
            }
        }
    }

    @Override
    public void onToggle() {
        if (true) return;
        try {
            if (!isEnabled()) {
                Minecraft mc = Minecraft.getMinecraft();
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setResizable(false);
                Display.setFullscreen(true);
            } else {
                fix(lastFullscreen);
            }
        } catch (LWJGLException var3) {
            var3.printStackTrace();
        }
    }
    
    //Display.setDisplayMode(new DisplayMode(Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d));
    public void fix(boolean fullscreen) {
        try {
            if (fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setLocation(0, 0);
                Display.setFullscreen(false);
                Display.setResizable(false);
            } else {
                Minecraft mc = Minecraft.getMinecraft();
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(mc.displayWidth, mc.displayHeight));
                Display.setResizable(true);
            }
        } catch (LWJGLException var3) {
            var3.printStackTrace();
        }

    }
}
