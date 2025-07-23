package de.kyrohpaneup.parkourutils;

import de.kyrohpaneup.parkourutils.hud.module.HudManager;
import de.kyrohpaneup.parkourutils.keybinds.KeybindManager;
import de.kyrohpaneup.parkourutils.mods.ModuleManager;
import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import de.kyrohpaneup.parkourutils.settings.SettingsManager;
import de.kyrohpaneup.parkourutils.stratreminders.sheet.SheetGui;
import de.kyrohpaneup.parkourutils.stratreminders.sheet.SheetManager;
import de.kyrohpaneup.parkourutils.stratreminders.strats.StratReminderManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

public class ParkourUtils {
    public static final String ID = "parkourutils";
    public static final String NAME = "ParkourUtils";
    public static final String VERSION = "1.0";

    private ConfigManager configManager;
    private StratReminderManager stratReminderManager;
    private SettingsManager settingsManager;
    private ModuleManager moduleManager;
    private HudManager hudManager;
    private KeybindManager keybindManager;
    private SheetManager sheetManager;
    private SheetGui sheetGui;

    public static ParkourUtils INSTANCE;

    public static ParkourUtils getInstance() {
        return INSTANCE;
    }

    public void init() {
        initManagers();
        registerEvents();
    }

    private void initManagers() {
        this.configManager = new ConfigManager(new File(Minecraft.getMinecraft().mcDataDir, ParkourUtils.NAME));
        this.stratReminderManager = new StratReminderManager(configManager);
        this.settingsManager = new SettingsManager();
        this.moduleManager = new ModuleManager();
        this.hudManager = new HudManager();
        this.keybindManager = new KeybindManager();
        this.sheetManager = new SheetManager();
        this.sheetGui = new SheetGui();
    }
    private void registerEvents() {
        MinecraftForge.EVENT_BUS.register(keybindManager);
    }

    public SheetGui getSheetGui() {
        return sheetGui;
    }

    public SheetManager getSheetManager() {
        return sheetManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public StratReminderManager getStratReminderManager() {
        return stratReminderManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public HudManager getHudManager() {
        return hudManager;
    }

    public KeybindManager getKeybindManager() {
        return keybindManager;
    }
}
