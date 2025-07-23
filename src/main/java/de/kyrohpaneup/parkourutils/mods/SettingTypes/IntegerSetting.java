package de.kyrohpaneup.parkourutils.mods.SettingTypes;

public class IntegerSetting {
    String name;
    int current;
    int minimum;
    int maximum;

    public IntegerSetting(String name, int current, int minimum, int maximum) {
        this.name = name;
        this.current = current;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public String getName() {
        return name;
    }

    public int getCurrent() {
        return current;
    }

    public int getMaximum() {
        return maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public void setName(String name) {
        this.name = name;
    }
}
