package de.kyrohpaneup.parkourutils.stratreminders.sheet;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class SheetManager {

    private StratSheet currentSheet = new StratSheet(ConfigManager.getMapId());

    public void loadSheet() {
        String mapId = ConfigManager.mapId;
        String url = "https://script.google.com/macros/s/AKfycbyqvCwJEp8RTheay5u5l2OBaWFUPuwX-31W_qWDOBlaldDNaHkePPW1mV6vdlgPoH0Ukg/exec";
        HttpGet request = new HttpGet(url + "?mapId=" + mapId);  // Achtung: map_id mit Unterstrich

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 1. HTTP-Request
            String jsonResponse = EntityUtils.toString(client.execute(request).getEntity());

            // 2. check if answer is empty
            if (jsonResponse == null || jsonResponse.trim().isEmpty() || jsonResponse.equalsIgnoreCase("null")) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                        ConfigManager.getPrefix() + "There is no sheet for the map " + mapId + "."
                ));
                return;
            }

            // 3. parse json
            Type responseType = new TypeToken<LinkedTreeMap<String, Object>>(){}.getType();
            LinkedTreeMap<String, Object> response = new Gson().fromJson(jsonResponse, responseType);

            // 4. error
            if (response.containsKey("error") && response.get("error") != null) {
                System.err.println("Error: " + response.get("error"));
                return;
            }

            // 5. extract data
            List<LinkedTreeMap<String, Object>> stratSheet = (List<LinkedTreeMap<String, Object>>) response.get("data");
            if (stratSheet == null || stratSheet.isEmpty()) {
                System.out.println("Couldn't find any sheet-data");
                return;
            }

            // 6. Handle data
            this.currentSheet = new StratSheet(mapId);
            currentSheet.updateSheet(stratSheet);


            System.out.println("Successfully loaded " + stratSheet.size() + " entries.");

        } catch (IOException e) {
            System.err.println("Networkerror: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Invalid JSON-Format: " + e.getMessage());
        } catch (ClassCastException e) {
            System.err.println("Dataformat unexpected: " + e.getMessage());
        }
    }

    private void notifyPlayer(String message) {
        if (Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText(ConfigManager.getPrefix() + message)
            );
        }
    }


    public StratSheet getCurrentSheet() {
        return currentSheet;
    }
}
