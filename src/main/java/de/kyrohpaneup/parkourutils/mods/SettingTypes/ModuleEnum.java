package de.kyrohpaneup.parkourutils.mods.SettingTypes;

public interface ModuleEnum {

    default String getString() {
        return this.toString();
    }
}
