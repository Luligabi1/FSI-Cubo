package com.github.klima7.client.model.blockentity;

import com.github.klima7.client.ClientMod;
import com.github.klima7.client.texture.RubiksCubeTexture;
import com.github.klima7.client.texture.RubiksCubeTextureManager;
import com.github.klima7.common.entity.StickerlessRubiksCubeBlockEntity;
import net.minecraft.resources.ResourceLocation;

public class StickerlessRubiksCubeModel extends AbstractRubiksCubeModel<StickerlessRubiksCubeBlockEntity> {

    @Override
    public ResourceLocation getTextureResource(StickerlessRubiksCubeBlockEntity entity) {
        RubiksCubeTextureManager rubiksCubeTextureManager = ClientMod.getInstance().getRubiksCubeTextureManager();
        RubiksCubeTexture texture = rubiksCubeTextureManager.getStickerlessTexture();
        return texture.getResourceLocation();
    }

}
