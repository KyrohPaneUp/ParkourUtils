package de.kyrohpaneup.parkourutils.mods.impl;

import de.kyrohpaneup.parkourutils.ParkourUtils;
import de.kyrohpaneup.parkourutils.mods.Category;
import de.kyrohpaneup.parkourutils.mods.Module;
import de.kyrohpaneup.parkourutils.mods.SettingTypes.ExecutionMethod;
import de.kyrohpaneup.parkourutils.mods.SettingTypes.KeySetting;
import de.kyrohpaneup.parkourutils.settings.Color;
import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import de.kyrohpaneup.parkourutils.stratreminders.SelectMapGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class StratSheets extends Module {

    final int openStratID = 1;
    final int openTagID = 2;
    final int setMapID = 3;

    KeySetting openStratSheetKey = new KeySetting("Open Strat Sheet Key", Keyboard.KEY_U);

    ExecutionMethod setStratSheetMap = new ExecutionMethod("Set Map", setMapID);
    ExecutionMethod openStratSheet = new ExecutionMethod("Open Strat Sheet", openStratID);
    ExecutionMethod openTagSheet = new ExecutionMethod("Open Tag Sheet", openTagID);

    public StratSheets() {
        super("StratSheets", true, Category.GENERAL, "stratsheets");

        setKeySettings(loadKeys());
        setMethods(loadMethods());
    }

    private List<ExecutionMethod> loadMethods() {
        List<ExecutionMethod> methods = new ArrayList<>();
        methods.add(setStratSheetMap);
        methods.add(openStratSheet);
        methods.add(openTagSheet);
        return methods;
    }

    private List<KeySetting> loadKeys() {
        List<KeySetting> settings = new ArrayList<>();
        settings.add(openStratSheetKey);
        return settings;
    }

    @Override
    public void execute(int i) {
        switch (i) {
            case openStratID:
                sendLink("www.docs.google.com/spreadsheets/d/1IJKfGsxa-vg3_EoVc8y5E0fek_bTSzawAaNMfFsfja0/edit", "Strat");
                break;
            case openTagID:
                sendLink("www.docs.google.com/spreadsheets/d/1DaA4u1ybNF0Vb50vu_X-XUrpdvf12pvKCoNucdV1XSc/edit", "Tag");
                break;
            case setMapID:
                Minecraft.getMinecraft().displayGuiScreen(new SelectMapGui());
                break;
        }
    }

    private void sendLink(String url, String type) {
        Minecraft mc = Minecraft.getMinecraft();
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(url));
            } else {
                mc.thePlayer.addChatMessage(new ChatComponentText(
                        ConfigManager.getPrefix() + type + " Sheet:"
                ));
                mc.thePlayer.addChatMessage(new ChatComponentText(
                        Color.AQUA.colorCode + url
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(openStratSheetKey.getKey())) {
            Minecraft.getMinecraft().displayGuiScreen(ParkourUtils.INSTANCE.getSheetGui());
        }
    }
}
