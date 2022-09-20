package com.github.klima7.client.model.item;

import com.github.klima7.client.ClientMod;
import com.github.klima7.client.texture.RubiksCubeTexture;
import com.github.klima7.client.texture.RubiksCubeTextureManager;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.item.StandardRubiksCubeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class StandardRubiksCubeItemModel extends BaseRubiksCubeItemModel<StandardRubiksCubeItem> {

    public ResourceLocation getTextureResource(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();

        if(tag == null) {
            return getTextureLocationWithoutTag();
        } else {
            return getTextureLocationWithTag(tag);
        }
    }

    private ResourceLocation getTextureLocationWithoutTag() {
        RubiksCubeTextureManager rubiksCubeTextureManager = ClientMod.getInstance().getRubiksCubeTextureManager();
        return rubiksCubeTextureManager.getSolvedTexture().getResourceLocation();
    }

    private ResourceLocation getTextureLocationWithTag(CompoundTag tag) {
        RubiksCubeTextureManager rubiksCubeTextureManager = ClientMod.getInstance().getRubiksCubeTextureManager();

        int id = tag.getInt("id");
        CubeStickers cubeStickers = CubeStickers.fromText(tag.getString("cubeStickers"));

        RubiksCubeTexture texture = rubiksCubeTextureManager.getTexture(id);
        texture.updateIfNeeded(cubeStickers);
        return texture.getResourceLocation();
    }

}
