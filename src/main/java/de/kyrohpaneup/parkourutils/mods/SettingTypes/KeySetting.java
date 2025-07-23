package de.kyrohpaneup.parkourutils.mods.SettingTypes;

public class KeySetting {
    String name;
    int key;

    public KeySetting(String name, int key) {
        this.name = name;
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
