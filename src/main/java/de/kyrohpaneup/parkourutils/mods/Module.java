package de.kyrohpaneup.parkourutils.mods;

import de.kyrohpaneup.parkourutils.ParkourUtils;
import de.kyrohpaneup.parkourutils.mods.SettingTypes.*;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Module {

    private final String name;
    private boolean enabled;
    private final Category category;
    private final String imgName;
    private String description = "";
    private List<StringSetting> stringSettings = new ArrayList<>();
    private List<IntegerSetting> integerSettings = new ArrayList<>();
    private List<EnumSetting> enumSettings = new ArrayList<>();
    private List<BooleanSetting> booleanSettings = new ArrayList<>();
    private List<KeySetting> keySettings = new ArrayList<>();
    private List<ExecutionMethod> methods = new ArrayList();

    public Module(String name, boolean enabled, Category category, String imgName) {
        this.name = name;
        this.enabled = enabled;
        this.category = category;
        this.imgName = imgName;
    }

    public void load() {

    }

    public void save() {

    }

    public void toggle() {
        enabled = !enabled;
        ParkourUtils.INSTANCE.getModuleManager().toggle(this);
        onToggle();
    }

    public ResourceLocation getImage() {
        if (imgName != null) {
            return new ResourceLocation("parkourclient", "textures/gui/icons/" + imgName + ".png");
        } else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Category getCategory() {
        return category;
    }

    public List<IntegerSetting> getInts() {
        return integerSettings;
    }

    public List<EnumSetting> getEnums() {
        return enumSettings;
    }

    public List<BooleanSetting> getBooleans() {
        return booleanSettings;
    }

    public List<StringSetting> getStrings() {
        return stringSettings;
    }

    public List<ExecutionMethod> getMethods() {
        return methods;
    }

    public List<KeySetting> getKeySettings() {
        return keySettings;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasSettings() {
        return !(getInts().isEmpty() || getEnums().isEmpty() || getBooleans().isEmpty() || getStrings().isEmpty());
    }

    public void setStringSettings(List<StringSetting> stringSettings) {
        this.stringSettings = stringSettings;
    }

    public void setIntegerSettings(List<IntegerSetting> integerSettings) {
        this.integerSettings = integerSettings;
    }

    public void setEnumSettings(List<EnumSetting> enumSettings) {
        this.enumSettings = enumSettings;
    }

    public void setBooleanSettings(List<BooleanSetting> booleanSettings) {
        this.booleanSettings = booleanSettings;
    }

    public void setKeySettings(List<KeySetting> keySettings) {
        this.keySettings = keySettings;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMethods(List<ExecutionMethod> methods) {
        this.methods = methods;
    }

    public void onToggle() {
        System.out.println("wrong method");
    }

    public void toggleBoolean(BooleanSetting setting) {
        for (BooleanSetting bSetting : booleanSettings) {
            if (bSetting == setting) {
                bSetting.setStatement(!bSetting.isStatement());
                break;
            }
        }
    }

    public void execute(int i) {
        return;
    }

    public <T extends Enum<T> & ModuleEnum> void switchEnum(EnumSetting setting) {
        T current = (T) setting.getAnEnum();
        T[] values = current.getDeclaringClass().getEnumConstants();
        setting.setAnEnum(values[(current.ordinal() + 1) % values.length]);
    }
}
