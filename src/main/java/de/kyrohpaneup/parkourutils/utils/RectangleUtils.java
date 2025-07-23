package de.kyrohpaneup.parkourutils.utils;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RectangleUtils {

    // Zeichnet ein abgerundetes Rechteck (angepasste Version)
    public static void drawRoundedRect(int x, int y, int width, int height, int radius, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        GL11.glDisable(GL11.GL_CULL_FACE);
        // Farbe setzen
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        GlStateManager.color(r, g, b, a);

        // Hauptrechteck
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x + radius, y);
        GL11.glVertex2f(x + width - radius, y);
        GL11.glVertex2f(x + width - radius, y + height);
        GL11.glVertex2f(x + radius, y + height);
        GL11.glEnd();

        // Linke/rechte Seiten
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y + radius);
        GL11.glVertex2f(x + radius, y + radius);
        GL11.glVertex2f(x + radius, y + height - radius);
        GL11.glVertex2f(x, y + height - radius);

        GL11.glVertex2f(x + width - radius, y + radius);
        GL11.glVertex2f(x + width, y + radius);
        GL11.glVertex2f(x + width, y + height - radius);
        GL11.glVertex2f(x + width - radius, y + height - radius);
        GL11.glEnd();

        // Ecken zeichnen (wie zuvor)
        drawCorner(x + radius, y + radius, radius, 180, 270);
        drawCorner(x + width - radius, y + radius, radius, 270, 360);
        drawCorner(x + radius, y + height - radius, radius, 90, 180);
        drawCorner(x + width - radius, y + height - radius, radius, 0, 90);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private static void drawCorner(float x, float y, float radius, int angleStart, int angleEnd) {
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2f(x, y);
        for (int i = angleStart; i <= angleEnd; i++) {
            float rad = (float) Math.toRadians(i);
            GL11.glVertex2f(x + (float) Math.cos(rad) * radius,
                    y + (float) Math.sin(rad) * radius);
        }
        GL11.glEnd();
    }

}
