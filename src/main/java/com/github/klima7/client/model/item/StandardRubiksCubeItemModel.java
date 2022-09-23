package com.github.klima7.client.model.item;

import com.github.klima7.client.ClientMod;
import com.github.klima7.client.texture.core.RubiksCubeTexture;
import com.github.klima7.client.texture.core.RubiksCubeTextureManager;
import com.github.klima7.common.item.helpers.StandardRubiksCubeItemWrapper;
import com.github.klima7.common.item.StandardRubiksCubeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class StandardRubiksCubeItemModel extends BaseRubiksCubeItemModel<StandardRubiksCubeItem> {

    public ResourceLocation getTextureResource(ItemStack itemStack) {
        RubiksCubeTextureManager rubiksCubeTextureManager = ClientMod.getInstance().getRubiksCubeTextureManager();
        StandardRubiksCubeItemWrapper itemWrapper = new StandardRubiksCubeItemWrapper(itemStack);

        if(!itemWrapper.isTagPresent()) {
            return rubiksCubeTextureManager.getSolvedTexture().getResourceLocation();
        }

        RubiksCubeTexture texture = rubiksCubeTextureManager.getTexture(itemWrapper.getId());
        texture.updateIfNeeded(itemWrapper.getCubeStickers());
        return texture.getResourceLocation();
    }

}
