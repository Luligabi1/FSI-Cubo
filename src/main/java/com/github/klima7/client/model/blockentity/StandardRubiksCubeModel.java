package com.github.klima7.client.model.blockentity;

import com.github.klima7.client.ClientMod;
import com.github.klima7.client.texture.RubiksCubeTexture;
import com.github.klima7.client.texture.RubiksCubeTextureManager;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.operation.rotation.InstantRotations;
import com.github.klima7.common.domain.operation.rotation.RotationsSet;
import com.github.klima7.common.entity.StandardRubiksCubeBlockEntity;
import net.minecraft.resources.ResourceLocation;

public class StandardRubiksCubeModel extends BaseRubiksCubeModel<StandardRubiksCubeBlockEntity> {

    @Override
    public ResourceLocation getTextureResource(StandardRubiksCubeBlockEntity entity) {
        CubeStickers rotatedStickers = CubeStickers.copyOf(entity.getCubeStickers());
        InstantRotations instantRotations = new InstantRotations(RotationsSet.createFromDirection(entity.getFacing()));
        instantRotations.execute(rotatedStickers);

        RubiksCubeTextureManager rubiksCubeTextureManager = ClientMod.getInstance().getRubiksCubeTextureManager();
        RubiksCubeTexture texture = rubiksCubeTextureManager.getTexture(entity.getId());
        texture.updateIfNeeded(rotatedStickers);
        return texture.getResourceLocation();
    }

}
