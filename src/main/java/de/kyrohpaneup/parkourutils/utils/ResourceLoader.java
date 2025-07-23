package de.kyrohpaneup.parkourutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {
    public static InputStream getResource(ResourceLocation location) {
        // if (Boolean.getBoolean("debug.resources")) {
        if (false) {
            // Debug-Modus
            String path = "src/main/resources/assets/"
                    + location.getResourceDomain() + "/"
                    + location.getResourcePath();
            try {
                return new FileInputStream(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Produktionsmodus
        try {
            return Minecraft.getMinecraft().getResourceManager()
                    .getResource(location).getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + location, e);
        }
    }
}
