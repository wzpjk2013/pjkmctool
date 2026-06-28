package org.pjkmctool.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.pjkmctool.client.gui.ModOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class PauseScreenMixin {

    @Unique
    @Inject(method = "initWidgets", at = @At("TAIL"))
    private void pjkmctool$addModOptionsButton(CallbackInfo ci) {
        GameMenuScreen self = (GameMenuScreen) (Object) this;
        int buttonWidth = 200;
        int buttonHeight = 20;
        int x = 4;
        int y = self.height - 24;

        self.addDrawableChild(ButtonWidget.builder(
                Text.translatable("pjkmctool.pause.menu_button"),
                button -> MinecraftClient.getInstance().setScreen(new ModOptionsScreen(self))
        ).dimensions(x, y, buttonWidth, buttonHeight).build());
    }
}