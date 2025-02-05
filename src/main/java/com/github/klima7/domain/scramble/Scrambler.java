package com.github.klima7.domain.scramble;

import com.github.klima7.core.misc.RubiksCubeData;
import com.github.klima7.domain.cube.stickers.CubeStickers;
import com.github.klima7.domain.operation.OperationDirection;
import com.github.klima7.domain.operation.move.Move;
import com.github.klima7.domain.operation.move.MoveFace;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scrambler {

    public static final int SCRAMBLE_LENGTH = 20;

    public static void scramble(CubeStickers cubeStickers, MinecraftServer server) {
        List<Move> scramble = generateScramble(server);
        for (Move move : scramble) {
            move.execute(cubeStickers);
        }
    }

    private static List<Move> generateScramble(MinecraftServer server) {
        List<Move> scramble = new ArrayList<>(SCRAMBLE_LENGTH);
        long seed = RubiksCubeData.getServerState(server).seed.hashCode();
        Random random = new Random(seed); // Única instância de Random para toda a sequência

        for (int i = 0; i < SCRAMBLE_LENGTH; i++) {
            Move move = generateRandomMove(random);
            scramble.add(move);
        }
        return scramble;
    }

    private static Move generateRandomMove(Random random) {
        MoveFace moveFace = getRandomMoveFace(random); // Usa a mesma instância de Random
        OperationDirection direction = getRandomOperationDirection(random); // Usa a mesma instância
        return new Move(moveFace, direction);
    }

    private static MoveFace getRandomMoveFace(Random random) {
        // Corrigido: usa nextInt() para evitar problemas com cast de double para int
        return MoveFace.values()[random.nextInt(MoveFace.values().length)];
    }

    private static OperationDirection getRandomOperationDirection(Random random) {
        // Corrigido: usa nextInt() para evitar problemas com cast de double para int
        return OperationDirection.values()[random.nextInt(OperationDirection.values().length)];
    }

}