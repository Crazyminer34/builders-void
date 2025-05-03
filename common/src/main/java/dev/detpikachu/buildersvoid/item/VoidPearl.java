package dev.detpikachu.buildersvoid.item;

import dev.detpikachu.buildersvoid.ModConfig;
import dev.detpikachu.buildersvoid.ModConstants;
import dev.detpikachu.buildersvoid.state.VoidState;
import dev.detpikachu.buildersvoid.state.containers.ReturnPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static dev.detpikachu.buildersvoid.ModConstants.id;

public class VoidPearl extends Item {
    public VoidPearl(Properties properties) {
        super(properties
            .stacksTo(1)
            .fireResistant()
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable("tooltip.buildersvoid.void_pearl.usage"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            addCooldown(player);
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }

        ItemStack itemStack = player.getItemInHand(usedHand);

        ServerPlayer serverPlayer = (ServerPlayer) player;
        UUID playerUUID = serverPlayer.getUUID();
        Vec3 playerPosition = serverPlayer.position();
        Vec2 playerRotation = serverPlayer.getRotationVector();

        ServerLevel currentDimension = (ServerLevel) level;
        ResourceKey<Level> voidDimensionKey = ResourceKey.create(Registries.DIMENSION, id("builders_void"));
        ServerLevel voidDimension = currentDimension.getServer().getLevel(voidDimensionKey);

        DimensionDataStorage voidStorage = voidDimension.getDataStorage();
        VoidState voidState = voidStorage.computeIfAbsent(VoidState::load, VoidState::new, VoidState.FILE_NAME);

        if (currentDimension.dimension() == voidDimensionKey) {
            // If the player is currently in the void dimension, teleport them to their previous location.
            // If no such location has been recorded, teleport them to their respawn location.
            // If their respawn location is in the void dimension, teleport them to the shared respawn position
            // in the Overworld.
            teleportFromVoid(
                currentDimension,
                voidDimensionKey,
                voidState,
                serverPlayer,
                playerUUID,
                playerRotation
            );
        } else {
            // If the player is not in the void dimension, store their current position and
            // teleport them to their appropriate location in the void dimension.
            Optional<UUID> targetPlayerUUID = Optional.empty();
            CompoundTag tag = itemStack.getTag();

            if (tag != null && tag.contains("UUID")) {
                // The item stack has a UUID NBT entry (because it's a Linked Void Pearl)
                targetPlayerUUID = Optional.of(tag.getUUID("UUID"));
            }

            teleportToVoid(
                currentDimension,
                voidDimension,
                voidState,
                serverPlayer,
                playerUUID,
                playerPosition,
                playerRotation,
                targetPlayerUUID
            );
        }

        return InteractionResultHolder.pass(itemStack);
    }

    protected void teleportToVoid(
        ServerLevel currentDimension,
        ServerLevel voidDimension,
        VoidState voidState,
        ServerPlayer serverPlayer,
        UUID playerUUID,
        Vec3 playerPosition,
        Vec2 playerRotation,
        Optional<UUID> targetPlayerUUID
    ) {
        // Save the player's current position
        voidState.pushReturnPosition(playerUUID, currentDimension, playerPosition);

        // Set the target UUID if provided, otherwise use the current player's UUID
        UUID targetUUID = playerUUID;
        if (targetPlayerUUID.isPresent()) {
            targetUUID = targetPlayerUUID.get();
        }

        // Get the index of the user from the dimension data
        int index;
        if (voidState.hasIndex(targetUUID)) {
            index = voidState.getIndex(targetUUID);
        } else {
            index = voidState.addIndex(targetUUID);
        }

        // Compute the positions and offsets based on the spacing and height constants
        Vec2 offset = getVoidOffset(index);
        BlockPos basePos = new BlockPos((int) (ModConstants.VOID_SPACING * offset.x), ModConstants.VOID_HEIGHT, (int) (ModConstants.VOID_SPACING * offset.y));
        Vec2 chunkPos = new Vec2(basePos.getX() / 16, basePos.getZ() / 16);

        // Force load the chunk if it's not already loaded
        boolean unforceChunk = false;
        if (!voidDimension.isLoaded(basePos)) {
            unforceChunk = true;
            voidDimension.setChunkForced((int) chunkPos.x, (int) chunkPos.y, true);
        }

        // Create a 3x3 Obsidian platform if it doesn't already exist
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BlockPos blockPos = basePos.offset(i, 0, j);
                if (voidDimension.getBlockState(blockPos).is(Blocks.AIR)) {
                    voidDimension.setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
                }
            }
        }

