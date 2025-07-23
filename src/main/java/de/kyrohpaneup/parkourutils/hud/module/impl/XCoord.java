package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;

public class XCoord extends HudModule {

    public XCoord() {
        super("X", 5, 30);
    }

    @Override
    public void draw() {
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, mc.thePlayer.posX), getX(), getY(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, mc.thePlayer.posX), getX(), getY(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
