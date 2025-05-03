//package dev.detpikachu.buildersvoid.item;
//
//import dev.detpikachu.buildersvoid.logic.ComponentLogic;
//import dev.detpikachu.buildersvoid.logic.DimensionLogic;
//import dev.detpikachu.buildersvoid.logic.TeleportLogic;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.level.Level;
//
//import java.util.List;
//
//public class LinkedVoidPearl extends VoidPearl {
//    public LinkedVoidPearl(Properties properties) {
//        super(properties);
//    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//        final var tag = stack.getTag();
//
//        if (tag == null || !tag.contains("UUID")) {
//            tooltipComponents.add(Component.translatable("tooltip.buildersvoid.linked_void_pearl.usage.unbound"));
//            return;
//        }
//
//        tooltipComponents.add(ComponentLogic.formatted("tooltip.buildersvoid.linked_void_pearl.usage.bound", tag.getString("User")));
//    }
//
//    @Override
//    public boolean isFoil(ItemStack stack) {
//        final var tag = stack.getTag();
//
//        if (tag == null) {
//            return false;
//        }
//
//        return tag.contains("UUID");
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
//        final var itemStack = player.getItemInHand(usedHand);
//        var tag = itemStack.getTag();
//
//        // If the player is crouching, we're attempting to bind the pearl to them
//        if (player.isCrouching()) {
//            // If it's already bound, do nothing
//            if (tag != null && tag.contains("UUID")) {
//                player.displayClientMessage(Component.translatable("message.buildersvoid.linked_void_pearl.already_bound"), true);
//                return InteractionResultHolder.pass(itemStack);
//            }
//
//            if (!level.isClientSide) {
//                tag = new CompoundTag();
//                tag.putString("User", player.getDisplayName().getString());
//                tag.putUUID("UUID", player.getUUID());
//                itemStack.setTag(tag);
//            }
//
//            player.displayClientMessage(Component.translatable("message.buildersvoid.linked_void_pearl.bound"), true);
//            return InteractionResultHolder.pass(itemStack);
//        }
//
//        // Make sure the pearl is bound, otherwise do nothing
//        if (tag == null || !tag.contains("UUID")) {
//            player.displayClientMessage(Component.translatable("message.buildersvoid.linked_void_pearl.unbound"), true);
//            return InteractionResultHolder.pass(itemStack);
//        }
//
//        // If the player is inside the void dimension, teleport them out
//        if (player.level().dimension().location().compareTo(DimensionLogic.DIMENSION_VOID) == 0) {
//            if (!level.isClientSide) {
//                TeleportLogic.teleportOutOfVoid((ServerPlayer) player);
//            }
//            addCooldown(player);
//            player.displayClientMessage(Component.translatable("message.buildersvoid.void_pearl.teleport_out"), true);
//            return InteractionResultHolder.pass(itemStack);
//        }
//
//        // Otherwise teleport them into the void dimension
//        if (!level.isClientSide) {
//            TeleportLogic.teleportIntoVoid((ServerPlayer) player, tag.getUUID("UUID"), false);
//        }
//        addCooldown(player);
//        player.displayClientMessage(ComponentLogic.formatted("message.buildersvoid.linked_void_pearl.teleport", tag.getString("User")), true);
//        return InteractionResultHolder.pass(itemStack);
//    }
//}
