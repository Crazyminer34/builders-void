package dev.detpikachu.buildersvoid.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains("UUID")) {
            tooltipComponents.add(Component.translatable("tooltip.buildersvoid.linked_void_pearl.usage.unbound"));
            return;
        }

        String format = Component.translatable("tooltip.buildersvoid.linked_void_pearl.usage.bound").getString();
        tooltipComponents.add(Component.literal(String.format(format, tag.getString("User"))));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        CompoundTag tag = stack.getTag();

        if (tag == null) {
            return false;
        }

        return tag.contains("UUID");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        CompoundTag tag = itemStack.getTag();

        if (player.isCrouching()) {
            // If the player is crouching, we're attempting to bind the Linked Void Pearl to them
            if (level.isClientSide) {
                return InteractionResultHolder.pass(itemStack);
            }

            if (tag != null && tag.contains("UUID")) {
                // If it's already bound, do nothing
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
        
        return super.use(level, player, usedHand);
    }
}
