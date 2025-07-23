package de.kyrohpaneup.parkourutils.mods.impl;

import de.kyrohpaneup.parkourutils.mods.Category;
import de.kyrohpaneup.parkourutils.mods.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InventoryJam extends Module {

    Minecraft mc;

    public InventoryJam() {
        super("InventoryJam", false, Category.GENERAL, "invjam");
        mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onGuiClose(GuiOpenEvent event) {
        if (event.gui == null) {
            syncMovementKeys();
        }
    }
    private void syncMovementKeys() {
        checkKey(mc.gameSettings.keyBindForward);
        checkKey(mc.gameSettings.keyBindBack);
        checkKey(mc.gameSettings.keyBindLeft);
        checkKey(mc.gameSettings.keyBindRight);
        checkKey(mc.gameSettings.keyBindJump);
        checkKey(mc.gameSettings.keyBindSprint);
    }

    private void checkKey(KeyBinding key) {
        int keyCode = key.getKeyCode();
        boolean isDown = org.lwjgl.input.Keyboard.isKeyDown(keyCode);
        KeyBinding.setKeyBindState(keyCode, isDown);
    }

}
