package dev.detpikachu.buildersvoid.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;

import static dev.detpikachu.buildersvoid.ModConstants.id;

public class VoidPearl extends Item {

    public VoidPearl(Properties properties) {

        super(properties
            .stacksTo(1)
            .fireResistant()
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }

        ServerLevel serverLevel = (ServerLevel) level;
        ServerPlayer serverPlayer = (ServerPlayer) player;
        Vec2 rotation = serverPlayer.getRotationVector();
        if (serverLevel.dimension() == ResourceKey.create(Registries.DIMENSION, id("builders_void"))) {

            ResourceKey<Level> respawnDimensionKey = serverPlayer.getRespawnDimension();
            ServerLevel respawnDimension = serverLevel.getServer().getLevel(respawnDimensionKey);

            BlockPos respawnPosition = serverPlayer.getRespawnPosition();
            if (respawnPosition == null) {

                respawnPosition = serverLevel.getSharedSpawnPos();
            }

            serverPlayer.teleportTo(respawnDimension, respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ(), rotation.y, rotation.x);
        } else {

            ResourceKey<Level> voidDimensionKey = ResourceKey.create(Registries.DIMENSION, id("builders_void"));
            ServerLevel voidDimension = serverLevel.getServer().getLevel(voidDimensionKey);

            boolean unforceChunk = false;
            if (!voidDimension.isLoaded(new BlockPos(1, 50, 1))) {

                unforceChunk = true;
                voidDimension.setChunkForced(0, 0, true);
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    BlockPos blockPos = new BlockPos(1 + i, 50, 1 + j);
                    if (voidDimension.getBlockState(blockPos).is(Blocks.AIR)) {

                        voidDimension.setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
                    }
                }
            }

            serverPlayer.teleportTo(voidDimension, 2.5, 51, 2.5, rotation.y, rotation.x);

            if (unforceChunk)
            {

                voidDimension.setChunkForced(0, 0, false);
            }
        }

        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }
}
