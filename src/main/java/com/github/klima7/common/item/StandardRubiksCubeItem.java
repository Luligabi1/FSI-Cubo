package com.github.klima7.common.item;

import com.github.klima7.client.renderer.item.StandardRubiksCubeItemRenderer;
import com.github.klima7.common.item.helpers.StandardRubiksCubeItemWrapper;
import com.github.klima7.core.init.BlockRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class StandardRubiksCubeItem extends BaseRubiksCubeItem {

    private static final String CONTROLLER_NAME = "rubiks_cube_item_controller";

    public StandardRubiksCubeItem() {
        super(BlockRegistry.STANDARD_RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new StandardRubiksCubeItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            StandardRubiksCubeItemWrapper itemWrapper = new StandardRubiksCubeItemWrapper(itemStack);
            itemWrapper.createTag(level);
            itemWrapper.scramble(level.getServer());
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 16;
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