package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.CoordsTracker;
import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import net.minecraft.client.Minecraft;

public class LastSidestep extends HudModule {

    public LastSidestep() {
        super("Last Strafe Timing", 150, 200);
    }

    @Override
    public void draw() {
        mc = Minecraft.getMinecraft();
        fr = mc.fontRendererObj;
        fr.drawStringWithShadow(ConfigManager.color1.colorCode + name + ConfigManager.color2.colorCode + " " + CoordsTracker.lastStrafe, getX(), getY(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        mc = Minecraft.getMinecraft();
        fr = mc.fontRendererObj;
        fr.drawStringWithShadow(ConfigManager.color1.colorCode + name + ConfigManager.color2.colorCode + " " + CoordsTracker.lastStrafe, getX(), getY(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
