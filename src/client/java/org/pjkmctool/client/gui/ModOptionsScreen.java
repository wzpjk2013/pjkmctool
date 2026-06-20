package org.pjkmctool.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.pjkmctool.config.ModConfig;

public class ModOptionsScreen extends Screen {
    private final Screen parent;
    
    public ModOptionsScreen(Screen parent) {
        super(Text.translatable("pjkmctool.mod_options.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 200;
        int buttonHeight = 20;
        int spacing = 25;
        int startY = this.height / 2 - 50;
        
        ModConfig config = ModConfig.INSTANCE;
        
        // 保持疾跑开关
        this.addDrawableChild(ButtonWidget.builder(
                getToggleText("pjkmctool.option.toggle_sprint", config.isToggleSprint()),
                button -> {
                    config.setToggleSprint(!config.isToggleSprint());
                    button.setMessage(getToggleText("pjkmctool.option.toggle_sprint", config.isToggleSprint()));
                    ModConfig.saveConfig();
                    sendStatusMessage();
                })
                .dimensions(this.width / 2 - buttonWidth / 2, startY, buttonWidth, buttonHeight)
                .build());

        // 边缘潜行开关
        this.addDrawableChild(ButtonWidget.builder(
                getToggleText("pjkmctool.option.edge_sneak", config.isEdgeSneak()),
                button -> {
                    config.setEdgeSneak(!config.isEdgeSneak());
                    button.setMessage(getToggleText("pjkmctool.option.edge_sneak", config.isEdgeSneak()));
                    ModConfig.saveConfig();
                    sendStatusMessage();
                })
                .dimensions(this.width / 2 - buttonWidth / 2, startY + spacing, buttonWidth, buttonHeight)
                .build());

        // 返回按钮
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> {
            ModConfig.saveConfig();
            assert this.client != null;
            this.client.setScreen(parent);
        }).dimensions(this.width / 2 - buttonWidth / 2, startY + spacing * 3, buttonWidth, buttonHeight).build());
    }

    private Text getToggleText(String key, boolean enabled) {
        return Text.translatable(key, 
            enabled ? Text.translatable("options.on").getString() : Text.translatable("options.off").getString());
    }

    private void sendStatusMessage() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            String sprintStatus = ModConfig.INSTANCE.isToggleSprint() ?
                Text.translatable("options.on").getString() : Text.translatable("options.off").getString();
            String sneakStatus = ModConfig.INSTANCE.isEdgeSneak() ?
                Text.translatable("options.on").getString() : Text.translatable("options.off").getString();
            Text message = Text.translatable("pjkmctool.status.message", sprintStatus, sneakStatus);
            client.execute(() -> {
                if (client.player != null) {
                    client.player.sendMessage(message, false);
                }
            });
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0x55FF55);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        ModConfig.saveConfig();
        assert this.client != null;
        this.client.setScreen(parent);
    }
}