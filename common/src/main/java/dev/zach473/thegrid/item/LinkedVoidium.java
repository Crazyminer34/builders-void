package dev.zach473.thegrid.item;

import dev.zach473.thegrid.data.ModDataComponents;
import dev.zach473.thegrid.data.UserIdentifier;
import dev.zach473.thegrid.logic.ComponentLogic;
import dev.zach473.thegrid.logic.DimensionLogic;
import dev.zach473.thegrid.logic.TeleportLogic;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class LinkedVoidium extends Voidium {
    public LinkedVoidium(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        final var userIdentifier = stack.get(ModDataComponents.USER_IDENTIFIER);

        if (userIdentifier == null) {
            tooltipComponents.add(Component.translatable("tooltip.thegrid.linked_void_pearl.usage.unbound"));
            return;
        }

        tooltipComponents.add(ComponentLogic.formatted("tooltip.thegrid.linked_void_pearl.usage.bound", userIdentifier.name()));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        final var userIdentifier = stack.get(ModDataComponents.USER_IDENTIFIER);
        return userIdentifier != null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        final var itemStack = player.getItemInHand(usedHand);
        var userIdentifier = itemStack.get(ModDataComponents.USER_IDENTIFIER);

        // If the player is crouching, we're attempting to bind the pearl to them
        if (player.isCrouching()) {
            // If it's already bound, do nothing
            if (userIdentifier != null) {
                player.displayClientMessage(Component.translatable("message.thegrid.linked_void_pearl.already_bound"), true);
                return InteractionResultHolder.pass(itemStack);
            }

            if (!level.isClientSide) {
                userIdentifier = new UserIdentifier(player.getUUID(), player.getDisplayName().getString());
                itemStack.set(ModDataComponents.USER_IDENTIFIER, userIdentifier);
            }

            player.displayClientMessage(Component.translatable("message.thegrid.linked_void_pearl.bound"), true);
            return InteractionResultHolder.pass(itemStack);
        }

        // Make sure the pearl is bound, otherwise do nothing
        if (userIdentifier == null) {
            player.displayClientMessage(Component.translatable("message.thegrid.linked_void_pearl.unbound"), true);
            return InteractionResultHolder.pass(itemStack);
        }

        // If the player is inside the void dimension, teleport them out
        if (player.level().dimension().location().compareTo(DimensionLogic.DIMENSION_VOID) == 0) {
            if (!level.isClientSide) {
                TeleportLogic.teleportOutOfVoid((ServerPlayer) player);
            }
            addCooldown(player);
            player.displayClientMessage(Component.translatable("message.thegrid.void_pearl.teleport_out"), true);
            return InteractionResultHolder.pass(itemStack);
        }

        // Otherwise teleport them into the void dimension
        if (!level.isClientSide) {
            TeleportLogic.teleportIntoVoid((ServerPlayer) player, userIdentifier.uuid(), false);
        }
        addCooldown(player);
        player.displayClientMessage(ComponentLogic.formatted("message.thegrid.linked_void_pearl.teleport", userIdentifier.name()), true);
        return InteractionResultHolder.pass(itemStack);
    }
}
