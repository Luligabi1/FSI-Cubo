package com.github.klima7.client;

import com.github.klima7.RubiksCubeMod;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

import java.util.ArrayList;
import java.util.List;

public final class KeyInit {

    private KeyInit() {}

    public static final List<KeyMapping> KEY_MAPPINGS = new ArrayList<>();

    public static KeyMapping REVERSE = registerKey("reverse", KeyMapping.CATEGORY_GAMEPLAY, InputConstants.KEY_R);

    private static KeyMapping registerKey(String name, String category, int keycode) {
        KeyMapping keyMapping = new KeyMapping("key." + RubiksCubeMod.MODID + "." + name, keycode, category);
        KEY_MAPPINGS.add(keyMapping);
        return keyMapping;
    }

}
