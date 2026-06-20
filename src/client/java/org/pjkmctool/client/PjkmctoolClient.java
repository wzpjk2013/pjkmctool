package org.pjkmctool.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.pjkmctool.config.ModConfig;

@Environment(EnvType.CLIENT)
public class PjkmctoolClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 加载配置
        ModConfig.loadConfig();
    }
}