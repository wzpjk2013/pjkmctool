package org.pjkmctool.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    public static ModConfig INSTANCE = new ModConfig();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "pjkmctool.json");
    
    private boolean toggleSprint = false;
    private boolean edgeSneak = false;

    public boolean isToggleSprint() {
        return toggleSprint;
    }

    public void setToggleSprint(boolean toggleSprint) {
        this.toggleSprint = toggleSprint;
    }

    public boolean isEdgeSneak() {
        return edgeSneak;
    }

    public void setEdgeSneak(boolean edgeSneak) {
        this.edgeSneak = edgeSneak;
    }

    public static void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            saveConfig();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            ModConfig config = GSON.fromJson(reader, ModConfig.class);
            if (config != null) {
                INSTANCE = config;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}