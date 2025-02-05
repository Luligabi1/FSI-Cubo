package com.github.klima7.core.event;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.entity.StandardRubiksCubeBlockEntity;
import com.github.klima7.core.init.BlockRegistry;
import com.github.klima7.core.init.PacketHandler;
import com.github.klima7.core.misc.RubiksCubeData;
import com.github.klima7.core.network.ServerboundUpdateRubiksCubePacket;
import com.github.klima7.domain.operation.OperationDirection;
import com.github.klima7.domain.operation.move.Move;
import com.github.klima7.domain.operation.move.MoveFace;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = RubiksCubeMod.MODID)
public class MiscEvents {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal(RubiksCubeMod.MODID)
                .requires(CommandSourceStack::isPlayer)
                .then(bfs(dispatcher))
                .then(dfs(dispatcher))
                .then(seed(dispatcher))
        );
    }

    public static ArgumentBuilder<CommandSourceStack, ?> bfs(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("bfs")
            .executes(ctx -> {

                var p = (Player) ctx.getSource().getEntity();

                var blockOptional = getHoveredCube(ctx);
                if (blockOptional.isEmpty()) return 0;
                var entity = blockOptional.get();

                Queue<State> queue = new LinkedList<>();
                Set<String> visited = new HashSet<>();

                StandardRubiksCubeBlockEntity original = entity.clone();
                queue.add(new State(original, new ArrayList<>()));
                visited.add(original.getCubeStickers().toText());

                boolean solved = false;
                long start = System.currentTimeMillis();

                while (!queue.isEmpty()) {
                    State current = queue.poll();

                    if (current.cube.getCubeStickers().isSolved()) {
                        solved = true;
                        break;
                    }


                    for (int i = 0; i < MOVEMENTS.size(); i++) {
                        Move move = MOVEMENTS.get(i);

                        if (!current.path.isEmpty()) {
                            Move lastMove = current.path.get(current.path.size() - 1);
                            if (isReverseMove(lastMove, move)) {
                                continue;
                            }
                        }

                        StandardRubiksCubeBlockEntity nextCube = current.cube.clone();
                        nextCube.applyOperation(move);

                        System.out.printf("Tentando: %s (%d)\n", move, i);

                        PacketHandler.CHANNEL.sendToServer(
                            new ServerboundUpdateRubiksCubePacket(entity.getBlockPos(), p.getUUID(), move.getMoveFace().getDirection(), move.getMoveDirection() == OperationDirection.COUNTERCLOCKWISE, false, false)
                        );

                        String nextState = nextCube.getCubeStickers().toText();
                        if (!visited.contains(nextState)) {
                            visited.add(nextState);

                            List<Move> newPath = new ArrayList<>(current.path);
                            newPath.add(move);
                            queue.add(new State(nextCube, newPath));
                        }
                    }
                }

                long end = System.currentTimeMillis();
                long time = end - start;

                if (!solved) {
                    ctx.getSource().sendSuccess(Component.literal("BFS falhou em " + time + "ms").withStyle(ChatFormatting.RED), true);
                    return 0;
                }

                ctx.getSource().sendSuccess(Component.literal("BFS resolvido em " + time + "ms").withStyle(ChatFormatting.GREEN), true);
                return Command.SINGLE_SUCCESS;
            });
    }

    public static ArgumentBuilder<CommandSourceStack, ?> dfs(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("dfs")
            .executes(ctx -> {

                var p = (Player) ctx.getSource().getEntity();

                var blockOptional = getHoveredCube(ctx);
                if (blockOptional.isEmpty()) return 0;
                var entity = blockOptional.get();

                Deque<State> stack = new ArrayDeque<>();
                Set<String> visited = new HashSet<>();

                StandardRubiksCubeBlockEntity original = entity.clone();
                stack.push(new State(original, new ArrayList<>()));
                visited.add(original.getCubeStickers().toText());

                boolean solved = false;
                long start = System.currentTimeMillis();

                while (!stack.isEmpty()) {
                    State current = stack.pop();

                    if (current.cube.getCubeStickers().isSolved()) {
                        solved = true;
                        break;
                    }

                    if (current.path.size() >= 70) {
                        continue;
                    }

                    for (int i = MOVEMENTS.size() - 1; i >= 0; i--) {
                        Move move = MOVEMENTS.get(i);

                        if (!current.path.isEmpty()) {
                            Move lastMove = current.path.get(current.path.size() - 1);
                            if (isReverseMove(lastMove, move)) {
                                continue;
                            }
                        }

                        StandardRubiksCubeBlockEntity nextCube = current.cube.clone();
                        nextCube.applyOperation(move);

                        System.out.printf("Tentando: %s (%d)\n", move, i);


                        PacketHandler.CHANNEL.sendToServer(
                            new ServerboundUpdateRubiksCubePacket(entity.getBlockPos(), p.getUUID(), move.getMoveFace().getDirection(), move.getMoveDirection() == OperationDirection.COUNTERCLOCKWISE, false, false)
                        );

                        String nextState = nextCube.getCubeStickers().toText();

                        if (!visited.contains(nextState)) {
                            visited.add(nextState);
                            List<Move> newPath = new ArrayList<>(current.path);
                            newPath.add(move);
                            stack.push(new State(nextCube, newPath));
                        }
                    }
                }

                long end = System.currentTimeMillis();
                long time = end - start;

                if (!solved) {
                    ctx.getSource().sendSuccess(Component.literal("DFS falhou em " + time + "ms").withStyle(ChatFormatting.RED), true);
                    return 0;
                }

                ctx.getSource().sendSuccess(Component.literal("DFS resolvido em " + time + "ms").withStyle(ChatFormatting.GREEN), true);
                return Command.SINGLE_SUCCESS;
            });
    }


    public static ArgumentBuilder<CommandSourceStack, ?> seed(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("seed")
            .then(Commands.argument("seed", StringArgumentType.string())
                .executes(ctx -> {

                    var seed = StringArgumentType.getString(ctx, "seed");

                    var data = RubiksCubeData.getServerState(ctx.getSource().getServer());
                    System.out.println(data.seed);

                    data.seed = seed;
                    data.setDirty();

                    ctx.getSource().sendSuccess(Component.literal("seed=" + seed).withStyle(ChatFormatting.GREEN), true);
                    return Command.SINGLE_SUCCESS;
        }));
    }


    private static Optional<StandardRubiksCubeBlockEntity> getHoveredCube(CommandContext<CommandSourceStack> ctx) {
        var p = (Player) ctx.getSource().getEntity();

        var raycast = p.pick(5, 1f, false);
        if (raycast.getType() != HitResult.Type.BLOCK || !(raycast instanceof BlockHitResult)) {
            ctx.getSource().sendFailure(Component.literal("Not a block!").withStyle(ChatFormatting.RED));
            return Optional.empty();
        }


        var block = p.level.getBlockState(((BlockHitResult) raycast).getBlockPos());
        if (!block.is(BlockRegistry.STANDARD_RUBIKS_CUBE.get())) {
            ctx.getSource().sendFailure(Component.literal("Not a Rubik's Cube!").withStyle(ChatFormatting.RED));
            return Optional.empty();
        }

        var blockEntity = p.level.getBlockEntity(((BlockHitResult) raycast).getBlockPos());
        if (blockEntity instanceof StandardRubiksCubeBlockEntity rubiksCubeBlockEntity) {
            return Optional.of(rubiksCubeBlockEntity);
        }

        return Optional.empty();
    }

    private static boolean isReverseMove(Move a, Move b) {
        return a.getMoveFace() == b.getMoveFace() && a.getMoveDirection() != b.getMoveDirection();
    }


    private static final List<Move> MOVEMENTS = List.of(
        new Move(MoveFace.NORTH, OperationDirection.CLOCKWISE),
        new Move(MoveFace.SOUTH, OperationDirection.CLOCKWISE),
        new Move(MoveFace.WEST, OperationDirection.CLOCKWISE),
        new Move(MoveFace.EAST, OperationDirection.CLOCKWISE),
        new Move(MoveFace.UP, OperationDirection.CLOCKWISE),
        new Move(MoveFace.DOWN, OperationDirection.CLOCKWISE),
        new Move(MoveFace.NORTH, OperationDirection.COUNTERCLOCKWISE),
        new Move(MoveFace.SOUTH, OperationDirection.COUNTERCLOCKWISE),
        new Move(MoveFace.WEST, OperationDirection.COUNTERCLOCKWISE),
        new Move(MoveFace.EAST, OperationDirection.COUNTERCLOCKWISE),
        new Move(MoveFace.UP, OperationDirection.COUNTERCLOCKWISE),
        new Move(MoveFace.DOWN, OperationDirection.COUNTERCLOCKWISE)
    );

    private static class State {
        StandardRubiksCubeBlockEntity cube;
        List<Move> path;

        public State(StandardRubiksCubeBlockEntity cube, List<Move> path) {
            this.cube = cube;
            this.path = new ArrayList<>(path);
        }
    }

}