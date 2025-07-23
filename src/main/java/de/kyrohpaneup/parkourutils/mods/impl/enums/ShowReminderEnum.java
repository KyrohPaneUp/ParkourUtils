package de.kyrohpaneup.parkourutils.mods.impl.enums;

import de.kyrohpaneup.parkourutils.mods.SettingTypes.ModuleEnum;

public enum ShowReminderEnum implements ModuleEnum {

    HOLD("Hold"),
    TOGGLE("Toggle");

    String string;

    ShowReminderEnum(String string) {
        this.string = string;
    }

}
