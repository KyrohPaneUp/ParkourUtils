package de.kyrohpaneup.parkourutils.mods;

import net.minecraftforge.common.MinecraftForge;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ModuleManager {

    public ArrayList<Module> modules = new ArrayList<>();

    public Set<Module> findAllModules() {

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages("de.kyrohpaneup.parkourutils.mods.impl")
                        .addScanners(new SubTypesScanner(false))
        );

        Set<Class<? extends Module>> moduleClasses =
                reflections.getSubTypesOf(Module.class);
        Set<Module> modules = new HashSet<>();
        for (Class<? extends Module> clazz : moduleClasses) {
            try {
                Module module = clazz.newInstance();
                modules.add(module);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return modules;
    }

    public ModuleManager() {
        modules.addAll(findAllModules());
    }

    public void enableModules() {
        modules.stream().filter(Module::isEnabled).forEach(MinecraftForge.EVENT_BUS::register);
        modules.stream().filter(module -> !module.isEnabled()).forEach(MinecraftForge.EVENT_BUS::unregister);
    }

    public Module getModule(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public void toggle(Module module) {
        if (module.isEnabled()) {
            MinecraftForge.EVENT_BUS.register(module);
        } else {
            MinecraftForge.EVENT_BUS.unregister(module);
        }
    }
}
