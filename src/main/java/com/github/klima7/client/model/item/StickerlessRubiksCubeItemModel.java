package com.github.klima7.client.model.item;

import com.github.klima7.client.ClientMod;
import com.github.klima7.client.texture.RubiksCubeTexture;
import com.github.klima7.client.texture.RubiksCubeTextureManager;
import com.github.klima7.common.item.StickerlessRubiksCubeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class StickerlessRubiksCubeItemModel extends BaseRubiksCubeItemModel<StickerlessRubiksCubeItem> {

    public ResourceLocation getTextureResource(ItemStack itemStack) {
        RubiksCubeTextureManager rubiksCubeTextureManager = ClientMod.getInstance().getRubiksCubeTextureManager();
        RubiksCubeTexture texture = rubiksCubeTextureManager.getStickerlessTexture();
        return texture.getResourceLocation();
    }

}
