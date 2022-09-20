package com.github.klima7.common.entity;

import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.operation.Operation;
import com.github.klima7.core.init.BlockEntityRegistry;
import com.github.klima7.core.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StandardRubiksCubeBlockEntity extends BaseRubiksCubeBlockEntity {

    public static final String CONTROLLER_NAME = "rubiks_cube_block_controller";

    private int id;
    private CubeStickers cubeStickers;

    public StandardRubiksCubeBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, BlockEntityRegistry.STANDARD_RUBIKS_CUBE.get(), CONTROLLER_NAME);
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        this.id = level.getFreeMapId();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("id", id);
        tag.putLong("startTime", startTime);
        tag.putString("cubeState", cubeStickers.toText());
        if(operation != null)
            tag.put("operation", operation.save());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.id = tag.getInt("id");
        this.startTime = tag.getLong("startTime");
        this.cubeStickers = CubeStickers.fromText(tag.getString("cubeState"));
        this.operation = tag.contains("operation") ? Operation.load(tag.getCompound("operation")) : null;
    }

    public void initializeFromItem(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if(tag == null) {
            this.cubeStickers = CubeStickers.getSolved();
        } else {
            this.cubeStickers = CubeStickers.fromText(tag.getString("cubeStickers"));
        }
    }

    public ItemStack asItem() {
        ItemStack itemStack = new ItemStack(ItemRegistry.STANDARD_RUBIKS_CUBE.get());
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt("id", id);
        tag.putString("cubeStickers", cubeStickers.toText());
        return itemStack;
    }

    @Override
    protected void applyOperation(Operation operation) {
        operation.execute(this.cubeStickers);
    }

    public int getId() {
        return id;
    }

    public CubeStickers getCubeStickers() {
        return this.cubeStickers;
    }

}
