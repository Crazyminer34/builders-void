package dev.detpikachu.buildersvoid.item;

import net.minecraft.world.item.Item;

public class VoidPearl extends Item {

    public VoidPearl(Properties properties) {

        super(properties
            .stacksTo(1)
            .fireResistant()
        );
    }
}
