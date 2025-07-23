package de.kyrohpaneup.parkourutils.hud.module;

import net.minecraft.client.Minecraft;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HudManager {

    public ArrayList<HudModule> hudModules = new ArrayList<>();

    public Set<HudModule> findAllHudModules() {

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages("de.kyrohpaneup.parkourclient.hud.module.impl")
                        .addScanners(new SubTypesScanner(false))
        );


        Set<Class<? extends HudModule>> moduleClasses =
                reflections.getSubTypesOf(HudModule.class);
        Set<HudModule> modules = new HashSet<>();
        for (Class<? extends HudModule> clazz : moduleClasses) {
            try {
                HudModule module = clazz.newInstance();
                modules.add(module);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return modules;
    }

    public HudManager() {
        hudModules.addAll(findAllHudModules());
    }

    public void renderModules() {
        for (HudModule module : hudModules) {
            if (module.isEnabled()) continue;
            if (!Minecraft.getMinecraft().gameSettings.showDebugInfo) {
                module.draw();
            }
        }
    }
}
