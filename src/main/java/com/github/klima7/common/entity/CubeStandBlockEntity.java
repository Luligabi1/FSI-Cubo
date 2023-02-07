package com.github.klima7.common.entity;

import com.github.klima7.core.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class CubeStandBlockEntity extends InventoryBlockEntity {

    public CubeStandBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CUBE_STAND.get(), pos, state, 1);
    }

    public void placeCube(ItemStack itemStack) {
        if(isEmpty()) {
            insertItem(0, itemStack);
        }
    }

    public boolean takeCube(Player player) {
        if(isEmpty()) {
            return false;
        }

        final ItemStack itemStack = extractItem(0);
        final var itemEntity = new ItemEntity(this.level, player.getX(), player.getY() + 0.5D,
                player.getZ(), itemStack);
        this.level.addFreshEntity(itemEntity);
        return true;
    }

    public ItemStack getCube() {
        return null;
    }

    public boolean isEmpty() {
        return getItemInSlot(0).getCount() == 0;
    }

}
