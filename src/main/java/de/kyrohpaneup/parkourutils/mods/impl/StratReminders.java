package de.kyrohpaneup.parkourutils.mods.impl;

import de.kyrohpaneup.parkourutils.ParkourUtils;
import de.kyrohpaneup.parkourutils.mods.Category;
import de.kyrohpaneup.parkourutils.mods.Module;
import de.kyrohpaneup.parkourutils.mods.SettingTypes.EnumSetting;
import de.kyrohpaneup.parkourutils.mods.SettingTypes.ExecutionMethod;
import de.kyrohpaneup.parkourutils.mods.SettingTypes.KeySetting;
import de.kyrohpaneup.parkourutils.mods.impl.enums.ShowReminderEnum;
import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import de.kyrohpaneup.parkourutils.stratreminders.SelectMapGui;
import de.kyrohpaneup.parkourutils.stratreminders.strats.CreateStratReminderGui;
import de.kyrohpaneup.parkourutils.stratreminders.strats.StratReminderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StratReminders extends Module {

    private final int setMapID = 1;

    KeySetting createReminder = new KeySetting("Create StratReminder Key", Keyboard.KEY_K);
    KeySetting editReminders = new KeySetting("Edit StratReminders Key", Keyboard.KEY_L);
    KeySetting showReminders = new KeySetting("Show StratReminders Key", Keyboard.KEY_GRAVE);

    EnumSetting toggleReminders = new EnumSetting("Toggle StratReminders", ShowReminderEnum.HOLD);

    ExecutionMethod setMap = new ExecutionMethod("Set Map", setMapID);

    Minecraft mc;

    StratReminderManager srm;

    public StratReminders() {
        super("StratReminders", true, Category.GENERAL, "stratreminders");

        setKeySettings(loadKeys());
        setEnumSettings(loadEnums());

        mc = Minecraft.getMinecraft();
        srm = ParkourUtils.INSTANCE.getStratReminderManager();

        setMethods(loadMethods());
    }

    private List<ExecutionMethod> loadMethods() {
        List<ExecutionMethod> methods = new ArrayList<>();
        methods.add(setMap);
        return methods;
    }

    private List<EnumSetting> loadEnums() {
        List<EnumSetting> settings = new ArrayList<>();
        settings.add(toggleReminders);
        return settings;
    }

    private List<KeySetting> loadKeys() {
        List<KeySetting> settings = new ArrayList<>();
        settings.add(createReminder);
        settings.add(editReminders);
        settings.add(showReminders);
        return settings;
    }

    @Override
    public void execute(int i) {
        switch (i) {
            case setMapID:
                Minecraft.getMinecraft().displayGuiScreen(new SelectMapGui());
                break;
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(createReminder.getKey())) {
            HashMap<String, String> stratMap = new HashMap<>();
            stratMap.put("map id", ConfigManager.mapId);
            mc.displayGuiScreen(new CreateStratReminderGui(stratMap, false));
        } else if (Keyboard.isKeyDown(editReminders.getKey())) {
            HashMap<String, String> stratMap = new HashMap<>();
            if (srm.getAllRemindersMap().isEmpty()) {
                mc.thePlayer.addChatMessage(new ChatComponentText(ConfigManager.getPrefix() + "You have no StratReminders for " + ConfigManager.getMapId() + " yet!"));
                return;
            }
            stratMap.put("map id", ConfigManager.mapId);
            mc.displayGuiScreen(new CreateStratReminderGui(stratMap, true));
        }
    }
        private boolean wasKeyDown = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            boolean isKeyDown = Keyboard.isKeyDown(showReminders.getKey());
            if (toggleReminders.getAnEnum().equals(ShowReminderEnum.HOLD)) {
                StratReminderManager.showReminders = isKeyDown;
            } else if (isKeyDown){

                if (!wasKeyDown) {
                    StratReminderManager.showReminders = !StratReminderManager.showReminders;
                }
            }
            wasKeyDown = isKeyDown;
        }
    }
}
