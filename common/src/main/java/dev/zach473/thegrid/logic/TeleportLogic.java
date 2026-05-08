package dev.zach473.thegrid.logic;

import dev.zach473.thegrid.ModConstants;
import dev.zach473.thegrid.state.ModState;
import dev.zach473.thegrid.types.IVec2;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class TeleportLogic {
    /**
     * Teleport the given player to the specified player UUID's base in the void.
     *
     * @param source The player to teleport
     * @param target The target UUID to teleport to
     * @param createIfNew Whether to create a new index if one doesn't exist for the target UUID
     */
    public static void teleportIntoVoid(ServerPlayer source, UUID target, boolean createIfNew) {
        final var dimension = DimensionLogic.getVoidDimension(source.serverLevel());
        final var modState = ModState.get(source.serverLevel());
        final var uuid = source.getUUID();

        // Save the player's return position
        modState.pushReturnPosition(uuid, source.serverLevel().dimension().location(), source.position());

        // Get the index of the target UUID and create one if appropriate
        int index;
        if (modState.hasIndex(target)) {
            index = modState.getIndex(target);
        } else {
            if (createIfNew) {
                index = modState.addIndex(target);
            } else {
                return;
            }
        }

        // Get the offset from the retrieved index
        final var offset = OffsetLogic.computeOffset(index);

        // Compute the various positions from the offset
        final var rootPosition = new BlockPos(ModConstants.VOID_SPACING * offset.x(), ModConstants.VOID_HEIGHT, ModConstants.VOID_SPACING * offset.y());
        final var chunkPosition = new IVec2(rootPosition.getX() / 16, rootPosition.getZ() / 16);

        // Force load the chunk if necessary
        var unforceChunk = false;
        if (!dimension.isLoaded(rootPosition)) {
            unforceChunk = true;
            dimension.setChunkForced(chunkPosition.x(), chunkPosition.y(), true);
        }

        // Create a 3x3 Obsidian platform if any of the blocks is air
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final var blockPos = rootPosition.offset(i, 0, j);
                if (dimension.getBlockState(blockPos).is(Blocks.AIR)) {
                    dimension.setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
                }
            }
        }

        // Teleport the player to the middle of the platform
        teleport(source, dimension, rootPosition.getX() + 1.5, rootPosition.getY() + 1, rootPosition.getZ() + 1.5);

        // Remove force load on the chunk if it was necessary
        if (unforceChunk) {
            dimension.setChunkForced(chunkPosition.x(), chunkPosition.y(), false);
        }
    }

    /**
     * Teleport a player out of the void dimension.
     *
     * @param player The ServerPlayer to teleport
     */
    public static void teleportOutOfVoid(ServerPlayer player) {
        final var modState = ModState.get(player.serverLevel());
        final var uuid = player.getUUID();

        // If there exists a return position for the player, teleport them to it.
        if (modState.hasReturnPosition(uuid)) {
            final var record = modState.popReturnPosition(uuid);

            final var dimension = DimensionLogic.getDimension(player.serverLevel(), record.dimension());
            final var position = record.position();

            teleport(player, dimension, position);
            return;
        }

        // If a return position doesn't exist for the player, teleport them to a suitable respawn position, in order:
        //     - If the player has a respawn dimension and position set;
        //         - And the respawn dimension is not the void dimension, teleport them there.
        //         - And the respawn dimension is the void dimension, continue;
        //     - If the player doesn't have a respawn dimension and position set, get the Overworld's shared respawn
        //       position and teleport them there.
        if (player.getRespawnDimension().location() != DimensionLogic.DIMENSION_VOID && player.getRespawnPosition() != null) {
            final var dimension = DimensionLogic.getDimension(player.serverLevel(), player.getRespawnDimension());
            final var position = player.getRespawnPosition();

            teleport(player, dimension, position);
            return;
        }

        final var dimension = DimensionLogic.getDimension(player.serverLevel(), ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"));
        final var position = dimension.getSharedSpawnPos();

        teleport(player, dimension, position);
    }

    /**
     * Teleport the player to the given position in the specified dimension, preserving the current rotation.
     *
     * @param player The player to teleport
     * @param dimension The target dimension
     * @param x The target X coordinate
     * @param y The target Y coordinate
     * @param z The target Z coordinate
     */
    public static void teleport(ServerPlayer player, ServerLevel dimension, double x, double y, double z) {
        final var position = new Vec3(x, y, z);
        teleport(player, dimension, position);
    }

    /**
     * Teleport the player to the given position in the specified dimension, preserving the current rotation.
     *
     * @param player The player to teleport
     * @param dimension The target dimension
     * @param position The target position
     */
    public static void teleport(ServerPlayer player, ServerLevel dimension, BlockPos position) {
        final var parsedPosition = new Vec3(position.getX(), position.getY(), position.getZ());
        teleport(player, dimension, parsedPosition);
    }

    /**
     * Teleport the player to the given position in the specified dimension, preserving the current rotation.
     *
     * @param player The player to teleport
     * @param dimension The target dimension
     * @param position The target position
     */
    public static void teleport(ServerPlayer player, ServerLevel dimension, Vec3 position) {
        final var rotation = player.getRotationVector();
        player.teleportTo(dimension, position.x, position.y, position.z, rotation.y, rotation.x);
    }
}
