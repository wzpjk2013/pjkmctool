package org.pjkmctool.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.pjkmctool.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class SprintMixin {

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;

        if (ModConfig.INSTANCE.isToggleSprint()) {
            if (canAutoSprint(player)) {
                player.setSprinting(true);
            }
        }
    }

    private boolean canAutoSprint(ClientPlayerEntity player) {
        // 只有在陆地上且没有潜行、没有使用物品、没有饥饿耗尽时才自动疾跑
        return player.isOnGround()
            && !player.isSneaking()
            && !player.isUsingItem()
            && player.getHungerManager().getFoodLevel() > 6
            && !isEdgeSneakActive(player);
    }

    private boolean isEdgeSneakActive(ClientPlayerEntity player) {
        // 如果边缘潜行功能开启且玩家正在向前移动，让边缘潜行优先
        return ModConfig.INSTANCE.isEdgeSneak()
            && MinecraftClient.getInstance().options.forwardKey.isPressed();
    }
}