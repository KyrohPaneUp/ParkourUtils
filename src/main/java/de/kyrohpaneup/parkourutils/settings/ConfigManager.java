package de.kyrohpaneup.parkourutils.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.kyrohpaneup.parkourutils.stratreminders.strats.StratReminder;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final File srFile;
    private final File gnrlFile;
    private final Map<String, StratReminder> remindersById = new ConcurrentHashMap<>();
    private final Map<String, List<StratReminder>> remindersByMap = new ConcurrentHashMap<>();

    // General Settings
    public static Color color1 = Color.PINK;
    public static Color color2 = Color.WHITE;
    public static String mapId = "";
    public static int digits = 3;

    public ConfigManager(File configDir) {
        this.gnrlFile = new File(configDir, "settings.json");
        this.srFile = new File(configDir, "stratreminders.json");
        loadAll();
    }

    public static String getColor1() {
        return color1.colorCode;
    }

    public static String getColor2() {
        return color2.colorCode;
    }

    public static String getPrefix() {
        return color1.colorCode + "[PKC] " + color2.colorCode;
    }

    private void loadAll() {
        loadGeneralSettings();
        loadStratReminders();
    }

    // -----------------------------------------
    // ----------- GeneralSettings -------------
    // -----------------------------------------

    public void setColor(Color color, String colorInt) {
        switch (colorInt) {
            case "1":
                color1 = color;
                System.out.println(color);
                break;
            case "2":
                color2 = color;
                break;
            default:
                break;
        }
        saveGeneralSettings();
    }

    public static String getMapId() {
        return mapId;
    }

    public void setMapId(String str) {
        mapId = str;
        saveGeneralSettings();
    }

    private void loadGeneralSettings() {
        if (!gnrlFile.exists()) {
            saveGeneralSettings(); // Erstelle Datei mit Standardwerten
            return;
        }

        try (Reader reader = new FileReader(gnrlFile)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);

            if (json.has("primaryColor")) {
                color1 = Color.valueOf(json.get("primaryColor").getAsString());
            }
            if (json.has("secondaryColor")) {
                color2 = Color.valueOf(json.get("secondaryColor").getAsString());
            }
            if (json.has("mapId")) {
                mapId = json.get("mapId").getAsString();
            }
        } catch (Exception e) {
            System.err.println("Failed to load settings: " + e.getMessage());
            saveGeneralSettings(); // Erstelle/resette bei Fehler
        }
    }

    private void saveGeneralSettings() {
        try (Writer writer = new FileWriter(gnrlFile)) {
            JsonObject json = new JsonObject();
            json.addProperty("primaryColor", color1.name());
            json.addProperty("secondaryColor", color2.name());
            json.addProperty("mapId", mapId);
            GSON.toJson(json, writer);
        } catch (Exception e) {
            System.err.println("Failed to save settings: " + e.getMessage());
        }
    }

    // -----------------------------------------
    // ----------- StratReminders --------------
    // -----------------------------------------

    // --------------- Haupt-API ---------------
    public void saveReminder(StratReminder reminder) {
        if (reminder.getId() == null) {
            reminder.setId(UUID.randomUUID().toString());
        }

        if (remindersById.containsKey(reminder.getId())) {
            deleteReminder(reminder.getId());
        }

        remindersById.put(reminder.getId(), reminder);
        remindersByMap.computeIfAbsent(reminder.map, k -> new ArrayList<>()).add(reminder);
        saveReminderFile();
    }

    public void updateReminder(StratReminder reminder) {
        if (remindersById.containsKey(reminder.getId())) {
            saveReminder(reminder);
        }
    }

    public void deleteReminder(String id) {
        System.out.println("DELETERING");
        StratReminder reminder = remindersById.remove(id);
        if (reminder != null) {
            remindersByMap.get(reminder.map).remove(reminder);
            saveReminderFile();
        }
    }

    // --------------- Getter ---------------
    public StratReminder getReminder(String id) {
        return remindersById.get(id);
    }

    public List<StratReminder> getRemindersByMap(String map) {
        return Collections.unmodifiableList(
                remindersByMap.getOrDefault(map, Collections.emptyList())
        );
    }

    public Map<String, List<StratReminder>> getAllRemindersMap() {
        return Collections.unmodifiableMap(remindersByMap);
    }

    // --------------- Persistenz ---------------
    private void loadStratReminders() {
        if (!srFile.exists()) {
            saveReminderFile(); // Erstelle leere Datei
            return;
        }

        try (Reader reader = new FileReader(srFile)) {
            JsonObject data = GSON.fromJson(reader, JsonObject.class);
            data.entrySet().forEach(entry -> {
                entry.getValue().getAsJsonArray().forEach(element -> {
                    StratReminder reminder = GSON.fromJson(element, StratReminder.class);
                    if (reminder.getId() == null) {
                        reminder.setId(UUID.randomUUID().toString());
                    }
                    remindersById.put(reminder.getId(), reminder);
                    remindersByMap.computeIfAbsent(reminder.map, k -> new ArrayList<>())
                            .add(reminder);
                });
            });
        } catch (Exception e) {
            System.err.println("Failed to load reminders: " + e.getMessage());
        }
    }

    private synchronized void saveReminderFile() {
        try (Writer writer = new FileWriter(srFile)) {
            JsonObject data = new JsonObject();
            remindersByMap.forEach((map, reminders) -> {
                JsonArray array = new JsonArray();
                reminders.forEach(r -> array.add(GSON.toJsonTree(r)));
                data.add(map, array);
            });
            GSON.toJson(data, writer);
        } catch (Exception e) {
            System.err.println("Failed to save reminders: " + e.getMessage());
        }
    }
}