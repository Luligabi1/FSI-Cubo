package com.github.klima7.client.texture;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.client.utils.sprite.Sprite;
import com.github.klima7.client.utils.sprite.SpritesImage;
import com.github.klima7.common.domain.cube.stickers.Sticker;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.EnumMap;

public class StickerSpritesManager {

    public static final ResourceLocation STICKERS_LOCATION =
            new ResourceLocation(RubiksCubeMod.MODID+":textures/block/rubiks_cube_stickers.png");

    public static final int STICKER_SIZE = 4;

    private final EnumMap<Sticker, Sprite> sprites;

    public StickerSpritesManager(ResourceManager resourceManager) throws IOException {
        sprites = getSprites(resourceManager);
    }

    public Sprite getSpriteForSticker(Sticker sticker) {
        return sprites.get(sticker);
    }

    private EnumMap<Sticker, Sprite> getSprites(ResourceManager resourceManager) throws IOException {
        SpritesImage spritesImage = new SpritesImage(NativeImage.read(resourceManager.open(STICKERS_LOCATION)), STICKER_SIZE, STICKER_SIZE);

        EnumMap<Sticker, Sprite> sprites = new EnumMap<>(Sticker.class);
        sprites.put(Sticker.WHITE, spritesImage.getSprite(0));
        sprites.put(Sticker.YELLOW, spritesImage.getSprite(1));
        sprites.put(Sticker.RED, spritesImage.getSprite(2));
        sprites.put(Sticker.ORANGE, spritesImage.getSprite(3));
        sprites.put(Sticker.BLUE, spritesImage.getSprite(4));
        sprites.put(Sticker.GREEN, spritesImage.getSprite(5));

        return sprites;
    }

}
