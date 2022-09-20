package com.github.klima7.common.item;

import com.github.klima7.common.block.BaseRubiksCubeBlock;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;

import java.util.function.Consumer;

public abstract class BaseRubiksCubeItem extends BlockItem implements IAnimatable, ISyncable {

    private static final Properties PROPERTIES = new Properties()
            .tab(CreativeModeTab.TAB_MISC)
            .stacksTo(1);

    private final String controllerName;
    private final AnimationFactory factory;

    public BaseRubiksCubeItem(BaseRubiksCubeBlock block, String controllerName) {
        super(block, PROPERTIES);
        this.controllerName = controllerName;
        this.factory = new AnimationFactory(this);
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = getRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<?> controller = new AnimationController<>(this, controllerName, 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void onAnimationSync(int id, int state) { }

    protected abstract BlockEntityWithoutLevelRenderer getRenderer();

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

}
