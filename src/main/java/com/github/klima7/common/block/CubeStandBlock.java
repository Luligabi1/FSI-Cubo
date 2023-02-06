package com.github.klima7.common.block;

import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

public class CubeStandBlock extends Block implements EntityBlock {

    public static final Properties PROPERTIES = Properties
            .of(Material.GLASS)
            .color(MaterialColor.COLOR_YELLOW)
            .strength(0.5f)
            .noOcclusion();

    public CubeStandBlock() {
        super(PROPERTIES);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegistry.CUBE_STAND.get().create(blockPos, blockState);
    }
}
