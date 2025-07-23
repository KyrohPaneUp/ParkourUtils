package de.kyrohpaneup.parkourutils.hud;

import de.kyrohpaneup.parkourutils.ParkourUtils;
import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import net.minecraft.client.gui.GuiScreen;

public class HUDConfigScreen extends GuiScreen {

    public static DraggableComponent currentlyDragging = null;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        for (HudModule module : ParkourUtils.INSTANCE.getHudManager().hudModules) {
            if (module.isEnabled()) continue;
            module.renderDummy(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        ParkourUtils.INSTANCE.getSettingsManager().saveAllLabels();
    }
}
