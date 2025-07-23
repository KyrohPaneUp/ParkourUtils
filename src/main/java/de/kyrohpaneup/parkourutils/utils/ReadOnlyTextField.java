package de.kyrohpaneup.parkourutils.utils;

import net.minecraft.client.gui.GuiTextField;

public class ReadOnlyTextField extends GuiTextField {

    private String column = "";

    public ReadOnlyTextField(int id, net.minecraft.client.gui.FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(id, fontRenderer, x, y, width, height);
        this.setEnableBackgroundDrawing(true);
        this.setMaxStringLength(Integer.MAX_VALUE);
    }

    @Override
    public boolean textboxKeyTyped(char typedChar, int keyCode) {

        if (keyCode == 203 || keyCode == 205 || keyCode == 14 || keyCode == 199 || keyCode == 207) {

            return super.textboxKeyTyped(typedChar, keyCode);
        }
        return false;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}