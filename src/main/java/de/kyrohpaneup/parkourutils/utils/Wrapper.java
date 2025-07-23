package de.kyrohpaneup.parkourutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Wrapper {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static FontRenderer fontRenderer = mc.fontRendererObj;
}
