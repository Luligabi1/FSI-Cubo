package com.github.klima7.common.entity;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.item.helpers.StandardRubiksCubeItemWrapper;
import com.github.klima7.domain.cube.stickers.CubeStickers;
import com.github.klima7.domain.operation.Operation;
import com.github.klima7.domain.operation.rotation.InstantRotations;
import com.github.klima7.domain.operation.rotation.RotationsSet;
import com.github.klima7.domain.scramble.ScrambleState;
import com.github.klima7.core.init.BlockEntityRegistry;
import com.github.klima7.core.init.ItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class StandardRubiksCubeBlockEntity extends BaseRubiksCubeBlockEntity {

    public static final String CONTROLLER_NAME = "rubiks_cube_block_controller";

    private int id;
    private CubeStickers cubeStickers;
    private ScrambleState scrambleState;

    public StandardRubiksCubeBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, BlockEntityRegistry.STANDARD_RUBIKS_CUBE.get(), CONTROLLER_NAME);
        this.scrambleState = ScrambleState.SOLVED;
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        this.id = level.getFreeMapId();
    }

    public void setFacing(Direction facing) {
        Operation op = new InstantRotations(RotationsSet.createToDirection(facing));
        op.execute(cubeStickers);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("id", id);
        tag.putString("cubeState", cubeStickers.toText());
        tag.putString("scrambleState", scrambleState.name());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.id = tag.getInt("id");
        this.cubeStickers = CubeStickers.fromText(tag.getString("cubeState"));
        this.scrambleState = ScrambleState.valueOf(tag.getString("scrambleState"));
    }

    public void initializeFromItem(ItemStack itemStack) {
        StandardRubiksCubeItemWrapper itemWrapper = new StandardRubiksCubeItemWrapper(itemStack);
        this.cubeStickers = itemWrapper.getCubeStickersOrDefault();
        this.scrambleState = itemWrapper.getScrambleStateOrDefault();
    }

    public ItemStack asItem() {
        ItemStack itemStack = new ItemStack(ItemRegistry.STANDARD_RUBIKS_CUBE.get());
        new StandardRubiksCubeItemWrapper(itemStack).createTag(id, cubeStickers, scrambleState);
        return itemStack;
    }

    public int getId() {
        return id;
    }

    public CubeStickers getCubeStickers() {
        return this.cubeStickers;
    }

    @Override
    protected void applyOperation(Operation operation) {
        ScrambleState oldScrambleState = scrambleState;
        operation.execute(this.cubeStickers);

        // update scrambleState
        if(cubeStickers.isSolved()) {
            scrambleState = ScrambleState.SOLVED;
        } else if(oldScrambleState != ScrambleState.AUTO_SCRAMBLED) {
            scrambleState = ScrambleState.MANUALLY_SCRAMBLED;
        }

        // grant advancements
        if(oldScrambleState == ScrambleState.AUTO_SCRAMBLED && scrambleState == ScrambleState.SOLVED) {
            grantAdvancement(playerUUID, "rubiks_cube_solved");
        }

        if(oldScrambleState == ScrambleState.AUTO_SCRAMBLED && cubeStickers.getSolvedFacesCount() >= 1) {
            grantAdvancement(playerUUID, "face_solved");
        }
    }

    private void grantAdvancement(UUID playerUUID, String advancementName) {
        if(level instanceof final ServerLevel serverLevel) {
            ServerAdvancementManager advancementManager = serverLevel.getServer().getAdvancements();
            Advancement advancement = advancementManager.getAdvancement(new ResourceLocation(RubiksCubeMod.MODID, advancementName));
            ServerPlayer player = (ServerPlayer) level.getPlayerByUUID(playerUUID);
            player.getAdvancements().award(advancement, "set_programmatically");
        }
    }

}
