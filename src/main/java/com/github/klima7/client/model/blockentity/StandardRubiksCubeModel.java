package com.github.klima7.client.model.blockentity;

import com.github.klima7.client.ClientMod;
import com.github.klima7.client.texture.core.RubiksCubeTexture;
import com.github.klima7.client.texture.core.RubiksCubeTextureManager;
import com.github.klima7.common.entity.StandardRubiksCubeBlockEntity;
import com.github.klima7.domain.cube.stickers.CubeStickers;
import com.github.klima7.domain.operation.rotation.InstantRotations;
import com.github.klima7.domain.operation.rotation.RotationsSet;
import net.minecraft.resources.ResourceLocation;

public class StandardRubiksCubeModel extends BaseRubiksCubeModel<StandardRubiksCubeBlockEntity> {

    @Override
    public ResourceLocation getTextureResource(StandardRubiksCubeBlockEntity entity) {
        CubeStickers stickers = getStickers(entity);
        return getTextureLocation(entity, stickers);
    }

    private CubeStickers getStickers(StandardRubiksCubeBlockEntity entity) {
        CubeStickers rotatedStickers = CubeStickers.copyOf(entity.getCubeStickers());
        InstantRotations instantRotations = new InstantRotations(RotationsSet.createFromDirection(entity.getFacing()));
        instantRotations.execute(rotatedStickers);
        return rotatedStickers;
    }

    private ResourceLocation getTextureLocation(StandardRubiksCubeBlockEntity entity, CubeStickers stickers) {
        RubiksCubeTextureManager rubiksCubeTextureManager = ClientMod.getInstance().getRubiksCubeTextureManager();
        RubiksCubeTexture texture = rubiksCubeTextureManager.getTexture(entity.getId());
        texture.updateIfNeeded(stickers);
        return texture.getResourceLocation();
    }

}
