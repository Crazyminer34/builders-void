package dev.detpikachu.buildersvoid.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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

        player.sendSystemMessage(Component.literal("Hello!"));
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }
}
