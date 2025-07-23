package de.kyrohpaneup.parkourutils.settings.controls;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControlManager {

    public final Map<KeyBinding, Key> keys = new HashMap<>();

    public void loadKeys() {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages("de.kyrohpaneup.parkourclient.settings.controls.impl")
                        .addScanners(new SubTypesScanner())
        );

        Set<Class<? extends Key>> keyClasses = reflections.getSubTypesOf(Key.class);

        for (Class<? extends Key> clazz : keyClasses) {
            try {
                Key key = clazz.newInstance();
                KeyBinding keyBinding = new KeyBinding(
                        key.getName(),
                        key.getStandardKey(),
                        "key.categories.parkour"
                );

                keys.put(keyBinding, key);
                ClientRegistry.registerKeyBinding(keyBinding);

            } catch (Exception e) {
                System.err.println("Failed to load key: " + clazz.getSimpleName());
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        keys.entrySet().stream()
                .filter(entry -> entry.getKey().isPressed())
                .forEach(entry -> {
                    entry.getValue().execute();
                });
    }
}