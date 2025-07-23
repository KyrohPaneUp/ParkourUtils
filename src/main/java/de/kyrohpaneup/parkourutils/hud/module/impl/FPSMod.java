package de.kyrohpaneup.parkourutils.hud.module.impl;

import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import net.minecraft.client.Minecraft;

public class FPSMod extends HudModule {

    public FPSMod() {
        super("FPS", 100, 100);
    }

    @Override
    public void draw() {
        mc = Minecraft.getMinecraft();
        fr = mc.fontRendererObj;
        fr.drawStringWithShadow(ConfigManager.getColor1() + "FPS: " + ConfigManager.getColor2() + mc.getDebugFPS(), getX(), getY(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        fr.drawStringWithShadow(ConfigManager.getColor1() + "FPS: " + ConfigManager.getColor2() + mc.getDebugFPS(), getX(), getY(), -1);

        super.renderDummy(mouseX, mouseY);
    }
}
