package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;

public class ZCoord extends HudModule {

    public ZCoord() {
        super("Z", 5, 50);
    }

    @Override
    public void draw() {
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, mc.thePlayer.posZ), getX(), getY(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, mc.thePlayer.posZ), getX(), getY(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
