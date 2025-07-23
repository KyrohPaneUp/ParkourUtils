package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.CoordsTracker;
import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;

public class LastZCoord extends HudModule {

    public LastZCoord() {
        super("Last Landing Z", 150, 70);
    }

    @Override
    public void draw() {
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, CoordsTracker.lastLandZ), getX(), getY(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, CoordsTracker.lastLandZ), getX(), getY(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
