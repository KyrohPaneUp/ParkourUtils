package de.kyrohpaneup.parkourutils.stratreminders.sheet;

import com.google.gson.internal.LinkedTreeMap;
import de.kyrohpaneup.parkourutils.ParkourUtils;
import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import de.kyrohpaneup.parkourutils.stratreminders.SelectMapGui;
import de.kyrohpaneup.parkourutils.stratreminders.strats.CreateStratReminderGui;
import de.kyrohpaneup.parkourutils.utils.ReadOnlyTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SheetGui extends GuiScreen {

    private SheetManager sm = ParkourUtils.INSTANCE.getSheetManager();
    private StratSheet stratSheet;
    private List<LinkedTreeMap<String, Object>> sheet;
    private List<List<ReadOnlyTextField>> textFields = new ArrayList<>();
    private int screenSize;
    private int scrollOffset = 0;
    private int visibleRows = 15;
    private int rowHeight = 20;
    private final int copyButtonID = 1;
    private final int createButtonID = 2;
    private final int selectMapButtonID = 3;
    private final int reloadButtonID = 4;
    private boolean isScrolling = false;
    private List<ReadOnlyTextField> selectedRow = new ArrayList<>();

    private void reloadGui() {
        this.scrollOffset = 0;
        this.isScrolling = false;
        initGui();
    }
    @Override
    public void initGui() {
        screenSize = this.width;
        stratSheet = ParkourUtils.INSTANCE.getSheetManager().getCurrentSheet();
        if (stratSheet == null) {
            Minecraft.getMinecraft().displayGuiScreen(null);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    ConfigManager.getPrefix() + "Failed to load the sheet for " + stratSheet.map + "!"
            ));
            return;
        }
        sheet = stratSheet.sheet;
        textFields.clear();

        if (sheet == null || sheet.isEmpty()) {
            Minecraft.getMinecraft().displayGuiScreen(null);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    ConfigManager.getPrefix() + "Failed to load the sheet for " + stratSheet.map + "!"
            ));
            return;
        }
        for (int i = 0; i < sheet.size(); i++) {
            LinkedTreeMap<String, Object> map = sheet.get(i);
            List<ReadOnlyTextField> rowFields = new ArrayList<>();
            int x = 20;

            ReadOnlyTextField rowField = new ReadOnlyTextField(i * 1000 + rowFields.size(), fontRendererObj, x, 0, getWidth("Row"), rowHeight);
            rowField.setText(String.valueOf(i));
            rowFields.add(rowField);
            x += rowField.width;

            for (String key : map.keySet()) {
                ReadOnlyTextField textField = new ReadOnlyTextField(i * 1000 + rowFields.size(), fontRendererObj, x, 0, getWidth(key), rowHeight);
                textField.setText(map.get(key).toString());
                textField.setColumn(key);
                rowFields.add(textField);
                x += textField.width;
            }
            textFields.add(rowFields);
        }
        int width = screenSize / 100 * 20;
        int buttonY =  20 + rowHeight + 5 + rowHeight * visibleRows + 20;

        this.buttonList.clear();
        GuiButton copyButton = new GuiButton(copyButtonID, screenSize / 2 + width / 5, buttonY, width, 20, "Copy Strat");
        this.buttonList.add(copyButton);
        copyButton.enabled = false;

        GuiButton createButton = new GuiButton(createButtonID, screenSize / 2 - width - width / 5, buttonY, width, 20, "Create StratReminder");
        this.buttonList.add(createButton);
        createButton.enabled = false;

        int sWidth = screenSize / 10;
        int sHeight = 20;
        int sX = screenSize - sWidth - 5;
        int sY = this.height - sHeight - 5;

        GuiButton selectMapButton = new GuiButton(selectMapButtonID, sX, sY, sWidth, sHeight, "Select Map");
        this.buttonList.add(selectMapButton);

        int rWidth = screenSize / 10;
        int rHeight = 20;
        int rX = 5;
        int rY = this.height - sHeight - 5;

        GuiButton reloadButton = new GuiButton(reloadButtonID, rX, rY, rWidth, rHeight, "Reload");
        this.buttonList.add(reloadButton);

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int headerY = 20;
        if (!textFields.isEmpty()) {
            List<ReadOnlyTextField> firstRow = textFields.get(0);
            int x = 20;

            drawString(fontRendererObj, "Row", x + 2, headerY + (rowHeight / 2) - (fontRendererObj.FONT_HEIGHT / 2), Color.WHITE.getRGB());
            x += getWidth("Row");

            for (ReadOnlyTextField field : firstRow) {
                if (field.getColumn() != null) {
                    int stringX = field.xPosition + (field.getWidth() - fontRendererObj.getStringWidth(field.getColumn())) / 2;
                    drawString(fontRendererObj,
                            field.getColumn(),
                            stringX,
                            headerY + (rowHeight / 2) - (fontRendererObj.FONT_HEIGHT / 2),
                            Color.WHITE.getRGB()
                    );
                    x += field.getWidth();
                }
            }

            drawRect(20, headerY + rowHeight, width - 20, headerY + rowHeight + 1, Color.GRAY.getRGB());
        }

        int y = headerY + rowHeight + 5;
        int endRow = Math.min(scrollOffset + visibleRows, textFields.size());

        for (int i = scrollOffset; i < endRow; i++) {
            List<ReadOnlyTextField> rowFields = textFields.get(i);
            for (ReadOnlyTextField field : rowFields) {
                field.yPosition = y;
                field.drawTextBox();
            }
            y += rowHeight;
        }

        drawScrollBar();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void setSelectedRow(List<ReadOnlyTextField> selectedRow) {
        this.selectedRow = selectedRow;

        for (GuiButton button : this.buttonList) {
            if (button.id == selectMapButtonID || button.id == reloadButtonID) continue;
            button.enabled = selectedRow != null;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        switch (button.id) {
            case copyButtonID:
                if (selectedRow == null) {
                    return;
                }
                setClipboardString(getCopyString());
                break;
            case createButtonID:
                if (selectedRow == null) {
                    return;
                }
                HashMap<String, String> reminderMap = new HashMap<>();
                for (ReadOnlyTextField field : selectedRow) {
                    if (field.getColumn().contains("Tip")) {
                        reminderMap.put("comment", field.getText());
                    } else {
                        reminderMap.put(field.getColumn().toLowerCase(), field.getText());
                    }
                }
                reminderMap.put("map id", stratSheet.map);

                CreateStratReminderGui reminderGui = new CreateStratReminderGui(reminderMap, false);
                mc.displayGuiScreen(reminderGui);
                break;
            case selectMapButtonID:
                mc.displayGuiScreen(new SelectMapGui());
                break;
            case reloadButtonID:
                sm.loadSheet();
                reloadGui();
                break;
        }
        super.actionPerformed(button);
    }

    private String getCopyString() {
        String position = "";
        String facing = "";
        String setup = "";
        String strat = "";

        for (ReadOnlyTextField field : selectedRow) {
            if (field.getText().isEmpty()) {
                continue;
            }

            switch (field.getColumn().toLowerCase()) {
                case "position":
                    position = field.getText() + " ";
                    break;
                case "facing":
                    facing = field.getText() + " ";
                    break;
                case "setup":
                    setup = field.getText() + " ";
                    break;
                case "strat":
                    strat = field.getText();
                    break;
            }
        }
        return position + facing + setup + strat;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        // Scrollbar-Handling
        int scrollBarX = width - 10;
        int scrollBarY = 20;
        int scrollBarHeight = visibleRows * rowHeight;

        if (mouseX >= scrollBarX && mouseX <= scrollBarX + 8 &&
                mouseY >= scrollBarY && mouseY <= scrollBarY + scrollBarHeight) {
            isScrolling = true;
            return;
        }
        setSelectedRow(null);
        for (List<ReadOnlyTextField> rowFields : textFields) {
            for (ReadOnlyTextField field : rowFields) {
                boolean mouseOver = mouseX >= field.xPosition && mouseX < field.xPosition + field.width &&
                        mouseY >= field.yPosition && mouseY < field.yPosition + field.height;

                if (mouseOver) {
                    if (isShiftKeyDown()) {
                        for (ReadOnlyTextField rowField : rowFields) {
                            rowField.setTextColor(Color.GREEN.getRGB());
                        }
                        setSelectedRow(rowFields);
                    } else {
                        field.setFocused(true);
                        field.setCursorPositionEnd();
                    }
                    break;
                } else {
                    field.setTextColor(Color.WHITE.getRGB());
                    field.setFocused(false);
                }
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (isScrolling) {
            float scrollRatio = (float)(mouseY - 20) / (visibleRows * rowHeight);
            scrollOffset = (int)(scrollRatio * (sheet.size() - visibleRows));
            scrollOffset = Math.max(0, Math.min(scrollOffset, sheet.size() - visibleRows));
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        isScrolling = false;
        super.mouseReleased(mouseX, mouseY, state);
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
        for (List<ReadOnlyTextField> rowFields : textFields) {
            for (ReadOnlyTextField field : rowFields) {
                if (field.isFocused()) {
                    field.textboxKeyTyped(typedChar, keyCode);
                    break;
                }
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int mouseWheelDelta = Mouse.getEventDWheel();
        if (mouseWheelDelta != 0) {
            if (mouseWheelDelta > 0) {
                scrollOffset = Math.max(0, scrollOffset - 1);
            } else {
                scrollOffset = Math.min(sheet.size() - visibleRows, scrollOffset + 1);
            }
        }
    }

    private void drawScrollBar() {
        int scrollBarX = width - 10;
        int scrollBarY = 20;
        int scrollBarHeight = visibleRows * rowHeight;

        drawRect(scrollBarX, scrollBarY, scrollBarX + 8, scrollBarY + scrollBarHeight, 0xFF888888);

        if (sheet.size() > visibleRows) {
            int scrollButtonHeight = Math.max(10, scrollBarHeight * visibleRows / sheet.size());
            int scrollButtonY = scrollBarY + (scrollOffset * (scrollBarHeight - scrollButtonHeight) / Math.max(1, sheet.size() - visibleRows));
            drawRect(scrollBarX, scrollButtonY, scrollBarX + 8, scrollButtonY + scrollButtonHeight, 0xFF555555);
        }
    }

    private int getWidth(String string) {
        switch (string.toLowerCase()) {
            case "section":
            case "facing":
                return screenSize / 100 * 7;
            case "jump":
            case "position":
                return screenSize / 100 * 12;
            case "strat":
                return screenSize / 100 * 10;
            case "strafe":
            case "turn":
            case "by":
                return screenSize / 100 * 6;
            case "setup":
            case "tips":
                return screenSize / 100 * 14;
            case "row":
                return screenSize / 100 * 4;
        }
        return 0;
    }
}