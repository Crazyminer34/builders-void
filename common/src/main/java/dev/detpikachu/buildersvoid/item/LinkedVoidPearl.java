package dev.detpikachu.buildersvoid.item;

import dev.detpikachu.buildersvoid.logic.DimensionLogic;
import dev.detpikachu.buildersvoid.logic.TeleportLogic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LinkedVoidPearl extends VoidPearl {
    public LinkedVoidPearl(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        final var tag = stack.getTag();

        if (tag == null || !tag.contains("UUID")) {
            tooltipComponents.add(Component.translatable("tooltip.buildersvoid.linked_void_pearl.usage.unbound"));
            return;
        }

        final var format = Component.translatable("tooltip.buildersvoid.linked_void_pearl.usage.bound").getString();
        tooltipComponents.add(Component.literal(String.format(format, tag.getString("User"))));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        final var tag = stack.getTag();

        if (tag == null) {
            return false;
        }

        return tag.contains("UUID");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        final var itemStack = player.getItemInHand(usedHand);
        var tag = itemStack.getTag();

        // If the player is crouching, we're attempting to bind the Linked Void Pearl to them
        if (player.isCrouching()) {
            if (level.isClientSide) {
                return InteractionResultHolder.pass(itemStack);
            }

            // If it's already bound, do nothing
            if (tag != null && tag.contains("UUID")) {
                return InteractionResultHolder.pass(itemStack);
            }

            tag = new CompoundTag();
            tag.putString("User", player.getDisplayName().getString());
            tag.putUUID("UUID", player.getUUID());
            itemStack.setTag(tag);

            return InteractionResultHolder.pass(itemStack);
        }

        // Make sure the pearl is bound, otherwise do nothing
        if (tag == null || !tag.contains("UUID")) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (level.isClientSide) {
            addCooldown(player);
            return InteractionResultHolder.pass(itemStack);
        }

        if (level.dimension().location().compareTo(DimensionLogic.DIMENSION_VOID) == 0) {
            TeleportLogic.teleportOutOfVoid((ServerPlayer) player);
            addCooldown(player);
            return InteractionResultHolder.pass(itemStack);
        }

        TeleportLogic.teleportIntoVoid((ServerPlayer) player, tag.getUUID("UUID"), false);
        addCooldown(player);
        return InteractionResultHolder.pass(itemStack);
    }
}
