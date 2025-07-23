package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.CoordsTracker;
import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;
import net.minecraft.client.Minecraft;

public class LastXCoord extends HudModule {

    public LastXCoord() {
        super("Last Landing X", 150, 130);
    }

    @Override
    public void draw() {
        mc = Minecraft.getMinecraft();
        fr = mc.fontRendererObj;
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, CoordsTracker.lastLandX), getX(), getY(), -1);
        super.draw();
    }
}
