package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.CoordsTracker;
import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;

public class LastYCoord extends HudModule {

    public LastYCoord() {
        super("Last Landing Y", 150, 100);
    }

    @Override
    public void draw() {
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, CoordsTracker.lastLandY), getX(), getY(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        fr.drawStringWithShadow(SettingsManager.getCoordString(name, CoordsTracker.lastLandY), getX(), getY(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
