package org.pjkmctool.client.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.pjkmctool.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class SprintMixin {

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void onTickMovement(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;

        if (ModConfig.INSTANCE.isToggleSprint()) {
            if (canAutoSprint(player)) {
                player.setSprinting(true);
            }
        }
    }

    private boolean canAutoSprint(ClientPlayerEntity player) {
        // 自动疾跑条件：在陆地上、没有潜行、没有使用物品、饥饿值足够
        return player.isOnGround()
            && !player.isSneaking()
            && !player.isUsingItem()
            && player.getHungerManager().getFoodLevel() > 6;
    }
}