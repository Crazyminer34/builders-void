package dev.zach473.thegrid.item;

import dev.zach473.thegrid.ModConfig;
import dev.zach473.thegrid.logic.DimensionLogic;
import dev.zach473.thegrid.logic.TeleportLogic;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class VoidPearl extends Item {
    public VoidPearl(Properties properties) {
        super(properties
            .stacksTo(1)
            .fireResistant()
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.thegrid.void_pearl.usage"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        // If the player is inside the void dimension, teleport them out
        if (level.dimension().location().compareTo(DimensionLogic.DIMENSION_VOID) == 0) {
            if (!level.isClientSide) {
                TeleportLogic.teleportOutOfVoid((ServerPlayer) player);
            }
            addCooldown(player);
            player.displayClientMessage(Component.translatable("message.thegrid.void_pearl.teleport_out"), true);
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }

        // Otherwise teleport them into the void dimension
        if (!level.isClientSide) {
            TeleportLogic.teleportIntoVoid((ServerPlayer) player, player.getUUID(), true);
        }
        addCooldown(player);
        player.displayClientMessage(Component.translatable("message.thegrid.void_pearl.teleport_in"), true);
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    protected void addCooldown(Player player) {
        final var config = ModConfig.getActive();

        if (!config.enableCooldown) {
            return;
        }

        player.getCooldowns().addCooldown(this, config.cooldown);
    }
}
