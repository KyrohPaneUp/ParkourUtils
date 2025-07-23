package de.kyrohpaneup.parkourutils.mods.SettingTypes;

public class BooleanSetting {
    public String name;
    public boolean statement;

    public BooleanSetting(String name, boolean statement) {
        this.name = name;
        this.statement = statement;
    }

    public boolean isStatement() {
        return statement;
    }

    public void setStatement(boolean statement) {
        this.statement = statement;
    }
}
