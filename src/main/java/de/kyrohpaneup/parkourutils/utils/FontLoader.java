package de.kyrohpaneup.parkourutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;

public class FontLoader extends FontRenderer {

    public Font font;

    private static final String FONT_PATH = "parkourclient:fonts/SourceSans3-Regular.ttf";

    public FontLoader(int size) {
        super(Minecraft.getMinecraft().gameSettings,
                new ResourceLocation("textures/font/ascii.png"),
                Minecraft.getMinecraft().getTextureManager(),
                false);

        try {
            InputStream is = ResourceLoader.getResource(new ResourceLocation(FONT_PATH));
            /*
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation(FONT_PATH)).getInputStream(); */
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, size);
            this.setFont(font);
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback auf Standardfont
            this.setFont(new Font("Arial", Font.PLAIN, size));
        }

        this.setUnicodeFlag(false);
        this.setBidiFlag(false);
    }

    private void setFont(Font font) {
        this.font = font;
        this.FONT_HEIGHT = (int)(font.getSize() * 0.9);
    }
}
