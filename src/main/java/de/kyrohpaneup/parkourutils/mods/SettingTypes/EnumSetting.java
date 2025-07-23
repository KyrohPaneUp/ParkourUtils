package de.kyrohpaneup.parkourutils.mods.SettingTypes;

public class EnumSetting {
    public String name;
    public ModuleEnum anEnum;

    public EnumSetting(String name, ModuleEnum anEnum) {
        this.name = name;
        this.anEnum =  anEnum;
    }

    public void setAnEnum(ModuleEnum anEnum) {
        this.anEnum = anEnum;
    }

    public ModuleEnum getAnEnum() {
        return anEnum;
    }
}
