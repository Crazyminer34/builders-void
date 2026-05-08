package dev.zach473.thegrid.data;

import net.blay09.mods.balm.api.component.BalmComponents;
import net.minecraft.core.component.DataComponentType;

import static dev.zach473.thegrid.ModConstants.id;

public class ModDataComponents {
    public static DataComponentType<UserIdentifier> USER_IDENTIFIER;

    public static void initialize(BalmComponents components) {
        components.registerComponent(() -> USER_IDENTIFIER = DataComponentType.<UserIdentifier>builder().
                persistent(UserIdentifier.CODEC).
                networkSynchronized(UserIdentifier.STREAM_CODEC).
                build(),
            id("user_identifier"));
    }
}