        // Teleport the player to the middle of the platform
        teleportPlayer(serverPlayer, voidDimension, basePos.getX() + 1.5, basePos.getY() + 1, basePos.getZ() + 1.5, playerRotation);

        // Remove force load on the chunk if it was necessary
        if (unforceChunk) {
            voidDimension.setChunkForced((int) chunkPos.x, (int) chunkPos.y, false);
        }
    }

    protected void teleportFromVoid(
        ServerLevel currentDimension,
        ResourceKey<Level> voidDimensionKey,
        VoidState voidState,
        ServerPlayer serverPlayer,
        UUID playerUUID,
        Vec2 playerRotation
    ) {

        if (voidState.hasReturnPosition(playerUUID)) {
            // If the player does have a previous position recorded, teleport them
            ReturnPosition returnPositionObj = voidState.popReturnPosition(playerUUID);
            Vec3 returnPosition = returnPositionObj.position();
            ResourceKey<Level> returnDimensionKey = ResourceKey.create(Registries.DIMENSION, returnPositionObj.dimension());
            ServerLevel returnDimension = currentDimension.getServer().getLevel(returnDimensionKey);

            teleportPlayer(serverPlayer, returnDimension, returnPosition, playerRotation);
            return;
        }

        // If the player does not have a previous position recorded, get their respawn dimension
        ResourceKey<Level> respawnDimensionKey = serverPlayer.getRespawnDimension();
        ServerLevel respawnDimension;
        if (respawnDimensionKey == voidDimensionKey) {
            // If the player's respawn dimension is the void dimension, get the Overworld's shared respawn position
            respawnDimensionKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("minecraft", "overworld"));
            respawnDimension = currentDimension.getServer().getLevel(respawnDimensionKey);
            BlockPos respawnPosition = respawnDimension.getSharedSpawnPos();

            teleportPlayer(serverPlayer, respawnDimension, respawnPosition, playerRotation);
            return;
        }

        // If the player's respawn dimension is different from the void dimension, teleport them there
        respawnDimension = currentDimension.getServer().getLevel(respawnDimensionKey);
        BlockPos respawnPosition = serverPlayer.getRespawnPosition();

        if (respawnPosition == null) {
            // If the player doesn't have a respawn position, get the shared one from the respawn dimension
            respawnPosition = respawnDimension.getSharedSpawnPos();
        }

        teleportPlayer(serverPlayer, respawnDimension, respawnPosition, playerRotation);
    }

    private void teleportPlayer(ServerPlayer player, ServerLevel dimension, double x, double y, double z, Vec2 rotation) {
        Vec3 position = new Vec3(x, y, z);
        teleportPlayer(player, dimension, position, rotation);
    }

    private void teleportPlayer(ServerPlayer player, ServerLevel dimension, BlockPos position, Vec2 rotation) {
        Vec3 parsedPosition = new Vec3(position.getX(), position.getY(), position.getZ());
        teleportPlayer(player, dimension, parsedPosition, rotation);
    }

    private void teleportPlayer(ServerPlayer player, ServerLevel dimension, Vec3 position, Vec2 rotation) {
        player.teleportTo(dimension, position.x, position.y, position.z, rotation.y, rotation.x);
        addCooldown(player);
    }

    private void addCooldown(Player player) {
        ModConfig config = ModConfig.getActive();

        if (!config.enableCooldown) {
            return;
        }

        player.getCooldowns().addCooldown(this, config.cooldown);
    }

    private Vec2 getVoidOffset(int index) {
        // https://math.stackexchange.com/a/163101
        int k = (int) Math.ceil((Math.sqrt(index) - 1) / 2);
        int t = (2 * k) + 1;
        int m = (int) Math.pow(t, 2);
        t -= 1;

        if (index >= m - t) {
            return new Vec2(k - (m - index), -k);
        } else {
            m -= t;
        }

        if (index >= m - t) {
            return new Vec2(-k, -k + (m - index));
        } else {
            m -= t;
        }

        if (index >= m - t) {
            return new Vec2(-k + (m - index), k);
        } else {
            return new Vec2(k, k - (m - index - t));
        }
    }
}
