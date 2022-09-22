package com.github.klima7.common.item;

import com.github.klima7.client.renderer.item.StandardRubiksCubeItemRenderer;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.scramble.Scrambler;
import com.github.klima7.core.init.BlockRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StandardRubiksCubeItem extends BaseRubiksCubeItem {

    private static final String CONTROLLER_NAME = "rubiks_cube_item_controller";

    public StandardRubiksCubeItem() {
        super(BlockRegistry.STANDARD_RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

    @Override
    protected BlockEntityWithoutLevelRenderer getRenderer() {
        return new StandardRubiksCubeItemRenderer();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            final ItemStack stack = player.getItemInHand(hand);
            addTagIfNotPresent(stack, level);
            scramble(stack);
        }
        return super.use(level, player, hand);
    }

    public static CompoundTag createTag(int id, CubeStickers stickers) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("id", id);
        tag.putString("cubeStickers", stickers.toText());
        return tag;
    }

    private void addTagIfNotPresent(ItemStack itemStack, Level level) {
        CompoundTag presentTag = itemStack.getTag();
        if(presentTag == null) {
            CompoundTag newTag = createTag(level.getFreeMapId(), CubeStickers.getSolved());
            itemStack.setTag(newTag);
        }
    }

    private void scramble(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        CubeStickers cubeStickers = CubeStickers.fromText(tag.getString("cubeStickers"));
        Scrambler.scramble(cubeStickers);
        tag.putString("cubeStickers", cubeStickers.toText());
    }

}
