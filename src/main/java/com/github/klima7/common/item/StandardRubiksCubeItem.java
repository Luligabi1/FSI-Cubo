package com.github.klima7.common.item;

import com.github.klima7.client.renderer.item.StandardRubiksCubeItemRenderer;
import com.github.klima7.common.item.helpers.StandardRubiksCubeItemWrapper;
import com.github.klima7.domain.cube.stickers.CubeStickers;
import com.github.klima7.domain.scramble.ScrambleState;
import com.github.klima7.domain.scramble.Scrambler;
import com.github.klima7.core.init.BlockRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            final ItemStack itemStack = player.getItemInHand(hand);
            StandardRubiksCubeItemWrapper itemWrapper = new StandardRubiksCubeItemWrapper(itemStack);
            itemWrapper.createTag(level);
            itemWrapper.scramble();
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        appendScrambleState(itemStack, components);
    }

    private void appendScrambleState(ItemStack itemStack, List<Component> components) {
        StandardRubiksCubeItemWrapper itemWrapper = new StandardRubiksCubeItemWrapper(itemStack);

        String tooltipId = switch (itemWrapper.getScrambleStateOrDefault()) {
            case SOLVED -> "item.rubiks_cube.scramble_state.solved";
            case AUTO_SCRAMBLED -> "item.rubiks_cube.scramble_state.auto_scrambled";
            case MANUALLY_SCRAMBLED -> "item.rubiks_cube.scramble_state.manually_scrambled";
        };

        components.add(Component.translatable(tooltipId));
    }

}
