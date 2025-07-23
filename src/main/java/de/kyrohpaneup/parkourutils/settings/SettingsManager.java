package de.kyrohpaneup.parkourutils.settings;

import de.kyrohpaneup.parkourutils.ParkourUtils;
import de.kyrohpaneup.parkourutils.hud.module.HudModule;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class SettingsManager {

    public static String getCoordString(String name, double coords) {
        String string = ConfigManager.getColor1() + name + ": " + ConfigManager.getColor2() + String.format("%." + ConfigManager.digits + "f", coords);
        string = string.replace(",", ".");
        return string;
    }

    public String toLowerCase(String string) {
        string = string.replace(" ", "");
        string = string.toLowerCase();
        return string;
    }

    public Configuration config;

    public void init(File file) {
        File configFile = new File(file, "parkourclient.cfg");
        config = new Configuration(configFile);
        loadConfig();
    }


    private void loadConfig() {
        try {
            config.load();
        } catch (Exception e) {
            System.out.println("Error loading config: " + e.getMessage());
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    public void loadLabels() {
        for (HudModule module : ParkourUtils.INSTANCE.getHudManager().hudModules) {
            System.out.println(toLowerCase(module.name));
            String path = "labels." + toLowerCase(module.name);
            boolean enabled = config.getBoolean("enabled", path, false, "");
            boolean used = config.getBoolean("used", path, true, "");
            int x = config.getInt("x", path, 0, 0, 500, "");
            int y = config.getInt("y", path, 0, 0, 500, "");

            module.setEnabled(enabled);
            module.setX(x);
            module.setY(y);
        }
    }

    public void saveAllLabels() {
        for (HudModule module : ParkourUtils.INSTANCE.getHudManager().hudModules) {
            saveLabel(module);
        }
    }

    public void saveLabel(HudModule module) {
        String path = "labels." + toLowerCase(module.name);
        boolean enabled = module.isEnabled();
        boolean used = true;
        int x = module.getX();
        int y = module.getY();

        config.get(path, "enabled", enabled).set(enabled);
        config.get(path, "used", used).set(used);
        config.get(path, "x", x).set(x);
        config.get(path, "y", y).set(y);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void setColor(String colorNumber, Color color) {
        switch (colorNumber) {
            case "1":
                ConfigManager.color1 = color;
                break;
            case "2":
                ConfigManager.color2 = color;
                break;
            default:
                break;
        }
    }
}
