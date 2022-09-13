package com.github.klima7.client.model.item;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.client.ClientMod;
import com.github.klima7.client.texture.FacesImageCreator;
import com.github.klima7.client.texture.RubiksCubeTexture;
import com.github.klima7.client.texture.RubiksCubeTextureManager;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.item.RubiksCubeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RubiksCubeItemModel extends AnimatedGeoModel<RubiksCubeItem> {

    @Override
    public ResourceLocation getAnimationResource(RubiksCubeItem animatable) {
        return new ResourceLocation(RubiksCubeMod.MODID, "animations/rubiks_cube.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(RubiksCubeItem object) {
        return new ResourceLocation(RubiksCubeMod.MODID, "geo/rubiks_cube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RubiksCubeItem object) {
        // Extended version of method used instead
        return null;
    }

    public ResourceLocation getTextureResource(RubiksCubeItem item, ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();

        if(tag == null) {
            return FacesImageCreator.FACES_LOCATION;
        }

        int id = tag.getInt("id");
        CubeStickers cubeStickers = CubeStickers.fromText(tag.getString("cubeStickers"));

        RubiksCubeTextureManager rubiksCubeTextureManager = ClientMod.getInstance().getRubiksCubeTextureManager();
        RubiksCubeTexture texture = rubiksCubeTextureManager.getTexture(id);
        texture.updateIfNeeded(cubeStickers);
        return texture.getResourceLocation();
    }

}
