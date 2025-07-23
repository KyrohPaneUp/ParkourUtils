package de.kyrohpaneup.parkourutils.mods.impl;

import de.kyrohpaneup.parkourutils.mods.Category;
import de.kyrohpaneup.parkourutils.mods.Module;
import de.kyrohpaneup.parkourutils.mods.SettingTypes.BooleanSetting;
import de.kyrohpaneup.parkourutils.mods.SettingTypes.KeySetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ToggleSprint extends Module {

    BooleanSetting toggleSprint = new BooleanSetting("ToggleSprint", false);
    BooleanSetting toggleSneak = new BooleanSetting("ToggleSneak", false);

    KeySetting toggleSprintKey = new KeySetting("Toggle Sprinting", Keyboard.KEY_CAPITAL);

    private boolean sprintingToggled = false;
    private boolean sneakingToggled = false;

    public ToggleSprint() {
        super("ToggleSprint", false, Category.GENERAL, "togglesprint");
        setBooleanSettings(loadBooleans());
        setKeySettings(loadKeys());
    }

    private List<BooleanSetting> loadBooleans() {
        List<BooleanSetting> settings = new ArrayList<>();
        settings.add(toggleSprint);
        settings.add(toggleSneak);
        return settings;
    }

    private List<KeySetting> loadKeys() {
        List<KeySetting> settings = new ArrayList<>();
        settings.add(toggleSprintKey);
        return settings;
    }

    @SubscribeEvent
    public void onTickStart(TickEvent event) {

        if (event.phase.equals(TickEvent.Phase.END)) {
            if (toggleSprint.isStatement() && sprintingToggled) {
                if (Minecraft.getMinecraft().thePlayer != null) {
                    GameSettings settings = Minecraft.getMinecraft().gameSettings;
                    KeyBinding.setKeyBindState(settings.keyBindSprint.getKeyCode(), true);
                }
            }
        }
            if (toggleSneak.isStatement() && sneakingToggled) {
                if (Minecraft.getMinecraft().thePlayer != null) {
                    GameSettings settings = Minecraft.getMinecraft().gameSettings;
                    KeyBinding.setKeyBindState(settings.keyBindSneak.getKeyCode(), true);
                }
            }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        if (toggleSprint.isStatement() && Keyboard.isKeyDown(toggleSprintKey.getKey())) {
            sprintingToggled = !sprintingToggled;

            if (!sprintingToggled) {
                KeyBinding.setKeyBindState(settings.keyBindSprint.getKeyCode(), false);
            }
        } else if (toggleSneak.isStatement() && settings.keyBindSneak.isPressed()) {
            KeyBinding.setKeyBindState(settings.keyBindSneak.getKeyCode(), true);
            sneakingToggled = !sneakingToggled;
        }
    }
}
