package de.kyrohpaneup.parkourutils.mods.SettingTypes;

public class ExecutionMethod {

    int id;
    String name;

    public ExecutionMethod(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
