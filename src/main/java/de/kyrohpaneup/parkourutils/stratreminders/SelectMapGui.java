package de.kyrohpaneup.parkourutils.stratreminders;

import de.kyrohpaneup.parkourutils.ParkourUtils;
import de.kyrohpaneup.parkourutils.settings.ConfigManager;
import de.kyrohpaneup.parkourutils.stratreminders.strats.StratReminderManager;
import de.kyrohpaneup.parkourutils.utils.GuiTextFieldWithStringID;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectMapGui extends GuiScreen {

    private final ConfigManager cm = ParkourUtils.INSTANCE.getConfigManager();
    private final StratReminderManager srm = ParkourUtils.INSTANCE.getStratReminderManager();

    private GuiTextFieldWithStringID textField;
    private GuiButton selectButton;
    private final List<GuiTextField> textFields = new ArrayList<>();
    private final List<GuiButton> scrollButtons = new ArrayList<>();

    private boolean init = false;
    private int scrolls = 0;
    private int visibleRows;

    @Override
    public void initGui() {
        textFields.clear();
        buttonList.clear();
        scrollButtons.clear();

        scrolls = 0;
        int scrollHeight = this.height - 40;
        visibleRows = Math.max(1, scrollHeight / 25); // mindestens 1 sichtbare Zeile

        int center = this.width / 2;
        int width = this.width / 10;
        int height = 20;
        int x = center - (width / 2);
        int y = (this.height / 2) - (height) - 5;

        this.textField = new GuiTextFieldWithStringID(1, fontRendererObj, x, y, width, height);
        textField.setFocused(true);

        int bWidth = this.width / 10;
        int bHeight = 20;
        int bX = center - (bWidth / 2);
        int bY = (this.height / 2) + 5;
        this.selectButton = new GuiButton(-1, bX, bY, bWidth, bHeight, "Select");
        this.buttonList.add(selectButton);

        loadPreviousMaps();
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        if (!init) {
            textField.setText(ConfigManager.getMapId());
            init = true;
        }

        selectButton.enabled = textField.getText() != null && !textField.getText().trim().isEmpty();
        textField.drawTextBox();

        scrollButtons.clear();
        int y = 20;
        for (int i = scrolls; i < scrolls + visibleRows && i < textFields.size(); i++) {
            GuiTextField field = textFields.get(i);
            field.yPosition = y;
            field.drawTextBox();

            GuiButton btn = new GuiButton(field.getId(), field.xPosition + field.getWidth() + field.getWidth() / 5, y, 20, 20, "");
            btn.drawButton(mc, mouseX, mouseY);
            scrollButtons.add(btn);

            y += 25;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int mouseWheelDelta = Mouse.getEventDWheel();

        if (mouseWheelDelta != 0) {
            if (mouseWheelDelta > 0) {
                scrolls = Math.max(0, scrolls - 1);
            } else {
                scrolls = Math.min(Math.max(0, textFields.size() - visibleRows), scrolls + 1);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        textField.mouseClicked(mouseX, mouseY, mouseButton);

        for (int i = scrolls; i < scrolls + visibleRows && i < textFields.size(); i++) {
            textFields.get(i).mouseClicked(mouseX, mouseY, mouseButton);
        }

        for (GuiButton btn : scrollButtons) {
            if (btn.mousePressed(mc, mouseX, mouseY)) {
                handleScrollButtonClick(btn.id);
            }
        }
    }

    private void handleScrollButtonClick(int id) {
        textFields.stream()
                .filter(tf -> tf.getId() == id)
                .findFirst()
                .ifPresent(tf -> textField.setText(tf.getText()));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        textField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == this.selectButton) {
            if (textField.getText() == null || textField.getText().trim().isEmpty()) return;

            cm.setMapId(textField.getText());
            mc.displayGuiScreen(null);
        }
        super.actionPerformed(button);
    }

    private void loadPreviousMaps() {
        List<String> maps = srm.getAllMaps();
        int id = 1;

        for (String map : maps) {
            createMap(map, id++);
        }
    }

    private void createMap(String map, int id) {
        int x = 25;
        GuiTextField field = new GuiTextField(id, fontRendererObj, x, 0, this.width / 10, 20);
        field.setText(map);
        textFields.add(field);
    }
}
