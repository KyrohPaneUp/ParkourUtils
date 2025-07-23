package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.CoordsTracker;
import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;
import net.minecraft.client.Minecraft;

public class JumpZ extends HudModule {
    public JumpZ() {
        super("Jump Z", 200, 160);
    }

    @Override
    public void draw() {
        mc = Minecraft.getMinecraft();
        fr = mc.fontRendererObj;
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, CoordsTracker.jumpZ), getX(), getY(), -1);
        super.draw();
    }
}
