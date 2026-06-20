package org.pjkmctool.client.gui;

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

        int buttonWidth = 150;
        int buttonHeight = 20;
        int spacing = 25;
        
        // 获取当前配置值
        ModConfig config = ModConfig.INSTANCE;
        
        // 创建“保持疾跑”复选框
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("pjkmctool.option.toggle_sprint", 
                    config.isToggleSprint() ? 
                        Text.translatable("options.on").getString() : 
                        Text.translatable("options.off").getString()),
                button -> {
                    // 切换疾跑状态
                    boolean newValue = !config.isToggleSprint();
                    config.setToggleSprint(newValue);
                    // 同步到游戏选项（通过 SprintMixin 自动处理）
                    // 更新按钮文本
                    button.setMessage(Text.translatable("pjkmctool.option.toggle_sprint", 
                        config.isToggleSprint() ? 
                            Text.translatable("options.on").getString() : 
                            Text.translatable("options.off").getString()));
                    // 立即保存配置
                    ModConfig.saveConfig();
                })
                .dimensions(this.width / 2 - buttonWidth / 2, this.height / 2 - 40, buttonWidth, buttonHeight)
                .build());

        // 创建“方块边缘自动潜行”复选框
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("pjkmctool.option.edge_sneak", 
                    config.isEdgeSneak() ? 
                        Text.translatable("options.on").getString() : 
                        Text.translatable("options.off").getString()),
                button -> {
                    // 切换边缘潜行状态
                    config.setEdgeSneak(!config.isEdgeSneak());
                    // 更新按钮文本
                    button.setMessage(Text.translatable("pjkmctool.option.edge_sneak", 
                        config.isEdgeSneak() ? 
                            Text.translatable("options.on").getString() : 
                            Text.translatable("options.off").getString()));
                    // 立即保存配置
                    ModConfig.saveConfig();
                })
                .dimensions(this.width / 2 - buttonWidth / 2, this.height / 2 - 40 + spacing, buttonWidth, buttonHeight)
                .build());

        // 返回按钮
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> {
            // 保存配置（虽然已经在每次修改时保存，但这里再保存一次以确保）
            ModConfig.saveConfig();
            // 返回父界面
            assert this.client != null;
            this.client.setScreen(parent);
        }).dimensions(this.width / 2 - buttonWidth / 2, this.height / 2 - 40 + spacing * 3, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        // 保存配置
        ModConfig.saveConfig();
        assert this.client != null;
        this.client.setScreen(parent);
    }
}