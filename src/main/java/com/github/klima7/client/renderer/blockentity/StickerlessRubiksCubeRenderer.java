package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.client.model.blockentity.StickerlessRubiksCubeModel;
import com.github.klima7.common.entity.StickerlessRubiksCubeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class StickerlessRubiksCubeRenderer extends AbstractRubiksCubeRenderer<StickerlessRubiksCubeBlockEntity> {

    public StickerlessRubiksCubeRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new StickerlessRubiksCubeModel());
    }

}
