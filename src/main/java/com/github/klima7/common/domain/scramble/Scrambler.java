package com.github.klima7.common.domain.scramble;

import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.operation.OperationDirection;
import com.github.klima7.common.domain.operation.move.Move;
import com.github.klima7.common.domain.operation.move.MoveFace;

import java.util.ArrayList;
import java.util.List;

public class Scrambler {

    public static final int SCRAMBLE_LENGTH = 20;

    public static void scramble(CubeStickers cubeStickers) {
        List<Move> scramble = generateScramble();
        for(Move move : scramble) {
            move.execute(cubeStickers);
        }
    }

    private static List<Move> generateScramble() {
        List<Move> scramble = new ArrayList<>(SCRAMBLE_LENGTH);
        for(int i=0; i<SCRAMBLE_LENGTH; i++) {
            Move move = generateRandomMove();
            scramble.add(move);
        }
        return scramble;
    }

    private static Move generateRandomMove() {
        MoveFace moveFace = getRandomMoveFace();
        OperationDirection direction = getRandomOperationDirection();
        return new Move(moveFace, direction);
    }

    private static MoveFace getRandomMoveFace() {
        return MoveFace.values()[(int) (Math.random() * MoveFace.values().length)];
    }

    private static OperationDirection getRandomOperationDirection() {
        return OperationDirection.values()[(int) (Math.random() * OperationDirection.values().length)];
    }

}
