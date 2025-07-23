package de.kyrohpaneup.parkourutils.hud.module;

import de.kyrohpaneup.parkourutils.ParkourUtils;
import de.kyrohpaneup.parkourutils.hud.DraggableComponent;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class HudModule {

    public Minecraft mc;
    public FontRenderer fr;

    public String name;
    public boolean enabled;
    public DraggableComponent drag;
    private SettingsManager settingsManager = ParkourUtils.INSTANCE.getSettingsManager();

    public int x, y;

    public HudModule(String name, int x, int y) {
        this.mc = Minecraft.getMinecraft();
        this.fr = mc.fontRendererObj;
        this.name = name;

        try {
            String path = "labels." +  settingsManager.toLowerCase(name);
            this.setEnabled(settingsManager.config.getBoolean("enabled", path, false, ""));
            // boolean used = config.getBoolean("used", path, true, "");
            this.x = settingsManager.config.getInt("x", path, x, 0, 850, "");
            this.y = settingsManager.config.getInt("y", path, y, 0, 550, "");
        } catch (NullPointerException e) {
            e.printStackTrace();
            this.x = x;
            this.y = y;
            this.enabled = false;
        }

        drag = new DraggableComponent(this.x, this.y, getWidth(), getHeight(), new Color(0, 0, 0, 0).getRGB());
    }

    public int getWidth() {
        return 50;
    }

    public int getHeight() {
        return 10;
    }

    public void draw() {

    }

    public void renderDummy(int mouseX, int mouseY) {
        drag.draw(mouseX, mouseY);
        draw();
    }

    public int getX() {
        return drag.getxPosition();
    }

    public int getY() {
        return drag.getyPosition();
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.setEnabled(!enabled);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
