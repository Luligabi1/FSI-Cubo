package com.github.klima7.client.renderer.item;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.client.model.item.RubiksCubeItemModel;
import com.github.klima7.client.renderer.texture.RubiksCubeTexture;
import com.github.klima7.client.renderer.texture.RubiksCubeTextureManager;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.item.RubiksCubeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RubiksCubeItemRenderer extends GeoItemRenderer<RubiksCubeItem> {

    private final RubiksCubeTextureManager rubiksCubeTextureManager = RubiksCubeTextureManager.getInstance();

    public RubiksCubeItemRenderer() {
        super(new RubiksCubeItemModel());
    }

    @Override
    public ResourceLocation getTextureLocation(RubiksCubeItem instance) {
        CompoundTag tag = currentItemStack.getTag();

        if(tag == null) {
            return new ResourceLocation(RubiksCubeMod.MODID+":textures/block/rubiks_cube.png");
        }

        int id = tag.getInt("id");
        CubeStickers cubeStickers = CubeStickers.fromText(tag.getString("cubeStickers"));

        RubiksCubeTexture texture = rubiksCubeTextureManager.getTexture(id);
        texture.updateIfNeeded(cubeStickers);
        return texture.getResourceLocation();
    }

}
