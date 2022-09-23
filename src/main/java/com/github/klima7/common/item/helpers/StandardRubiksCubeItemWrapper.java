package com.github.klima7.common.item.helpers;

import com.github.klima7.domain.cube.stickers.CubeStickers;
import com.github.klima7.domain.scramble.ScrambleState;
import com.github.klima7.domain.scramble.Scrambler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StandardRubiksCubeItemWrapper {

    private final ItemStack itemStack;

    public StandardRubiksCubeItemWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void createTag(int id, CubeStickers cubeStickers, ScrambleState scrambleState) {
        CompoundTag tag = new CompoundTag();
        itemStack.setTag(tag);

        setId(id);
        setCubeStickers(cubeStickers);
        setScrambleState(scrambleState);
    }

    public void createTag(Level level) {
        createTag(level.getFreeMapId(), CubeStickers.getSolved(), ScrambleState.SOLVED);
    }

    public boolean isTagPresent() {
        return itemStack.getTag() != null;
    }

    public int getId() {
        assertTagPresent();
        return itemStack.getTag().getInt("id");
    }

    public CubeStickers getCubeStickers() {
        assertTagPresent();
        return CubeStickers.fromText(itemStack.getTag().getString("cubeStickers"));
    }

    public CubeStickers getCubeStickersOrDefault() {
        if(isTagPresent()) {
            return getCubeStickers();
        } else {
            return CubeStickers.getSolved();
        }
    }

    public ScrambleState getScrambleState() {
        assertTagPresent();
        return ScrambleState.valueOf(itemStack.getTag().getString("scrambleState"));
    }

    public ScrambleState getScrambleStateOrDefault() {
        if(isTagPresent()) {
            return getScrambleState();
        } else {
            return ScrambleState.SOLVED;
        }
    }

    public void setId(int id) {
        assertTagPresent();
        itemStack.getTag().putInt("id", id);
    }

    public void setCubeStickers(CubeStickers cubeStickers) {
        assertTagPresent();
        itemStack.getTag().putString("cubeStickers", cubeStickers.toText());
    }

    public void setScrambleState(ScrambleState scrambleState) {
        assertTagPresent();
        itemStack.getTag().putString("scrambleState", scrambleState.name());
    }

    public void scramble() {
        CubeStickers cubeStickers = getCubeStickersOrDefault();
        Scrambler.scramble(cubeStickers);
        setCubeStickers(cubeStickers);
        setScrambleState(ScrambleState.AUTO_SCRAMBLED);
    }

    private void assertTagPresent() {
        if(!isTagPresent()) {
            throw new IllegalStateException("Tag is not present in ItemStack");
        }
    }

}
