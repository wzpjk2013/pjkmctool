package org.pjkmctool.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.pjkmctool.client.gui.ModOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Text title) {
        super(title);
    }

    @Unique
    @Inject(method = "initWidgets", at = @At("TAIL"))
    private void pjkmctool$addModOptionsButton(CallbackInfo ci) {
        int buttonWidth = 200;
        int buttonHeight = 20;
        int x = 4;
        int y = this.height - 24;

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("pjkmctool.pause.menu_button"),
                button -> MinecraftClient.getInstance().setScreen(new ModOptionsScreen(this))
        ).dimensions(x, y, buttonWidth, buttonHeight).build());
    }
}