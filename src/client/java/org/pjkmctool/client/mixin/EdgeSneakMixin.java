package org.pjkmctool.client.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.pjkmctool.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class EdgeSneakMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;

        if (ModConfig.INSTANCE.isEdgeSneak()) {
            if (shouldAutoSneak(player)) {
                player.setSneaking(true);
            }
        }
    }

    private boolean shouldAutoSneak(ClientPlayerEntity player) {
        double playerX = player.getX();
        double playerY = player.getY();
        double playerZ = player.getZ();

        BlockPos feetPos = player.getBlockPos().down();
        BlockState feetState = MinecraftClient.getInstance().world.getBlockState(feetPos);
        boolean hasSupport = !feetState.isAir();

        float yawRad = player.getYaw() * ((float) Math.PI / 180.0f);
        double forwardX = -Math.sin(yawRad);
        double forwardZ = Math.cos(yawRad);

        double checkX = playerX + forwardX * 0.2;
        double checkZ = playerZ + forwardZ * 0.2;

        BlockPos checkPos = new BlockPos((int) Math.floor(checkX), (int) Math.floor(playerY - 1), (int) Math.floor(checkZ));
        BlockState checkState = MinecraftClient.getInstance().world.getBlockState(checkPos);
        boolean edgeAhead = checkState.isAir();

        boolean movingForward = MinecraftClient.getInstance().options.forwardKey.isPressed();

        return movingForward && edgeAhead && hasSupport;
    }
}