package de.kyrohpaneup.parkourutils.keybinds;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class KeybindManager {

    private ArrayList<PKKeybind> keybinds = new ArrayList<>();

    public ArrayList<PKKeybind> getKeybinds() {
        return keybinds;
    }

    public void removeKey(PKKeybind keybind) {
        keybinds.remove(keybind);
    }

    public void addKey(PKKeybind keybind) {
        keybinds.add(keybind);
    }

    @SubscribeEvent
    public void checkKeys() {
        for (PKKeybind keybind : keybinds) {
            if (Keyboard.isKeyDown(keybind.getKey())) {
                keybind.onPress();
            }
        }
    }
}
