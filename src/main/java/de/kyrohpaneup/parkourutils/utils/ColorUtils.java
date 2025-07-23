package de.kyrohpaneup.parkourutils.utils;

public class ColorUtils {

    public static int getColorFromCode(String colorCode) {
        switch (colorCode) {
            case "§0": return 0xCC000000; // black
            case "§1": return 0xCC0000AA; // dark blue
            case "§2": return 0xCC00AA00; // dark green
            case "§3": return 0xCC00AAAA; // dark aqua
            case "§4": return 0xCCAA0000; // dark red
            case "§5": return 0xCCAA00AA; // dark purple
            case "§6": return 0xCCFFAA00; // gold
            case "§7": return 0xCCAAAAAA; // gray
            case "§8": return 0xCC555555; // dark gray
            case "§9": return 0xCC5555FF; // blue
            case "§a": return 0xCC55FF55; // green
            case "§b": return 0xCC55FFFF; // aqua
            case "§c": return 0xCCFF5555; // red
            case "§d": return 0xCCFF55FF; // light purple
            case "§e": return 0xCCFFFF55; // yellow
            case "§f": return 0xCCFFFFFF; // white
            default: return 0xCCFFFFFF; // fallback: white
        }
    }
}
