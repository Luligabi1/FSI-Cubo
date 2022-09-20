package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.client.model.blockentity.StandardRubiksCubeModel;
import com.github.klima7.common.entity.StandardRubiksCubeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class StandardRubiksCubeRenderer extends BaseRubiksCubeRenderer<StandardRubiksCubeBlockEntity> {

    public StandardRubiksCubeRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new StandardRubiksCubeModel());
    }

}
