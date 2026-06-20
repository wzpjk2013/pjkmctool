package org.pjkmctool.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.pjkmctool.client.gui.ModOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At("TAIL"))
    private void addModOptionsButton(CallbackInfo ci) {
        // 添加模组设置按钮到左下角
        int buttonWidth = 200;
        int buttonHeight = 20;
        int x = 4; // 左边距
        int y = this.height - 24; // 底部边距
        
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("pjkmctool.pause.menu_button"), button -> {
            MinecraftClient client = MinecraftClient.getInstance();
            client.setScreen(new ModOptionsScreen(this));
        }).dimensions(x, y, buttonWidth, buttonHeight).build());
    }
}