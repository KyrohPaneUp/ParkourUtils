package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.CoordsTracker;
import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;
import net.minecraft.client.Minecraft;

public class JumpX extends HudModule {
    public JumpX() {
        super("Jump X", 200, 100);
    }

    @Override
    public void draw() {
        mc = Minecraft.getMinecraft();
        fr = mc.fontRendererObj;
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, CoordsTracker.jumpX), getX(), getY(), -1);
        super.draw();
    }
}
