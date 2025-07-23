package de.kyrohpaneup.parkourutils.hud.module;

public enum ModuleSettings {

    FPS(100, 100, true),
    XCOORD(5, 30, true),
    YCOORD(5, 40, true),
    ZCOORD(5, 50, true);

    int x;
    int y;
    boolean enabled;

    ModuleSettings(int x, int y, boolean enabled) {
        this.x = x;
        this.y = y;
        this.enabled = enabled;
    }
}
