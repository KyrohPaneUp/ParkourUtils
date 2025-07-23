package de.kyrohpaneup.parkourutils.stratreminders.strats;

import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import de.kyrohpaneup.parkourutils.stratreminders.SelectMapGui;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
import java.util.stream.Collectors;

public class StratReminderManager {
    private SelectMapGui mapGui = new SelectMapGui();
    private final ConfigManager configManager;
    public static boolean hideComments = true;
    public static boolean showReminders = false;

    public StratReminderManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void toggleReminders() {

    }

    // --------------- CRUD-Operationen ---------------
    public void addReminder(StratReminder reminder) {
        configManager.saveReminder(reminder);
    }

    public void updateReminder(StratReminder reminder) {
        configManager.updateReminder(reminder);
    }

    public void removeReminder(String reminderId) {
        configManager.deleteReminder(reminderId);
    }

    // --------------- Abfragen ---------------
    public List<StratReminder> getCurrentMapReminders() {
        return configManager.getRemindersByMap(ConfigManager.mapId);
    }

    public List<StratReminder> getAllRemindersList() {
        List<StratReminder> allReminders = new ArrayList<>();
        configManager.getAllRemindersMap().values().forEach(allReminders::addAll);
        return allReminders;
    }

    public List<Map<String, String>> getAllRemindersMap() {
        List<StratReminder> allReminders = getCurrentMapReminders();
        List<Map<String, String>> list = new ArrayList<>();
        for (StratReminder reminder : allReminders) {
            list.add(toSimpleMap(reminder));
        }
        return list;
    }

    public List<String> getAllMaps() {
        List<StratReminder> reminders = getAllRemindersList();
        return reminders.stream()
                .map(StratReminder::getMap)
                .filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    public StratReminder getReminder(String id) {
        return configManager.getReminder(id);
    }

    // --------------- Rendering ---------------
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (showReminders) {
            getCurrentMapReminders().forEach(reminder ->
                    reminder.render(event.partialTicks)
            );
        }
    }

    // --------------- Hilfsmethoden ---------------
    public Map<String, String> toSimpleMap(StratReminder reminder) {
        Map<String, String> map = new HashMap<>();
        map.put("id", reminder.getId());
        map.put("map", reminder.map);
        map.put("position", reminder.position);
        map.put("facing", reminder.facing);
        map.put("setup", reminder.setup);
        map.put("strat", reminder.input);
        map.put("comment", reminder.comment);
        map.put("x", String.valueOf(reminder.x));
        map.put("y", String.valueOf(reminder.y));
        map.put("z", String.valueOf(reminder.z));
        return map;
    }
}