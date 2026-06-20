package org.pjkmctool.client;

import net.fabricmc.api.ClientModInitializer;
import org.pjkmctool.config.ModConfig;

public class PjkmctoolClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 加载配置
        ModConfig.loadConfig();
    }
}