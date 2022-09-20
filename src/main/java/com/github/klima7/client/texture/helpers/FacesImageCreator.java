package com.github.klima7.client.texture.helpers;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.client.texture.core.RubiksCubeTexture;
import com.github.klima7.client.utils.sprite.Sprite;
import com.github.klima7.client.utils.sprite.SpritesImage;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class FacesImageCreator {

    public static final ResourceLocation FACES_LOCATION =
            new ResourceLocation(RubiksCubeMod.MODID+":textures/block/rubiks_cube/faces.png");

    private static NativeImage facesImage;

    public static NativeImage createFacesImage(ResourceManager resourceManager) {
        constructFaceImageIfNeeded(resourceManager);
        return getFacesImageCopy();
    }

    private static void constructFaceImageIfNeeded(ResourceManager resourceManager) {
        if(facesImage == null) {
            try {
                facesImage = constructFaceImage(resourceManager);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private static NativeImage constructFaceImage(ResourceManager resourceManager) throws IOException {
        SpritesImage spritesImage = new SpritesImage(NativeImage.read(resourceManager.open(FACES_LOCATION)), 16, 16);

        Sprite insideFace = spritesImage.getSprite(0);
        Sprite outsideFace = spritesImage.getSprite(1);

        NativeImage facesImage = new NativeImage(RubiksCubeTexture.WIDTH, RubiksCubeTexture.HEIGHT, true);

        insideFace.blit(facesImage, 0, 0);
        outsideFace.blit(facesImage, 0, 16);
        outsideFace.blit(facesImage, 16, 16);
        outsideFace.blit(facesImage, 32, 16);
        outsideFace.blit(facesImage, 48, 16);
        outsideFace.blit(facesImage, 16, 0);
        outsideFace.blit(facesImage, 16, 32);

        return facesImage;
    }

    private static NativeImage getFacesImageCopy() {
        NativeImage copy = new NativeImage(RubiksCubeTexture.WIDTH, RubiksCubeTexture.HEIGHT, true);
        copy.copyFrom(facesImage);
        return copy;
    }

}
